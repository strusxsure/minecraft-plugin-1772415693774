package com.stormai.soulboundsmp.listeners;

import com.stormai.soulboundsmp.SoulBoundSMP;
import com.stormai.soulboundsmp.utils.SoulManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class DeathListener implements Listener {

    private final SoulBoundSMP plugin;
    private final SoulManager soulManager;

    public DeathListener(SoulBoundSMP plugin) {
        this.plugin = plugin;
        this.soulManager = plugin.getSoulManager();
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        int originalDropsSize = e.getDrops().size();

        ItemStack soulItem = new ItemStack(Material.NETHER_STAR);
        soulItem.setAmount(1);
        Item item = e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), soulItem);

        item.setCustomName(ChatColor.RED + "" + ChatColor.BOLD + "SOUL");
        item.setGlowing(true);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!item.isDead()) {
                    item.remove();
                }
            }
        }.runTaskLater(plugin, 600); // Despawn after 30 seconds (~30 long ticks x 20tps = 1 sec ≈ 1 min lifetime here).

        // Reduce player's remaining souls by one upon losing a life (simulating how hearts work).
        soulManager.modifySouls(e.getEntity().getUniqueId(), -1);

        e.setDeathMessage(null); // Prevent spammy message if desired
    }
}