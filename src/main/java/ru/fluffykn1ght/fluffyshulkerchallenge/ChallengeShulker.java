package ru.fluffykn1ght.fluffyshulkerchallenge;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import ru.fluffykn1ght.pluginutils.StringDeserializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class ChallengeShulker {
    public String name;
    public UUID uuid;
    public Material blockType;
    public ShulkerSpawn spawn;
    public List<ShulkerMob> mobs;
    public Set<ShulkerDropItem> items;
    public int minMoney;
    public int maxMoney;
    public int time;

    public static ChallengeShulker fromConfig(ConfigurationSection config) {
        ChallengeShulker shulker = new ChallengeShulker();
        shulker.uuid = UUID.fromString(config.getName());
        shulker.name = config.getString("name");
        shulker.blockType = Material.valueOf(config.getString("blockType"));
        shulker.spawn = ShulkerSpawn.fromString(config.getString("spawn"));
        for (String uuidString : config.getConfigurationSection("mobs").getKeys(false)) {
            shulker.mobs.add(ShulkerMob.fromConfig(config.getRoot().getConfigurationSection("mobs." + uuidString)));
        }
        for (String uuidString : config.getConfigurationSection("items").getKeys(false)) {
            shulker.items.add(ShulkerDropItem.fromConfig(config.getConfigurationSection("items." + uuidString)));
        }
        shulker.minMoney = config.getInt("money.min");
        shulker.maxMoney = config.getInt("money.max");
        shulker.time = config.getInt("time");

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
    }
}
