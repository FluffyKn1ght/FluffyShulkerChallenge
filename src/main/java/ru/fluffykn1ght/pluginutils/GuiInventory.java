package ru.fluffykn1ght.pluginutils;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

public class GuiInventory implements Listener {
    public Inventory gui;
    public Map<Integer, GuiItem> items;
    public Runnable close = () -> {};
    public final Player player;

    public GuiInventory(Component title, int rows, Map<Integer, GuiItem> items, Player player) {
        this.gui = Bukkit.getServer().createInventory(null, rows * 9, title);
        this.player = player;
        this.changeItems(items);
    }

    public void changeItems() {
        gui.clear();
        for (int itemIndex : this.items.keySet()) {
            GuiItem guiItem = this.items.get(itemIndex);
            gui.setItem(itemIndex, guiItem.stack);
            guiItem.update.run();
        }
    }

    public void changeItems(Map<Integer, GuiItem> newItems) {
        items = newItems;
        gui.clear();
        for (int itemIndex : this.items.keySet()) {
            GuiItem guiItem = this.items.get(itemIndex);
            gui.setItem(itemIndex, guiItem.stack);
            guiItem.update.run();
        }
    }

    public void update() {
        for (int itemIndex : this.items.keySet()) {
            GuiItem guiItem = this.items.get(itemIndex);
            guiItem.update.run();
        }
    }

    public void fillEmptySlots(int row, Material material) {
        for (int i = 9 * row; i < (9 * (row + 1)); i++) {
            if (gui.getItem(i) == null) {
                ItemStack stack = new ItemStack(material);
                ItemMeta meta = stack.getItemMeta();
                meta.displayName(Component.text(""));
                stack.setItemMeta(meta);
                gui.setItem(i, stack);
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory() != gui) {
            return;
        }
        event.setCancelled(true);
        if (event.getRawSlot() > gui.getSize()) {
            return;
        }
        GuiItem item = items.get(event.getSlot());
        if (item != null) {
            item.handleClick(event.getClick());
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getInventory() == gui) {
            close.run();
            event.getHandlers().unregisterAll(this);
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        if (event.getPlayer() == player) {
            close.run();
            event.getHandlers().unregisterAll(this);
        }
    }
}
