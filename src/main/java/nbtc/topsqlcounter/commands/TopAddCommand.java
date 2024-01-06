package nbtc.topsqlcounter.commands;

import nbtc.topsqlcounter.TopSQLCounter;
import nbtc.topsqlcounter.util.TextHelper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TopAddCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;

        if (args.length != 6){
            TextHelper.sendPrefixedMessage(player, "&cUsage: /topadd (name) (playerNameColumn) (mysql) (database) (column) (count)");
            return false;
        }

        String name = args[0];
        String playerNameColumn = args[1];
        String mysql = args[2];
        String database = args[3];
        String column = args[4];
        int count = Integer.parseInt(args[5]);

        if (count == 0){
            TextHelper.sendPrefixedMessage(player, "&cFuck yourself nigger, this is not a fucking number");
            return false;
        }

        TopSQLCounter.getInstance().getTopManager().addTop(name, playerNameColumn, database, mysql, column, count, player.getLocation());

        return false;
    }
}
