package nbtc.topsqlcounter.data;

import lombok.Data;
import me.clip.placeholderapi.PlaceholderAPI;
import me.filoghost.holographicdisplays.api.hologram.Hologram;
import me.filoghost.holographicdisplays.plugin.bridge.placeholderapi.PlaceholderAPIHook;
import nbtc.topsqlcounter.TopSQLCounter;
import nbtc.topsqlcounter.storage.connector.MySQLConnector;
import nbtc.topsqlcounter.util.TextHelper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class Top {
    String name, column, database, mysql, playerNameColumn;
    int count;
    Location location;
    private Hologram hologram;
    public Top(String name, String playerNameColumn, String column, String database, String mysql, int count, Location location){
        this.name = name;
        this.column = column;
        this.mysql = mysql;
        this.database = database;
        this.count = count;
        this.location = location;
        this.playerNameColumn = playerNameColumn;
        setup();
    }
    private void setup(){
        hologram = TopSQLCounter.getInstance().getHoloApi().createHologram(location);
        hologram.getLines().appendText(TextHelper.format("&eTOP " + count + " " + column.toUpperCase()));
        hologram.getLines().appendText("");

        try (Connection connection = MySQLConnector.getConnection(mysql)) {

            String query = "SELECT "+column+","+playerNameColumn+" FROM " + database + " ORDER BY " + column + " DESC LIMIT " + count;

            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                int rank = 1;
                while (resultSet.next()) {
                    Player player = Bukkit.getPlayer(resultSet.getString(playerNameColumn));
                    String playerName;
                    if (player == null) {
                        playerName = "&9" + resultSet.getString(playerNameColumn);
                    } else {
                        playerName = PlaceholderAPI.setPlaceholders(player, "%luckperms_suffix%"+player.getName());
                    }
                    String entryText = TextHelper.format("&7#" + rank + " " + playerName + " &8» &7" + resultSet.getInt(column) + " " + column.toLowerCase());
                    hologram.getLines().appendText(entryText);
                    rank++;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void refresh(){
        hologram.delete();
        setup();
    }
}
