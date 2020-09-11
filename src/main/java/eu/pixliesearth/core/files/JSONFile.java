package eu.pixliesearth.core.files;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JSONFile extends FileBase {
	
	private JsonObject json;
    private JsonParser jsonParser;
    
	public JSONFile(String path, String filename) {
		super(path, filename, ".json");
		this.jsonParser = new JsonParser();
		try {
			this.json = loadFileIntoJson();
		} catch (IOException e) {
			this.json = this.jsonParser.parse("{}").getAsJsonObject();
		}
	}
	
	@Override
	public String getFileStructure() {
		return "{}";
	}
	
	public JsonObject loadFileIntoJson() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(getFile()));
		String s = reader.lines().collect(Collectors.joining("\n"));
		reader.close();
		if (s.equals("")) s = "{}";
		return this.jsonParser.parse(s).getAsJsonObject();
	}
	
	public void put(String key, String value) {
		if (value==null) return;
		if (this.json.has(key)) this.json.remove(key);
		this.json.addProperty(key, value);
		saveJsonToFile();
	}
	
	public void put(String key, Object value) {
		if (value==null) return;
		if (this.json.has(key)) this.json.remove(key);
		this.json.addProperty(key, value.toString());
		saveJsonToFile();
	}
	
	public void put(String key, Long value) {
		if (value==null) return;
		if (this.json.has(key)) this.json.remove(key);
		this.json.addProperty(key, value);
		saveJsonToFile();
	}
	
	public void put(String key, Integer value) {
		if (value==null) return;
		if (this.json.has(key)) this.json.remove(key);
		this.json.addProperty(key, value);
		saveJsonToFile();
	}
	
	public void put(String key, Boolean value) {
		if (value==null) return;
		if (this.json.has(key)) this.json.remove(key);
		this.json.addProperty(key, value);
		saveJsonToFile();
	}
	
	public void put(String key, Character value) {
		if (value==null) return;
		if (this.json.has(key)) this.json.remove(key);
		this.json.addProperty(key, value);
		saveJsonToFile();
	}
	
	public void remove(String key) {
		if (this.json.has(key)) {this.json.remove(key);saveJsonToFile();}
	}
	
	public String get(String key) {
		if (this.json.has(key)) return json.get(key).getAsString(); else return null;
	}
	
	public Object containsKey(String key) {
		return this.json.has(key);
	}
	
	public Set<String> keySet() {
        Set<String> keys = new HashSet<String>();
        Set<Map.Entry<String, JsonElement>> entries = this.json.entrySet();
        for (Map.Entry<String, JsonElement> entry : entries) {
            keys.add(entry.getKey());
        }
        return keys;
    }
	
	public void saveJsonToFile(JsonObject json) {
		try {
			clearFile();
			writeLineToFile(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveJsonToFile() {
		try {
			clearFile();
			writeLineToFile(this.json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}