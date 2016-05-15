package logic;

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
import org.apache.log4j.Logger;

import frontend.beans.ProjectSetupBean;

public class ProjectSetupService {

	public final static String[] EXT = new String[] { "java", "js", "html", "sql","xml" };
	final static Logger LOG = Logger.getLogger(ProjectSetupService.class);
	
	/**
	 * Copies the project template and tomee to an new project location.
	 * @param properties
	 */
	public void copyProjectTemplate(ProjectSetupBean properties) {
		try {
			File srcDir = new File(properties.getProjectPath() + "/" + properties.getCurrentProjectName());
			File srcDirTomee = new File(properties.getProjectPath() + "/tomee");
			File destDir = new File(properties.getTargetPath() + "/" + properties.getCurrentProjectName());
			File destDirTomee = new File(properties.getTargetPath() + "/tomee");
			FileUtils.copyDirectory(srcDir, destDir);
			FileUtils.copyDirectory(srcDirTomee, destDirTomee);
		} catch (IOException e) {
			LOG.error("Error copying files for project template.", e);
		}
	}
	
	/**
	 * This is used for renaming the whole project. Renames all occurences of the project name with a new project name.
	 * @param properties
	 */
	public void renameAll(ProjectSetupBean properties) {
		LOG.info("Try to rename project " + properties.getNewProjectName() + ".");
		File parentdirectory = new File(properties.getTargetPath());
		renameDirectories(properties.getCurrentProjectName(), properties.getNewProjectName(), parentdirectory);
		renameFiles(properties.getCurrentProjectName(), properties.getNewProjectName(), parentdirectory);
	}

	/**
	 * Renames all directories named like the project to the new project name.
	 * @param oldProjectName - current project name
	 * @param newProjectName new project name
	 * @param currentdirectory - current directory
	 */
	private void renameDirectories(String oldProjectName, String newProjectName, File currentdirectory) {
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
					File renamedChildDirectory = new File(childdirectory.getParent() + "/" + newProjectName);
					Files.move(childdirectory.toPath(), renamedChildDirectory.toPath());
					renameDirectories(oldProjectName, newProjectName, renamedChildDirectory);
				} else {
					renameDirectories(oldProjectName, newProjectName, childdirectory);
				}
			} catch (IOException e) {
				LOG.error("Problems moving file with name " + childdirectory.getName());
				LOG.error(e.getMessage());
				renameDirectories(oldProjectName, newProjectName, childdirectory);
			}

		}
	}

	/**
	 * Renames all files which was named like the project.
	 * @param oldProjectName - current project name
	 * @param newProjectName - new project name
	 * @param directory current directory
	 */
	private void renameFiles(String oldProjectName, String newProjectName, File directory) {
		@SuppressWarnings("unchecked")
		Collection<File> files = FileUtils.listFiles(directory, EXT, true);
		for (File file : files) {
			LOG.info("FileName: " + file.getAbsolutePath());
			try {
				renameFileContent(oldProjectName, newProjectName, file);
			} catch (IOException e) {
				LOG.error("Problems rename file with name " + file.getName());
				LOG.error(e.getMessage());
			}
		}
	}

	/**
	 * Renames all occurences of the project name in the project name in the file.
	 * @param oldProjectName - current project name
	 * @param newProjectName - new project name
	 * @param fileIn
	 * @throws IOException
	 */
	private void renameFileContent(String oldProjectName, String newProjectName, File fileIn) throws IOException {
		Path filePath = fileIn.toPath();
		Path moved = Files.move(filePath, new File(fileIn.getName() + "_OLD").toPath());
		File fileOut = Files.createFile(filePath).toFile();
		File fileOld = moved.toFile();

		BufferedReader reader = new BufferedReader(new FileReader(fileOld));
		PrintWriter writer = new PrintWriter(new FileWriter(fileOut));

		try {
			String line = null;
			while ((line = reader.readLine()) != null)
				writer.println(line.replaceAll(oldProjectName, newProjectName));
		} catch (IOException e) {
			LOG.error("Error processing file with name " + fileIn.getName());
			LOG.error(e.getMessage());
		} finally {
			reader.close();
			writer.close();
			moved.toFile().delete();
		}

	}
}
