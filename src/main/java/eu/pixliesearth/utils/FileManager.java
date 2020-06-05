package eu.pixliesearth.utils;

import com.google.common.base.Charsets;
import eu.pixliesearth.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileManager {

    private File file;
    private String name, directory;
    private FileConfiguration configuration;
    private Main instance = Main.getInstance();

    public void setFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return this.file;
    }

    public String getName() {
        return this.name;
    }

    public String getDirectory() {
        return this.directory;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public void setConfiguration(YamlConfiguration configuration) {
        this.configuration = configuration;
    }

    public FileConfiguration getConfiguration() {
        return this.configuration;
    }

    public FileManager(JavaPlugin plugin, String name, String directory) {
        setName(name);

        setDirectory(directory);

        file = new File(directory, name + ".yml");

        if(!file.exists()) {
            plugin.saveResource(name + ".yml", false);
        }
        this.configuration = YamlConfiguration.loadConfiguration(this.getFile());
    }

    public void save() {
        try {
            configuration.save(file);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void reload() {
        configuration = YamlConfiguration.loadConfiguration(this.getFile());

        InputStream ConfigStream = instance.getResource(this.name + ".yml");
        if(ConfigStream == null) {
            return;
        }

        configuration.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(ConfigStream, Charsets.UTF_8)));
    }

}