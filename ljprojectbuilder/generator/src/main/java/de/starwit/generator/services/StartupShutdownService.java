package de.starwit.generator.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class StartupShutdownService {
	final static Logger LOG = LoggerFactory.getLogger(StartupShutdownService.class);
 
    @PostConstruct
    private void startup() {
    	Locale.setDefault(Locale.GERMAN);
    	findFilesAndDelete();
    }
    
    @PreDestroy
    private void shutdown() {
    	findFilesAndDelete();
    }

	private void findFilesAndDelete() {
		try {
			File oldDestDir = new File(System.getProperty("java.io.tmpdir"));
			final File[] files = oldDestDir.listFiles((File pathname) -> pathname.getName().startsWith("LJ_"));
		    for (File file : files) {
	    		deleteTempProject(file);
			}
		} catch (IOException e) {
			LOG.error("Error deleting temporary folder for project", e);
		}
	}
    
	public void deleteTempProject(File oldDestDir) throws IOException {
		Path oldDestDirPath = Paths.get(oldDestDir.getPath());
		Files.walkFileTree(oldDestDirPath, new DeleteFileVisitor());
	}
	
	private class DeleteFileVisitor extends SimpleFileVisitor<Path> {
		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) throws IOException {
			File file1 = new File(file.toUri());
			file1.setWritable(true);
			Files.deleteIfExists(file);
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			Files.deleteIfExists(dir); // this will work because Files in the directory are already deleted
			return FileVisitResult.CONTINUE;
		}
	}
 }