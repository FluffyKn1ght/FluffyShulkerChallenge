package ru.fluffykn1ght.pluginutils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GuiItem {
    public ItemStack stack;

    private final Runnable leftClick;
    private final Runnable rightClick;
    private final Runnable shiftLeftClick;
    private final Runnable shiftRightClick;
    private final Runnable middleClick;
    private final Runnable drop;

    public final Runnable update;

    public GuiItem(Material material, int amount, @Nullable Component name, @Nullable List<Component> lore, @Nullable Runnable update) {
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
        this.leftClick = () -> {};
        this.rightClick = () -> {};
        this.shiftLeftClick = () -> {};
        this.shiftRightClick = () -> {};
        this.middleClick = () -> {};
        this.drop = () -> {};
        if (update == null) {
            this.update = () -> {};
        }
        else {
            this.update = update;
        }
    }

    public GuiItem(ItemStack stack, @Nullable Runnable update, @Nullable Runnable leftClick, @Nullable Runnable rightClick, @Nullable Runnable shiftLeftClick, @Nullable Runnable shiftRightClick, @Nullable Runnable middleClick, @Nullable Runnable drop) {
        this.stack = stack;
        this.leftClick = leftClick;
        this.rightClick = rightClick;
        this.shiftLeftClick = shiftLeftClick;
        this.shiftRightClick = shiftRightClick;
        this.middleClick = middleClick;
        this.drop = drop;
        if (update == null) {
            this.update = () -> {};
        }
        else {
            this.update = update;
        }
    }

    public GuiItem leftClick(Runnable run) {
        return new GuiItem(stack,
                this.update,
                run,
                this.rightClick,
                this.shiftLeftClick,
                this.shiftRightClick,
                this.middleClick,
                this.drop
        );
    }

    public GuiItem rightClick(Runnable run) {
        return new GuiItem(stack,
                this.update,
                this.leftClick,
                run,
                this.shiftLeftClick,
                this.shiftRightClick,
                this.middleClick,
                this.drop
        );
    }

    public GuiItem shiftLeftClick(Runnable run) {
        return new GuiItem(stack,
                this.update,
                this.leftClick,
                this.rightClick,
                run,
                this.shiftRightClick,
                this.middleClick,
                this.drop
        );
    }

    public GuiItem shiftRightClick(Runnable run) {
        return new GuiItem(stack,
                this.update,
                this.leftClick,
                this.rightClick,
                this.shiftLeftClick,
                run,
                this.middleClick,
                this.drop
        );
    }

    public GuiItem middleClick(Runnable run) {
        return new GuiItem(stack,
                this.update,
                this.middleClick,
                this.rightClick,
                this.shiftLeftClick,
                this.shiftRightClick,
                run,
                this.drop
        );
    }

    public GuiItem drop(Runnable run) {
        return new GuiItem(stack,
                this.update,
                this.leftClick,
                this.rightClick,
                this.shiftLeftClick,
                this.shiftRightClick,
                this.middleClick,
                run
        );
    }

    public void handleClick(ClickType click) {
        switch (click) {
            case LEFT -> leftClick.run();
            case RIGHT -> rightClick.run();
            case SHIFT_LEFT -> shiftLeftClick.run();
            case SHIFT_RIGHT -> shiftRightClick.run();
            case MIDDLE -> middleClick.run();
            case DROP -> drop.run();
        }
    }
}
