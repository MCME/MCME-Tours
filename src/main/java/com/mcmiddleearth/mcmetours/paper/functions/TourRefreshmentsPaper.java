package com.mcmiddleearth.mcmetours.paper.functions;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * @author Jubo
 */
public class TourRefreshmentsPaper {

    public static void giveRefreshments(Player player){
        ItemStack cookie = new ItemStack(Material.COOKIE,5);
        ItemMeta cookie_meta = cookie.getItemMeta();
        cookie_meta.setDisplayName("Biscuit");
        cookie.setItemMeta(cookie_meta);
        player.getInventory().addItem(cookie);
        ItemStack tea = new ItemStack(Material.HONEY_BOTTLE,5);
        ItemMeta tea_meta = tea.getItemMeta();
        tea_meta.setDisplayName("Tea");
        tea.setItemMeta(tea_meta);
        player.getInventory().addItem(tea);
    }
}
