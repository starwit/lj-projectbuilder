 package de.starwit.generator.services;

 import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.starwit.generator.config.Constants;
import de.starwit.persistence.exception.NotificationException;



 public class AppCheckoutTest {

 	protected AppCheckout checkout = new AppCheckout();
 	protected StartupShutdownService startup = new StartupShutdownService();

   final static Logger LOG = LoggerFactory.getLogger(AppCheckoutTest.class);

 	@Test
 	public void cloneGitRepoWithoutAuthTest() throws NotificationException, IOException, InterruptedException {
 		final Path destDir = this.createDirectory(Constants.TMP_DIR + Constants.FILE_SEP + "tmplirejarp").toPath();
 		LOG.info("Path is " + destDir.toString());
 		Git.gitClone(destDir, "https://github.com/starwit/lirejarp.git", "master");

 		String[] dirContent = destDir.toFile().list();
 		assertTrue("Cloning repository results in an empty directory.", (dirContent != null && dirContent.length > 0));
 		checkout.deleteTempURLApp(Constants.TMP_DIR + Constants.FILE_SEP + "tmplirejarp");
 	}

 	private File createDirectory(final String location) {
 		final File file = new File(location);
 		if (file.exists()) {
 			checkout.deleteTempURLApp(location);
 		}
 		final boolean iscreated = file.mkdir();
 		assertTrue(iscreated);
 		return file;
 	}

 }