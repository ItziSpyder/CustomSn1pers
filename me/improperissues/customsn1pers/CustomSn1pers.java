package me.improperissues.customsn1pers;

import me.improperissues.customsn1pers.commands.Commands;
import me.improperissues.customsn1pers.commands.Tabs;
import me.improperissues.customsn1pers.events.PlayerEvents;
import me.improperissues.customsn1pers.files.Balance;
import me.improperissues.customsn1pers.files.Shop;
import me.improperissues.customsn1pers.inventory.ShopEvents;
import me.improperissues.customsn1pers.items.ItemManger;
import me.improperissues.customsn1pers.items.Launch;
import me.improperissues.customsn1pers.items.Raycast;
import me.improperissues.customsn1pers.other.Sounds;
import org.bukkit.plugin.java.JavaPlugin;

public final class CustomSn1pers extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getLogger().info("CustomSn1pers has loaded!");

        // Configs
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        Balance.setup();
        Balance.get().options().copyDefaults(true);
        Balance.save();
        Shop.setup();
        Shop.get().options().copyDefaults(true);
        Shop.save();

        // Commands
        getCommand("creategun").setExecutor(new Commands(this));
        getCommand("creategun").setTabCompleter(new Tabs());
        getCommand("createexplosive").setExecutor(new Commands(this));
        getCommand("createexplosive").setTabCompleter(new Tabs());
        getCommand("guns").setExecutor(new Commands(this));
        getCommand("shelf").setExecutor(new Commands(this));
        getCommand("shelf").setTabCompleter(new Tabs());
        getCommand("unshelf").setExecutor(new Commands(this));
        getCommand("unshelf").setTabCompleter(new Tabs());
        getCommand("custommodeldata").setExecutor(new Commands(this));
        getCommand("custommodeldata").setTabCompleter(new Tabs());
        // Events
        getServer().getPluginManager().registerEvents(new PlayerEvents(),this);
        getServer().getPluginManager().registerEvents(new Raycast(this),this);
        getServer().getPluginManager().registerEvents(new Launch(this),this);
        getServer().getPluginManager().registerEvents(new Sounds(this),this);
        getServer().getPluginManager().registerEvents(new ShopEvents(),this);

        // Items
        ItemManger.setItems();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getServer().getLogger().info("CustomSn1pers shutting down!");
    }
}
