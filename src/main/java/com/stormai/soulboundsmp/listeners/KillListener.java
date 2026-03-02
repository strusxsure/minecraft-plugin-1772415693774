package com.stormai.soulboundsmp.listeners;

import com.stormai.soulboundsmp.SoulBoundSMP;
import com.stormai.soulboundsmp.utils.SoulManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class KillListener implements Listener {

    private final SoulManager soulManager;

    public KillListener(SoulBoundSMP plugin) {
        this.soulManager = plugin.getSoulManager();
    }

    @EventHandler(ignoreCancelled = true)
    public void onKill(PlayerDeathEvent event) {
        Player killer = event.getPlayer().getKiller();
        if (killer != null && !event.getEntity().equals(killer)) {
            soulManager.modifySouls(killer.getUniqueId(), 1);
        }
    }
}