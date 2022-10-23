package me.improperissues.customsn1pers.files;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Balance {

    private static File file;
    private static FileConfiguration data;

    public static void setup() {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("CustomSn1pers").getDataFolder(),"server/balance.yml");

        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException exception) {
            // empty
        }

        data = YamlConfiguration.loadConfiguration(file);
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

    public static double getBal(OfflinePlayer player) {
        return data.getDouble("server.balance." + player.getUniqueId());
    }

    public static String getStringBal(OfflinePlayer player) {
        double bal = getBal(player);
        if (bal >= (1000000000000D)) {
            return "$" + Math.floor(bal/10000000000D)/100 + "T";
        } else if (bal >= (1000000000D)) {
            return "$" + Math.floor(bal/10000000D)/100 + "B";
        } else if (bal >= (1000000D)) {
            return "$" + Math.floor(bal/10000D)/100 + "M";
        } else if (bal >= (1000D)) {
            return "$" + Math.floor(bal/10D)/100 + "K";
        } else {
            return "$" + Math.floor(bal * 100)/100;
        }
    }

    public static void setBal(OfflinePlayer player,double amount) {
        data.set("server.balance." + player.getUniqueId(),amount);
        save();
    }
 }
