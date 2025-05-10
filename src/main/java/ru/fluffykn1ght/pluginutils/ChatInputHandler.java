package ru.fluffykn1ght.pluginutils;

import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.function.Consumer;

public class ChatInputHandler implements Listener {
    public final Consumer<Event> callback;

    public ChatInputHandler(Consumer<Event> callback) {
        this.callback = callback;
    }

    @EventHandler
    public void onChatEvent(AsyncChatEvent event) {
        callback.accept(event);
    }
}
