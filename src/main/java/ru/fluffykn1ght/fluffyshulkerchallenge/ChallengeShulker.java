package ru.fluffykn1ght.fluffyshulkerchallenge;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class ChallengeShulker {
    public String name = "Шалкер";
    public UUID uuid = UUID.randomUUID();
    public Material blockType = Material.SHULKER_BOX;
    public ShulkerSpawn spawn;
    public List<ShulkerMob> mobs = new ArrayList<>();
    public Set<ShulkerDropItem> items = new HashSet<>();
    public int minMoney = 0;
    public int maxMoney = 0;
    public int time = 300;
    public boolean survive = false;

    public static ChallengeShulker fromConfig(ConfigurationSection config) {
        ChallengeShulker shulker = new ChallengeShulker();
        shulker.uuid = UUID.fromString(config.getName());
        shulker.name = config.getString("name");
        shulker.blockType = Material.valueOf(config.getString("blockType"));
        shulker.spawn = ShulkerSpawn.fromString(config.getString("spawn"));
        for (String uuidString : config.getConfigurationSection("mobs").getKeys(false)) {
            shulker.mobs.add(ShulkerMob.fromConfig(config.getRoot().getConfigurationSection("mobs." + uuidString)));
        }
        Set<String> itemKeys = config.getConfigurationSection("items").getKeys(false);
        if (itemKeys != null) {
            for (String uuidString : itemKeys) {
                shulker.items.add(ShulkerDropItem.fromConfig(config.getConfigurationSection("items." + uuidString)));
            }
        }
        shulker.minMoney = config.getInt("money.min");
        shulker.maxMoney = config.getInt("money.max");
        shulker.time = config.getInt("challenge.time");
        shulker.survive = config.getBoolean("challenge.survive");

        return shulker;
    }

    public void saveToConfig(Configuration config) {
        String path = "shulkers." + uuid;
        config.set(path + ".name", name);
        config.set(path + ".blockType", blockType.toString());
        List<UUID> mobUuids = new ArrayList<>();
        for (ShulkerMob mob : mobs) {
            mobUuids.add(mob.uuid);
        }
        config.set(path + ".mobs", mobUuids);
        for (ShulkerDropItem item : items) {
            config.set(path + ".items." + item.uuid + ".stack", item.stack);
            config.set(path + ".items." + item.uuid + ".min", item.minAmount);
            config.set(path + ".items." + item.uuid + ".max", item.maxAmount);
        }
        config.set(path + ".money.min", minMoney);
        config.set(path + ".money.max", maxMoney);
        config.set(path + ".challenge.time", time);
        config.set(path + ".challenge.survive", survive);
        config.set(path + ".spawn", spawn.toString());
    }
}
