package ru.fluffykn1ght.fluffyshulkerchallenge;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.*;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class ShulkerMob {
    public EntityType type;
    public String name;
    public UUID uuid = UUID.randomUUID();
    public ItemStack head;
    public ItemStack chest;
    public ItemStack pants;
    public ItemStack boots;
    public ItemStack hand;

    public static ShulkerMob fromConfig(ConfigurationSection config) {
        ShulkerMob mob = new ShulkerMob();
        mob.name = config.getString("name");
        mob.uuid = UUID.fromString(config.getName());
        mob.type = EntityType.valueOf(config.getString("type"));
        mob.head = config.getItemStack("head");
        mob.chest = config.getItemStack("chest");
        mob.pants = config.getItemStack("pants");
        mob.boots = config.getItemStack("boots");
        mob.hand = config.getItemStack("hand");
        return mob;
    }

    public Entity spawn(Location location) {
        Entity entity = location.getWorld().spawnEntity(location, type);
        if (entity instanceof HumanEntity) {
            HumanEntity humanEntity = (HumanEntity) entity;
            EntityEquipment equipment = humanEntity.getEquipment();
            equipment.setHelmet(head);
            equipment.setChestplate(chest);
            equipment.setLeggings(pants);
            equipment.setBoots(boots);
            equipment.setItemInMainHand(hand);
            return entity;
        }
        else {
            entity.remove();
            return null;
        }
    }
}
