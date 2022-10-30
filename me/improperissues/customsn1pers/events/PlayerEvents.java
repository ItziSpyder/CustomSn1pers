package me.improperissues.customsn1pers.events;

import me.improperissues.customsn1pers.items.Explosives;
import me.improperissues.customsn1pers.items.FireArms;
import me.improperissues.customsn1pers.items.Launch;
import me.improperissues.customsn1pers.items.Raycast;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerEvents implements Listener {

    @EventHandler
    public static void PlayerInteractEvent(PlayerInteractEvent e) {
        Player p  = e.getPlayer();

        try {
            try {
                ItemStack main = p.getInventory().getItemInMainHand();
                ItemStack off = p.getInventory().getItemInOffHand();
                if (FireArms.isHoldingGun(p) || FireArms.isHoldingGun(p)) {
                    e.setCancelled(true);
                    switch (e.getAction()) {
                        case RIGHT_CLICK_AIR:
                        case RIGHT_CLICK_BLOCK:
                            if (FireArms.isGun(main)) {
                                Raycast.fireBullet(p, p.getEyeLocation(), p.getLocation().getDirection(), p.getInventory().getItemInMainHand());
                            }
                            break;
                        case LEFT_CLICK_AIR:
                        case LEFT_CLICK_BLOCK:
                            if (FireArms.isGun(main)) {
                                Raycast.reload(p,p.getInventory().getItemInMainHand());
                            }
                            break;
                    }
                } else if (Explosives.isExplosive(main) || Explosives.isExplosive(off)) {
                    e.setCancelled(true);
                    switch (e.getAction()) {
                        case RIGHT_CLICK_AIR:
                        case RIGHT_CLICK_BLOCK:
                            if (Explosives.isExplosive(main)) {
                                Launch.launch(p,p.getInventory().getItemInMainHand(),p.getLocation().getDirection());
                            }
                            break;
                    }
                }

            } catch (StringIndexOutOfBoundsException exception) {
                p.sendMessage("§8>> §cThis item seems to have an issue or has been modified! Try getting a new one!");
            }
        } catch (IllegalArgumentException | NullPointerException exception) {
            // empty
        }
    }
}
