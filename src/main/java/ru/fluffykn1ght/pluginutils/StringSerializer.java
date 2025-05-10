package ru.fluffykn1ght.pluginutils;

import org.bukkit.Location;

public class StringSerializer {
    public static String locationXYZ(Location location) {
        return location.getWorld() + "/" + location.getX() + "/" + location.getY() + "/" + location.getZ();
    }
}
