package me.jbeast291.hubplugin.commands;

import com.google.common.collect.Lists;
import me.jbeast291.hubplugin.Hubplugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Marker;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class hubplugincommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("hubplugin")) {
            if (args.length == 0) {
                sender.sendMessage("§cPlease specify a sub command!");
                return false;
                // could probably have this show like a help page that shows all the available arguments for this command
            }
            if (args[0].equalsIgnoreCase("deletelaunchpad")) {
                if (args.length == 1) {
                    sender.sendMessage("§cPlease specify a sub-sub command!");
                    return false;
                }
                FileConfiguration config = Hubplugin.getInstance().getSaveDataHandlerinstance().getConfig();
                List<String> names = config.getStringList("Launch-Pads.names");
                if(!names.contains(String.format(args[1]))){
                    sender.sendMessage("§cLaunch pad is not registered!");
                    return false;
                }
                deleteLaunchPad(names, args, config, sender);

            }
            if (args[0].equalsIgnoreCase("registerlaunchpad")) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage("§c§l[!]§c Only players can execute this command.");
                    return false;
                }
                if (args.length <= 3) {
                    sender.sendMessage("§cPlease specify a sub-sub command!");
                    return false;
                }
                Player player = (Player) sender;
                FileConfiguration config = Hubplugin.getInstance().getSaveDataHandlerinstance().getConfig();
                List<String> names = config.getStringList("Launch-Pads.names");
                //check if it is already registered
                if(names.contains(String.format(args[1]))){
                    sender.sendMessage("§cLaunch pad is already registered!");
                    return false;
                }
                //register
                registerLaunchPad(names, args, config, player, sender);
            }

            if (args[0].equalsIgnoreCase("displaylaunchpadnames")) {
                sender.sendMessage("§fCurrently registered launch pads: §a" + Hubplugin.getInstance().getSaveDataHandlerinstance().getConfig().getStringList("Launch-Pads.names"));
            }


            if (args[0].equalsIgnoreCase("config")) {
                if (args.length == 1) {
                    sender.sendMessage("§cPlease specify a sub-sub command!");
                    return false;
                }
                if (args[1].equalsIgnoreCase("sethubspawn")) {
                    if (!(sender instanceof Player)) {
                        sender.sendMessage("§c§l[!]§c Only players can execute this command.");
                        return false;
                    }
                    Player player = (Player) sender;
                    setHubSpawn(player, sender);
                    return true;//command succeeded
                }
                if (args[1].equalsIgnoreCase("reload")) {
                    Hubplugin.getInstance().reloadConfig();
                    Hubplugin.getInstance().getSaveDataHandlerinstance().reloadCustomConfig();
                    sender.sendMessage("§2Config reloaded!");
                    return true;//command succeeded
                }
                sender.sendMessage("§cInvalid Subcommand");
            }
            return false;
        }
        return false;

    }
    void setHubSpawn(Player player, CommandSender sender){
        Hubplugin.getInstance().getConfig().set("spawn-position.spawn-world", player.getLocation().getWorld().getName());
        Hubplugin.getInstance().getConfig().set("spawn-position.spawn-x", Double.valueOf(String.format("%,.2f", player.getLocation().getX())));
        Hubplugin.getInstance().getConfig().set("spawn-position.spawn-y", Double.valueOf(String.format("%,.2f", player.getLocation().getY())));
        Hubplugin.getInstance().getConfig().set("spawn-position.spawn-z", Double.valueOf(String.format("%,.2f", player.getLocation().getZ())));
        Hubplugin.getInstance().getConfig().set("spawn-position.spawn-yaw", Double.valueOf(String.format("%,.2f", player.getLocation().getYaw())));
        Hubplugin.getInstance().getConfig().set("spawn-position.spawn-pitch", Double.valueOf(String.format("%,.2f", player.getLocation().getPitch())));
        Hubplugin.getInstance().saveConfig();
        sender.sendMessage("Successfully set hub spawnpoint to " +
                String.format("%,.2f", player.getLocation().getX()) + ", " +
                String.format("%,.2f", player.getLocation().getY()) + ", " +
                String.format("%,.2f", player.getLocation().getZ()) + ". \nwith yaw: " +
                String.format("%,.2f", player.getLocation().getYaw()) + " and pitch: " +
                String.format("%,.2f", player.getLocation().getPitch()) +
                " in" +
                player.getLocation().getWorld().getName() + "\"");
    }
    void deleteLaunchPad(List<String> names, String[] args, FileConfiguration config, CommandSender sender){
        names.remove(String.format(args[1]));
        config.set("Launch-Pads.names", names);
        config.set("Launch-Pads.pads." + String.format(args[1]), null);
        Hubplugin.getInstance().getSaveDataHandlerinstance().saveCustomConfig();
        sender.sendMessage("§fDeleted §c" + (args[1]));
    }
    void registerLaunchPad(List<String> names, String[] args, FileConfiguration config, Player player, CommandSender sender){
        names.add(String.format(args[1]));
        config.set("Launch-Pads.names", names);
        config.set("Launch-Pads.pads." + String.format(args[1]) + ".x", player.getLocation().getBlockX());
        config.set("Launch-Pads.pads." + String.format(args[1]) + ".y", player.getLocation().getBlockY());
        config.set("Launch-Pads.pads." + String.format(args[1]) + ".z", player.getLocation().getBlockZ());
        config.set("Launch-Pads.pads." + String.format(args[1]) + ".vec-x", Double.valueOf(args[2]));
        config.set("Launch-Pads.pads." + String.format(args[1]) + ".vec-y", Double.valueOf(args[3]));
        config.set("Launch-Pads.pads." + String.format(args[1]) + ".vec-z", Double.valueOf(args[4]));
        Hubplugin.getInstance().getSaveDataHandlerinstance().saveCustomConfig();
        sender.sendMessage("§fSuccessfully set §c" + args[1] + "§f to: §9" + player.getLocation().getBlockX() +
                "§f, §9" + player.getLocation().getBlockY() + "§f, §9" + player.getLocation().getBlockY() +
                "§f. Velocity vector set to: §a" + Double.valueOf(args[2]) + "§f, §a" + Double.valueOf(args[3]) +
                "§f, §a" + Double.valueOf(args[4]));
    }
}
