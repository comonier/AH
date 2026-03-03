package com.comonier.ah.menus;

import com.comonier.ah.AH;
import com.comonier.ah.managers.MenuManager;
import com.comonier.ah.managers.MessageManager;
import com.comonier.ah.models.AuctionItem;
import com.comonier.ah.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import java.util.List;

public class HistoryMenu {

    private final AH plugin;
    private final MenuManager menuManager;
    private final MessageManager mm;

    public HistoryMenu(AH plugin) {
        this.plugin = plugin;
        this.menuManager = plugin.getMenuManager();
        this.mm = plugin.getMessageManager();
    }

    /**
     * Abre o menu de histórico com paginação (54 slots de conteúdo)
     */
    public void open(Player player, String title, int page, List<AuctionItem> items) {
        Inventory gui = menuManager.createBaseInventory(title + " - " + page);

        int start = (page - 1) * 45;
        int end = start + 45;

        // Preenchimento dos itens históricos nos slots 0-44
        for (int i = start; i - end < 0; i++) {
            if (i - items.size() >= 0) {
                break;
            }

            AuctionItem auctionItem = items.get(i);
            int slot = i - start;
            
            // Adiciona lore extra indicando preço e data se necessário
            gui.setItem(slot, new ItemBuilder(auctionItem.getItemStack().getType())
                    .setName(auctionItem.getItemStack().getItemMeta().getDisplayName())
                    .setLore("&7Preço: &a$" + auctionItem.getPrice()).build());
        }

        // Botão Página Anterior (Slot 48)
        if (page - 1 > 0) {
            gui.setItem(48, new ItemBuilder(Material.ARROW)
                    .setName("&aPágina Anterior").build());
        }

        // Botão Próxima Página (Slot 50)
        if (items.size() - end > 0) {
            gui.setItem(50, new ItemBuilder(Material.ARROW)
                    .setName("&aPróxima Página").build());
        }

        player.openInventory(gui);
    }
}
