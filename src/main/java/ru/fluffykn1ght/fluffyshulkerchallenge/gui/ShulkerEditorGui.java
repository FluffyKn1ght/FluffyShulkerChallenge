package ru.fluffykn1ght.fluffyshulkerchallenge.gui;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import ru.fluffykn1ght.fluffyshulkerchallenge.ChallengeShulker;
import ru.fluffykn1ght.fluffyshulkerchallenge.FluffyShulkerChallenge;
import ru.fluffykn1ght.fluffyshulkerchallenge.UserInterfaces;
import ru.fluffykn1ght.pluginutils.*;

import java.util.HashMap;
import java.util.Map;

enum ShulkerEditorPage {
    MAIN,
    BLOCKTYPE,
    MOBS,
    ITEMS,
    CHALLENGE
}

public class ShulkerEditorGui {
    public static GuiInventory get(Player player, ChallengeShulker shulker, FluffyShulkerChallenge plugin) {
        return get(player, shulker, ShulkerEditorPage.MAIN, plugin);
    }

    public static GuiInventory get(Player player, ChallengeShulker shulker, ShulkerEditorPage page, FluffyShulkerChallenge plugin) {
        switch (page) {
            case MAIN -> {
                return MainPage.get(player, shulker, plugin);
            }
        }
        return get(player, shulker, ShulkerEditorPage.MAIN, plugin);
    }
}

class MainPage {
    public static GuiInventory get(Player player, ChallengeShulker shulker, FluffyShulkerChallenge plugin) {
        Map<Integer, GuiItem> items = new HashMap<>();
        items.put(27, new GuiItem(
                        Material.SPECTRAL_ARROW,
                        1,
                        InternalPluginLanguage.get("gui-back"),
                        null,
                        () -> {
                        }
                )
                        .leftClick(() -> UserInterfaces.openShulkerGui(player))
        );
        items.put(10, new GuiItem(
                shulker.blockType,
                1,
                InternalPluginLanguage.getAndFormat(
                        "gui-shulkers-shulker-name",
                        new String[]{shulker.name}
                ),
                InternalPluginLanguage.getAndFormatLore("gui-shulkereditor-shulker-lore",
                        new String[][]{
                                new String[]{
                                        shulker.uuid.toString()
                                },
                                null,
                                new String[]{
                                        String.valueOf(shulker.mobs.size())
                                },
                                new String[]{
                                        String.valueOf(shulker.items.size()),
                                        "$",
                                        String.valueOf(shulker.minMoney),
                                        String.valueOf(shulker.maxMoney)
                                },
                                new String[]{
                                        "<В разработке!>"
                                },
                                new String[]{
                                        String.valueOf(shulker.time)
                                },
                                new String[]{
                                        "<TODO>"
                                },
                                null,
                                null
                        }
                ),
                () -> {})
        );

        items.put(12, new GuiItem(
                Material.PURPLE_GLAZED_TERRACOTTA,
                1,
                InternalPluginLanguage.get("gui-shulkereditor-main-blocktype-name"),
                InternalPluginLanguage.getLore("gui-shulkereditor-main-blocktype-lore"),
                () -> {}
            )
                .leftClick(() -> ItemStackPicker.askForItem(
                        (ItemStack stack) -> {
                            if (!stack.getType().isSolid()) {
                                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 0.5F);
                            }
                            else {
                                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
                                shulker.blockType = stack.getType();
                            }
                            UserInterfaces.openShulkerEditorGui(player, shulker);
                        },
                        () -> UserInterfaces.openShulkerEditorGui(player, shulker),
                        player, plugin
                ))
        );
        items.put(13, new GuiItem(
                //Material.FOX_SPAWN_EGG,
                Material.RED_STAINED_GLASS_PANE,
                1,
                InternalPluginLanguage.get("gui-shulkereditor-main-spawn-name"),
                InternalPluginLanguage.getLore("gui-shulkereditor-main-spawn-lore"),
                () -> {}
        ));
        items.put(14, new GuiItem(
                Material.CREEPER_HEAD,
                1,
                InternalPluginLanguage.get("gui-shulkereditor-main-mobs-name"),
                InternalPluginLanguage.getLore("gui-shulkereditor-main-mobs-lore"),
                () -> {}
        ));
        items.put(15, new GuiItem(
                Material.CHEST,
                1,
                InternalPluginLanguage.get("gui-shulkereditor-main-items-name"),
                InternalPluginLanguage.getLore("gui-shulkereditor-main-items-lore"),
                () -> {}
        ));
        items.put(16, new GuiItem(
                Material.COMMAND_BLOCK_MINECART,
                1,
                InternalPluginLanguage.get("gui-shulkereditor-main-challenge-name"),
                InternalPluginLanguage.getLore("gui-shulkereditor-main-challenge-lore"),
                () -> {}
        ));

        GuiInventory gui = new GuiInventory(InternalPluginLanguage.getAndFormat("gui-shulkereditor-main-title", new String[]{shulker.name}), 4, items, player);
        gui.fillEmptySlots(0, Material.GRAY_STAINED_GLASS_PANE);
        gui.fillEmptySlots(1, Material.GRAY_STAINED_GLASS_PANE);
        gui.fillEmptySlots(2, Material.GRAY_STAINED_GLASS_PANE);
        gui.fillEmptySlots(3, Material.PURPLE_STAINED_GLASS_PANE);
        return gui;
    }
}
