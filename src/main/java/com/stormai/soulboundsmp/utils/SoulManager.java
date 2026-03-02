package com.stormai.soulboundsmp.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.stormai.soulboundsmp.SoulBoundSMP;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SoulManager {

    private final Map<UUID, Integer> playerSouls = new HashMap<>();
    private final Gson gson = new Gson();
    private File dataFile;
    private JsonObject jsonData;

    public SoulManager(SoulBoundSMP plugin) {
        load(plugin);
    }

    public int getPlayerSouls(UUID uuid) {
        return playerSouls.getOrDefault(uuid, 0);
    }

    public void setPlayerSouls(UUID uuid, int amount) {
        playerSouls.put(uuid, amount <= 0 ? 0 : amount);
    }

    public void modifySouls(UUID uuid, int delta) {
        playerSouls.put(uuid, Math.max(0, getPlayerSouls(uuid) + delta));
    }

    public void saveAll() {
        try {
            jsonData = new JsonObject();
            for (Map.Entry<UUID, Integer> entry : playerSouls.entrySet()) {
                jsonData.addProperty(entry.getKey().toString(), entry.getValue());
            }
            java.nio.file.Files.writeString(dataFile.toPath(), gson.toJson(jsonData));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void load(SoulBoundSMP plugin) {
        dataFile = new File(plugin.getDataFolder(), "players_data.json");

        if (!dataFile.exists()) {
            plugin.getDataFolder().mkdirs();
            try {
                dataFile.createNewFile();
                jsonData = new JsonObject();
                java.nio.file.Files.writeString(dataFile.toPath(), "{}");
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            String content = java.nio.file.Files.readString(dataFile.toPath());
            jsonData = JsonParser.parseString(content.isEmpty() ? "{}" : content).getAsJsonObject();

            for (String key : jsonData.keySet()) {
                UUID uuid = UUID.fromString(key);
                int souls = jsonData.get(key).getAsInt();
                playerSouls.put(uuid, souls);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}