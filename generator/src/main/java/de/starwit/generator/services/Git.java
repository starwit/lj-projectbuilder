package de.starwit.generator.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.starwit.persistence.exception.NotificationException;

/** Class for running git commands. */
public class Git {

	private final static Logger LOG = LoggerFactory.getLogger(Git.class);

	public static void gitInit(Path directory) throws NotificationException {
		runCommand(directory, "git", "init");
	}

	public static void gitStage(Path directory) throws NotificationException {
		runCommand(directory, "git", "add", "-A");
	}

	public static void gitCommit(Path directory, String message) throws NotificationException {
		runCommand(directory, "git", "commit", "-m", message);
	}

	public static void gitPush(Path directory) throws NotificationException {
		runCommand(directory, "git", "push");
	}

	public static void gitClone(Path directory, String originUrl) throws NotificationException {
		runCommand(directory, "git", "clone", originUrl, ".");
	}

	public static void gitClone(Path directory, String originUrl, String branch)
			throws NotificationException {
		runCommand(directory, "git", "clone", "-b", branch, originUrl, ".");
	}

	public static void runCommand(Path directory, String... command)
			throws NotificationException {
		Objects.requireNonNull(directory, "directory");
		if (!Files.exists(directory)) {
			throw new NotificationException("error.git.directorynotexists", "can't run command in non-existing directory '" + directory + "'");
		}
		ProcessBuilder pb = new ProcessBuilder()
				.command(command)
				.directory(directory.toFile());
		try {
			Process p = pb.start();
			int exitVal = p.waitFor();

			if (exitVal == 0) {
				LOG.info("git commad SUCCESS in directory {}", directory.toFile());
			} else {
				final BufferedReader b = new BufferedReader(new InputStreamReader(p.getErrorStream()));
				String line = b.readLine();
				String output = "";
				while (line != null) {
					output = output + line + " ";
					line = b.readLine();
				}
				if (output.contains("403")) {
					throw new NotificationException("error.git.access.denied", output);
				} else if (output.contains("use a personal access token")) {
					throw new NotificationException("error.git.access.tokenusagerequired", output);
				} else if (output.contains("Invalid username or password")) {
					throw new NotificationException("error.git.access.invaliduserpassword", output);
				} else if (output.contains("Repository not found")) {
					throw new NotificationException("error.git.access.repositorynotfound", output);
				}
				p.destroy();
				throw new NotificationException("error.git.exit", "Git clone runs into error.");
			}
		} catch (final InterruptedException ex ) {
			Log.warn("interrupted", ex);
			// Restore interrupted state...
			Thread.currentThread().interrupt();
			throw new NotificationException("error.git.exit", "Git clone runs into error.");
		} catch (final IOException ex) {
			throw new NotificationException("error.git.exit", "Git clone runs into error.");
		}
	}
}