package ru.fluffykn1ght.pluginutils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.List;

public class PluginLanguage {
    private static YamlConfiguration lang;

    public static void setLang(YamlConfiguration lang) {
        PluginLanguage.lang = lang;
    }

    public static Component get(String name) {
        Component component = MiniMessage.miniMessage().deserialize(lang.getString(name, "[Unknown string: " + name + "]"));
        return component.decoration(TextDecoration.ITALIC, false);
    }

    public static Component get(String name, boolean keepItalics) {
        Component component = MiniMessage.miniMessage().deserialize(lang.getString(name, "[Unknown string: " + name + "]"));
        if (keepItalics) {
            return component;
        }
        return component.decoration(TextDecoration.ITALIC, false);
    }

    public static Component getAndFormat(String name, String[] format) {
        Component component = MiniMessage.miniMessage().deserialize(lang.getString(name, "[Unknown string: " + name + "]").formatted(format));
        return component.decoration(TextDecoration.ITALIC, false);
    }

    public static Component getAndFormat(String name, String[] format, boolean keepItalics) {
        Component component = MiniMessage.miniMessage().deserialize(lang.getString(name, "[Unknown string: " + name + "]").formatted(format));
        if (keepItalics) {
            return component;
        }
        return component.decoration(TextDecoration.ITALIC, false);
    }

    public static List<Component> getLore(String name) {
        List<Component> out = new ArrayList<>();
        for (String rawComponent : lang.getStringList(name)) {
            out.add(MiniMessage.miniMessage().deserialize(rawComponent).decoration(TextDecoration.ITALIC, false));
        }
        return out;
    }

    public static List<Component> getAndFormatLore(String name, String[][] formats) {
        List<Component> out = new ArrayList<>();
        int i = 0;
        for (String rawComponent : lang.getStringList(name)) {
            if (formats[i] != null) {
                out.add(MiniMessage.miniMessage().deserialize(rawComponent.formatted(formats[i])).decoration(TextDecoration.ITALIC, false));
            }
            else {
                out.add(MiniMessage.miniMessage().deserialize(rawComponent).decoration(TextDecoration.ITALIC, false));
            }
            i++;
        }
        return out;
    }

    public static String getRaw(String name) {
        return lang.getString(name, "[Unknown string: " + name + "]");
    }
}
