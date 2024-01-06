package nbtc.topsqlcounter.storage.connector;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import nbtc.topsqlcounter.TopSQLCounter;
import nbtc.topsqlcounter.storage.ConfigYML;
import nbtc.topsqlcounter.util.Debugger;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MySQLConnector {
    private static final Map<String, HikariDataSource> dataSources = new HashMap<>();

    public static void setupDataSources() {
        ConfigurationSection databasesSection = TopSQLCounter.getInstance().getConfigYML().getManager().getConfigurationSection("Databases");

        if (databasesSection != null) {
            for (String dbName : databasesSection.getKeys(false)) {
                ConfigurationSection dbConfig = databasesSection.getConfigurationSection(dbName);

                HikariConfig config = new HikariConfig();
                config.setJdbcUrl("jdbc:mysql://" + dbConfig.getString("Host") + ":" + dbConfig.getInt("Port") + "/" + dbConfig.getString("Database"));
                config.setUsername(dbConfig.getString("User"));
                config.setPassword(dbConfig.getString("Pass"));
                config.setMaximumPoolSize(3);

                HikariDataSource dataSource = null;

                try {
                    dataSource = new HikariDataSource(config);
                    Debugger.debugError("DATABASE - " + dbName + " - CONNECTED");
                } catch (Exception e) {
                    Debugger.debugInfo("DATABASE - " + dbName + " - FAILURE");
                }

                dataSources.put(dbName, dataSource);
            }
        } else {
            System.out.println("No 'Databases' section found in the configuration.");
        }
    }

    public static Connection getConnection(String dbName) throws SQLException {
        HikariDataSource dataSource = dataSources.get(dbName);
        if (dataSource != null) {
            return dataSource.getConnection();
        } else {
            throw new IllegalArgumentException("Database not found: " + dbName);
        }
    }

    public static void closeDataSources() {
        for (HikariDataSource dataSource : dataSources.values()) {
            dataSource.close();
        }
    }

}
