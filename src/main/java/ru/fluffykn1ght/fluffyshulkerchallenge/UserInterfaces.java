package ru.fluffykn1ght.fluffyshulkerchallenge;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import ru.fluffykn1ght.pluginutils.ChatInputHandler;
import ru.fluffykn1ght.pluginutils.GuiInventory;
import ru.fluffykn1ght.pluginutils.GuiItem;
import ru.fluffykn1ght.pluginutils.PluginLanguage;

import java.util.HashMap;
import java.util.Map;

public class UserInterfaces {
    private static FluffyShulkerChallenge plugin;

    public static void setPlugin(FluffyShulkerChallenge plugin) {
        UserInterfaces.plugin = plugin;
    }


    public static void openMainGui(Player player) {
        openGui(getMainGui(player), player);
    }

    public static void openShulkerGui(Player player) {
        openGui(getShulkersGui(player), player);
    }


    private static void openGui(GuiInventory guiInventory, Player player) {
        plugin.getServer().getPluginManager().registerEvents(guiInventory, plugin);
        player.openInventory(guiInventory.gui);
    }


    private static GuiInventory getMainGui(Player player) {
        Map<Integer, GuiItem> items = new HashMap<>();
        items.put(10, new GuiItem(
                Material.SHULKER_BOX,
                1,
                PluginLanguage.get("gui-main-shulkers-name"),
                PluginLanguage.getLore("gui-main-shulkers-lore"),
                () -> {}
            )
                .leftClick(() -> openShulkerGui(player))
        );
        items.put(13, new GuiItem(
                        Material.SKELETON_SKULL,
                        1,
                        PluginLanguage.get("gui-main-mobs-name"),
                        PluginLanguage.getLore("gui-main-mobs-lore"),
                        () -> {}
                )
        );
        items.put(16, new GuiItem(
                        Material.BEACON,
                        1,
                        PluginLanguage.get("gui-main-active-name"),
                        PluginLanguage.getLore("gui-main-active-lore"),
                        () -> {}
                )
        );

        GuiInventory gui = new GuiInventory(PluginLanguage.get("gui-main-title"), 4, items, player);
        gui.fillEmptySlots(0, Material.GRAY_STAINED_GLASS_PANE);
        gui.fillEmptySlots(1, Material.GRAY_STAINED_GLASS_PANE);
        gui.fillEmptySlots(2, Material.GRAY_STAINED_GLASS_PANE);
        gui.fillEmptySlots(3, Material.BLACK_STAINED_GLASS_PANE);
        return gui;
    }

    private static GuiInventory getShulkersGui(Player player) {
        Map<Integer, GuiItem> items = new HashMap<>();
        items.put(36, new GuiItem(
                        Material.SPECTRAL_ARROW,
                        1,
                        PluginLanguage.get("gui-back"),
                        null,
                        () -> {}
                )
                .leftClick(() -> openMainGui(player))
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
                            plugin.challengeShulkerHandler.createShulker(
                                    player,
                                    ChatInputHandler.getContent(event.originalMessage())
                            );
                        },
                        () -> {}, // TODO
                        player,
                        true,
                        "Введите имя для нового шалкера:"
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

    private static GuiInventory getShulkerEditorGui(Player player, ChallengeShulker shulker) {
        Map<Integer, GuiItem> items = new HashMap<>();
        items.put(27, new GuiItem(
                        Material.SPECTRAL_ARROW,
                        1,
                        PluginLanguage.get("gui-back"),
                        null,
                        () -> {}
                )
                        .leftClick(() -> openMainGui(player))
        );
        items.put(10, new GuiItem(
                shulker.blockType,
                1,
                PluginLanguage.getAndFormat(
                        "gui-shulkers-shulker-name",
                        new String[]{shulker.name}
                ),
                PluginLanguage.getAndFormatLore("gui-shulkereditor-shulker-lore",
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
                                null
                        }
                ),
                () -> {})
        );

        items.put(12, new GuiItem(
                Material.PURPLE_GLAZED_TERRACOTTA,
                1,
                PluginLanguage.get("gui-shulkereditor-main-blocktype-name"),
                PluginLanguage.getLore("gui-shulkereditor-main-blocktype-lore"),
                () -> {}
        ));
        items.put(13, new GuiItem(
                Material.FOX_SPAWN_EGG,
                1,
                PluginLanguage.get("gui-shulkereditor-main-spawn-name"),
                PluginLanguage.getLore("gui-shulkereditor-main-spawn-lore"),
                () -> {}
        ));
        items.put(14, new GuiItem(
                Material.CREEPER_HEAD,
                1,
                PluginLanguage.get("gui-shulkereditor-main-mobs-name"),
                PluginLanguage.getLore("gui-shulkereditor-main-mobs-lore"),
                () -> {}
        ));
        items.put(15, new GuiItem(
                Material.BUNDLE,
                1,
                PluginLanguage.get("gui-shulkereditor-main-items-name"),
                PluginLanguage.getLore("gui-shulkereditor-main-items-lore"),
                () -> {}
        ));
        items.put(16, new GuiItem(
                Material.COMMAND_BLOCK_MINECART,
                1,
                PluginLanguage.get("gui-shulkereditor-main-challenge-name"),
                PluginLanguage.getLore("gui-shulkereditor-main-challenge-lore"),
                () -> {}
        ));

        GuiInventory gui = new GuiInventory(PluginLanguage.getAndFormat("gui-shulkereditor-main-title", new String[]{ shulker.name }), 4, items, player);
        gui.fillEmptySlots(0, Material.GRAY_STAINED_GLASS_PANE);
        gui.fillEmptySlots(1, Material.GRAY_STAINED_GLASS_PANE);
        gui.fillEmptySlots(2, Material.GRAY_STAINED_GLASS_PANE);
        gui.fillEmptySlots(3, Material.PURPLE_STAINED_GLASS_PANE);
        return gui;
    }
}
