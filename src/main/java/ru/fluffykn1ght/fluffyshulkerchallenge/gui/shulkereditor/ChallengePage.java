package ru.fluffykn1ght.fluffyshulkerchallenge.gui.shulkereditor;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.fluffykn1ght.fluffyshulkerchallenge.ChallengeShulker;
import ru.fluffykn1ght.fluffyshulkerchallenge.FluffyShulkerChallenge;
import ru.fluffykn1ght.fluffyshulkerchallenge.gui.MainGui;
import ru.fluffykn1ght.pluginutils.GuiHandler;
import ru.fluffykn1ght.pluginutils.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChallengePage {
    public static GuiInventory get(Player player, ChallengeShulker shulker, FluffyShulkerChallenge plugin) {
        Map<Integer, GuiItem> items = new HashMap<>();
        items.put(27, new GuiItem(
                        Material.SPECTRAL_ARROW,
                        1,
                        InternalPluginLanguage.get("gui-back"),
                        null
                )
                        .leftClick((guiItem) -> GuiHandler.openGui(MainGui.get(player), player))
        );
        Material challenge;
        if (shulker.survive) {
            challenge = Material.LIME_DYE;
        }
        else {
            challenge = Material.GRAY_DYE;
        }

        items.put(11, new GuiItem(
                challenge,
                1,
                InternalPluginLanguage.get("gui-shulkereditor-challenge-survival-name"),
                InternalPluginLanguage.getAndFormatLore("gui-shulkereditor-challenge-survival-lore", new String[][]{
                        null,
                        new String[] { ValueFancifier.bool(shulker.survive, InternalPluginLanguage.getRaw("shulker-mode-survival"), InternalPluginLanguage.getRaw("shulker-mode-normal")) },
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                })
            )
                .update((guiItem -> {
                    ItemStack newStack;
                    if (shulker.survive) {
                        newStack = new ItemStack(Material.LIME_DYE, 1);
                    }
                    else {
                        newStack = new ItemStack(Material.GRAY_DYE, 1);
                    }
                    guiItem.setStack(newStack);
                    guiItem.stack.lore(
                            InternalPluginLanguage.getAndFormatLore("gui-shulkereditor-challenge-survival-lore", new String[][]{
                                    null,
                                    new String[] { ValueFancifier.bool(shulker.survive, InternalPluginLanguage.getRaw("shulker-mode-survival"), InternalPluginLanguage.getRaw("shulker-mode-normal")) },
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null
                    }));
                }))

                .leftClick((guiItem) -> {
                    //plugin.challengeShulkerHandler.shulkers.remove(shulker);
                    shulker.survive = !shulker.survive;
                    if (shulker.survive && shulker.time <= 0) {
                        shulker.time = 60;
                    }
                    //plugin.challengeShulkerHandler.shulkers.add(shulker);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
                    guiItem.gui.update();
                })
        );

        List<Component> timeLore;
        if (shulker.survive) {
            timeLore = InternalPluginLanguage.getAndFormatLore(
                    "gui-shulkereditor-challenge-time-lore-survival",
                    new String[][]{
                            new String[]{String.valueOf(shulker.time)},
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null
                    }
            );
        }
        else {
            timeLore = InternalPluginLanguage.getAndFormatLore(
                    "gui-shulkereditor-challenge-time-lore-normal",
                    new String[][]{
                            new String[]{String.valueOf(shulker.time)},
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null
                    }
            );
        }



        items.put(12, new GuiItem(
                Material.CLOCK,
                1,
                InternalPluginLanguage.get("gui-shulkereditor-challenge-time-name"),
                timeLore
            )
                .update((guiItem) -> {
                    List<Component> newLore;
                    if (shulker.survive) {
                        newLore = InternalPluginLanguage.getAndFormatLore(
                                "gui-shulkereditor-challenge-time-lore-survival",
                                new String[][]{
                                        new String[]{String.valueOf(shulker.time)},
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null
                                }
                        );
                    } else {
                        newLore = InternalPluginLanguage.getAndFormatLore(
                                "gui-shulkereditor-challenge-time-lore-normal",
                                new String[][]{
                                        new String[]{String.valueOf(shulker.time)},
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null
                                }
                        );
                    }
                    guiItem.stack.lore(newLore);
                }
            )
                .leftClick((guiItem) -> {
                    shulker.time += 1;
                    guiItem.gui.update();
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
                })
                .rightClick((guiItem) -> {
                    if ((shulker.survive && shulker.time - 1 == 0) || shulker.time -1 < 0) {
                        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 0.5F);
                        return;
                    }
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
                    shulker.time -= 1;
                    guiItem.gui.update();
                })
                .shiftLeftClick((guiItem) -> {
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
                    shulker.time += 60;
                    guiItem.gui.update();
                })
                .shiftRightClick((guiItem) -> {
                    if ((shulker.survive && shulker.time - 60 == 0) || shulker.time -1 < 60) {
                        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 0.5F);
                        return;
                    }
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
                    shulker.time -= 60;
                    guiItem.gui.update();
                })
                .drop((guiItem) -> {
                    if (shulker.survive) {
                        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 0.5F);
                    }
                    else {
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
                        shulker.time = 0;
                        guiItem.gui.update();
                    }
                })
        );

        GuiInventory gui = new GuiInventory(InternalPluginLanguage.getAndFormat("gui-shulkereditor-challenge-title", new String[]{shulker.name}), 4, items, player);
        gui.fillEmptySlots(0, Material.GRAY_STAINED_GLASS_PANE);
        gui.fillEmptySlots(1, Material.GRAY_STAINED_GLASS_PANE);
        gui.fillEmptySlots(2, Material.GRAY_STAINED_GLASS_PANE);
        gui.fillEmptySlots(3, Material.PURPLE_STAINED_GLASS_PANE);
        gui.close = () -> shulker.saveToConfig(plugin.data);
        return gui;
    }
}
