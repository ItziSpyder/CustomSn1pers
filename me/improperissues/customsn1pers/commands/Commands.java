package me.improperissues.customsn1pers.commands;

import me.improperissues.customsn1pers.CustomSn1pers;
import me.improperissues.customsn1pers.files.Shop;
import me.improperissues.customsn1pers.inventory.ShopEvents;
import me.improperissues.customsn1pers.items.Explosives;
import me.improperissues.customsn1pers.items.FireArms;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Commands implements CommandExecutor {

    // instance of the main class
    static CustomSn1pers plugin;
    public Commands(CustomSn1pers plugin) {
        Commands.plugin = plugin;
    }

    // commands
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }

        try {
            Player p = (Player) sender;
            switch (command.getName().toLowerCase()) {
                case "creategun":
                    // creategun material maxammo range damage firecooldown uncertaincy bulletsPerShot displayname
                    // §

                    if (args.length >= 8) {
                        Material material = Material.valueOf(args[0].toUpperCase());
                        int maxAmmo = Integer.parseInt(args[1]);
                        double range = Double.parseDouble(args[2]);
                        double damage = Double.parseDouble(args[3]);
                        int cooldown = Integer.parseInt(args[4]);
                        double uncertaincy = Double.parseDouble(args[5]);
                        int bulletsPerShot = Integer.parseInt(args[6]);
                        String display = args[7].replaceAll("&","§").replaceAll("_"," ");

                        p.getInventory().addItem(FireArms.createGun(material,display,maxAmmo,maxAmmo,range,damage,cooldown,uncertaincy,bulletsPerShot));
                        if (range > 100
                                || uncertaincy > 1
                                || uncertaincy < 0.001
                                || bulletsPerShot > 10
                        ) {
                            p.sendMessage("§8>> §cWHAO THERE! The set values of this gun exceeds the recommended limit for performance reasons! " +
                                    "Discard your item unless you know what you are doing!");
                            p.playSound(p.getLocation(), Sound.ENTITY_SHULKER_TELEPORT,10,10);
                            break;
                        }
                    } else {
                        p.sendMessage("§8>> §cIncomplete or invalid command!");
                    }
                    return true;
                case "createexplosive":
                    // createexplosive material projectileMaterial power weight breakBlocks setFire displayName
                    // §

                    if (args.length >= 7) {
                        Material material = Material.valueOf(args[0].toUpperCase());
                        Material projectile = Material.valueOf(args[1].toUpperCase());
                        double power = Double.parseDouble(args[2]);
                        double weight = Double.parseDouble(args[3]);
                        boolean breakBlocks = Boolean.parseBoolean(args[4]);
                        boolean setFire = Boolean.parseBoolean(args[5]);
                        String display = args[6].replaceAll("&","§").replaceAll("_"," ");

                        p.getInventory().addItem(Explosives.createExplosive(material,projectile,power,weight,display,breakBlocks,setFire));
                        if (power > 20
                                || weight < 3
                        ) {
                            p.sendMessage("§8>> §cWHAO THERE! The set values of this explosive exceeds the recommended limit for performance reasons! " +
                                    "Discard your item unless you know what you are doing!");
                            p.playSound(p.getLocation(), Sound.ENTITY_SHULKER_TELEPORT,10,10);
                            break;
                        }
                    } else {
                        p.sendMessage("§8>> §cIncomplete or invalid command!");
                    }
                    return true;
                case "guns":
                    ShopEvents.openGunShop(p,0);
                    return true;
                case "shelf":
                    if (args.length >= 1) {
                        double price = Double.parseDouble(args[0]);
                        ItemStack item = p.getInventory().getItemInMainHand();
                        ItemMeta meta = item.getItemMeta();
                        String display = meta.getDisplayName();
                        Shop.shelf(item,price);
                        p.sendMessage("§8>> §7Successfully shelved \"" + display + "§7\" into the shop for §6" + price + " §eⓒ §7!");
                    } else {
                        p.sendMessage("§8>> §cIncomplete or invalid command!");
                    }

                    return true;
                case "unshelf":
                    if (args.length >= 1) {
                        String itemName = args[0].replaceAll("&","§").replaceAll("_"," ");
                        Shop.unshelf(itemName);
                        p.sendMessage("§8>> §7Successfully unshelved \"" + itemName + "§7\" from the shop!");
                    } else {
                        p.sendMessage("§8>> §cIncomplete or invalid command!");
                    }
                    return true;
            }
        } catch (NullPointerException | ClassCastException | IllegalArgumentException exception) {
            sender.sendMessage("§8>> §cIncomplete or invalid command!");
        }

        return true;
    }
}
