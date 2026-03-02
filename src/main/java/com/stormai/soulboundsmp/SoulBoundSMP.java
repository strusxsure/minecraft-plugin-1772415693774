package com.stormai.soulboundsmp;

import com.stormai.soulboundsmp.commands.SoulsCommand;
import com.stormai.soulboundsmp.config.ConfigManager;
import com.stormai.soulboundsmp.listeners.DeathListener;
import com.stormai.soulboundsmp.listeners.KillListener;
import com.stormai.soulboundsmp.utils.SoulManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class SoulBoundSMP extends JavaPlugin {

    private static SoulBoundSMP instance;
    private ConfigManager configManager;
    private SoulManager soulManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        configManager = new ConfigManager(this);
        soulManager = new SoulManager(this);

        registerCommands();
        registerEvents();

        getServer().getConsoleSender().sendMessage("[SoulBoundSMP] Enabled successfully!");
    }

    @Override
    public void onDisable() {
        soulManager.saveAll(); // Save changes on shutdown
        getServer().getConsoleSender().sendMessage("[SoulBoundSMP] Disabled.");
    }

    private void registerCommands() {
        getCommand("souls").setExecutor(new SoulsCommand(this));
    }

    private void registerEvents() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new KillListener(this), this);
        pm.registerEvents(new DeathListener(this), this);
    }

    public static SoulBoundSMP getInstance() {
        return instance;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public SoulManager getSoulManager() {
        return soulManager;
    }
}