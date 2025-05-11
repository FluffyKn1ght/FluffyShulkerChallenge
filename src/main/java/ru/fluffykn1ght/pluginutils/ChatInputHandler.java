package ru.fluffykn1ght.pluginutils;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.title.Title;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.time.Duration;
import java.util.function.Consumer;

public class ChatInputHandler implements Listener {
    public final Consumer<AsyncChatEvent> callback;
    public final Runnable cancel;
    public final Player player;
    public final boolean cancellable;

    public ChatInputHandler(Consumer<AsyncChatEvent> callback, Runnable cancel, Player player, boolean cancellable) {
        this.callback = callback;
        this.cancel = cancel;
        this.player = player;
        this.cancellable = cancellable;
    }

    public static void askForChatInput(Consumer<AsyncChatEvent> callback, Runnable cancel, Player player, boolean cancellable, String details) {
        ChatInputHandler chatInputHandler = new ChatInputHandler(callback, cancel, player, cancellable);
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
    }

    public static String getContent(Component component) {
        TextComponent textComponent = (TextComponent) component;
        return textComponent.content();
    }

    @EventHandler
    public void onChatEvent(AsyncChatEvent event) {
        if (event.getPlayer() == player) {
            callback.accept(event);
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
            cancel.run();
            destroy();
        }
    }

    private void destroy() {
        AsyncChatEvent.getHandlerList().unregisterAll(this);
    }
}
