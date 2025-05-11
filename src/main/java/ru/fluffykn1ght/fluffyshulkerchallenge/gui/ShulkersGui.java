package ru.fluffykn1ght.fluffyshulkerchallenge.gui;

import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import ru.fluffykn1ght.fluffyshulkerchallenge.ChallengeShulker;
import ru.fluffykn1ght.fluffyshulkerchallenge.FluffyShulkerChallenge;
import ru.fluffykn1ght.fluffyshulkerchallenge.UserInterfaces;
import ru.fluffykn1ght.pluginutils.ChatInputHandler;
import ru.fluffykn1ght.pluginutils.GuiInventory;
import ru.fluffykn1ght.pluginutils.GuiItem;
import ru.fluffykn1ght.pluginutils.PluginLanguage;

import java.util.HashMap;
import java.util.Map;

public class ShulkersGui {
    public static GuiInventory get(Player player, FluffyShulkerChallenge plugin) {
        Map<Integer, GuiItem> items = new HashMap<>();
        items.put(36, new GuiItem(
                        Material.SPECTRAL_ARROW,
                        1,
                        PluginLanguage.get("gui-back"),
                        null,
                        () -> {}
                )
                        .leftClick(() -> UserInterfaces.openMainGui(player))
        );
        items.put(40, new GuiItem(
                        Material.ANVIL,
                        1,
                        PluginLanguage.get("gui-shulkers-new-name"),
                        PluginLanguage.getLore("gui-shulkers-new-lore"),
                        () -> {}
                )
                        .leftClick(() -> ChatInputHandler.askForChatInput(
                                (AsyncChatEvent event) -> {
                                    ChallengeShulker shulker = plugin.challengeShulkerHandler.createShulker(
                                            player,
                                            ChatInputHandler.getContent(event.originalMessage())
                                    );
                                    UserInterfaces.openShulkerEditorGui(player, shulker);
                                },
                                () -> UserInterfaces.openShulkerGui(player), // TODO
                                player,
                                true,
                                "Введите имя для нового шалкера:",
                                plugin
                        ))
        );

        int i = 0;
        for (ChallengeShulker shulker : plugin.challengeShulkerHandler.shulkers) {
            items.put(i, new GuiItem(
                    shulker.blockType,
                    1,
                    PluginLanguage.getAndFormat(
                            "gui-shulkers-shulker-name",
                            new String[]{shulker.name}
                    ),
                    PluginLanguage.getAndFormatLore("gui-shulkers-shulker-lore",
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
                                            "$", // TODO: Поддержка Vault
                                            String.valueOf(shulker.minMoney),
                                            String.valueOf(shulker.maxMoney)
                                    },
                                    new String[]{
                                            "<TODO>"
                                    },
                                    new String[]{
                                            String.valueOf(shulker.time)
                                    },
                                    new String[]{
                                            "<TODO>"
                                    },
                                    null,
                                    null,
                                    null,
                                    null
                            }
                    ),
                    () -> {})
                    .rightClick(() -> UserInterfaces.openShulkerEditorGui(player, shulker))
            );

            i++;
        }

        GuiInventory gui = new GuiInventory(PluginLanguage.get("gui-shulkers-title"), 5, items, player);
        gui.fillEmptySlots(0, Material.GRAY_STAINED_GLASS_PANE);
        gui.fillEmptySlots(1, Material.GRAY_STAINED_GLASS_PANE);
        gui.fillEmptySlots(2, Material.GRAY_STAINED_GLASS_PANE);
        gui.fillEmptySlots(3, Material.GRAY_STAINED_GLASS_PANE);
        gui.fillEmptySlots(4, Material.PURPLE_STAINED_GLASS_PANE);
        return gui;
    }
}
