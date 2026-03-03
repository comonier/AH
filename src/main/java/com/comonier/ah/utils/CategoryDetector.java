package com.comonier.ah.utils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
public class CategoryDetector {
    public static String getCategoryPath(ItemStack item) {
        if (item == null) return "cat-items";
        Material type = item.getType();
        String name = type.name();
        // PRIORIDADE: Verifica se e um item de Blocos de Protecao pelo nome
        if (item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            if (meta.hasDisplayName()) {
                if (meta.getDisplayName().contains("Blocos de Protecao")) {
                    return "cat-protection";
                }
            }
        }
        if (name.contains("SHULKER_BOX")) return "cat-shulker";
        if (name.contains("SWORD") || name.contains("PICKAXE") || 
            name.contains("AXE") || name.contains("SHOVEL") || 
            name.contains("HOE") || type == Material.BOW || 
            type == Material.CROSSBOW || type == Material.TRIDENT) {
            return "cat-tools";
        }
        if (name.contains("HELMET") || name.contains("CHESTPLATE") || 
            name.contains("LEGGINGS") || name.contains("BOOTS") || 
            type == Material.ELYTRA || type == Material.SHIELD) {
            return "cat-armor";
        }
        if (type.isEdible()) return "cat-food";
        if (name.contains("ORE") || name.contains("INGOT") || 
            name.contains("RAW") || type == Material.DIAMOND || 
            type == Material.EMERALD || type == Material.COAL) {
            return "cat-ores";
        }
        if (type == Material.ENCHANTED_BOOK || type == Material.BOOK) {
            return "cat-books";
        }
        if (type == Material.SPAWNER) return "cat-spawners";
        if (type.isBlock() && name.contains("LOG") == false && name.contains("LEAVES") == false) {
            if (name.contains("STONE") || name.contains("BRICK") || name.contains("PLANK")) {
                return "cat-blocks";
            }
        }
        if (name.contains("SEED") || name.contains("WHEAT") || 
            type == Material.SUGAR_CANE || type == Material.CARROT || 
            type == Material.POTATO || type == Material.PUMPKIN) {
            return "cat-farm";
        }
        if (type == Material.ROTTEN_FLESH || type == Material.BONE || 
            type == Material.GUNPOWDER || type == Material.STRING || 
            type == Material.ENDER_PEARL) {
            return "cat-drops";
        }
        return "cat-items";
    }
}
