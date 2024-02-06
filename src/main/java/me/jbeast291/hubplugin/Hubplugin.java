package me.jbeast291.hubplugin;

import me.jbeast291.hubplugin.commands.hubplugincommand;
import me.jbeast291.hubplugin.commands.hubplugintabcompleter;
import me.jbeast291.hubplugin.savedatahandle.SaveDataHandler;
import me.jbeast291.hubplugin.handlers.PlayerEventHandler;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.io.IOException;

public final class Hubplugin extends JavaPlugin {

    private static Hubplugin instance;
    private SaveDataHandler SaveDataHandlerinstance;

    @Override
    public void onEnable() {

        //instaces
        SaveDataHandlerinstance = new SaveDataHandler();
        instance = this;

        //default configs
        SaveDataHandlerinstance.saveDefaultCustomConfig();
        saveDefaultConfig();

        // Plugin startup logic
        Bukkit.getLogger().info("Hub Plugin starting up! Created by Jacoby Smith (@jbeast291).");
        getCommand("hubplugin").setExecutor(new hubplugincommand());
        getCommand("hubplugin").setTabCompleter(new hubplugintabcompleter());
        new PlayerEventHandler(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getLogger().info("Hub Plugin Shutting Down");
    }
    public static Hubplugin getInstance() {
        return instance;
    }
    public SaveDataHandler getSaveDataHandlerinstance() {
        return SaveDataHandlerinstance;
    }

}
