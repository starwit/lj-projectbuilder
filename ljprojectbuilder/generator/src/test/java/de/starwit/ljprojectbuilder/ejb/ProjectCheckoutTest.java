package de.starwit.ljprojectbuilder.ejb;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.Test;

import de.starwit.generator.config.Constants;
import de.starwit.generator.services.ProjectCheckout;
import de.starwit.generator.services.StartupShutdownService;
import de.starwit.ljprojectbuilder.exception.NotificationException;

public class ProjectCheckoutTest {
	
	protected ProjectCheckout checkout = new ProjectCheckout();
	protected StartupShutdownService startup = new StartupShutdownService();
	
	@Test
	public void cloneGitRepoWithoutAuthTest() throws NotificationException, IOException  {
		Files.createDirectory(new File(Constants.TMP_DIR + "lirejarp").toPath());
		checkout.gitCloneCommand("https://github.com/witchpou/lirejarp.git", Constants.TMP_DIR + "lirejarp", "master");
		checkout.deleteTempProject(Constants.TMP_DIR + "lirejarp");
	}
	
	
	
	
}