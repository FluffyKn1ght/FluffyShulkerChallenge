package ru.fluffykn1ght.fluffyshulkerchallenge;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.concurrent.ThreadLocalRandom;

public class ShulkerDropItem {
    public ItemStack stack;
    public int minAmount;
    public int maxAmount;

    public static ShulkerDropItem fromConfig(ConfigurationSection config) {
        ShulkerDropItem drop = new ShulkerDropItem();
        drop.stack = config.getItemStack("stack");
        drop.minAmount = config.getInt("min");
        drop.maxAmount = config.getInt("max");
        return drop;
    }

    public ItemStack drop() {
        ItemStack dropStack = this.stack.clone();
        dropStack.setAmount(ThreadLocalRandom.current().nextInt(minAmount, maxAmount + 1));
        return dropStack;
    }
}
