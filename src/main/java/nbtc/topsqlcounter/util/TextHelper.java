package nbtc.topsqlcounter.util;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class TextHelper {
    public static String format(String text){
        return ChatColor.translateAlternateColorCodes('&', text);
    }
    public static void sendPrefixedMessage(Player player, String text){
        player.sendMessage(format("&8┃ &c&lRedstonePvP &8&l» &f" + text));
    }
    public static void broadcastPrefixedMessage(String text){
        Bukkit.getServer().broadcastMessage(format("&8┃ &c&lRedstonePvP &8&l» &f" + text));
    }
    public static void sendPrefixedMessage(CommandSender player, String text){
        player.sendMessage(format("&8┃ &c&lRedstonePvP &8&l» &f" + text));
    }
    public static void sendSlashedMessage(Player player, String text){
        player.sendMessage(format("&8┃ " + text));
    }

    public static void sendMessage(Player player, String text){
        player.sendMessage(format(text));
    }


}
