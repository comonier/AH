package com.comonier.ah.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;

public class ItemBuilder {

    private final ItemStack item;
    private final ItemMeta meta;

    public ItemBuilder(Material material) {
        this.item = new ItemStack(material);
        this.meta = item.getItemMeta();
    }

    /**
     * Define o nome do item com tradução de cores
     */
    public ItemBuilder setName(String name) {
        if (name == null) return this;
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        return this;
    }

    /**
     * Define a lore do item com tradução de cores
     */
    public ItemBuilder setLore(String... loreLines) {
        if (loreLines == null) return this;
        List<String> coloredLore = new ArrayList<>();
        
        for (String line : loreLines) {
            coloredLore.add(ChatColor.translateAlternateColorCodes('&', line));
        }
        
        meta.setLore(coloredLore);
        return this;
    }

    /**
     * Finaliza a construção e retorna o ItemStack
     */
    public ItemStack build() {
        item.setItemMeta(meta);
        return item;
    }
}
