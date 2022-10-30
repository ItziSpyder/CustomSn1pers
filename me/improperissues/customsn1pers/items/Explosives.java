package me.improperissues.customsn1pers.items;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class Explosives {

    public static ItemStack createExplosive(Material material, Material projectile, Double power, Double weight, String displayName, boolean breakBlocks, boolean setFire) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("§8§l[ §r" + displayName.replaceAll("&","§").replaceAll("_"," ") + " §8§l]");
        meta.setLore(new ArrayList<>(Arrays.asList(
                "§7Projectile: §f" + projectile.name(),
                "§7Power: §f" + power,
                "§7Weight: §f" + weight,
                "§7Break blocks: §f" + breakBlocks,
                "§7Set fire: §f" + setFire,
                "   ",
                "§8#customgrenades"
        )));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES,ItemFlag.HIDE_ENCHANTS,ItemFlag.HIDE_DYE,ItemFlag.HIDE_UNBREAKABLE,ItemFlag.HIDE_DESTROYS,ItemFlag.HIDE_PLACED_ON,ItemFlag.HIDE_POTION_EFFECTS);
        meta.setUnbreakable(true);
        AttributeModifier modifier = new AttributeModifier("movement",-0.01D, AttributeModifier.Operation.ADD_NUMBER);
        meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED,modifier);

        item.setItemMeta(meta);
        return item;
    }

    public static Material getProjectile(ItemStack explosive) {
        if (isExplosive(explosive)) {
            return Material.valueOf(explosive.getItemMeta().getLore().get(0).substring(16).toUpperCase());
        }
        return null;
    }

    public static double getPower(ItemStack explosive) {
        if (isExplosive(explosive)) {
            return Double.parseDouble(explosive.getItemMeta().getLore().get(1).substring(11));
        }
        return 0;
    }

    public static double getWeight(ItemStack explosive) {
        if (isExplosive(explosive)) {
            return Double.parseDouble(explosive.getItemMeta().getLore().get(2).substring(12));
        }
        return 0;
    }

    public static boolean getBreakBlocks(ItemStack explosive) {
        if (isExplosive(explosive)) {
            return Boolean.parseBoolean(explosive.getItemMeta().getLore().get(3).substring(18));
        }
        return false;
    }

    public static boolean getSetFire(ItemStack explosive) {
        if (isExplosive(explosive)) {
            return Boolean.parseBoolean(explosive.getItemMeta().getLore().get(4).substring(14));
        }
        return false;
    }

    public static boolean isExplosive(ItemStack item) {
        return item.getItemMeta().getLore().contains("§8#customgrenades");
    }
}
