package ru.fluffykn1ght.fluffyshulkerchallenge;

import java.util.HashSet;
import java.util.Set;

public class ChallengeShulkerHandler {
    private final FluffyShulkerChallenge plugin;

    public Set<ChallengeShulker> shulkers = new HashSet<>();

    public ChallengeShulkerHandler(FluffyShulkerChallenge plugin) {
        this.plugin = plugin;
    }

    public void reloadShulkers() {
        plugin.getLogger().info("Загрузка шалкеров из файла данных...");
        shulkers.clear();
        for (String shulkerUuidString : plugin.data.getConfigurationSection("shulkers").getKeys(false)) {
            shulkers.add(ChallengeShulker.fromConfig(plugin.data.getConfigurationSection("shulkers." + shulkerUuidString)));
        }
        plugin.getLogger().info("Загружено " + shulkers.size() + " шалкеров");
    }
}
