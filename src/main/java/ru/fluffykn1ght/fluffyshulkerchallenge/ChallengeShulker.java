package ru.fluffykn1ght.fluffyshulkerchallenge;

import org.bukkit.Location;
import org.bukkit.Material;

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
}
