package ru.spark.plainindicator.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import ru.spark.plainindicator.BeautyIndicator;

public class ReloadCommand implements CommandExecutor {
    private BeautyIndicator beautyIndicator;

    public ReloadCommand(BeautyIndicator beautyIndicator) {
        this.beautyIndicator = beautyIndicator;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender.hasPermission("plainindicator.reload") && strings.length > 0 && strings[0].equalsIgnoreCase("reload")) {
            beautyIndicator.onReload();
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f[&cPlainIndicator&f] Plugin was successfully reloaded"));
            return true;
        }
        return false;
    }
}