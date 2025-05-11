package ru.fluffykn1ght.pluginutils;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ItemStackPicker implements Listener {
    public final Consumer<ItemStack> callback;
    public final Runnable cancel;
    public final Player player;
    private final JavaPlugin plugin;
    private ForwarderGuiInventory gui;

    public ItemStackPicker(Consumer<ItemStack> callback, Runnable cancel, Player player, JavaPlugin plugin) {
        this.callback = callback;
        this.cancel = cancel;
        this.player = player;
        this.plugin = plugin;
    }

    public static void askForItem(Consumer<ItemStack> callback, Runnable cancel, Player player, JavaPlugin plugin) {
        ItemStackPicker itemStackPicker = new ItemStackPicker(callback, cancel, player, plugin);

        Map<Integer, GuiItem> items = new HashMap<>();
        items.put(18, new GuiItem(
                Material.BARRIER,
                1,
                PluginLanguage.get("gui-back"),
                new ArrayList<>(),
                () -> {}
            )
                .leftClick(() -> {
                    cancel.run();
                    itemStackPicker.destroy();
                })
        );

        itemStackPicker.gui = new ForwarderGuiInventory(
                PluginLanguage.get("iteminput-title"),
                3,
                items,
                player,
                (InventoryClickEvent event) -> {
                    if (event.getClickedInventory() != null) {
                        callback.accept(event.getClickedInventory().getItem(event.getSlot()));
                    }
                },
                plugin
        );
        itemStackPicker.gui.fillEmptySlots(0, Material.BLACK_STAINED_GLASS_PANE);
        itemStackPicker.gui.fillEmptySlots(1, Material.BLACK_STAINED_GLASS_PANE);
        itemStackPicker.gui.fillEmptySlots(2, Material.BLACK_STAINED_GLASS_PANE);
        player.openInventory(itemStackPicker.gui.gui);
    }

    private void destroy() {
        HandlerList.unregisterAll(this);
        gui.gui.close();
    }
}

class ForwarderGuiInventory extends GuiInventory {
    private final Consumer<InventoryClickEvent> callback;

    public ForwarderGuiInventory(Component title, int rows, Map<Integer, GuiItem> items, Player player, Consumer<InventoryClickEvent> callback, JavaPlugin plugin) {
        super(title, rows, items, player);
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.callback = callback;
    }

    @Override
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory() != gui) {
            return;
        }
        event.setCancelled(true);
        if (event.getRawSlot() > gui.getSize()) {
            if (event.getClickedInventory() == event.getWhoClicked().getInventory()) {
                if (event.getClickedInventory().getItem(event.getSlot()) != null) {
                    callback.accept(event);
                    HandlerList.unregisterAll(this);
                }
            }
        }
        GuiItem item = items.get(event.getSlot());
        if (item != null) {
            item.handleClick(event.getClick());
        }
    }
}