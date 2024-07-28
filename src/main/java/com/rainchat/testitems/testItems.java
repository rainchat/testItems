package com.rainchat.testitems;

import com.rainchat.testitems.listeners.ItemListener;
import com.rainchat.testitems.managers.ItemManager;
import com.rainchat.testitems.utils.sturcute.StructureBuilder;
import com.rainchat.testitems.utils.wg.WorldGuardUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class testItems extends JavaPlugin implements Listener, TabCompleter {

    private ItemManager itemManager;

    @Override
    public void onLoad() {
        WorldGuardUtils.initialize(this);
    }

    @Override
    public void onEnable() {
        WorldGuardUtils.onEnable(this);
        itemManager = new ItemManager(this);

        getServer().getPluginManager().registerEvents(new ItemListener(itemManager), this);
    }

    @Override
    public void onDisable() {
        StructureBuilder.restoreAllOriginalBlocks();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (command.getName().equalsIgnoreCase("igive")) {
                if (args.length == 0) {
                    itemManager.giveSpecialItems(player);
                    return true;
                } else if (args.length == 1) {
                    String itemName = args[0];
                    itemManager.giveSpecialItemByName(player, itemName);
                    return true;
                } else {
                    player.sendMessage("Usage: /igive [item_name]");
                }
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (command.getName().equalsIgnoreCase("igive")) {
            if (args.length == 1) {
                List<String> itemNames = itemManager.getItemNames();
                List<String> completions = new ArrayList<>();
                for (String name : itemNames) {
                    if (name.toLowerCase().startsWith(args[0].toLowerCase())) {
                        completions.add(name);
                    }
                }
                return completions;
            }
        }
        return null;
    }
}
