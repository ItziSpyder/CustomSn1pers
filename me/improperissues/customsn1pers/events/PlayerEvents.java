package me.improperissues.customsn1pers.events;

import me.improperissues.customsn1pers.files.Balance;
import me.improperissues.customsn1pers.items.FireArms;
import me.improperissues.customsn1pers.items.Raycast;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerEvents implements Listener {

    @EventHandler
    public static void PlayerInteractEvent(PlayerInteractEvent e) {
        Player p  = e.getPlayer();

        try {
            switch (e.getAction()) {
                case RIGHT_CLICK_AIR:
                case RIGHT_CLICK_BLOCK:
                    if (FireArms.isHoldingGun(p)) {
                        e.setCancelled(true);
                        Raycast.fireBullet(p,p.getEyeLocation(),p.getLocation().getDirection(),p.getInventory().getItemInMainHand());
                    }
                    break;
                case LEFT_CLICK_AIR:
                case LEFT_CLICK_BLOCK:
                    if (FireArms.isHoldingGun(p)) {
                        e.setCancelled(true);
                        Raycast.reload(p,p.getInventory().getItemInMainHand());
                    }
                    break;
            }
        } catch (IllegalArgumentException | NullPointerException exception) {
            // empty
        }
    }
}
