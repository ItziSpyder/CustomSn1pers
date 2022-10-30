package me.improperissues.customsn1pers.items;

import me.improperissues.customsn1pers.CustomSn1pers;
import me.improperissues.customsn1pers.other.Sounds;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.UUID;

public class Launch implements Listener {

    // instance of the main class
    static CustomSn1pers plugin;
    public Launch(CustomSn1pers plugin) {
        Launch.plugin = plugin;
    }
    static HashMap<String,Long> cooldown = new HashMap<>();

    // events
    @EventHandler
    public static void ProjectileHitEvent(ProjectileHitEvent e) {
        Entity entity = e.getEntity();
        if (entity instanceof Snowball) {
            if (entity.getScoreboardTags().contains("§8#customgrenades")) {
                String[] data = entity.getCustomName().split(";");
                entity.getWorld().createExplosion(entity.getLocation(),Float.parseFloat(data[1]),Boolean.parseBoolean(data[3]),Boolean.parseBoolean(data[2]),Bukkit.getServer().getPlayer(UUID.fromString(data[0])));
            }
        }
    }

    // methods
    public static boolean launch(Player player, ItemStack explosive, Vector vector) {
        if (!Explosives.isExplosive(explosive)) {
            return false;
        }
        if (cooldown.containsKey(player.getName()) && cooldown.get(player.getName()) > System.currentTimeMillis()) {
            return false;
        }

        Material projectile = Explosives.getProjectile(explosive);
        double power = Explosives.getPower(explosive);
        double weight = Explosives.getWeight(explosive);
        boolean breakBlocks = Explosives.getBreakBlocks(explosive);
        boolean setFire = Explosives.getSetFire(explosive);

        cooldown.put(player.getName(), (long) (System.currentTimeMillis() + (weight * 50)));
        Sounds.playAll(player.getLocation(), Sound.UI_BUTTON_CLICK, 10, 10,200);
        Sounds.playAll(player.getLocation(), Sound.ITEM_TRIDENT_THROW,10,10,200);
        Sounds.playAll(player.getLocation(), Sound.ITEM_TRIDENT_THROW,10,0.1F,200);

        explosive.setAmount(explosive.getAmount() - 1);
        Snowball sb = player.getWorld().spawn(player.getEyeLocation(), Snowball.class);
        if (projectile != null) {
            sb.setItem(new ItemStack(projectile));
        }
        sb.setGlowing(true);
        sb.setShooter(player);
        sb.addScoreboardTag("§8#customgrenades");
        sb.setCustomName(player.getUniqueId() + ";" + power + ";" + breakBlocks + ";" + setFire);
        sb.setSilent(true);
        sb.setVelocity(vector.multiply(10 - weight));

        return true;
    }
}
