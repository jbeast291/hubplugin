package me.jbeast291.hubplugin.commands;

import me.jbeast291.hubplugin.Hubplugin;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class hubplugintabcompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args){
        Hubplugin.getInstance().getLogger().info(Arrays.toString(args));
        if (command.getName().equalsIgnoreCase("hubplugin")) {

            if (args.length == 1) {
                List<String> ListToReturn = new ArrayList<>();
                ListToReturn.add("deletelaunchpad");
                ListToReturn.add("registerlaunchpad");
                ListToReturn.add("displaylaunchpadnames");
                ListToReturn.add("config");
                return ListToReturn;
            }
            else if (args[0].equalsIgnoreCase("deletelaunchpad") && args.length == 2) {
                List<String> ListToReturn = new ArrayList<>();
                ListToReturn.add("(Name)");
                return ListToReturn;
            }
            else if (args[0].equalsIgnoreCase("registerlaunchpad")) {
                if (args.length == 2) {
                    List<String> ListToReturn = new ArrayList<>();
                    ListToReturn.add("(Name)");
                    return ListToReturn;
                }
                else if (args.length == 3) {
                    List<String> ListToReturn = new ArrayList<>();
                    ListToReturn.add("(LaunchVecX)");
                    return ListToReturn;
                }
                else if (args.length == 4) {
                    List<String> ListToReturn = new ArrayList<>();
                    ListToReturn.add("(LaunchVecY)");
                    return ListToReturn;
                }
                else if (args.length == 5) {
                    List<String> ListToReturn = new ArrayList<>();
                    ListToReturn.add("(LaunchVecZ)");
                    return ListToReturn;
                }
            }
            else if (args[0].equalsIgnoreCase("config")) {
                if (args.length == 2) {
                    List<String> ListToReturn = new ArrayList<>();
                    ListToReturn.add("sethubspawn");
                    ListToReturn.add("reload");
                    return ListToReturn;
                }
            }
            return new ArrayList<>();
        }
        return new ArrayList<>();
    }
}
