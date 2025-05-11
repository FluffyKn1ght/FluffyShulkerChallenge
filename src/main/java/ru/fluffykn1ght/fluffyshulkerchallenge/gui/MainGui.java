package ru.fluffykn1ght.fluffyshulkerchallenge.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import ru.fluffykn1ght.fluffyshulkerchallenge.UserInterfaces;
import ru.fluffykn1ght.pluginutils.GuiInventory;
import ru.fluffykn1ght.pluginutils.GuiItem;
import ru.fluffykn1ght.pluginutils.PluginLanguage;

import java.util.HashMap;
import java.util.Map;

public class MainGui {
    public static GuiInventory get(Player player) {
        Map<Integer, GuiItem> items = new HashMap<>();
        items.put(10, new GuiItem(
                        Material.SHULKER_BOX,
                        1,
                        PluginLanguage.get("gui-main-shulkers-name"),
                        PluginLanguage.getLore("gui-main-shulkers-lore"),
                        () -> {}
                )
                        .leftClick(() -> UserInterfaces.openShulkerGui(player))
        );
        items.put(13, new GuiItem(
                        Material.SKELETON_SKULL,
                        1,
                        PluginLanguage.get("gui-main-mobs-name"),
                        PluginLanguage.getLore("gui-main-mobs-lore"),
                        () -> {}
                )
        );
        items.put(16, new GuiItem(
                        Material.BEACON,
                        1,
                        PluginLanguage.get("gui-main-active-name"),
                        PluginLanguage.getLore("gui-main-active-lore"),
                        () -> {}
                )
        );

        GuiInventory gui = new GuiInventory(PluginLanguage.get("gui-main-title"), 4, items, player);
        gui.fillEmptySlots(0, Material.GRAY_STAINED_GLASS_PANE);
        gui.fillEmptySlots(1, Material.GRAY_STAINED_GLASS_PANE);
        gui.fillEmptySlots(2, Material.GRAY_STAINED_GLASS_PANE);
        gui.fillEmptySlots(3, Material.BLACK_STAINED_GLASS_PANE);
        return gui;
    }
}
