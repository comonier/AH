package com.comonier.ah.managers;

import com.comonier.ah.AH;
import com.comonier.ah.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.entity.Player;

public class MenuManager {

    private final AH plugin;
    private final MessageManager mm;

    public MenuManager(AH plugin, MessageManager mm) {
        this.plugin = plugin;
        this.mm = mm;
    }

    /**
     * Aplica a barra de navegação inferior comum a todos os menus
     */
    public void applyNavigation(Inventory inv) {
        // Slot 45: Itens Vendidos (Diamante)
        inv.setItem(45, new ItemBuilder(Material.DIAMOND)
                .setName(mm.getMessage("button-sold"))
                .setLore(mm.getMessage("lore-sold")).build());

        // Slot 46: Itens Leiloados (Esmeralda)
        inv.setItem(46, new ItemBuilder(Material.EMERALD)
                .setName(mm.getMessage("button-auctioned"))
                .setLore(mm.getMessage("lore-auctioned")).build());

        // Slot 47: Itens Comprados (Barra de Ouro)
        inv.setItem(47, new ItemBuilder(Material.GOLD_INGOT)
                .setName(mm.getMessage("button-purchased"))
                .setLore(mm.getMessage("lore-purchased")).build());

        // Slot 49: Voltar ao Menu (PLAYER_HEAD)
        inv.setItem(49, new ItemBuilder(Material.PLAYER_HEAD)
                .setName(mm.getMessage("button-back")).build());

        // Slot 51: Pesquisa (OAK_SIGN)
        inv.setItem(51, new ItemBuilder(Material.OAK_SIGN)
                .setName(mm.getMessage("button-search"))
                .setLore(mm.getMessage("lore-search")).build());

        // Slot 52: Meus Leilões (Beacon)
        inv.setItem(52, new ItemBuilder(Material.BEACON)
                .setName(mm.getMessage("button-my-auctions"))
                .setLore(mm.getMessage("lore-my-auctions")).build());

        // Slot 53: Itens Cancelados ou Expirados (Clock)
        inv.setItem(53, new ItemBuilder(Material.CLOCK)
                .setName(mm.getMessage("button-cancelled"))
                .setLore(mm.getMessage("lore-cancelled")).build());
    }

    /**
     * Cria um inventário base de 54 slots
     */
    public Inventory createBaseInventory(String title) {
        Inventory inv = Bukkit.createInventory(null, 54, title);
        applyNavigation(inv);
        return inv;
    }
}
