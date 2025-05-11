package ru.fluffykn1ght.pluginutils;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.Duration;
import java.util.function.Consumer;

public class ChatInputHandler implements Listener {
    public final Consumer<AsyncChatEvent> callback;
    public final Runnable cancel;
    public final Player player;
    public final boolean cancellable;
    private final JavaPlugin plugin;

    public ChatInputHandler(Consumer<AsyncChatEvent> callback, Runnable cancel, Player player, boolean cancellable, JavaPlugin plugin) {
        this.callback = callback;
        this.cancel = cancel;
        this.player = player;
        this.cancellable = cancellable;
        this.plugin = plugin;
    }

    public static void askForChatInput(Consumer<AsyncChatEvent> callback, Runnable cancel, Player player, boolean cancellable, String details, JavaPlugin plugin) {
        ChatInputHandler chatInputHandler = new ChatInputHandler(callback, cancel, player, cancellable, plugin);
        player.closeInventory();
        player.sendMessage(PluginLanguage.getAndFormat("chatinput-main", new String[]{ details }));
        if (cancellable) {
            player.sendMessage(PluginLanguage.get("chatinput-cancel"));
        }
        player.showTitle(
                Title.title(
                        Component.text().build(),
                        PluginLanguage.get("chatinput-title"),
                        Title.Times.times(
                                Duration.ofSeconds(1),
                                Duration.ofSeconds(3600),
                                Duration.ofSeconds(1)
                        )
                )
        );
        plugin.getServer().getPluginManager().registerEvents(chatInputHandler, plugin);
    }

    public static String getContent(Component component) {
        TextComponent textComponent = (TextComponent) component;
        return textComponent.content();
    }

    @EventHandler
    public void onChatEvent(AsyncChatEvent event) {
        if (event.getPlayer() == player) {
            plugin.getServer().getScheduler().runTask(plugin, () -> callback.accept(event));
            event.setCancelled(true);
            player.resetTitle();
            destroy();
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        if (event.getPlayer() == player) {
            destroy();
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerPunch(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
            event.setCancelled(true);
            player.resetTitle();
            plugin.getServer().getScheduler().runTask(plugin, cancel);
            destroy();
        }
    }

    private void destroy() {
        HandlerList.unregisterAll(this);
    }
}
