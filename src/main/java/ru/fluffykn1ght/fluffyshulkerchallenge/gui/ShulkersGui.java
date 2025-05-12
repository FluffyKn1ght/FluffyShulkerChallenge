package ru.fluffykn1ght.fluffyshulkerchallenge.gui;

import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import ru.fluffykn1ght.fluffyshulkerchallenge.ChallengeShulker;
import ru.fluffykn1ght.fluffyshulkerchallenge.FluffyShulkerChallenge;
import ru.fluffykn1ght.fluffyshulkerchallenge.gui.shulkereditor.MainPage;
import ru.fluffykn1ght.pluginutils.GuiHandler;
import ru.fluffykn1ght.pluginutils.*;

import java.util.HashMap;
import java.util.Map;

public class ShulkersGui {
    public static GuiInventory get(Player player, FluffyShulkerChallenge plugin) {
        Map<Integer, GuiItem> items = new HashMap<>();
        items.put(36, new GuiItem(
                        Material.SPECTRAL_ARROW,
                        1,
                        InternalPluginLanguage.get("gui-back"),
                        null
                )
                        .leftClick((guiItem) -> GuiHandler.openGui(MainGui.get(player), player))
        );
        items.put(40, new GuiItem(
                        Material.ANVIL,
                        1,
                        InternalPluginLanguage.get("gui-shulkers-new-name"),
                        InternalPluginLanguage.getLore("gui-shulkers-new-lore")
                )
                        .leftClick((guiItem) -> ChatInputHandler.askForChatInput(
                                (AsyncChatEvent event) -> {
                                    ChallengeShulker shulker = plugin.challengeShulkerHandler.createShulker(
                                            player,
                                            ChatInputHandler.getContent(event.originalMessage())
                                    );
                                    GuiHandler.openGui(MainPage.get(player, shulker, plugin), player);
                                },
                                () -> GuiHandler.openGui(MainGui.get(player), player),
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
                    InternalPluginLanguage.getAndFormat(
                            "gui-shulkers-shulker-name",
                            new String[]{shulker.name}
                    ),
                    InternalPluginLanguage.getAndFormatLore("gui-shulkers-shulker-lore",
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
                                    null,
                                    null,
                                    null
                            }
                    ))
                    .rightClick((guiItem) -> GuiHandler.openGui(MainPage.get(player, shulker, plugin), player))
            );

            i++;
        }

        GuiInventory gui = new GuiInventory(InternalPluginLanguage.get("gui-shulkers-title"), 5, items, player);
        gui.fillEmptySlots(0, Material.GRAY_STAINED_GLASS_PANE);
        gui.fillEmptySlots(1, Material.GRAY_STAINED_GLASS_PANE);
        gui.fillEmptySlots(2, Material.GRAY_STAINED_GLASS_PANE);
        gui.fillEmptySlots(3, Material.GRAY_STAINED_GLASS_PANE);
        gui.fillEmptySlots(4, Material.PURPLE_STAINED_GLASS_PANE);
        return gui;
    }
}
