package eu.pixliesearth.core.files;

import java.io.IOException;
import java.util.List;

public final class ListFile extends FileBase {
	/**
	 * Initialises the class with file data, when used setupFile(FilePath, FileName, FileExtension) is not required to be called
	 * 
	 * @param FilePath The path to the file
	 * @param FileName The files name
	 * @param FileExtension The files extension
	 */
	public ListFile(String FilePath, String FileName, String FileExtension) {
		super(FilePath, FileName, FileExtension);
	}
	/**
	 * Initialises the class with file data and the default extension, when used setupFile(FilePath, FileName, FileExtension) is not required to be called
	 * 
	 * @param FilePath The path to the file
	 * @param FileName The files name
	 */
	public ListFile(String FilePath, String FileName) {
		super(FilePath, FileName, ".bbl");
	}
	/**
	 * Returns the file save structure, For example "KEY=DATA" would mean that the data is stored as a key and data with an equals sign to separate them
	 * 
	 * @return The data format that the data is stored in
	 */
	@Override
	public String getFileStructure() {
		return "DATA\n";
	}
	/**
	 * Returns the file as a List
	 * 
	 * @return
	 * @throws IOException 
	 */
	public List<String> loadList() throws IOException {
		return loadFileIntoArray();
	}
	/**
	 * Writes the file based of the List provided
	 * 
	 * @param list The list that will be saved to the file
	 * @throws IOException 
	 */
	public void saveList(List<String> list) throws IOException {
		for (String s : list) 
			writeLineToFile(s);
	}
}