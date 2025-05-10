package ru.fluffykn1ght.pluginutils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class StringDeserializer {
    public static Location locationXYZ(String string) {
        // world/x/y/z
        String[] data = string.split("/");
        return new Location(Bukkit.getWorld(data[0]), Double.parseDouble(data[1]),Double.parseDouble(data[2]),Double.parseDouble(data[3]));
    }
}
