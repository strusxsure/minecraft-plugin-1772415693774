package com.stormai.soulboundsmp.config;

import com.stormai.soulboundsmp.SoulBoundSMP;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigManager {

    private final File configFile;
    private final FileConfiguration config;

    public ConfigManager(SoulBoundSMP plugin) {
        configFile = new File(plugin.getDataFolder(), "config.yml");
        if (!configFile.exists())
            plugin.saveResource("config.yml", false);
        
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public boolean getBoolean(String path, boolean def) {
        return config.getBoolean(path, def);
    }

    public double getDouble(String path, double def) {
        return config.getDouble(path, def);
    }

    public String getString(String path, String def) {
        return config.getString(path, def);
    }

    public long getLong(String path, long def) {
        return config.getLong(path, def);
    }
}