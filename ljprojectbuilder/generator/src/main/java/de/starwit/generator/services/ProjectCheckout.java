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
import org.eclipse.jgit.api.errors.GitAPIException;
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
		
		try {

			CloneCommand cloneCommand = Git.cloneRepository()
					.setURI(srcDir)
					.setDirectory(destDir)
					.setCloneAllBranches( true )
					.setBranch(branch);
			if (dto.getProject().getTemplate().isCredentialsRequired()) {
				cloneCommand = cloneCommand.setCredentialsProvider(new UsernamePasswordCredentialsProvider(dto.getUsername(), dto.getPassword()));
			}
			
			Git git = null;
			try {
				git = cloneCommand.call();
				git.checkout();
				git.close();
			} catch (GitAPIException e) {
				if (git != null) {
					git.close();
				}
				this.deleteTempProject(destDirString);
				LOG.error("Error copying files for project template.", e);
				ResponseMetadata data = new ResponseMetadata(ResponseCode.ERROR, "error.projectcheckout.checkoutprojecttemplate.default");
				throw new NotificationException(data);
			}
		} catch (Exception e) {
			this.deleteTempProject(destDirString);
			LOG.error("Error copying files for project template.", e);
			ResponseMetadata data = new ResponseMetadata(ResponseCode.ERROR, "error.projectcheckout.checkoutprojecttemplate.default");
			throw new NotificationException(data);
		}

	}
}
