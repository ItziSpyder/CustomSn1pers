package me.improperissues.customsn1pers.commands;

import me.improperissues.customsn1pers.files.Shop;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tabs implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> list = new ArrayList<>();

        switch (command.getName().toLowerCase()) {
            case "creategun":
                switch (args.length) {
                    case 1:
                        for (Material material : Material.class.getEnumConstants()) {
                            if (isTool(material)) {
                                list.add(material.name().toLowerCase());
                            }
                        }
                        break;
                    case 2:
                        list.add("§8<maxAmmo: int>");
                        break;
                    case 3:
                        list.add("§8<rangeInBlocks: double>");
                        break;
                    case 4:
                        list.add("§8<damage: double>");
                        break;
                    case 5:
                        list.add("§8<cooldownInTicks: int>");
                        break;
                    case 6:
                        list.add("§8<uncertaincy: double>");
                        break;
                    case 7:
                        list.add("§8<bulletsPerShot: int>");
                        break;
                    case 8:
                        list.add("§8<displayName: string>");
                        break;
                }
                break;
            case "createexplosive":
                switch (args.length) {
                    case 1:
                    case 2:
                        return new ArrayList<>(Arrays.asList(
                                "tripwire_hook",
                                "redstone_torch",
                                "torch",
                                "lever",
                                "fire_charge",
                                "snowball",
                                "egg",
                                "ender_pearl",
                                "ender_eye",
                                "lightning_rod"
                        ));
                    case 3:
                        list.add("§8<power: double>");
                        break;
                    case 4:
                        list.add("§8<weight: double>");
                        break;
                    case 5:
                        list.add("§8<breakBlocks: boolean>");
                        break;
                    case 6:
                        list.add("§8<setFire: boolean>");
                        break;
                    case 7:
                        list.add("§8<displayName: string>");
                        break;
                }
                break;
            case "shelf":
                switch (args.length) {
                    case 1:
                        list.add("§8<price: double>");
                        break;
                }
                break;
            case "unshelf":
                switch (args.length) {
                    case 1:
                        for (String name : Shop.getEntries()) {
                            list.add(name.replaceAll("§","&").replaceAll(" ","_"));
                        }
                        break;
                }
                break;
        }

        return list;
    }



    // methods
    public static boolean isTool(Material material) {
        String name = material.name().toLowerCase();
        return (name.contains("_pickaxe") ||
                name.contains("_sword") ||
                name.contains("_shovel") ||
                name.contains("_hoe") ||
                name.contains("_axe")) &&
                !name.contains("legacy_");
    }
}
