package de.starwit.generator.services;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import de.starwit.dto.GitAuthDto;
import de.starwit.generator.config.Constants;
import de.starwit.persistence.entity.AppTemplate;
import de.starwit.persistence.exception.NotificationException;
import de.starwit.service.impl.AppTemplateService;

@Service
public class AppCheckout {

	final static Logger LOG = LoggerFactory.getLogger(AppCheckout.class);

	@Autowired
	private AppTemplateService appTemplateService;

	public String createTempAppDirectory(final String tempDirectoryName) throws NotificationException {
		try {
			Path destDir = null;
			destDir = Files.createTempDirectory(Constants.LJ_PREFIX + tempDirectoryName);
			return destDir.getFileName().toString();
		} catch (final IOException e) {
			LOG.error("Error creating temporary folder for app", e);
			throw new NotificationException("error.appcheckout.createtempappfolder",
					"Error creating temporary folder for app");
		}
	}

	public void deleteTempURLApp(final String oldDestDirUrl) {
		final File oldDestDir = new File(oldDestDirUrl);
		deleteTempApp(oldDestDir);
	}

	private void deleteTempApp(final File oldDestDir) {
		if (!oldDestDir.exists()) {
			return;
		}
		final Path oldDestDirPath = oldDestDir.toPath();
		try {
			Files.walkFileTree(oldDestDirPath, new DeleteFileVisitor());
		} catch (final IOException e) {
			LOG.error("Error deleting temporary folder for app", e);
		}
	}

	/**
	 * Deletes all temp files created during git clone / checkout.
	 */
	public void findFilesAndDelete() {
		File oldDestDir = new File(Constants.TMP_DIR);
		final File[] files = oldDestDir
				.listFiles((File pathname) -> pathname.getName().startsWith(Constants.LJ_PREFIX));
		for (File file : files) {
			long diff = new Date().getTime() - file.lastModified();
			if (diff > Constants.MILLISECONDS_UNTIL_DELETION) {
				deleteTempApp(file);
			}
		}
	}

	private class DeleteFileVisitor extends SimpleFileVisitor<Path> {
		@Override
		public FileVisitResult visitFile(final Path file, final BasicFileAttributes attributes) throws IOException {
			final File file1 = new File(file.toUri());
			file1.setWritable(true);
			Files.deleteIfExists(file);
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(final Path dir, final IOException exc) throws IOException {
			Files.deleteIfExists(dir);
			return FileVisitResult.CONTINUE;
		}
	}

	/**
	 * Copies the app template and tomee to an new app location.
	 * 
	 * @param entity
	 * @return
	 * @throws NotificationException
	 */
	public AppTemplate checkoutAndUpdateAppTemplate(final GitAuthDto dto, final String targetDownloadPath)
			throws NotificationException {
		final AppTemplate appTemplate = appTemplateService.findById(dto.getAppTemplateId());
		String destDirString = Constants.TMP_DIR + Constants.FILE_SEP + targetDownloadPath;
		final File destDir = new File(destDirString);
		String srcDir = appTemplate.getLocation();
		String branch = Constants.DEFAULT_BRANCH;
		if (appTemplate.getBranch() != null) {
			branch = appTemplate.getBranch();
		}

		if (!srcDir.matches(Constants.GIT_URL_REGEX)) {
			LOG.info("Error copying files for app template. The given git url is not valid.");
			throw new NotificationException("error.appcheckout.giturlisinvalid", "The given git url is not valid.");
		} else if (!appTemplate.getBranch().matches(Constants.GIT_BRANCH_REGEX)) {
			LOG.info("Invalid branch name for git clone");
			throw new NotificationException("error.appcheckout.branchisinvalid", "Invalid branch name for git clone.");
		}

		if (appTemplate.isCredentialsRequired()) {
			if (!dto.getUsername().matches(Constants.GIT_USER_REGEX)) {
				LOG.info("Invalid username for git clone");
				throw new NotificationException("error.appcheckout.usernameisinvalid", "Invalid username for git clone.");
			} else if (!dto.getPassword().matches(Constants.GIT_PASSWORD_REGEX)) {
				LOG.info("Invalid password for git clone");
				throw new NotificationException("error.appcheckout.passworisinvalid", "Invalid password for git clone.");
			}
			dto.setPass(HtmlUtils.htmlEscape(dto.getPassword()));
			srcDir = srcDir.replaceAll("://", "://" + dto.getUsername() + ":" + dto.getPass() + "@");
		}

		try {
			URL srcUrl = new URL(srcDir);
			Git.gitClone(destDir.toPath(), srcUrl, branch);
			return saveTemplateFile(appTemplate, destDir.getAbsolutePath());
		} catch (MalformedURLException ex) {
			this.deleteTempURLApp(Constants.TMP_DIR + Constants.FILE_SEP + destDirString);
			throw new NotificationException("error.appcheckout.giturlisinvalid", "The given git url is not valid.");

		} catch (NotificationException e) {
			this.deleteTempURLApp(Constants.TMP_DIR + Constants.FILE_SEP + destDirString);
			throw e;
		}
	}

	protected AppTemplate saveTemplateFile(AppTemplate template, String newAppFolder) throws NotificationException {
		try {
			// create object mapper instance
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			// convert JSON string to Book object
			AppTemplate appTemplate = mapper.readValue(
					Paths.get(newAppFolder + Constants.FILE_SEP + Constants.TEMPLATE_CONFIG).toFile(),
					AppTemplate.class);

			appTemplate.setId(template.getId());
			appTemplate.setLocation(template.getLocation());
			appTemplate.setDescription(template.getDescription());
			appTemplate.setBranch(template.getBranch());
			appTemplate.setCredentialsRequired(template.isCredentialsRequired());
			appTemplate.setGroups(template.getGroups());

			return appTemplateService.updateFromRepo(appTemplate);
		} catch (Exception ex) {
			LOG.error("JSON mapping of code template failed.", ex);
			throw new NotificationException("error.appcheckout.jsonmapping.git", ex.getMessage());
		}
	}
}
