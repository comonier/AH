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
public class ActiveAuctionsMenu {
    private final MenuManager menuManager;
    private final MessageManager mm;
    public ActiveAuctionsMenu(AH plugin) {
        this.menuManager = plugin.getMenuManager();
        this.mm = plugin.getMessageManager();
    }
    public void open(Player player, int page, List<AuctionItem> items, String filterName) {
        String title = mm.getMessage("menu-active") + " [" + filterName + "]";
        Inventory gui = menuManager.createBaseInventory(title);
        int start = (page - 1) * 45;
        int end = start + 45;
        for (int i = start; i < end; i++) {
            if (i >= items.size()) break;
            AuctionItem aItem = items.get(i);
            boolean isOwner = aItem.getSellerUUID().equals(player.getUniqueId());
            ItemBuilder ib = new ItemBuilder(aItem.getItemStack().getType())
                .setName(aItem.getItemStack().getItemMeta().getDisplayName())
                .setLore(
                    "&7Vendedor: &f" + aItem.getSellerName(),
                    "&7Preco: &a$" + aItem.getPrice(),
                    "",
                    isOwner ? "&cClique para cancelar este leilao!" : "&eClique para comprar!"
                );
            gui.setItem(i - start, ib.build());
        }
        if (page > 1) gui.setItem(48, new ItemBuilder(Material.ARROW).setName("&aPagina Anterior").build());
        if (items.size() > end) gui.setItem(50, new ItemBuilder(Material.ARROW).setName("&aProxima Pagina").build());
        player.openInventory(gui);
    }
}
