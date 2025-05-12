package ru.fluffykn1ght.pluginutils;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class GuiHandler {
    public static JavaPlugin plugin;

    public static void setPlugin(JavaPlugin plugin) {
        GuiHandler.plugin = plugin;
    }

    public static void openGui(GuiInventory guiInventory, Player player) {
        plugin.getServer().getPluginManager().registerEvents(guiInventory, plugin);
        player.openInventory(guiInventory.gui);
    }
}
