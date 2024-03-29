package de.starwit.generator.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import de.starwit.generator.config.Constants;
import de.starwit.persistence.entity.AppTemplate;
import de.starwit.persistence.entity.TemplateFile;
import de.starwit.persistence.exception.NotificationException;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AppCheckoutTest {

    @Autowired
    private AppCheckout appCheckout;

    protected StartupShutdownService startup = new StartupShutdownService();

    final static Logger LOG = LoggerFactory.getLogger(AppCheckoutTest.class);

    @Test
    public void cloneGitRepoWithoutAuthTest() throws NotificationException, IOException, InterruptedException {
        final Path destDir = this.createDirectory(Constants.TMP_DIR + Constants.FILE_SEP + "tmplirejarp").toPath();
        LOG.info("Path is " + destDir.toString());
        Git.gitClone(destDir, new URL("https://github.com/starwit/lirejarp.git"), "master");

        String[] dirContent = destDir.toFile().list();
        assertTrue((dirContent != null && dirContent.length > 0), "Cloning repository results in an empty directory.");
        appCheckout.deleteTempURLApp(Constants.TMP_DIR + Constants.FILE_SEP + "tmplirejarp");
    }

    private File createDirectory(final String location) {
        final File file = new File(location);
        if (file.exists()) {
            appCheckout.deleteTempURLApp(location);
        }
        final boolean iscreated = file.mkdir();
        assertTrue(iscreated);
        return file;
    }

    @Test
    public void canLoadTemplateProperties() throws NotificationException, IOException, InterruptedException {
        final Path destDir = this.createDirectory(Constants.TMP_DIR + Constants.FILE_SEP + "tmplirejarp").toPath();
        LOG.info("Path is " + destDir.toString());

        AppTemplate template = new AppTemplate();
        template.setLocation("https://github.com/starwit/project-templates.git");
        template.setBranch("v2");
        List<String> groups = new ArrayList<>();
        groups.add("public");
        template.setGroups(groups);

        Git.gitClone(destDir, new URL(template.getLocation()), template.getBranch());

        template = appCheckout.saveTemplateFile(template, destDir.toString());
        assertEquals("lirejarp", template.getTemplateName());
        assertEquals("xyz", template.getPackagePlaceholder());
        assertNotNull(template.getTemplateFiles());
        boolean containsFilename = false;
        for (TemplateFile templateFile : template.getTemplateFiles()) {
            if (templateFile.getFileName().equals("${domain.name}Entity.java")) {
                containsFilename = true;
                break;
            }
        }
        assertTrue(containsFilename);

        String[] dirContent = destDir.toFile().list();
        assertTrue((dirContent != null && dirContent.length > 0), "Cloning repository results in an empty directory.");
        appCheckout.deleteTempURLApp(Constants.TMP_DIR + Constants.FILE_SEP + "tmplirejarp");
    }
}