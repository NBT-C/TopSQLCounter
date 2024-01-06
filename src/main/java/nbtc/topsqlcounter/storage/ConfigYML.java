package nbtc.topsqlcounter.storage;

import nbtc.topsqlcounter.TopSQLCounter;
import nbtc.topsqlcounter.util.YMLFile;

public class ConfigYML extends YMLFile {
    public ConfigYML() {
        super("configuration.yml", TopSQLCounter.getInstance().getDataFolder().getPath());
        createNew();
    }

    @Override
    public void setDefaults() {
        setString("Databases.Test.Host", "localhost");
        setString("Databases.Test.Pass", "");
        setString("Databases.Test.User", "nbts");
        setString("Databases.Test.Database", "test");
        setValue("Databases.Test.Port", 3306);
        setString("Holograms", "");

    }
}
