package ru.fluffykn1ght.fluffyshulkerchallenge;

import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import ru.fluffykn1ght.pluginutils.PluginLanguage;

import java.io.File;
import java.io.IOException;

public final class FluffyShulkerChallenge extends JavaPlugin {
    public CommandHandler commandHandler;

    public YamlConfiguration data;
    private final File dataFile = new File(getDataFolder(), "data.yml");

    public YamlConfiguration lang;
    private final File langFile = new File(getDataFolder(), "lang.yml");


    @Override
    public void onEnable() {
        getLogger().info("Запуск плагина... (версия " + getPluginMeta().getVersion() + ")");
        getLogger().info("Автор плагина: FluffyKn1ght [spigotmc.ru]");

        if (!reload()) { return; }

        PluginLanguage.setLang(lang);

        commandHandler = new CommandHandler();
        getCommand("shlkch").setExecutor(commandHandler);
        getCommand("shlkch").setTabCompleter(commandHandler);

        getLogger().info("Плагин готов к работе!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public boolean reload() {
        saveDefaultConfig();
        reloadConfig();

        if (!dataFile.exists()) {
            saveResource("data.yml", false);
        }
        try {
            data.load(dataFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidConfigurationException e) {
            getLogger().severe("Файл данных (data.yml) повреждён и будет сброшен!");
            saveResource("data.yml", true);
            try {
                data.load(dataFile);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (InvalidConfigurationException ex) {
                // ошибка в дефолтном файле
                getLogger().info("Вы нашли баг :) Свяжитесь с разработчиком");
                getServer().getPluginManager().disablePlugin(this);
                return false;
            }
        }

        if (!langFile.exists()) {
            saveResource("lang.yml", false);
        }
        try {
            lang.load(langFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidConfigurationException e) {
            getLogger().severe("Файл языка (lang.yml) повреждён и будет сброшен!");
            saveResource("lang.yml", true);
            try {
                lang.load(langFile);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (InvalidConfigurationException ex) {
                // ошибка в дефолтном файле
                getLogger().info("Вы нашли баг :) Свяжитесь с разработчиком");
                getServer().getPluginManager().disablePlugin(this);
                return false;
            }
        }

        getLogger().info("Данные (пере)загружены");
        return true;
    }
}
