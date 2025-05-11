package ru.fluffykn1ght.fluffyshulkerchallenge;

import org.bukkit.entity.Player;
import ru.fluffykn1ght.fluffyshulkerchallenge.gui.MainGui;
import ru.fluffykn1ght.fluffyshulkerchallenge.gui.ShulkerEditorGui;
import ru.fluffykn1ght.fluffyshulkerchallenge.gui.ShulkersGui;
import ru.fluffykn1ght.pluginutils.GuiInventory;

public class UserInterfaces {
    private static FluffyShulkerChallenge plugin;

    public static void setPlugin(FluffyShulkerChallenge plugin) {
        UserInterfaces.plugin = plugin;
    }


    public static void openMainGui(Player player) {
        openGui(MainGui.get(player), player);
    }

    public static void openShulkerGui(Player player) {
        openGui(ShulkersGui.get(player, plugin), player);
    }

    public static void openShulkerEditorGui(Player player, ChallengeShulker shulker) {
        openGui(ShulkerEditorGui.get(player, shulker, plugin), player);
    }


    private static void openGui(GuiInventory guiInventory, Player player) {
        plugin.getServer().getPluginManager().registerEvents(guiInventory, plugin);
        player.openInventory(guiInventory.gui);
    }
}
