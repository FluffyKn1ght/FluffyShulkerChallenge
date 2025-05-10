package ru.fluffykn1ght.fluffyshulkerchallenge;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import ru.fluffykn1ght.pluginutils.GuiInventory;
import ru.fluffykn1ght.pluginutils.GuiItem;
import ru.fluffykn1ght.pluginutils.PluginLanguage;

import java.util.HashMap;
import java.util.Map;

public class UserInterfaces {
    private static FluffyShulkerChallenge plugin;

    public static void setPlugin(FluffyShulkerChallenge plugin) {
        UserInterfaces.plugin = plugin;
    }


    public static void openMainGui(Player player) {
        UserInterfaces.openGui(getMainGui(player), player);
    }


    private static void openGui(GuiInventory guiInventory, Player player) {
        plugin.getServer().getPluginManager().registerEvents(guiInventory, plugin);
        player.openInventory(guiInventory.gui);
    }


    private static GuiInventory getMainGui(Player player) {
        Map<Integer, GuiItem> items = new HashMap<>();
        items.put(10, new GuiItem(
                Material.SHULKER_BOX,
                1,
                PluginLanguage.get("gui-main-shulkers-name"),
                PluginLanguage.getLore("gui-main-shulkers-lore"),
                () -> {}
            )
        );

        GuiInventory gui = new GuiInventory(PluginLanguage.get("gui-main-title"), 4, items);
        gui.fillEmptySlots(0, Material.GRAY_STAINED_GLASS_PANE);
        gui.fillEmptySlots(1, Material.GRAY_STAINED_GLASS_PANE);
        gui.fillEmptySlots(2, Material.GRAY_STAINED_GLASS_PANE);
        gui.fillEmptySlots(3, Material.BLACK_STAINED_GLASS_PANE);
        return gui;
    }
}
