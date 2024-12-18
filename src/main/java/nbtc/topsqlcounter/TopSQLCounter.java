package nbtc.topsqlcounter;

import lombok.Getter;
import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import nbtc.topsqlcounter.commands.TopAddCommand;
import nbtc.topsqlcounter.manager.TopManager;
import nbtc.topsqlcounter.storage.ConfigYML;
import nbtc.topsqlcounter.storage.connector.MySQLConnector;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class TopSQLCounter extends JavaPlugin {
    private static @Getter TopSQLCounter instance;
    private TopManager topManager;
    private ConfigYML configYML;
    private HolographicDisplaysAPI holoApi;
    private void init(){
        instance = this;
        topManager = new TopManager();
        configYML = new ConfigYML();
        holoApi = HolographicDisplaysAPI.get(this);
    }
    @Override
    public void onEnable() {
        if (!Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays")) {
            getLogger().severe("*** HolographicDisplays is not installed or not enabled. ***");
            getLogger().severe("*** This plugin will be disabled. ***");
            this.setEnabled(false);
            return;
        }
        init();
        MySQLConnector.setupDataSources();
        getTopManager().loadTops();
        getCommand("topadd").setExecutor(new TopAddCommand());
    }

    @Override
    public void onDisable() {
        MySQLConnector.closeDataSources();
    }
}
