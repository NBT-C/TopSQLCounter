package nbtc.topsqlcounter.util;

import nbtc.topsqlcounter.TopSQLCounter;

import java.util.logging.Level;

public class Debugger {
    public static void debugInfo(String msg){
        TopSQLCounter.getInstance().getLogger().log(Level.INFO, msg);
    }
    public static void debugError(String msg){
        TopSQLCounter.getInstance().getLogger().log(Level.SEVERE, msg);

    }
}
