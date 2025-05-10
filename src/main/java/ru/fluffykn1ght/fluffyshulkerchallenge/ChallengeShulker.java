package ru.fluffykn1ght.fluffyshulkerchallenge;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import ru.fluffykn1ght.pluginutils.StringDeserializer;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class ChallengeShulker {
    public String name;
    public UUID uuid;
    public Material blockType;
    public Location location;
    public List<ShulkerMob> mobs;
    public Set<ShulkerDropItem> items;
    public int minMoney;
    public int maxMoney;

    public static ChallengeShulker fromConfig(ConfigurationSection config) {
        ChallengeShulker shulker = new ChallengeShulker();
        shulker.uuid = UUID.fromString(config.getName());
        shulker.name = config.getString("name");
        shulker.blockType = Material.valueOf(config.getString("blockType"));
        shulker.location = StringDeserializer.locationXYZ(config.getString("location"));
        for (String uuidString : config.getConfigurationSection("mobs").getKeys(false)) {
            shulker.mobs.add(ShulkerMob.fromConfig(config.getConfigurationSection("mobs." + uuidString)));
        }
        for (String uuidString : config.getConfigurationSection("items").getKeys(false)) {
            shulker.items.add(ShulkerDropItem.fromConfig(config.getConfigurationSection("items." + uuidString)));
        }
        shulker.minMoney = config.getInt("money.min");
        shulker.minMoney = config.getInt("money.max");

        return shulker;
    }
}
