package de.starwit.generator.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import de.starwit.persistence.entity.ProjectEntity;
import de.starwit.persistence.exception.NotificationException;
import de.starwit.persistence.response.ResponseCode;
import de.starwit.persistence.response.ResponseMetadata;
import de.starwit.generator.config.Constants;


@Component("ProjectRenamer")
public class ProjectRenamer {
	
	public final static String[] EXT = new String[] { "java", "js", "html", "sql","xml","md","MD","log","yml" };
	final static Logger LOG = LoggerFactory.getLogger(ProjectRenamer.class);
	
	/**
	 * This is used for renaming the whole project. Renames all occurrences of the project name with a new project name.
	 * @param properties
	 */
	public void renameProjectTitle(ProjectEntity entity) throws NotificationException {
		LOG.info("Try to rename project " + entity.getTemplate().getPackagePrefix() + ".");

		File parentdirectory;
		parentdirectory = new File(Constants.TMP_DIR + Constants.FILE_SEP + entity.getTargetPath());
		String currentProjectName = entity.getTemplate().getTitle();
		renameDirectories(currentProjectName, entity.getTitle(), parentdirectory, false);
		renameFiles(currentProjectName, entity.getTitle(), parentdirectory);
	}
	
	/**
	 * This is used for renaming a package structure.
	 * @param properties
	 */
	public void renamePackage(ProjectEntity entity) throws NotificationException {
		LOG.info("Try to rename package " + entity.getTitle() + ".");

		File parentdirectory;
		parentdirectory = new File(Constants.TMP_DIR + Constants.FILE_SEP + entity.getTargetPath());
		renameDirectories(entity.getTemplate().getPackagePrefix(), entity.getPackagePrefix(), parentdirectory, true);
		renameFiles(entity.getTemplate().getPackagePrefix(), entity.getPackagePrefix(), parentdirectory);
	}

	/**
	 * Renames all directories named like the project to the new project name.
	 * @param oldProjectName - current project name
	 * @param newProjectName new project name
	 * @param currentdirectory - current directory
	 */
	private void renameDirectories(String oldProjectName, String newProjectName, File currentdirectory, boolean toLowerCase) throws NotificationException {
		File[] childfiles = currentdirectory.listFiles(new FilenameFilter() {
			public boolean accept(File childfiles, String name) {
				return new File(childfiles, name).isDirectory();
			}
		});
		if (childfiles == null) {
			return;
		}
		for (File childdirectory : childfiles) {
			LOG.info("FileName: " + childdirectory.getAbsolutePath());
			try {
				if (oldProjectName.equals(childdirectory.getName())) {
					String path = childdirectory.getParent() + Constants.FILE_SEP + newProjectName;
					if (toLowerCase) {
						path = childdirectory.getParent() + Constants.FILE_SEP + newProjectName.toLowerCase();
					}
					File renamedChildDirectory = new File(path);
					Files.move(childdirectory.toPath(), renamedChildDirectory.toPath());
					renameDirectories(oldProjectName, newProjectName, renamedChildDirectory, true);
				} else {
					renameDirectories(oldProjectName, newProjectName, childdirectory, true);
				}
			} catch (IOException e) {
				LOG.error("Problems moving file with name " + childdirectory.getName(), e);
				ResponseMetadata data = new ResponseMetadata(ResponseCode.ERROR, "error.projectrenamer.renamedirectories");
				throw new NotificationException(data);
			}
		}
	}

	/**
	 * Renames all files which was named like the project.
	 * @param oldProjectName - current project name
	 * @param newProjectName - new project name
	 * @param directory current directory
	 */
	private void renameFiles(String oldProjectName, String newProjectName, File directory) throws NotificationException {
		@SuppressWarnings("unchecked")
		Collection<File> files = FileUtils.listFiles(directory, EXT, true);
		for (File file : files) {
			LOG.info("FileName: " + file.getAbsolutePath());
			renameFileContent(oldProjectName, newProjectName, file);
		}
	}

	/**
	 * Renames all occurences of the project name in the file.
	 * @param oldProjectName - current project name
	 * @param newProjectName - new project name
	 * @param fileIn
	 * @throws IOException
	 */
	private void renameFileContent(String oldProjectName, String newProjectName, File fileIn) throws NotificationException {
		Path filePath = fileIn.toPath();
		File old = null;
		File fileOut = null;
		BufferedReader reader = null;
		PrintWriter writer = null;
		String filename = fileIn.getName();

		try {

			boolean toLowerCase = true;
		    old = new File(filePath.getParent() + Constants.FILE_SEP + "OLD_" + filename);
		    old.createNewFile();
		    FileUtils.copyFile(filePath.toFile(), old);
		    FileUtils.forceDelete(filePath.toFile());
			fileOut = Files.createFile(filePath).toFile();

			reader = new BufferedReader(new FileReader(old));
			 writer = new PrintWriter(new FileWriter(fileOut));
			String line = null;
			boolean lineToLowerCase;
			while ((line = reader.readLine()) != null) {
				
				lineToLowerCase = toLowerCase;
				if (line.contains("appBase=")
						|| line.contains("<webappDir>") 
						|| line.contains("<artifactId>")
						|| line.contains("name=\"app_path\"")) {
					lineToLowerCase  = false;
				}
				
				if (lineToLowerCase) {
					writer.println(line.replaceAll(oldProjectName, newProjectName.toLowerCase()));
				} else {
					writer.println(line.replaceAll(oldProjectName, newProjectName));
				}
			}
			
		} catch (IOException e) {
			LOG.error("Error processing file with name " + newProjectName, e);
			ResponseMetadata data = new ResponseMetadata(ResponseCode.ERROR, "error.projectrenamer.renamefilecontent");
			throw new NotificationException(data);
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
				if (writer != null) {
					writer.close();
				}
				if (old != null && old.exists()) {
					old.delete();
				}
			} catch(IOException e) {
				LOG.error("Error closing reader and writer", e);
			}
		}
	}

}
