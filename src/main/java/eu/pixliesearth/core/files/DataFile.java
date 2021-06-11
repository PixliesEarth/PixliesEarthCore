package eu.pixliesearth.core.files;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public final class DataFile extends FileBase {
	/**
	 * Initialises the class with file data, when used setupFile(FilePath, FileName, FileExtension) is not required to be called
	 * 
	 * @param FilePath The path to the file
	 * @param FileName The files name
	 * @param FileExtension The files extension
	 */
	public DataFile(String FilePath, String FileName, String FileExtension) {
		super(FilePath, FileName, FileExtension);
	}
	/**
	 * Initialises the class with file data and the default extension, when used setupFile(FilePath, FileName, FileExtension) is not required to be called
	 * 
	 * @param FilePath The path to the file
	 * @param FileName The files name
	 */
	public DataFile(String FilePath, String FileName) {
		super(FilePath, FileName, ".bbd");
	}
	/**
	 * Initialises the class without any file data, when used setupFile(FilePath, FileName, FileExtension) must be called to initialise properly
	 */
	public DataFile() {
		super();
	}
	/**
	 * Returns the file save structure, For example "KEY=DATA" would mean that the data is stored as a key and data with an equals sign to separate them
	 * 
	 * @return The data format that the data is stored in
	 */
	@Override
	public String getFileStructure() {
		return "KEY=DATA";
	}
	/**
	 * Returns the file as an map based on getFileStructure();
	 * 
	 * @return A map of the file
	 * @throws FileNotFoundException
	 */
	public Map<String, String> loadDataMap() throws FileNotFoundException {
		if (!doesFileExist()) throw new FileNotFoundException("The file "+getFileConstruct()+" does not exist!");
		HashMap<String, String> map = new HashMap<String, String>();
	    Scanner reader = new Scanner(getFile());
	    while (reader.hasNext()) {
	    	String s = reader.nextLine();
	    	int i = s.indexOf('=');
	    	map.put(s.substring(0, i), s.substring(i+1, s.length()));
		}
	    reader.close();
		return map;
	}
	/**
	 * Writes the file based of the map provided
	 * 
	 * @param map The map that will be saved to the file
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void saveDataMap(Map<String, String> map) throws FileNotFoundException, IOException {
		if (!doesFileExist()) throw new FileNotFoundException("The file "+getFileConstruct()+" does not exist!");
		PrintWriter pr = new PrintWriter(new BufferedWriter(new FileWriter(getFile(), true)));
		Set<String> keys = map.keySet();
	    for (String key : keys) {
	    	pr.println(key+"="+map.get(key));
	    }
		pr.close();
	}
}