package me.improperissues.customsn1pers.other;

import me.improperissues.customsn1pers.CustomSn1pers;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class Sounds implements Listener {

    // instance of the main class
    static CustomSn1pers plugin;
    public Sounds(CustomSn1pers plugin) {
        Sounds.plugin = plugin;
    }

    // methods
    public static void playAll(Location location, Sound sound, float volume, float pitch, double range) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getWorld() == location.getWorld() && p.getLocation().distanceSquared(location) < range) {
                p.playSound(location,sound,volume,pitch);
            }
        }
    }

    public static void fireShot(Location location) {
        playAll(location,Sound.ITEM_FLINTANDSTEEL_USE,1,0.1F,500);
        playAll(location,Sound.ENTITY_FIREWORK_ROCKET_BLAST,2,0.1F,500);
        playAll(location,Sound.ITEM_ARMOR_EQUIP_DIAMOND,10,2,500);
    }

    public static void reloadGun(Location location) {
        playAll(location,Sound.ENTITY_HORSE_GALLOP,1,0.1F,100);
        playAll(location,Sound.ITEM_ARMOR_EQUIP_NETHERITE,5,0.1F,100);
    }

}
