package com.comonier.ah.menus;

import com.comonier.ah.managers.MenuManager;
import com.comonier.ah.managers.MessageManager;
import com.comonier.ah.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class CategoryMenu {
    private final MenuManager menuManager;
    private final MessageManager mm;

    public CategoryMenu(MenuManager menuManager, MessageManager mm) {
        this.menuManager = menuManager;
        this.mm = mm;
    }

    public void open(Player player) {
        String title = mm.getMessage("menu-categories");
        Inventory gui = menuManager.createBaseInventory(title);

        // Slot 13: Todos os leilões
        gui.setItem(13, new ItemBuilder(Material.BOOK).setName(mm.getMessage("button-all")).build());

        // Fileira 1 (Slots 19-25)
        gui.setItem(19, new ItemBuilder(Material.GOLD_INGOT).setName(mm.getMessage("cat-cash")).build());
        gui.setItem(20, new ItemBuilder(Material.TRIPWIRE_HOOK).setName(mm.getMessage("cat-keys")).build());
        gui.setItem(21, new ItemBuilder(Material.GOLDEN_SHOVEL).setName(mm.getMessage("cat-protection")).build());
        gui.setItem(22, new ItemBuilder(Material.BRICKS).setName(mm.getMessage("cat-blocks")).build());
        gui.setItem(23, new ItemBuilder(Material.STICK).setName(mm.getMessage("cat-items")).build());
        gui.setItem(24, new ItemBuilder(Material.DIAMOND_PICKAXE).setName(mm.getMessage("cat-tools")).build());
        gui.setItem(25, new ItemBuilder(Material.DIAMOND_CHESTPLATE).setName(mm.getMessage("cat-armor")).build());

        // Fileira 2 (Slots 28-34)
        gui.setItem(28, new ItemBuilder(Material.APPLE).setName(mm.getMessage("cat-food")).build());
        gui.setItem(29, new ItemBuilder(Material.ENCHANTED_BOOK).setName(mm.getMessage("cat-books")).build());
        gui.setItem(30, new ItemBuilder(Material.SPAWNER).setName(mm.getMessage("cat-spawners")).build());
        gui.setItem(31, new ItemBuilder(Material.ROTTEN_FLESH).setName(mm.getMessage("cat-drops")).build());
        gui.setItem(32, new ItemBuilder(Material.SHULKER_BOX).setName(mm.getMessage("cat-shulker")).build());
        gui.setItem(33, new ItemBuilder(Material.WHEAT).setName(mm.getMessage("cat-farm")).build());
        gui.setItem(34, new ItemBuilder(Material.DIAMOND_ORE).setName(mm.getMessage("cat-ores")).build());

        player.openInventory(gui);
    }
}
