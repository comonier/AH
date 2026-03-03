package com.comonier.ah.commands;
import com.comonier.ah.AH;
import com.comonier.ah.managers.*;
import com.comonier.ah.menus.*;
import com.comonier.ah.models.AuctionItem;
import com.comonier.ah.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;
import java.util.List;
public class AuctionCommand implements CommandExecutor {
    private final AH plugin;
    private final CategoryMenu categoryMenu;
    private final MessageManager mm;
    public AuctionCommand(AH plugin, CategoryMenu categoryMenu) {
        this.plugin = plugin;
        this.categoryMenu = categoryMenu;
        this.mm = plugin.getMessageManager();
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player == false) {
            if (args.length > 0 && args[0].equalsIgnoreCase("reload")) { handleReload(sender); return true; }
            sender.sendMessage(mm.getMessage("only-players")); return true;
        }
        Player p = (Player) sender;
        if (args.length == 0) { categoryMenu.open(p); return true; }
        String sub = args[0].toLowerCase();
        if (sub.equals("reload")) { handleReload(p); return true; }
        if (sub.equals("remove")) { handleAdminRemove(p, args); return true; }
        if (sub.equals("sellblocks")) { handleSellBlocks(p, args); return true; }
        if (sub.equals("sell")) { handleSell(p, args); return true; }
        handleMenus(p, sub);
        return true;
    }
    private void handleReload(CommandSender sender) {
        if (sender.hasPermission("ah.admin") == false) { sender.sendMessage(mm.getPrefix() + mm.getMessage("no-permission")); return; }
        plugin.reloadConfig();
        plugin.getMessageManager().loadMessages();
        sender.sendMessage(mm.getPrefix() + "§aConfiguracoes e mensagens recarregadas!");
        plugin.getWebhookManager().announce("&aAH recarregado com sucesso!", ":arrows_counterclockwise: AH recarregado com sucesso!");
    }
    private void handleAdminRemove(Player p, String[] args) {
        if (p.hasPermission("ah.admin") == false) { p.sendMessage(mm.getPrefix() + mm.getMessage("no-permission")); return; }
        if (args.length != 2) { p.sendMessage(mm.getPrefix() + "§cUse: /ah remove <jogador>"); return; }
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
        List<AuctionItem> all = plugin.getAuctionManager().getByCategory("cat-protection");
        all.addAll(plugin.getAuctionManager().getActiveAuctions());
        List<AuctionItem> toMove = new ArrayList<>();
        for (AuctionItem item : all) { if (item.getSellerUUID().equals(target.getUniqueId())) toMove.add(item); }
        if (toMove.isEmpty()) { p.sendMessage(mm.getPrefix() + "§cSem itens ativos."); return; }
        for (AuctionItem item : toMove) { plugin.getExpiredManager().addExpiredItem(target.getUniqueId(), item); plugin.getAuctionManager().removeItem(item); }
        p.sendMessage(mm.getPrefix() + "§eItens de " + args[1] + " movidos para expirados.");
    }
    private void handleSellBlocks(Player p, String[] args) {
        if (args.length != 3) { p.sendMessage(mm.getPrefix() + "§cUse: /ah sellblocks <quantidade> <preco>"); return; }
        try {
            int amount = Integer.parseInt(args[1]);
            double price = Double.parseDouble(args[2]);
            int playerBlocks = plugin.getClaimBlockManager().getBonusBlocks(p.getUniqueId());
            if (amount > playerBlocks) { p.sendMessage(mm.getPrefix() + "§cVoce nao tem " + amount + " blocos!"); return; }
            double min = amount * 10.0; double max = amount * 100.0;
            if (min > price || price > max) { p.sendMessage(mm.getPrefix() + "§cPreco deve estar entre " + (int)min + " e " + (int)max); return; }
            ItemStack item = new ItemBuilder(Material.GOLDEN_SHOVEL).setName("§6" + amount + " Blocos de Protecao").setLore("§7Vendedor: §f" + p.getName(), "§7Preco: §a$" + (int)price).build();
            plugin.getAuctionManager().listItem(p.getUniqueId(), item, price, "cat-protection");
            plugin.getClaimBlockManager().removeBonusBlocks(p.getUniqueId(), amount);
            String msg = "&f" + p.getName() + " &eadicionou &f" + amount + " blocos de protecao &aa venda no auction house por &a$" + (int)price;
            plugin.getWebhookManager().announce(msg, ":shield: " + p.getName() + " adicionou " + amount + " blocos por $" + (int)price);
        } catch (Exception e) { p.sendMessage(mm.getPrefix() + "§cErro ao processar venda."); }
    }
    private void handleSell(Player p, String[] args) {
        if (args.length != 2) { p.sendMessage(mm.getPrefix() + "§cUse: /ah sell <preco>"); return; }
        ItemStack item = p.getInventory().getItemInMainHand();
        if (item == null || item.getType() == Material.AIR) { p.sendMessage(mm.getPrefix() + mm.getMessage("item-error")); return; }
        try {
            double price = Double.parseDouble(args[1]);
            if (0.01 > price) { p.sendMessage(mm.getPrefix() + mm.getMessage("invalid-price")); return; }
            plugin.getAuctionManager().listItem(p.getUniqueId(), item.clone(), price, null);
            String itemName = item.hasItemMeta() && item.getItemMeta().hasDisplayName() ? item.getItemMeta().getDisplayName() : item.getType().name();
            p.getInventory().setItemInMainHand(null);
            String msg = "&f" + p.getName() + " &eadicionou &f" + itemName + " &aa venda no auction house por &a$" + (int)price;
            plugin.getWebhookManager().announce(msg, ":shopping_cart: " + p.getName() + " adicionou " + itemName + " por $" + (int)price);
        } catch (Exception e) { p.sendMessage(mm.getPrefix() + "§cPreco invalido."); }
    }
    private void handleMenus(Player p, String sub) {
        HistoryMenu h = new HistoryMenu(plugin);
        if (sub.equals("sales")) h.open(p, mm.getMessage("menu-sales"), 1, plugin.getSoldManager().getSoldItems(p.getUniqueId()));
        if (sub.equals("purchase")) h.open(p, mm.getMessage("menu-purchases"), 1, plugin.getPurchaseManager().getPurchasedItems(p.getUniqueId()));
        if (sub.equals("expired")) h.open(p, mm.getMessage("menu-expired"), 1, plugin.getExpiredManager().getExpiredItems(p.getUniqueId()));
        if (sub.equals("active")) p.performCommand("ah");
    }
}
