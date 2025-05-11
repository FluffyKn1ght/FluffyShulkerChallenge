package ru.fluffykn1ght.fluffyshulkerchallenge;

import org.bukkit.Server;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ShulkerSpawn {
    public boolean autospawn = false;
    public List<UUID> worldUuids = new ArrayList<>();
    public List<World> worlds = new ArrayList<>();
    public double xrange = 1000;
    public double zrange = 1000;
    public int period = 600;

    public static ShulkerSpawn fromString(String string) {
        // autospawn/world1,world2,.../xrange/zrange/period
        String[] data = string.split("/");
        ShulkerSpawn spawn = new ShulkerSpawn();

        spawn.autospawn = Boolean.parseBoolean(data[0]);
        spawn.xrange = Double.parseDouble(data[2]);
        spawn.zrange = Double.parseDouble(data[3]);
        spawn.period = Integer.parseInt(data[4]);
        for (String worldUuidString : data[1].split(",")) {
            spawn.worldUuids.add(UUID.fromString(worldUuidString));
        }

        return spawn;
    }

    public void getWorlds(Server server) {
        for (UUID worldUuid : worldUuids) {
            World world = server.getWorld(worldUuid);
            if (world != null) {
                worlds.add(world);
            }
        }
    }

    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append(autospawn).append("/");
        StringBuilder uuids = new StringBuilder();
        for (UUID worldUuid : worldUuids) {
            if (!uuids.toString().isEmpty()) {
                uuids.append(",");
            }
            uuids.append(worldUuid);
        }
        string.append(uuids).append("/");
        string.append(xrange).append("/");
        string.append(zrange).append("/");
        string.append(period);

        return string.toString();
    }
}
