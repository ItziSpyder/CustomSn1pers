package me.improperissues.customsn1pers.inventory;

import me.improperissues.customsn1pers.files.Balance;
import me.improperissues.customsn1pers.files.Shop;
import me.improperissues.customsn1pers.items.FireArms;
import me.improperissues.customsn1pers.items.ItemManger;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ShopEvents implements Listener {

    // Variables

    // Events
    @EventHandler
    public static void InventoryClickEvent(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        Inventory inv = e.getClickedInventory();
        String title = e.getView().getTitle();

        try {
            ItemStack item = e.getCurrentItem();
            ItemMeta meta = item.getItemMeta();
            String display = meta.getDisplayName();

            if (!inv.getType().equals(InventoryType.PLAYER)) {
                if (title.contains("§8>> §7Very Cool Shop")) {
                    e.setCancelled(true);
                    int index = Integer.parseInt(title.substring(29)) - 1;
                    switch (display) {
                        case "Next Page":
                            openGunShop(p,index + 1);
                            p.playSound(p.getLocation(),Sound.ITEM_BOOK_PAGE_TURN,10,10);
                            break;
                        case "Previous Page":
                            if (index >= 1) {
                                openGunShop(p,index - 1);
                                p.playSound(p.getLocation(),Sound.ITEM_BOOK_PAGE_TURN,10,10);
                                break;
                            }
                    }
                    if (isSellable(item)) {
                        OfflinePlayer player = Bukkit.getOfflinePlayer(p.getUniqueId());
                        double bal = Balance.getBal(player);
                        double price = Shop.getPrice(item);
                        if (bal >= price) {
                            Balance.setBal(player,bal - price);
                            Balance.save();
                            ItemStack buy = new ItemStack(item.getType());
                            buy.setItemMeta(item.getItemMeta());
                            removePriceTag(buy);
                            Inventory pinv = p.getInventory();
                            p.playSound(p.getLocation(),Sound.ENTITY_PLAYER_LEVELUP,10,10);
                            if (pinv.firstEmpty() != -1) {
                                pinv.addItem(buy);
                            } else {
                                p.getWorld().dropItemNaturally(p.getLocation(),buy);
                            }
                            openGunShop(p,index);
                        } else {
                            p.closeInventory();
                            p.playSound(p.getLocation(),Sound.ENTITY_SHULKER_TELEPORT,1,10);
                            p.sendMessage("§8>> §cYou cannot afford this!");
                        }
                    }
                }
            }
        } catch (NullPointerException exception) {
            // empty
        }
    }

    // Methods
    public static void openGunShop(Player player, int index) {
        Inventory menu = Bukkit.createInventory(player,54,"§8>> §7Very Cool Shop    §eP." + (index + 1));
        ItemStack x = ItemManger.blank_red;
        ItemStack y = ItemManger.blank_light_gray;
        ItemStack a = ItemManger.air;
        ItemStack next = ItemManger.next;
        ItemStack previous = ItemManger.previous;

        ItemStack bal = new ItemStack(Material.KNOWLEDGE_BOOK);
        ItemMeta balM = bal.getItemMeta();
        balM.setDisplayName("§7Your Balance: §a" + Balance.getStringBal(Bukkit.getOfflinePlayer(player.getUniqueId())));
        bal.setItemMeta(balM);

        ItemStack[] contents = {
                x,x,x,x,x,x,x,x,x,
                y,y,y,y,y,y,y,y,y,
                y,a,a,a,a,a,a,a,y,
                y,a,a,a,a,a,a,a,y,
                y,a,a,a,a,a,a,a,y,
                previous,x,x,x,bal,x,x,x,next
        };
        menu.setContents(contents);
        Shop.fillMenu(menu,index);
        player.openInventory(menu);
    }

    public static boolean isSellable(ItemStack item) {
        try {
            List<String> lore = item.getItemMeta().getLore();
            assert lore != null;
            for (String string : lore) {
                if (string.contains("§7Price: §a$")) {
                    return true;
                }
            }
            return false;
        } catch (NullPointerException exception) {
            return false;
        }
    }

    public static void removePriceTag(ItemStack item) {
        try {
            if (isSellable(item)) {
                ItemMeta meta = item.getItemMeta();
                List<String> lore = meta.getLore();
                assert lore != null;
                lore.removeIf(string-> string.contains("§7Price: §a$"));
                meta.setLore(lore);
                item.setItemMeta(meta);
            }
        } catch (NullPointerException exception) {
            // empty
        }
    }
}
