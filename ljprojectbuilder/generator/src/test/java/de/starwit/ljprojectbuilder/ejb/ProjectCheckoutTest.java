package de.starwit.ljprojectbuilder.ejb;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.apache.log4j.Logger;
import org.junit.Test;

import de.starwit.generator.config.Constants;
import de.starwit.generator.services.Git;
import de.starwit.generator.services.ProjectCheckout;
import de.starwit.generator.services.StartupShutdownService;
import de.starwit.ljprojectbuilder.exception.NotificationException;
import static org.junit.Assert.*;

public class ProjectCheckoutTest {

	protected ProjectCheckout checkout = new ProjectCheckout();
	protected StartupShutdownService startup = new StartupShutdownService();

	private final static Logger LOG =  Logger.getLogger("ProjectCheckoutTest");

	@Test
	public void cloneGitRepoWithoutAuthTest() throws NotificationException, IOException, InterruptedException {
		final Path destDir = this.createDirectory(Constants.TMP_DIR + Constants.FILE_SEP + "tmplirejarp").toPath();
		String dir = destDir.toString();
		LOG.info("Path is " + dir);
		Git.gitClone(destDir, "https://github.com/witchpou/lirejarp.git", "master");

		String[] dirContent = destDir.toFile().list();
		assertTrue("Cloning repository results in an empty directory.", (dirContent != null && dirContent.length > 0));
		checkout.deleteTempProject(dir);
	}

	private File createDirectory(final String location) {
		final File file = new File(location);
		if (file.exists()) {
			checkout.deleteTempProject(location);
			fail("Directory " + location + "should not exist.");
		}
		final boolean iscreated = file.mkdir();
		assertTrue(iscreated);
		return file;
	}

}