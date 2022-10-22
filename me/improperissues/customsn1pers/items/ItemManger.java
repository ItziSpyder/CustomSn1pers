package me.improperissues.customsn1pers.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemManger {

    public static void setItems() {
        setBlank_red();
        setBlank_light_gray();
        setBlank_white();
        setNext();
        setPrevious();
    }

    public static ItemStack blank_light_gray;
    public static ItemStack blank_red;
    public static ItemStack blank_white;
    public static ItemStack air = new ItemStack(Material.AIR);
    public static ItemStack previous;
    public static ItemStack next;



    private static void setBlank_light_gray() {
        ItemStack item = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(" ");

        item.setItemMeta(meta);
        blank_light_gray = item;
    }

    private static void setBlank_red() {
        ItemStack item = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(" ");

        item.setItemMeta(meta);
        blank_red = item;
    }

    private static void setBlank_white() {
        ItemStack item = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(" ");

        item.setItemMeta(meta);
        blank_white = item;
    }

    private static void setNext() {
        ItemStack item = new ItemStack(Material.ARROW);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("Next Page");

        item.setItemMeta(meta);
        next = item;
    }

    private static void setPrevious() {
        ItemStack item = new ItemStack(Material.ARROW);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("Previous Page");

        item.setItemMeta(meta);
        previous = item;
    }
}
