package me.improperissues.customsn1pers.items;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FireArms {

    public static ItemStack createGun(Material itemMaterial, String displayName, int ammo, int maxAmmo, double range, double damage, int fireCooldown) {
        ItemStack item = new ItemStack(itemMaterial);
        ItemMeta meta = item.getItemMeta();

        assert meta != null;
        meta.setDisplayName("§r§8§l>> " + displayName + " §r§8§l<<");
        meta.setLore(new ArrayList<>(Arrays.asList(
                "§7Ammo: §f" + ammo + "/" + maxAmmo,
                "§7Range: §f" + range,
                "§7Damage: §f" + damage,
                "§7Fire cooldown: §f" + fireCooldown,
                "   ",
                "§8#customsn1pers"
        )));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES,ItemFlag.HIDE_ENCHANTS,ItemFlag.HIDE_DYE,ItemFlag.HIDE_UNBREAKABLE,ItemFlag.HIDE_DESTROYS,ItemFlag.HIDE_PLACED_ON,ItemFlag.HIDE_POTION_EFFECTS);
        meta.setUnbreakable(true);
        AttributeModifier modifier = new AttributeModifier("movement",-0.02D, AttributeModifier.Operation.ADD_NUMBER);
        meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED,modifier);

        item.setItemMeta(meta);
        return item;
    }

    public static void setAmmo(ItemStack gunItem, int value) {
        int maxAmmo = FireArms.getMaxAmmo(gunItem);
        double range = FireArms.getRange(gunItem);
        double damage = FireArms.getDamage(gunItem);
        int cooldown = FireArms.getFireRate(gunItem);

        ItemMeta meta = gunItem.getItemMeta();
        meta.setLore(new ArrayList<>(Arrays.asList(
                "§7Ammo: §f" + value + "/" + maxAmmo,
                "§7Range: §f" + range,
                "§7Damage: §f" + damage,
                "§7Fire cooldown: §f" + cooldown,
                "   ",
                "§8#customsn1pers"
        )));
        gunItem.setItemMeta(meta);
    }

    public static int getFireRate(ItemStack gunItem) {
        try {
            List<String> lore = Objects.requireNonNull(gunItem.getItemMeta()).getLore();
            assert lore != null;
            String fireRate = lore.get(3).substring(19);

            return Integer.parseInt(fireRate);
        } catch (NullPointerException exception) {
            return 0;
        }
    }

    public static double getDamage(ItemStack gunItem) {
        try {
            List<String> lore = Objects.requireNonNull(gunItem.getItemMeta()).getLore();
            assert lore != null;
            String damage = lore.get(2).substring(12);

            return Double.parseDouble(damage);
        } catch (NullPointerException exception) {
            return 0;
        }
    }

    public static double getRange(ItemStack gunItem) {
        try {
            List<String> lore = Objects.requireNonNull(gunItem.getItemMeta()).getLore();
            assert lore != null;
            String range = lore.get(1).substring(11);

            return Double.parseDouble(range);
        } catch (NullPointerException exception) {
            return 0;
        }
    }

    public static int getAmmo(ItemStack gunItem) {
        try {
            List<String> lore = Objects.requireNonNull(gunItem.getItemMeta()).getLore();
            assert lore != null;
            String[] countAndMax = lore.get(0).substring(10).split("/");

            return Integer.parseInt(countAndMax[0]);
        } catch (NullPointerException exception) {
            return 0;
        }
    }

    public static int getMaxAmmo(ItemStack gunItem) {
        try {
            List<String> lore = Objects.requireNonNull(gunItem.getItemMeta()).getLore();
            assert lore != null;
            String[] countAndMax = lore.get(0).substring(10).split("/");

            return Integer.parseInt(countAndMax[1]);
        } catch (NullPointerException exception) {
            return 0;
        }
    }

    public static boolean isHoldingGun(Player player) {
        List<String> lore = player.getInventory().getItemInMainHand().getItemMeta().getLore();
        return lore.contains("§8#customsn1pers");
    }

    public static boolean isGun(ItemStack item) {
        return item.getItemMeta().getLore().contains("§8#customsn1pers");
    }

}
