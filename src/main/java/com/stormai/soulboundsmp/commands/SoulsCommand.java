package com.stormai.soulboundsmp.commands;

import com.stormai.soulboundsmp.SoulBoundSMP;
import com.stormai.soulboundsmp.gui.SoulsMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SoulsCommand implements CommandExecutor {

    private final SoulBoundSMP plugin;

    public SoulsCommand(SoulBoundSMP plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Player only!");
            return true;
        }

        Player player = (Player) sender;
        new SoulsMenu(plugin, player).openInventory();

        return true;
    }
}