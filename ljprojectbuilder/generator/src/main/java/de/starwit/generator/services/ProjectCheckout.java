package de.starwit.generator.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Locale;

import javax.inject.Named;

import org.apache.log4j.Logger;

import de.starwit.generator.config.Constants;
import de.starwit.generator.dto.GeneratorDto;
import de.starwit.ljprojectbuilder.entity.ProjectEntity;
import de.starwit.ljprojectbuilder.exception.NotificationException;
import de.starwit.ljprojectbuilder.response.ResponseCode;
import de.starwit.ljprojectbuilder.response.ResponseMetadata;

@Named("ProjectCheckout")
public class ProjectCheckout {
	
	public final static Logger LOG = Logger.getLogger(ProjectCheckout.class);
	
	public String createTempProjectDirectory(ProjectEntity project) throws NotificationException {
		try {
			Path destDir = null;
			destDir = Files.createTempDirectory(Constants.LJ_PREFIX + project.getTitle());
			return destDir.getFileName().toString();
		} catch (IOException e) {
			LOG.error("Error creating temporary folder for project", e);
			ResponseMetadata data = new ResponseMetadata(ResponseCode.ERROR, "error.projectcheckout.createtempprojectfolder");
			throw new NotificationException(data);
		}
	}
	
	public void deleteTempProject(String oldDestDirUrl) {
			File oldDestDir = new File(oldDestDirUrl);
			if (!oldDestDir.exists()) {
				return;
			}
			Path oldDestDirPath = Paths.get(oldDestDirUrl);
			try {
				Files.walkFileTree(oldDestDirPath, new DeleteFileVisitor());
			} catch (IOException e) {
				LOG.error("Error deleting temporary folder for project", e);
			}
	}
	
	private class DeleteFileVisitor extends SimpleFileVisitor<Path> {
		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) throws IOException {
			File file1 = new File(file.toUri());
			file1.setWritable(true);
			Files.deleteIfExists(file);
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			Files.deleteIfExists(dir); // this will work because Files in the directory are already deleted
			return FileVisitResult.CONTINUE;
		}
	}
	
	/**
	 * Copies the project template and tomee to an new project location.
	 * @param entity
	 * @return 
	 * @throws NotificationException 
	 */
	@SuppressWarnings("unused")
	public void checkoutProjectTemplate(GeneratorDto dto)  throws NotificationException {
		ProjectEntity entity = dto.getProject();
		String destDirString = Constants.TMP_DIR + entity.getTargetPath();
		File destDir = new File (destDirString);
		String srcDir = entity.getTemplate().getLocation();
		String branch = Constants.DEFAULT_BRANCH;
		if (entity.getTemplate().getBranch() != null) {
			branch = entity.getTemplate().getBranch();
		}
		
		if (dto.getProject().getTemplate().isCredentialsRequired()) {
			dto.setPassword(dto.getPassword().replaceAll("@", "%40"));
			srcDir = srcDir.replaceAll("://", "://" + dto.getUsername() + ":" + dto.getPassword() + "@");
		}
		
		gitCloneCommand(srcDir, destDirString, branch);
	}

	public void gitCloneCommand(String srcDir, String destDirString, String branch) throws NotificationException {
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.directory(new File(destDirString));
		String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
		if (OS.indexOf("win") >= 0) {
			// Run a windows command
			processBuilder.command("cmd.exe", "/c", "git clone -b " + branch + " " + srcDir);
		} else {
			// Run a shell command
			processBuilder.command("bash", "-c", "git clone -b " + branch + " " + srcDir);
		}

		try {

			Process process = processBuilder.start();
			StringBuilder output = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String line;
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}

			int exitVal = process.waitFor();
			if (exitVal == 0) {
				LOG.info("git clone SUCCESS with directory " + srcDir);
			} else {
				process.destroy();
				this.deleteTempProject(destDirString);
				LOG.error("Error copying files for project template.");
				ResponseMetadata data = new ResponseMetadata(ResponseCode.ERROR,
						"error.projectcheckout.checkoutprojecttemplate.transport");
				throw new NotificationException(data);
			}

		} catch (IOException e) {
			this.deleteTempProject(destDirString);
			LOG.error("Error copying files for project template.", e);
			ResponseMetadata data = new ResponseMetadata(ResponseCode.ERROR,
					"error.projectcheckout.checkoutprojecttemplate.transport");
			throw new NotificationException(data);
		} catch (InterruptedException e) {
			this.deleteTempProject(destDirString);
			LOG.error("Error copying files for project template.", e);
			ResponseMetadata data = new ResponseMetadata(ResponseCode.ERROR,
					"error.projectcheckout.checkoutprojecttemplate.transport");
			throw new NotificationException(data);
		}

	}

}
