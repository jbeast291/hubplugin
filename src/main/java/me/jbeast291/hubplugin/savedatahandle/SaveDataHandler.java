package me.jbeast291.hubplugin.savedatahandle;


import me.jbeast291.hubplugin.Hubplugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

public class SaveDataHandler {
    private FileConfiguration customdata = null;
    private File customdataFile = null;
    private String ConfigFileName = "SavedLocations.yml";

    public void reloadCustomConfig() {
        if (customdataFile == null) {
            customdataFile = new File(Hubplugin.getInstance().getDataFolder(), ConfigFileName);
        }
        customdata = YamlConfiguration.loadConfiguration(customdataFile);

        // Look for defaults in the jar
        Reader defConfigStream = null;
        defConfigStream = new InputStreamReader(Hubplugin.getInstance().getResource(ConfigFileName), StandardCharsets.UTF_8);
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            customdata.setDefaults(defConfig);
        }
    }

    public FileConfiguration getConfig() {
        if (customdata == null) {
            reloadCustomConfig();
        }
        return customdata;
    }

    public void saveCustomConfig() {
        if (customdata == null || customdataFile == null) {
            return;
        }
        try {
            getConfig().save(customdataFile);
        } catch (IOException ex) {
            Hubplugin.getInstance().getLogger().log(Level.SEVERE, "Could not save config to " + customdataFile, ex);
        }
    }

    public void saveDefaultCustomConfig() {
        if (customdataFile == null) {
            customdataFile = new File(Hubplugin.getInstance().getDataFolder(), ConfigFileName);
        }
        if (!customdataFile.exists()) {
            Hubplugin.getInstance().saveResource(ConfigFileName, false);
        }
    }
}
