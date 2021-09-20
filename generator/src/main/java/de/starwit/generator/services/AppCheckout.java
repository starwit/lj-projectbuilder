package de.starwit.generator.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import de.starwit.persistence.entity.App;
import de.starwit.persistence.exception.NotificationException;
import de.starwit.persistence.response.ResponseCode;
import de.starwit.persistence.response.ResponseMetadata;
import de.starwit.generator.config.Constants;
import de.starwit.generator.dto.GeneratorDto;

@Service
public class AppCheckout {

  final static Logger LOG = LoggerFactory.getLogger(AppCheckout.class);

	public String createTempAppDirectory(final App app) throws NotificationException {
		try {
			Path destDir = null;
			destDir = Files.createTempDirectory(Constants.LJ_PREFIX + app.getTitle());
			return destDir.getFileName().toString();
		} catch (final IOException e) {
			LOG.error("Error creating temporary folder for app", e);
			final ResponseMetadata data = new ResponseMetadata(ResponseCode.ERROR,
					"error.appcheckout.createtempappfolder");
			throw new NotificationException(data);
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
		final File[] files = oldDestDir.listFiles((File pathname) -> pathname	.getName().startsWith(Constants.LJ_PREFIX));
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
	public void checkoutAppTemplate(final GeneratorDto dto) throws NotificationException {
		final App entity = dto.getApp();
		String destDirString = Constants.TMP_DIR + Constants.FILE_SEP + entity.getTargetPath();
		final File destDir = new File(destDirString);
		String srcDir = entity.getTemplate().getLocation();
		String branch = Constants.DEFAULT_BRANCH;
		if (entity.getTemplate().getBranch() != null) {
			branch = entity.getTemplate().getBranch();
		}

		if (dto.getApp().getTemplate().isCredentialsRequired()) {
			dto.setPassword(dto.getPass().replaceAll("@", "%40"));
			srcDir = srcDir.replaceAll("://", "://" + dto.getUser() + ":" + dto.getPass() + "@");
			System.out.println(srcDir);
		}

		try {
			Git.gitClone(destDir.toPath(), srcDir, branch);
		} catch (IOException | InterruptedException e) {
			this.deleteTempURLApp(Constants.TMP_DIR + Constants.FILE_SEP + destDirString);
			LOG.error("Error copying files for app template.", e);
			final ResponseMetadata data = new ResponseMetadata(ResponseCode.ERROR,
					"error.appcheckout.checkoutapptemplate.transport");
			throw new NotificationException(data);
		} catch (RuntimeException e) {
			this.deleteTempURLApp(Constants.TMP_DIR + Constants.FILE_SEP + destDirString);
			LOG.error("Error copying files for app template.", e);
			final ResponseMetadata data = new ResponseMetadata(ResponseCode.ERROR,
					"error.appcheckout.checkoutapptemplate.git");
			throw new NotificationException(data);
		}
	}
}
