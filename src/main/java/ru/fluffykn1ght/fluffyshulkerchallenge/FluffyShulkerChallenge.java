package ru.fluffykn1ght.fluffyshulkerchallenge;

import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import ru.fluffykn1ght.pluginutils.ConfigurationUpgrader;
import ru.fluffykn1ght.pluginutils.PluginLanguage;

import java.io.File;
import java.io.IOException;

public final class FluffyShulkerChallenge extends JavaPlugin {
    public CommandHandler commandHandler;

    public YamlConfiguration data = new YamlConfiguration();
    private final File dataFile = new File(getDataFolder(), "data.yml");

    public YamlConfiguration lang = new YamlConfiguration();
    private final File langFile = new File(getDataFolder(), "lang.yml");

    private final int LANG_VERSION = 1;
    private final int DATA_VERSION = 1;
    //private final int CONFIG_VERSION = 1;

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

        if (data.getInt("version") < DATA_VERSION) {
            getLogger().info("Апгрейд файла данных (data.yml) с версии " + data.getInt("version") + " до " + DATA_VERSION);
            data = ConfigurationUpgrader.upgrade(data, YamlConfiguration.loadConfiguration(getTextResource("data.yml")));
        }
        if (lang.getInt("version") < LANG_VERSION) {
            getLogger().info("Апгрейд файла языка (lang.yml) с версии " + lang.getInt("version") + " до " + LANG_VERSION);
            lang = ConfigurationUpgrader.upgrade(lang, YamlConfiguration.loadConfiguration(getTextResource("lang.yml")));
        }

        getLogger().info("Данные (пере)загружены!");
        return true;
    }
}
