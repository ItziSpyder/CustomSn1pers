package me.improperissues.customsn1pers.files;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Shop {

    private static File file;
    private static FileConfiguration data;

    public static void setup() {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("CustomSn1pers").getDataFolder(),"server/shop.yml");

        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException exception) {
            // empty
        }

        data = YamlConfiguration.loadConfiguration(file);
        if (data.getConfigurationSection("server.shopitems") == null) {
            data.set("server.shopitems", new ArrayList<>());
            save();
        }
    }

    public static FileConfiguration get() {
        return data;
    }

    public static void save() {
        try {
            data.save(file);
        } catch (IOException exception) {
            // empty
        }
    }

    public static void reload() {
        data = YamlConfiguration.loadConfiguration(file);
    }

    // Methods for the files
    public static List<String> getEntries() {
        try {
            return new ArrayList<>(data.getConfigurationSection("server.shopitems").getKeys(false));
        } catch (NullPointerException exception) {
            return new ArrayList<>();
        }
    }

    public static void shelf(ItemStack item, double price) {
        ItemStack shelfing = new ItemStack(item.getType());
        ItemMeta meta = item.getItemMeta();
        String display = meta.getDisplayName();
        List<String> lore = meta.getLore();
        lore.add("§7Price: §a$" + price);
        meta.setLore(lore);
        shelfing.setItemMeta(meta);
        data.set("server.shopitems." + display + ".price",price);
        data.set("server.shopitems." + display + ".item",shelfing);
        save();
    }

    public static void unshelf(String itemDisplayName) {
        data.set("server.shopitems." + itemDisplayName, null);
        save();
    }

    public static double getPrice(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        String display = meta.getDisplayName();
        if (getEntries().contains(display)) {
            return data.getDouble("server.shopitems." + display + ".price");
        } else {
            return 0;
        }
    }

    public static void fillMenu(Inventory menu, int index) {
        try {
            for (int i = (index * 21); i < (index * 21 + 21); i ++) {
                String display = getEntries().get(i);
                ItemStack item = data.getItemStack("server.shopitems." + display + ".item");
                menu.setItem(menu.firstEmpty(),item);
            }
        } catch (NullPointerException | IndexOutOfBoundsException exception) {
            // empty
        }
    }
}
