package de.starwit.generator.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import javax.inject.Named;

import org.apache.log4j.Logger;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import de.starwit.generator.config.Constants;
import de.starwit.generator.dto.GeneratorDto;
import de.starwit.ljprojectbuilder.entity.ProjectEntity;
import de.starwit.ljprojectbuilder.exception.NotificationException;
import de.starwit.ljprojectbuilder.response.ResponseCode;
import de.starwit.ljprojectbuilder.response.ResponseMetadata;

@Named("ProjectCheckout")
public class ProjectCheckout {
	
	public final static Logger LOG = Logger.getLogger(ProjectCheckout.class);
	
	public String createNewProjectDirectory(ProjectEntity project) throws NotificationException {
		try {
			Path destDir = null;
			destDir = Files.createTempDirectory(Constants.LJ_PREFIX + project.getTitle());
			return destDir.getFileName().toString();
		} catch (IOException e) {
			LOG.error("Error creating temporary folder for project", e);
			ResponseMetadata data = new ResponseMetadata(ResponseCode.ERROR, "error.projectsetup.createprojectfolder");
			throw new NotificationException(data);
		}
	}
	
	public void deleteOldProject(String oldDestDirUrl) throws NotificationException {
			File oldDestDir = new File(oldDestDirUrl);
			if (!oldDestDir.exists()) {
				return;
			}
			Path oldDestDirPath = Paths.get(oldDestDirUrl);
			try {
				Files.walkFileTree(oldDestDirPath, new DeleteFileVisitor());
			} catch (IOException e) {
				LOG.error("Error deleting temporary folder for project", e);
				ResponseMetadata data = new ResponseMetadata(ResponseCode.ERROR, "error.projectsetup.deleteprojectfolder");
				throw new NotificationException(data);
			}
	}
	
	private class DeleteFileVisitor extends SimpleFileVisitor<Path> {
		@Override
		   public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) throws IOException {
		       Files.deleteIfExists(file); // this will work because it's always a File
		       return FileVisitResult.CONTINUE;
		   }

		   @Override
		   public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
		       Files.deleteIfExists(dir); //this will work because Files in the directory are already deleted
		       return FileVisitResult.CONTINUE;
		   }
	}
	
	/**
	 * Copies the project template and tomee to an new project location.
	 * @param entity
	 * @return 
	 * @throws NotificationException 
	 */
	public void checkoutProjectTemplate(GeneratorDto dto)  throws NotificationException {
		ProjectEntity entity = dto.getProject();
		File destDir = new File (Constants.TMP_DIR + Constants.FILE_SEP + entity.getTargetPath());
		String srcDir = entity.getTemplate().getLocation();
		String branch = "master";
		if (entity.getTemplate().getBranch() != null) {
			branch = entity.getTemplate().getBranch();
		}

		try {
			CloneCommand cloneCommand = Git.cloneRepository()
					.setURI(srcDir)
					.setDirectory(destDir)
					.setCloneAllBranches( true )
					.setBranch(branch);
			if (dto.getUsername() != null) {
				cloneCommand = cloneCommand.setCredentialsProvider(new UsernamePasswordCredentialsProvider(dto.getUsername(), dto.getPassword()));
			}
			Git git = cloneCommand.call();
			git.checkout();
			
		} catch (Exception e) {
			LOG.error("Error copying files for project template.", e);
			ResponseMetadata data = new ResponseMetadata(ResponseCode.ERROR, "error.project.copytemplate.templatenotfound");
			throw new NotificationException(data);
		}
	}
}
