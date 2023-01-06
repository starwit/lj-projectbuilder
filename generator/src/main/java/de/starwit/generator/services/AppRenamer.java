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

import de.starwit.persistence.entity.App;
import de.starwit.persistence.exception.NotificationException;
import de.starwit.generator.config.Constants;

@Component("AppRenamer")
public class AppRenamer {

    public final static String[] EXT = new String[] { "java", "js", "html", "sql", "xml", "md", "MD", "log", "yml",
            "json", "conf", "sh", "yaml" };
    final static Logger LOG = LoggerFactory.getLogger(AppRenamer.class);

    /**
     * This is used for renaming the whole app. Renames all occurrences of the app
     * name with a new app name.
     *
     * @param properties
     */
    public void renameAppTitle(App entity) throws NotificationException {
        File parentdirectory;
        parentdirectory = new File(Constants.TMP_DIR + Constants.FILE_SEP + entity.getTargetPath());
        String currentAppName = entity.getTemplate().getTemplateName();
        renameDirectories(currentAppName, entity.getTitle(), parentdirectory, false);
        renameFiles(currentAppName, entity.getTitle(), parentdirectory);
    }

    /**
     * This is used for renaming a package structure.
     *
     * @param properties
     */
    public void renamePackage(App entity) throws NotificationException {
        File parentdirectory;
        parentdirectory = new File(Constants.TMP_DIR + Constants.FILE_SEP + entity.getTargetPath());
        renameDirectories(entity.getTemplate().getPackagePlaceholder(), entity.getPackagePrefix(), parentdirectory,
                true);
        renameFiles(entity.getTemplate().getPackagePlaceholder(), entity.getPackagePrefix(), parentdirectory);
    }

    /**
     * Renames all directories named like the app to the new app name.
     *
     * @param oldAppName       - current app name
     * @param newAppName       new app name
     * @param currentdirectory - current directory
     */
    private void renameDirectories(String oldAppName, String newAppName, File currentdirectory, boolean toLowerCase)
            throws NotificationException {
        File[] childfiles = currentdirectory.listFiles(new FilenameFilter() {
            public boolean accept(File childfiles, String name) {
                return new File(childfiles, name).isDirectory();
            }
        });
        if (childfiles == null) {
            return;
        }
        for (File childdirectory : childfiles) {
            try {
                if (oldAppName.equals(childdirectory.getName())) {
                    String path = childdirectory.getParent() + Constants.FILE_SEP + newAppName;
                    if (toLowerCase) {
                        path = childdirectory.getParent() + Constants.FILE_SEP + newAppName.toLowerCase();
                    }
                    File renamedChildDirectory = new File(path);
                    Files.move(childdirectory.toPath(), renamedChildDirectory.toPath());
                    renameDirectories(oldAppName, newAppName, renamedChildDirectory, true);
                } else {
                    renameDirectories(oldAppName, newAppName, childdirectory, true);
                }
            } catch (IOException e) {
                LOG.error("Problems moving file with name " + childdirectory.getName(), e);
                throw new NotificationException("error.apprenamer.renamedirectories",
                        "Problems moving file with name " + childdirectory.getName());
            }
        }
    }

    /**
     * Renames all files which was named like the app.
     *
     * @param oldAppName - current app name
     * @param newAppName - new app name
     * @param directory  current directory
     */
    private void renameFiles(String oldAppName, String newAppName, File directory) throws NotificationException {
        Collection<File> files = FileUtils.listFiles(directory, EXT, true);
        for (File file : files) {
            if (!("package.json").equals(file.getName())) {
                renameFileContent(oldAppName, newAppName, file);
            }
        }
    }

    /**
     * Renames all occurences of the app name in the file.
     *
     * @param oldAppName - current app name
     * @param newAppName - new app name
     * @param fileIn
     * @throws IOException
     */
    private void renameFileContent(String oldAppName, String newAppName, File fileIn) throws NotificationException {
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
                    lineToLowerCase = false;
                }

                if (lineToLowerCase) {
                    writer.println(line.replaceAll(oldAppName, newAppName.toLowerCase()));
                } else {
                    writer.println(line.replaceAll(oldAppName, newAppName));
                }
            }

        } catch (IOException e) {
            LOG.error("Error processing file with name " + newAppName, e);
            throw new NotificationException("error.apprenamer.renamefilecontent",
                    "Error processing file with name " + newAppName);
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
            } catch (IOException e) {
                LOG.error("Error closing reader and writer", e);
            }
        }
    }

}
