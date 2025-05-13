package ru.fluffykn1ght.fluffyshulkerchallenge.gui.shulkereditor;

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
                    //plugin.challengeShulkerHandler.shulkers.add(shulker);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
                    guiItem.gui.update();
                })
        );

        GuiInventory gui = new GuiInventory(InternalPluginLanguage.getAndFormat("gui-shulkereditor-challenge-title", new String[]{shulker.name}), 4, items, player);
        gui.fillEmptySlots(0, Material.GRAY_STAINED_GLASS_PANE);
        gui.fillEmptySlots(1, Material.GRAY_STAINED_GLASS_PANE);
        gui.fillEmptySlots(2, Material.GRAY_STAINED_GLASS_PANE);
        gui.fillEmptySlots(3, Material.PURPLE_STAINED_GLASS_PANE);
        return gui;
    }
}
