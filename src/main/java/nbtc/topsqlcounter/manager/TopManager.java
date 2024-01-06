package nbtc.topsqlcounter.manager;

import nbtc.topsqlcounter.TopSQLCounter;
import nbtc.topsqlcounter.data.Top;
import nbtc.topsqlcounter.storage.ConfigYML;
import nbtc.topsqlcounter.util.TextHelper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class TopManager {
    private HashMap<String, Top> tops = new HashMap<>();

    public void addTop(String name, String playerNameColumn, String database, String mysql, String column, int count, Location location) {
        Top top = new Top(name, playerNameColumn, column, database, mysql, count, location);
        tops.put(name, top);
        TopSQLCounter.getInstance().getConfigYML().setString("Holograms." + name + ".Database", database);
        TopSQLCounter.getInstance().getConfigYML().setString("Holograms." + name + ".MySQL", mysql);
        TopSQLCounter.getInstance().getConfigYML().setString("Holograms." + name + ".Column", column);
        TopSQLCounter.getInstance().getConfigYML().setString("Holograms." + name + ".PlayerNameColumn", playerNameColumn);
        TopSQLCounter.getInstance().getConfigYML().setValue("Holograms." + name + ".Count", count);
        TopSQLCounter.getInstance().getConfigYML().setObject("Holograms." + name + ".Location", location);
    }

    public void loadTops() {
        ConfigurationSection hologramsSection = TopSQLCounter.getInstance().getConfigYML().getManager().getConfigurationSection("Holograms");

        if (hologramsSection == null) return;
        for (String hologramKey : hologramsSection.getKeys(false)) {
            String topName = hologramKey;
            ConfigurationSection topSection = TopSQLCounter.getInstance().getConfigYML().getManager().getConfigurationSection("Holograms." + topName);

            if (topSection == null) continue;
            String database = topSection.getString("Database");
            String mysql = topSection.getString("MySQL");
            String playerNameColumn = topSection.getString("PlayerNameColumn");
            String column = topSection.getString("Column");
            int count = topSection.getInt("Count");
            Location location = (Location) topSection.get("Location");

            Top top = new Top(topName, playerNameColumn, column, database, mysql, count, location);
            tops.put(topName, top);

        }

        for (Top top : tops.values()) {
            System.out.println(top);
        }

        startTopRefresher();

    }

    private void startTopRefresher(){
        new BukkitRunnable(){
            @Override
            public void run() {
                Bukkit.broadcastMessage(TextHelper.format("&8&l┃ &6&lTOP &8┃ &fThe &eholograms &fon all &6tops&f have been &asuccessfully&f updated."));
                for (Player player : Bukkit.getOnlinePlayers()){
                    player.playSound(player.getLocation(), Sound.NOTE_STICKS, 2L, 1L);
                }
                for (Top top : tops.values()){
                    top.refresh();
                }
            }
        }.runTaskTimer(TopSQLCounter.getInstance(), 100L, 20 * 60 * 30L);
    }

}


