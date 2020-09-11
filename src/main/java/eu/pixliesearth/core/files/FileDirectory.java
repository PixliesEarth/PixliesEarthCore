package eu.pixliesearth.core.files;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.craftbukkit.libs.org.apache.commons.io.FilenameUtils;

public class FileDirectory {
	public String DirectoryName;
	public String DirectoryPath;
	/**
	 * Initialises the class with directory data
	 * 
	 * @param DirectoryPath The path to the directory
	 * @param DirectoryName The directories name
	 */
	public FileDirectory(String DirectoryPath, String DirectoryName) {
		this.DirectoryName = DirectoryName;
		this.DirectoryPath = DirectoryPath;
		try {
			initiliseDirectory();
		} catch (Exception e) {
			System.err.println("Error while intilising the directory "+getDirectoryConstruct());
			e.printStackTrace();
		}
	}
	/**
	 * Initialises the class with directory data
	 * 
	 * @param DirectoryPath The path to the directory
	 */
	public FileDirectory(String DirectoryPath) {
		this.DirectoryName = "";
		this.DirectoryPath = DirectoryPath;
		try {
			initiliseDirectory();
		} catch (Exception e) {
			System.err.println("Error while intilising the directory "+getDirectoryConstruct());
			e.printStackTrace();
		}
	}
	/**
	 * Returns the directory path
	 * 
	 * @return The directory path
	 */
	public String getDirectoryPath() {
		return this.DirectoryPath;
	}
	/**
	 * Returns the directory path
	 * 
	 * @return The directory path
	 */
	public String getDirectoryName() {
		return this.DirectoryName;
	}
	
	public String getDirectoryConstruct() {
		return this.DirectoryPath+this.DirectoryName;
	}
	/**
	 * Returns true if the directory exists, false if it does not
	 * 
	 * @return Weather or not the directory exists
	 */
	public boolean doesDirectoryExist() {
		if (Files.exists(Paths.get(getDirectoryConstruct()))) return true; else return false;
	}
	/**
	 * Initialises the directory by creating it if it does not exist
	 * 
	 * @throws IOException
	 */
	private void initiliseDirectory() throws IOException {
		File directory = new File(getDirectoryConstruct());
	    if (!doesDirectoryExist()) directory.mkdirs();
	}
	/**
	 * Deletes the directory
	 * 
	 * @throws FileNotFoundException
	 */
	public void deleteDirectory() throws FileNotFoundException {
		if (!doesDirectoryExist()) throw new FileNotFoundException("The directory "+getDirectoryConstruct()+" does not exist!");
		new File(getDirectoryConstruct()).delete();
	}
	
	public Set<FileBase> getFilesInDirectory() {
		Set<FileBase> set = new HashSet<FileBase>();
		File dir = new File(getDirectoryConstruct());
		for (File f : dir.listFiles()) {
			String path = f.getAbsolutePath();
			set.add(new FileBase(FilenameUtils.getFullPath(path), FilenameUtils.getBaseName(path), "."+FilenameUtils.getExtension(path)));
		}
		return set;
	}
}