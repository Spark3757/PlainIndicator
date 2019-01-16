package ru.spark.plainindicator;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.command.Command;
import org.bukkit.plugin.java.annotation.command.Commands;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.Website;
import org.bukkit.plugin.java.annotation.plugin.author.Author;
import ru.spark.plainindicator.command.ReloadCommand;
import ru.spark.plainindicator.controller.CombatController;
import ru.spark.plainindicator.controller.PlayerController;
import ru.spark.plainindicator.listener.CombatListener;

@Plugin(name = "PlainIndicator", version = "1.0")
@Description("Minecraft plugin for indicating mob health")
@Commands(@Command(name = "plainindicator", desc = "Help command"))
@Website("https://github.com/Spark3757/PlainIndicator")
@Author("Spark1337")
public class BeautyIndicator extends JavaPlugin {

    private final String pluginPrefix = "&f[&cPlainIndicator&f] ";
    private CombatController combatController;
    private PlayerController playerController;

    @Override
    public void onEnable() {
        combatController = new CombatController(this, getConfig());
        playerController = new PlayerController(this);

        getCommand("plainindicator").setExecutor(new ReloadCommand(this));

        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&aPlugin successfully enabled!"));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "Github page: &ahttps://github.com/Spark3757/PlainIndicator"));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', pluginPrefix + "Author: &eSpark1337 [https://spark1337.ru]"));

        Bukkit.getPluginManager().registerEvents(new CombatListener(this), this);
        Metrics metrics = new Metrics(this);
    }

    public void onReload() {
        saveDefaultConfig();
        reloadConfig();

        combatController.onReload(getConfig());
    }

    @Override
    public void onDisable() {
        combatController.onDisable();

        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', pluginPrefix + "&cPlugin successfully disabled!"));
    }

    public CombatController getCombatController() {
        return combatController;
    }


    public PlayerController getPlayerController() {
        return playerController;
    }
}
