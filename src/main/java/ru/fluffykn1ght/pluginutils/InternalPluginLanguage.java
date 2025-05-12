package ru.fluffykn1ght.pluginutils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class InternalPluginLanguage extends PluginLanguage {
    private static YamlConfiguration lang;

    public static void init(JavaPlugin plugin) {
        InternalPluginLanguage.lang = YamlConfiguration.loadConfiguration(new InputStreamReader(plugin.getResource("lang.yaml")));
    }

    public static void setLang(YamlConfiguration lang) {
        // этого случится НИКОГДА не должно, но всёёёёёё жеееее...
        Bukkit.getLogger().warning("КАКОГО ФИГА БЫЛ ВЫЗВАН МЕТОД InternalPluginLanguage.setLang()?!?!?!?!?");
    }
}
