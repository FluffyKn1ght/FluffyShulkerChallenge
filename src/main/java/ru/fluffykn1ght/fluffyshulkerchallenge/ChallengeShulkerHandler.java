package ru.fluffykn1ght.fluffyshulkerchallenge;

import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class ChallengeShulkerHandler {
    private final FluffyShulkerChallenge plugin;

    public Set<ChallengeShulker> shulkers = new HashSet<>();

    public ChallengeShulkerHandler(FluffyShulkerChallenge plugin) {
        this.plugin = plugin;
    }

    public void reloadShulkers() {
        plugin.getLogger().info("Загрузка шалкеров из файла данных...");
        shulkers.clear();
        if (plugin.data.getConfigurationSection("shulkers") == null) { return; }
        for (String shulkerUuidString : plugin.data.getConfigurationSection("shulkers").getKeys(false)) {
            shulkers.add(ChallengeShulker.fromConfig(plugin.data.getConfigurationSection("shulkers." + shulkerUuidString)));
        }
        plugin.getLogger().info("Загружено " + shulkers.size() + " шалкеров");
    }

    public ChallengeShulker createShulker(Player player, String name) {
        ChallengeShulker shulker = new ChallengeShulker();
        shulker.name = name;
        shulker.spawn = new ShulkerSpawn();
        shulkers.add(shulker);
        shulker.saveToConfig(plugin.data);
        plugin.saveData();
        return shulker;
    }
}
