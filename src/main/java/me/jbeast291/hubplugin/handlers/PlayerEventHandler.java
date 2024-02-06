package me.jbeast291.hubplugin.handlers;

import me.jbeast291.hubplugin.Hubplugin;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.util.Vector;

import java.awt.*;
import java.util.List;
import java.util.Objects;

public class PlayerEventHandler implements Listener {
    public PlayerEventHandler(Hubplugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.getInventory().clear();
        FileConfiguration config = Hubplugin.getInstance().getConfig();

        player.teleportAsync(new Location(
                Bukkit.getWorld(config.getString("spawn-position.spawn-world")),
                config.getDouble("spawn-position.spawn-x"),
                config.getDouble("spawn-position.spawn-y"),
                config.getDouble("spawn-position.spawn-z"),
                config.getLong("spawn-position.spawn-yaw"),
                config.getLong("spawn-position.spawn-pitch")));


        player.sendMessage("§rWelcome to the §9Bellarmine §rclub server");
        player.setGameMode(GameMode.SURVIVAL);
    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null)
        {
            return;
        }
        if(IsPreasurePlate(e.getClickedBlock().getType()) && PressureplateIsRegistered(e.getClickedBlock().getLocation())){
            Player player = e.getPlayer();
            launchPlayer(player, GetPressureplateNameFromLoc(e.getClickedBlock().getLocation()));
            return;
        }
        if(e.getPlayer().hasPermission("hubplugin.admin")){
            return;
        }
        if(IsProtectedBlock(e.getClickedBlock().getType())){
            e.setCancelled(true);
            e.getPlayer().sendMessage("§cYou cannot interact with this!");
        }
    }

    public void launchPlayer(Player player, String pressureplateName){
        FileConfiguration config = Hubplugin.getInstance().getSaveDataHandlerinstance().getConfig();
        player.setVelocity(new Vector(config.getDouble("Launch-Pads.pads." + pressureplateName + ".vec-x"),
                config.getDouble("Launch-Pads.pads." + pressureplateName + ".vec-y"),
                config.getDouble("Launch-Pads.pads." + pressureplateName + ".vec-z")));
        //DEBUG BELOW
    }
    public boolean PressureplateIsRegistered(Location location){
        FileConfiguration config = Hubplugin.getInstance().getSaveDataHandlerinstance().getConfig();
        List<String> strings = config.getStringList("Launch-Pads.names");

        for (int i = 0; i < strings.size(); i++){
            if (    Objects.equals(config.get("Launch-Pads.pads." + strings.get(i) + ".x"), location.getBlockX()) &&
                    Objects.equals(config.get("Launch-Pads.pads." + strings.get(i) + ".y"), location.getBlockY()) &&
                    Objects.equals(config.get("Launch-Pads.pads." + strings.get(i) + ".z"), location.getBlockZ())){
                return true;
            }
        }
        return false;
    }
    public String GetPressureplateNameFromLoc(Location location){
        FileConfiguration config = Hubplugin.getInstance().getSaveDataHandlerinstance().getConfig();
        List<String> strings = config.getStringList("Launch-Pads.names");

        for (int i = 0; i < strings.size(); i++){
            if (    Objects.equals(config.get("Launch-Pads.pads." + strings.get(i) + ".x"), location.getBlockX()) &&
                    Objects.equals(config.get("Launch-Pads.pads." + strings.get(i) + ".y"), location.getBlockY()) &&
                    Objects.equals(config.get("Launch-Pads.pads." + strings.get(i) + ".z"), location.getBlockZ())){
                return strings.get(i);
            }
        }
        return "";
    }
    public boolean IsPreasurePlate(Material material){
        if (material.equals(Material.OAK_PRESSURE_PLATE) ||
            material.equals(Material.SPRUCE_PRESSURE_PLATE) ||
            material.equals(Material.BIRCH_PRESSURE_PLATE) ||
            material.equals(Material.JUNGLE_PRESSURE_PLATE) ||
            material.equals(Material.ACACIA_PRESSURE_PLATE) ||
            material.equals(Material.DARK_OAK_PRESSURE_PLATE) ||
            material.equals(Material.MANGROVE_PRESSURE_PLATE) ||
            material.equals(Material.CHERRY_PRESSURE_PLATE) ||
            material.equals(Material.BAMBOO_PRESSURE_PLATE) ||
            material.equals(Material.CRIMSON_PRESSURE_PLATE) ||
            material.equals(Material.WARPED_PRESSURE_PLATE) ||
            material.equals(Material.STONE_PRESSURE_PLATE) ||
            material.equals(Material.POLISHED_BLACKSTONE_PRESSURE_PLATE) ||
            material.equals(Material.HEAVY_WEIGHTED_PRESSURE_PLATE) ||
            material.equals(Material.LIGHT_WEIGHTED_PRESSURE_PLATE)){
            return true;
        }
        return false;
    }
    public boolean IsProtectedBlock(Material material){
        if (material.equals(Material.OAK_FENCE_GATE) ||
                material.equals(Material.SPRUCE_FENCE_GATE) ||
                material.equals(Material.BIRCH_FENCE_GATE) ||
                material.equals(Material.JUNGLE_FENCE_GATE) ||
                material.equals(Material.ACACIA_FENCE_GATE) ||
                material.equals(Material.DARK_OAK_FENCE_GATE) ||
                material.equals(Material.MANGROVE_FENCE_GATE) ||
                material.equals(Material.CHERRY_FENCE_GATE) ||
                material.equals(Material.BAMBOO_FENCE_GATE) ||
                material.equals(Material.CRIMSON_FENCE_GATE) ||
                material.equals(Material.WARPED_FENCE_GATE) ||

                material.equals(Material.OAK_TRAPDOOR) ||
                material.equals(Material.SPRUCE_TRAPDOOR) ||
                material.equals(Material.BIRCH_TRAPDOOR) ||
                material.equals(Material.JUNGLE_TRAPDOOR) ||
                material.equals(Material.ACACIA_TRAPDOOR) ||
                material.equals(Material.DARK_OAK_TRAPDOOR) ||
                material.equals(Material.MANGROVE_TRAPDOOR) ||
                material.equals(Material.CHERRY_TRAPDOOR) ||
                material.equals(Material.BAMBOO_TRAPDOOR) ||
                material.equals(Material.CRIMSON_TRAPDOOR) ||
                material.equals(Material.WARPED_TRAPDOOR) ||
                material.equals(Material.IRON_TRAPDOOR) ||

                material.equals(Material.OAK_DOOR) ||
                material.equals(Material.SPRUCE_DOOR) ||
                material.equals(Material.BIRCH_DOOR) ||
                material.equals(Material.JUNGLE_DOOR) ||
                material.equals(Material.ACACIA_DOOR) ||
                material.equals(Material.DARK_OAK_DOOR) ||
                material.equals(Material.MANGROVE_DOOR) ||
                material.equals(Material.CHERRY_DOOR) ||
                material.equals(Material.BAMBOO_DOOR) ||
                material.equals(Material.CRIMSON_DOOR) ||
                material.equals(Material.WARPED_DOOR) ||
                material.equals(Material.IRON_DOOR)){
            return true;
        }
        return false;
    }
}
