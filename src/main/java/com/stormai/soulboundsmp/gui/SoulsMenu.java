package com.stormai.soulboundsmp.gui;

import com.stormai.soulboundsmp.SoulBoundSMP;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class SoulsMenu implements Listener {

    private final SoulBoundSMP plugin;
    private final Player player;

    public SoulsMenu(SoulBoundSMP plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public void openInventory() {
        Inventory inventory = Bukkit.createInventory(null, 9, Component.text("Your Soul Powers"));

        UUID uuid = player.getUniqueId();
        int soulCount = plugin.getSoulManager().getPlayerSouls(uuid);

        addItem(inventory, Material.FIRE_CHARGE, 0,
                "&cGain Temporary Strength",
                Arrays.asList(
                        "&7Cost: &a1 Soul",
                        "",
                        "&fActivates STRONG!",
                        "",
                        "&7Your current SOUL count: &e" + soulCount));

        addItem(inventory, Material.RABBIT_FOOT, 1,
                "&bGain Speed Boost",
                Arrays.asList(
                        "&7Cost: &a1 Soul",
                        "",
                        "&fGives SPEED II for 10s!",
                        "",
                        "&7Your current SOUL count: &e" + soulCount));

        addItem(inventory, Material.APPLE, 2,
                "&aRestore Max Health Temporarily",
                Arrays.asList(
                        "&7Cost: &a2 Souls",
                        "",
                        "&fAdds EXTRA hearts temporarily.",
                        "",
                        "&7Your current SOUL count: &e" + soulCount));

        player.openInventory(inventory);
    }

    private void addItem(Inventory inv, Material mat, int index, String name, List<String> lore) {
        ItemStack stack = new ItemStack(mat);
        ItemMeta meta = stack.getItemMeta();
        assert meta != null;
        meta.displayName(Component.text(ChatColor.translateAlternateColorCodes('&', name)));
        meta.lore(lore.stream()
                .map(str -> Component.text(ChatColor.translateAlternateColorCodes('&', str)))
                .toList());
        stack.setItemMeta(meta);
        inv.setItem(index, stack);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!event.getView().title().equals(Component.text("Your Soul Powers"))) return;

        event.setCancelled(true);

        Player p = (Player) event.getWhoClicked();
        int slot = event.getRawSlot();

        switch (slot) {
            case 0 -> activateStrength(p);
            case 1 -> activateSpeed(p);
            case 2 -> activateExtraHearts(p);
            default -> p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 1, 1);
        }
    }

    private void activateStrength(Player p) {
        spendAndAction(p, 1, () -> {
            p.addPotionEffect(new org.bukkit.potion.PotionEffect(
                    org.bukkit.potion.PotionEffectType.INCREASE_DAMAGE, // STRENGTH
                    20 * 10, // Duration (ticks → 10s)
                    1));
            p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
        });
    }

    private void activateSpeed(Player p) {
        spendAndAction(p, 1, () -> {
            p.addPotionEffect(new org.bukkit.potion.PotionEffect(
                    org.bukkit.potion.PotionEffectType.SPEED,
                    20 * 10,
                    1));
            p.playSound(p.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CONVERTED, 1, 1);
        });
    }

    private void activateExtraHearts(Player p) {
        spendAndAction(p, 2, () -> {
            AttributeInstance attr = p.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            double boost = 4; // hearts (x 2 Health points)

            attr.setBaseValue(attr.getValue() + boost);

            org.bukkit.scheduler.BukkitRunnable task = new org.bukkit.scheduler.BukkitRunnable() {
                @Override
                public void run() {
                    attr.setBaseValue(attr.getValue() - boost);
                }
            };
            task.runTaskLater(plugin, 20 * 60); // After 1 minute reduce again.

            p.playSound(p.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1, 1);
        });
    }

    private void spendAndAction(Player p, int cost, Runnable runnable) {
        UUID uuid = p.getUniqueId();
        int soulsLeft = plugin.getSoulManager().getPlayerSouls(uuid);

        if (cost > soulsLeft) {
            p.sendMessage(ChatColor.RED + "Not enough souls! Required: " + cost + ".");
            return;
        }

        plugin.getSoulManager().modifySouls(uuid, -cost);
        runnable.run();
        p.closeInventory();
    }
}