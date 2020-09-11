package eu.pixliesearth.core.files;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class FileBase {
	public String FileName;
	public String FilePath;
	public String FileExtension;
	/**
	 * Initialises the class with file data, when used setupFile(FilePath, FileName, FileExtension) is not required to be called
	 * 
	 * @param FilePath The path to the file
	 * @param FileName The files name
	 * @param FileExtension The files extension
	 */
	public FileBase(String FilePath, String FileName, String FileExtension) {
		this.FileExtension = FileExtension;
		this.FileName = FileName;
		this.FilePath = FilePath;
		try {
			initiliseFile();
		} catch (Exception e) {
			System.err.println("Error while intilising the file "+getFileConstruct());
			e.printStackTrace();
		}
	}
	/**
	 * Initialises the class without any file data, when used setupFile(FilePath, FileName, FileExtension) must be called to initialise properly
	 */
	public FileBase() {
		this.FileExtension = null;
		this.FileName = null;
		this.FilePath = null;
	}
	/**
	 * Sets the file data
	 * 
	 * @param FilePath The path to the file
	 * @param FileName The files name
	 * @param FileExtension The files extension
	 */
	public void setupFile(String FilePath, String FileName, String FileExtension) {
		this.FileExtension = FileExtension+".temp";
		this.FileName = FileName;
		this.FilePath = FilePath;
		try {
			initiliseFile();
		} catch (Exception e) {
			System.err.println("Error while intilising the file "+getFileConstruct());
			e.printStackTrace();
		}
	}
	/**
	 * Returns the files name
	 * 
	 * @return The files name
	 */
	public String getFileName() {
		return this.FileName;
	}
	/**
	 * Returns the files path
	 * 
	 * @return The files path
	 */
	public String getFilePath() {
		return this.FilePath;
	}
	/**
	 * Returns the files extension
	 * 
	 * @return The files extension
	 */
	public String getFileExtension() {
		return this.FileExtension;
	}
	/**
	 * Returns the files location, useful for manipulating the file
	 * 
	 * @return The files exact location
	 */
	public String getFileConstruct() {
		return this.FilePath+this.FileName+this.FileExtension;
	}
	/**
	 * Returns the exact file, useful for manipulating the file
	 * 
	 * @return The file as an object
	 */
	public File getFile() {
		return new File(getFileConstruct());
	}
	/**
	 * Returns true if the file exists, false if it does not
	 * 
	 * @return Weather or not the file exists
	 */
	public boolean doesFileExist() {
		return Files.exists(Paths.get(getFileConstruct()));
	}
	/**
	 * Returns the file save structure, For example "KEY=DATA" would mean that the data is stored as a key and data with an equals sign to separate them
	 * 
	 * @return Null
	 */
	public String getFileStructure() {
		return null;
	}
	/**
	 * Initialises the file by creating it if it does not exist
	 * 
	 * @throws IOException
	 */
	private void initiliseFile() throws IOException {
		if (!doesFileExist()) {
			try{
				PrintWriter writer = new PrintWriter(getFileConstruct(), "UTF-8");
				writer.print("");
				writer.close();
			} catch (IOException e) {
				throw new IOException(e);
			}
		} else {
			//already exists so do nothing
		}
	}
	/**
	 * Clears a files data/Wipes a file
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void clearFile() throws IOException {
		Writer output = new BufferedWriter(new FileWriter(getFileConstruct(), false));
		output.write("");
		output.close();
	}
	/**
	 * Returns the file as an array
	 * 
	 * @return An array containing the files lines
	 * @throws FileNotFoundException
	 */
	public ArrayList<String> loadFileIntoArray() throws FileNotFoundException {
	    Scanner reader = new Scanner(getFile());
	    ArrayList<String> result = new ArrayList<>();
	    while (reader.hasNext()) {
	    	result.add(reader.nextLine());
		}
	    reader.close();
		return result;
	}
	/**
	 * Writes a line to a file
	 * 
	 * @param line The string that will be written to the file
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void writeLineToFile(String line) throws FileNotFoundException, IOException {
		if (!doesFileExist()) throw new FileNotFoundException("The file "+getFileConstruct()+" does not exist!");
		PrintWriter pr = new PrintWriter(new BufferedWriter(new FileWriter(getFile(), true)));
		pr.println(line);
		pr.close();
	}
	/**
	 * Deletes the file
	 * 
	 * @throws FileNotFoundException
	 */
	public void deleteFile() {
	    getFile().delete();
	}
	/**
	 * Writes an array to the file
	 * 
	 * @param array The string array that will be added to the file
	 * @throws IOException
	 */
	public void writeArrayToFile(ArrayList<String> array) throws IOException {
		if (!doesFileExist()) throw new FileNotFoundException("The file "+getFileConstruct()+" does not exist!");
		PrintWriter pr = new PrintWriter(new BufferedWriter(new FileWriter(getFile(), true)));
		for (String s : array) {
			pr.println(s);
		}
		pr.close();
	}
}