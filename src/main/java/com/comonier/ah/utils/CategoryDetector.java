package com.comonier.ah.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class CategoryDetector {

    /**
     * Identifica a categoria de um item baseado no seu Material
     */
    public static String getCategoryPath(ItemStack item) {
        if (item == null) return "cat-items";
        
        Material type = item.getType();
        String name = type.name();

        // Shulkers (Prioridade)
        if (name.contains("SHULKER_BOX")) return "cat-shulker";

        // Ferramentas e Armas
        if (name.contains("SWORD") || name.contains("PICKAXE") || 
            name.contains("AXE") || name.contains("SHOVEL") || 
            name.contains("HOE") || type == Material.BOW || 
            type == Material.CROSSBOW || type == Material.TRIDENT) {
            return "cat-tools";
        }

        // Armaduras
        if (name.contains("HELMET") || name.contains("CHESTPLATE") || 
            name.contains("LEGGINGS") || name.contains("BOOTS") || 
            type == Material.ELYTRA || type == Material.SHIELD) {
            return "cat-armor";
        }

        // Comidas
        if (type.isEdible()) return "cat-food";

        // Minérios e Barras
        if (name.contains("ORE") || name.contains("INGOT") || 
            name.contains("RAW") || type == Material.DIAMOND || 
            type == Material.EMERALD || type == Material.COAL) {
            return "cat-ores";
        }

        // Livros Encantados
        if (type == Material.ENCHANTED_BOOK || type == Material.BOOK) {
            return "cat-books";
        }

        // Geradores (Spawners)
        if (type == Material.SPAWNER) return "cat-spawners";

        // Blocos de Construção
        if (type.isBlock() && !name.contains("LOG") && !name.contains("LEAVES")) {
            if (name.contains("STONE") || name.contains("BRICK") || name.contains("PLANK")) {
                return "cat-blocks";
            }
        }

        // Fazenda e Plantação
        if (name.contains("SEED") || name.contains("WHEAT") || 
            type == Material.SUGAR_CANE || type == Material.CARROT || 
            type == Material.POTATO || type == Material.PUMPKIN) {
            return "cat-farm";
        }

        // Drop de Mobs
        if (type == Material.ROTTEN_FLESH || type == Material.BONE || 
            type == Material.GUNPOWDER || type == Material.STRING || 
            type == Material.ENDER_PEARL) {
            return "cat-drops";
        }

        return "cat-items"; // Categoria padrão caso não encaixe em nenhuma
    }
}
