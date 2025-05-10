package ru.fluffykn1ght.pluginutils;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.Set;

public class ConfigurationUpgrader {
    public static YamlConfiguration upgrade(YamlConfiguration oldConfig, YamlConfiguration newConfig) {
        YamlConfiguration result = new YamlConfiguration();
        try {
            result.loadFromString(oldConfig.saveToString());
        } catch (InvalidConfigurationException e) {
            Bukkit.getLogger().severe("Ошибка апгрейда одного из YAML файлов! - Неверный синтаксис");
            return null;
        }
        Set<String> oldConfigKeys = oldConfig.getKeys(false);
        Set<String> newConfigKeys = newConfig.getKeys(false);
        for (String key : newConfigKeys) {
            if (!oldConfigKeys.contains(key)) {
                result.set(key, newConfig.get(key));
            }
        }
        for (String key : oldConfigKeys) {
            if (!newConfigKeys.contains(key)) {
                result.set("LEGACY-CONFIG-VALUES." + key, oldConfig.get(key));
            }
        }
        return result;
    }
}
