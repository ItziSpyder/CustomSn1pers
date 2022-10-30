package me.improperissues.customsn1pers.items;

import me.improperissues.customsn1pers.CustomSn1pers;
import me.improperissues.customsn1pers.files.Balance;
import me.improperissues.customsn1pers.other.Sounds;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class Raycast implements Listener {

    // Instance of the main class
    static CustomSn1pers plugin;
    public Raycast(CustomSn1pers plugin) {
        Raycast.plugin = plugin;
    }

    // Variables
    static HashMap<String,Long> shootCool = new HashMap<>();

    // Methods
    public static boolean fireBullet(Player player, Location location, Vector vector, ItemStack shootWith) {
        int currentAmmo = FireArms.getAmmo(shootWith);
        int maxAmmo = FireArms.getMaxAmmo(shootWith);
        double range = FireArms.getRange(shootWith);
        double damage = FireArms.getDamage(shootWith);
        int cooldown = FireArms.getFireRate(shootWith);
        double uncertaincy = FireArms.getUncertaincy(shootWith);
        int bulletsPerShot = FireArms.getBulletsPerShot(shootWith);

        if (currentAmmo < 1) {
            reload(player,shootWith);
            return false;
        }
        if (shootCool.containsKey(player.getName()) && shootCool.get(player.getName()) > System.currentTimeMillis()) {
            return false;
        }

        currentAmmo -= 1;
        FireArms.setAmmo(shootWith,currentAmmo);
        shootCool.put(player.getName(),System.currentTimeMillis() + (50L * cooldown));
        Sounds.fireShot(location);
        player.setVelocity(player.getVelocity().add(player.getLocation().getDirection().multiply(0.05).multiply(-1)));
        for (int i = 0; i < bulletsPerShot; i ++) {
            Vector newVector = applyVectorUncertaincy(uncertaincy,vector);
            for (double distance = 0; distance < range; distance += 0.5) {
                double x = newVector.getX() * distance;
                double y = newVector.getY() * distance;
                double z = newVector.getZ() * distance;
                String message = "§8>> §7(§e" + currentAmmo + "§7/" + maxAmmo + ") §8<<";

                Location newLoc = new Location(location.getWorld(), location.getX() + x, location.getY() + y, location.getZ() + z);
                Particle.DustOptions travel = new Particle.DustOptions(Color.fromRGB(102, 102, 102), 0.3F);
                newLoc.getWorld().spawnParticle(Particle.REDSTONE, newLoc, 10, 0, 0, 0, 0, travel);

                for (Entity entity : newLoc.getWorld().getNearbyEntities(newLoc, 1, 1, 1)) {
                    if (entity instanceof LivingEntity && entity != player) {
                        double distanceToHead = newLoc.distanceSquared(((LivingEntity) entity).getEyeLocation());
                        if (distanceToHead < 1.2) {
                            damage *= 1.2;
                            message = "§8>> §cHead Shot  §8<< §6+0.3 §eⓒ";
                            Balance.setBal(player,Balance.getBal(player) + 0.3);
                            player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1, 0.5F);
                        } else {
                            message = "§6+0.1 §eⓒ";
                            Balance.setBal(player,Balance.getBal(player) + 0.1);
                            player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1, 10);
                        }
                        ((LivingEntity) entity).damage(damage, player);
                        if (entity.isDead()) {
                            message += " §6+1.0 §eⓒ";
                            Balance.setBal(player,Balance.getBal(player) + 1);
                        }
                        Particle.DustOptions dust = new Particle.DustOptions(Color.RED, 3);
                        entity.getWorld().spawnParticle(Particle.REDSTONE, entity.getLocation().add(0, 1, 0), 1, 0.5, 0.5, 0.5, 0, dust);
                        distance = range;
                        break;
                    }
                }
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));

                if (!newLoc.getWorld().getBlockAt(newLoc).isPassable()) {
                    distance = range;
                }
            }
        }
        return true;
    }

    public static void reload(Player player, ItemStack gunItem) {
        if (FireArms.getAmmo(gunItem) != FireArms.getMaxAmmo(gunItem)) {
            shootCool.put(player.getName(),System.currentTimeMillis() + (2000));
            FireArms.setAmmo(gunItem,FireArms.getMaxAmmo(gunItem));
            Sounds.reloadGun(player.getLocation());
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§8>> §bReloading... §8<<"));
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                @Override
                public void run() {
                    Sounds.playAll(player.getLocation(),Sound.ITEM_ARMOR_EQUIP_NETHERITE,3,0.1F,100);
                }
            },25);
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                @Override
                public void run() {
                    int currentAmmo = FireArms.getAmmo(gunItem);
                    int maxAmmo = FireArms.getMaxAmmo(gunItem);
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§8>> §7(§e" + currentAmmo + "§7/" + maxAmmo + ") §8<<"));
                }
            },40);
        }
    }

    public static Vector applyVectorUncertaincy(double uncertaincy, Vector vector) {
        ThreadLocalRandom r = ThreadLocalRandom.current();
        double x = r.nextDouble(-uncertaincy,uncertaincy);
        double y = r.nextDouble(-uncertaincy,uncertaincy);
        double z = r.nextDouble(-uncertaincy,uncertaincy);
        return vector.add(new Vector(x,y,z));
    }
}
