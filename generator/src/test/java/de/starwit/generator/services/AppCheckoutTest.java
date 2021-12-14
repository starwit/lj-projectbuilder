 package de.starwit.generator.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.starwit.generator.GeneratorApplication;
import de.starwit.generator.config.Constants;
import de.starwit.persistence.entity.AppTemplate;
import de.starwit.persistence.exception.NotificationException;

@SpringBootTest(classes = {GeneratorApplication.class})
@EnableAutoConfiguration
@AutoConfigureMockMvc(addFilters = false)
@RunWith(SpringRunner.class)
public class AppCheckoutTest {

	@Autowired
 	private AppCheckout appCheckout;

 	protected StartupShutdownService startup = new StartupShutdownService();

   final static Logger LOG = LoggerFactory.getLogger(AppCheckoutTest.class);

 	@Test
 	public void cloneGitRepoWithoutAuthTest() throws NotificationException, IOException, InterruptedException {
 		final Path destDir = this.createDirectory(Constants.TMP_DIR + Constants.FILE_SEP + "tmplirejarp").toPath();
 		LOG.info("Path is " + destDir.toString());
 		Git.gitClone(destDir, "https://github.com/starwit/lirejarp.git", "master");

 		String[] dirContent = destDir.toFile().list();
 		assertTrue("Cloning repository results in an empty directory.", (dirContent != null && dirContent.length > 0));
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
		 template.setLocation("https://github.com/starwit/lirejarp.git");
		 template.setBranch("master");

		 Git.gitClone(destDir, template.getLocation(), template.getBranch());
 
		 appCheckout.saveTemplateProperties(template, destDir.toString());
		 assertEquals("lirejarp", template.getTemplateName());
		 assertEquals("xyz", template.getPackagePlaceholder());
 
		 String[] dirContent = destDir.toFile().list();
		 assertTrue("Cloning repository results in an empty directory.", (dirContent != null && dirContent.length > 0));
		 appCheckout.deleteTempURLApp(Constants.TMP_DIR + Constants.FILE_SEP + "tmplirejarp");
	 }

 }