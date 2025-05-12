package ru.fluffykn1ght.pluginutils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class GuiItem {
    public ItemStack stack;

    private final Consumer<GuiItem> leftClick;
    private final Consumer<GuiItem> rightClick;
    private final Consumer<GuiItem> shiftLeftClick;
    private final Consumer<GuiItem> shiftRightClick;
    private final Consumer<GuiItem> middleClick;
    private final Consumer<GuiItem> drop;

    public final Consumer<GuiItem> update;

    public GuiInventory gui;

    public GuiItem(Material material, int amount, @Nullable Component name, @Nullable List<Component> lore) {
        this.stack = new ItemStack(material, amount);
        if (name != null) {
            ItemMeta meta = this.stack.getItemMeta();
            meta.displayName(name);
            meta.displayName(meta.displayName().decoration(TextDecoration.ITALIC, false));
            this.stack.setItemMeta(meta);
        }
        if (lore != null) {
            this.stack.lore(lore);
        }
        this.leftClick = (guiItem) -> {};
        this.rightClick = (guiItem) -> {};
        this.shiftLeftClick = (guiItem) -> {};
        this.shiftRightClick = (guiItem) -> {};
        this.middleClick = (guiItem) -> {};
        this.drop = (guiItem) -> {};
        this.update = (item) -> {};
    }

    public GuiItem(ItemStack stack, @Nullable Consumer<GuiItem> update, @Nullable Consumer<GuiItem> leftClick, @Nullable Consumer<GuiItem> rightClick, @Nullable Consumer<GuiItem> shiftLeftClick, @Nullable Consumer<GuiItem> shiftRightClick, @Nullable Consumer<GuiItem> middleClick, @Nullable Consumer<GuiItem> drop, GuiInventory gui) {
        this.stack = stack;
        this.leftClick = leftClick;
        this.rightClick = rightClick;
        this.shiftLeftClick = shiftLeftClick;
        this.shiftRightClick = shiftRightClick;
        this.middleClick = middleClick;
        this.drop = drop;
        this.update = update;
        this.gui = gui;
    }

    public GuiItem leftClick(Consumer<GuiItem> run) {
        return new GuiItem(stack,
                this.update,
                run,
                this.rightClick,
                this.shiftLeftClick,
                this.shiftRightClick,
                this.middleClick,
                this.drop,
                this.gui
        );
    }

    public GuiItem rightClick(Consumer<GuiItem> run) {
        return new GuiItem(stack,
                this.update,
                this.leftClick,
                run,
                this.shiftLeftClick,
                this.shiftRightClick,
                this.middleClick,
                this.drop,
                this.gui
        );
    }

    public GuiItem shiftLeftClick(Consumer<GuiItem> run) {
        return new GuiItem(stack,
                this.update,
                this.leftClick,
                this.rightClick,
                run,
                this.shiftRightClick,
                this.middleClick,
                this.drop,
                this.gui
        );
    }

    public GuiItem shiftRightClick(Consumer<GuiItem> run) {
        return new GuiItem(stack,
                this.update,
                this.leftClick,
                this.rightClick,
                this.shiftLeftClick,
                run,
                this.middleClick,
                this.drop,
                this.gui
        );
    }

    public GuiItem middleClick(Consumer<GuiItem> run) {
        return new GuiItem(stack,
                this.update,
                this.middleClick,
                this.rightClick,
                this.shiftLeftClick,
                this.shiftRightClick,
                run,
                this.drop,
                this.gui
        );
    }

    public GuiItem drop(Consumer<GuiItem> run) {
        return new GuiItem(stack,
                this.update,
                this.leftClick,
                this.rightClick,
                this.shiftLeftClick,
                this.shiftRightClick,
                this.middleClick,
                run,
                this.gui
        );
    }

    public GuiItem update(Consumer<GuiItem> run) {
        return new GuiItem(stack,
                run,
                this.leftClick,
                this.rightClick,
                this.shiftLeftClick,
                this.shiftRightClick,
                this.middleClick,
                this.drop,
                this.gui
        );
    }

    public void handleClick(ClickType click) {
        switch (click) {
            case LEFT -> leftClick.accept(this);
            case RIGHT -> rightClick.accept(this);
            case SHIFT_LEFT -> shiftLeftClick.accept(this);
            case SHIFT_RIGHT -> shiftRightClick.accept(this);
            case MIDDLE -> middleClick.accept(this);
            case DROP -> drop.accept(this);
        }
    }

    public void setStack(ItemStack stack) {
        ItemMeta oldMeta = this.stack.getItemMeta();
        this.stack = stack;
        this.stack.setItemMeta(oldMeta);
    }
}
