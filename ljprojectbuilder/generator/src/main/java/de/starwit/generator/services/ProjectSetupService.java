package de.starwit.generator.services;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.eclipse.jgit.api.Git;

import de.starwit.generator.config.Constants;
import de.starwit.generator.dto.GeneratorDto;
import de.starwit.generator.exeptions.ProjectSetupException;
import de.starwit.ljprojectbuilder.ejb.ProjectService;
import de.starwit.ljprojectbuilder.entity.ProjectEntity;
import de.starwit.ljprojectbuilder.response.ResponseCode;
import de.starwit.ljprojectbuilder.response.ResponseMetadata;
/**
 * Class for processeing the whole project setup. A newly configured project is created an can be used.
 * @author Anett Huebner
 *
 */
@Local
@Stateless(name = "ProjectSetupService")
public class ProjectSetupService implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ProjectService projectService;
	
	@Inject
	private GeneratorService generatorSerivce;
	
	public final static String[] EXT = new String[] { "java", "js", "html", "sql","xml", "md","log" };
	final static Logger LOG = Logger.getLogger(ProjectSetupService.class);
	
	/**
	 * Executes all functions needed to setup the new project. These are:
	 *  - checkout template-project from git-repository
	 *  - rename the project as configured in generatorDto.project
	 *  - rename the "starwit" package as configured in generatorDto.project
	 *  - generate CRUD-Operations for the several layers (Database access, REST API and frontend)
	 * @param dto - configuration for project setup
	 * @return
	 * @throws ProjectSetupException
	 */
	public void setupAndGenerateProject(GeneratorDto dto) throws ProjectSetupException {
		ProjectEntity entity = findProjectById(dto.getProject().getId());
		entity = createProjectFolder(entity);
		checkoutProjectTemplate(entity);
		renameProjectTitle(entity);
		renamePackage(entity);
		dto.setProject(entity);
		
		generatorSerivce.generate(dto);
	}

	/**
	 * Loads the project from database.
	 * @param projectid
	 * @return
	 * @throws ProjectSetupException
	 */
	private ProjectEntity findProjectById(Long projectid) throws ProjectSetupException {
		ProjectEntity entity = projectService.findById(projectid);
		if (entity == null) {
			LOG.error("Error setup project for generation. Project with id " + projectid + " could not be found.");
			ResponseMetadata data = new ResponseMetadata(ResponseCode.ERROR, "error.projectsetup.projectnotfound");
			throw new ProjectSetupException(data);
		}
		return entity;
	}
	
	private ProjectEntity createProjectFolder(ProjectEntity entity) throws ProjectSetupException {
		try {
			Path destDir = null;
			if (entity != null && entity.getTargetPath() != null) {
				File oldDestDir = new File(Constants.TMP_DIR + Constants.FILE_SEP + entity.getTargetPath());
				if (oldDestDir.exists()) {
					oldDestDir.delete();
				}
			}
			
			destDir = Files.createTempDirectory(Constants.LJ_PREFIX + entity.getTitle());
			entity.setTargetPath(destDir.getFileName().toString());
			entity = projectService.update(entity);
			return entity;
		} catch (IOException e) {
			LOG.error("Error creating temporary folder for project", e);
			ResponseMetadata data = new ResponseMetadata(ResponseCode.ERROR, "error.projectsetup.createprojectfolder");
			throw new ProjectSetupException(data);
		}
	}
	
	/**
	 * Copies the project template and tomee to an new project location.
	 * @param entity
	 * @return 
	 * @throws ProjectSetupException 
	 */
	private void checkoutProjectTemplate(ProjectEntity entity)  throws ProjectSetupException {
		
		File destDir = new File (Constants.TMP_DIR + Constants.FILE_SEP + entity.getTargetPath());
		String srcDir = entity.getTemplate().getTemplateLocation();
		String branch = "master";
		if (entity.getTemplate().getBranch() != null) {
			branch = entity.getTemplate().getBranch();
		}

		try {
			Git git = Git.cloneRepository()
					.setURI(srcDir)
					.setDirectory(destDir)
					.setCloneAllBranches( true )
					.setBranch(branch)
					.call();
			git.checkout();
			
		} catch (Exception e) {
			LOG.error("Error copying files for project template.", e);
			ResponseMetadata data = new ResponseMetadata(ResponseCode.ERROR, "error.project.copytemplate.templatenotfound");
			throw new ProjectSetupException(data);
		}
	}
	
	/**
	 * This is used for renaming the whole project. Renames all occurrences of the project name with a new project name.
	 * @param properties
	 */
	private void renameProjectTitle(ProjectEntity entity) throws ProjectSetupException {
		LOG.info("Try to rename project " + entity.getTemplate().getTemplatePackagePrefix() + ".");

		File parentdirectory;
		parentdirectory = new File(Constants.TMP_DIR + Constants.FILE_SEP + entity.getTargetPath());
		String currentProjectName = entity.getTemplate().getTemplateTitle();
		renameDirectories(currentProjectName, entity.getTitle(), parentdirectory, false);
		renameFiles(currentProjectName, entity.getTitle(), parentdirectory);
	}
	
	/**
	 * This is used for renaming a package structure.
	 * @param properties
	 */
	private void renamePackage(ProjectEntity entity) throws ProjectSetupException {
		LOG.info("Try to rename package " + entity.getTitle() + ".");

		File parentdirectory;
		parentdirectory = new File(Constants.TMP_DIR + Constants.FILE_SEP + entity.getTargetPath());
		renameDirectories(entity.getTemplate().getTemplatePackagePrefix(), entity.getPackagePrefix(), parentdirectory, true);
		renameFiles(entity.getTemplate().getTemplatePackagePrefix(), entity.getPackagePrefix(), parentdirectory);
	}

	/**
	 * Renames all directories named like the project to the new project name.
	 * @param oldProjectName - current project name
	 * @param newProjectName new project name
	 * @param currentdirectory - current directory
	 */
	private void renameDirectories(String oldProjectName, String newProjectName, File currentdirectory, boolean toLowerCase) throws ProjectSetupException {
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
				ResponseMetadata data = new ResponseMetadata(ResponseCode.ERROR, "error.projectsetup.renamedirectories");
				throw new ProjectSetupException(data);
			}
		}
	}

	/**
	 * Renames all files which was named like the project.
	 * @param oldProjectName - current project name
	 * @param newProjectName - new project name
	 * @param directory current directory
	 */
	private void renameFiles(String oldProjectName, String newProjectName, File directory) throws ProjectSetupException {
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
	private void renameFileContent(String oldProjectName, String newProjectName, File fileIn) throws ProjectSetupException {
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
			ResponseMetadata data = new ResponseMetadata(ResponseCode.ERROR, "error.projectsetup.renamefilecontent");
			throw new ProjectSetupException(data);
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