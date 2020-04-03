package de.starwit.generator.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

/** Class for running git commands. */
public class Git {

  final static Logger LOG = LoggerFactory.getLogger(Git.class);

	public static void gitInit(Path directory) throws IOException, InterruptedException {
		runCommand(directory, "git", "init");
	}

	public static void gitStage(Path directory) throws IOException, InterruptedException {
		runCommand(directory, "git", "add", "-A");
	}

	public static void gitCommit(Path directory, String message) throws IOException, InterruptedException {
		runCommand(directory, "git", "commit", "-m", message);
	}

	public static void gitPush(Path directory) throws IOException, InterruptedException {
		runCommand(directory, "git", "push");
	}

	public static void gitClone(Path directory, String originUrl) throws IOException, InterruptedException {
		runCommand(directory, "git", "clone", originUrl, ".");
    }
    
    public static void gitClone(Path directory, String originUrl, String branch) throws IOException, InterruptedException {
		runCommand(directory, "git", "clone", "-b", branch, originUrl, ".");
	}

	public static void runCommand(Path directory, String... command) throws IOException, InterruptedException {
		Objects.requireNonNull(directory, "directory");
		if (!Files.exists(directory)) {
			throw new RuntimeException("can't run command in non-existing directory '" + directory + "'");
		}
		ProcessBuilder pb = new ProcessBuilder()
				.command(command)
				.directory(directory.toFile());
		Process p = pb.start();
		int exitVal = p.waitFor();

		if (exitVal == 0) {
			LOG.info("git commad SUCCESS in directory " + directory.toFile().toString());
		} else {
			p.destroy();
			throw new RuntimeException("Process ends with errors.");
		}
	}
}