package com.comonier.ah;

import com.comonier.ah.commands.*;
import com.comonier.ah.managers.*;
import com.comonier.ah.menus.CategoryMenu;
import com.comonier.ah.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;

public class AH extends JavaPlugin {
    private static AH instance;
    private MessageManager messageManager;
    private MenuManager menuManager;
    private SearchManager searchManager;
    private AuctionManager auctionManager;
    private EconomyManager economyManager;
    private TaskManager taskManager;
    private SoldManager soldManager;
    private PurchaseManager purchaseManager;
    private ExpiredManager expiredManager;
    private CategoryMenu categoryMenu;

    @Override
    public void onEnable() {
        instance = this;
        if (!getDataFolder().exists()) getDataFolder().mkdirs();
        saveDefaultConfig();
        loadLanguageFiles();
        this.messageManager = new MessageManager(this);
        this.menuManager = new MenuManager(this, messageManager);
        this.searchManager = new SearchManager(this);
        this.economyManager = new EconomyManager(this);
        this.soldManager = new SoldManager(this);
        this.purchaseManager = new PurchaseManager(this);
        this.expiredManager = new ExpiredManager(this);
        this.auctionManager = new AuctionManager(this);
        this.taskManager = new TaskManager(this);
        this.categoryMenu = new CategoryMenu(menuManager, messageManager);
        AuctionCommand auCmd = new AuctionCommand(this, categoryMenu);
        getCommand("ah").setExecutor(auCmd);
        getCommand("ah").setTabCompleter(new AuctionTabCompleter());
        getServer().getPluginManager().registerEvents(new AuctionListener(this, categoryMenu, searchManager), this);
        getServer().getPluginManager().registerEvents(new SearchListener(searchManager), this);
    }

    @Override
    public void onDisable() {
        if (auctionManager != null) auctionManager.saveToDatabase();
        Bukkit.getOnlinePlayers().forEach(p -> p.closeInventory());
        instance = null;
    }

    private void loadLanguageFiles() {
        for (String lang : new String[]{"en", "pt"}) {
            String file = "messages_" + lang + ".yml";
            if (!(new File(getDataFolder(), file)).exists()) saveResource(file, false);
        }
    }

    public static AH getInstance() { return instance; }
    public MessageManager getMessageManager() { return messageManager; }
    public MenuManager getMenuManager() { return menuManager; }
    public SearchManager getSearchManager() { return searchManager; }
    public AuctionManager getAuctionManager() { return auctionManager; }
    public EconomyManager getEconomyManager() { return economyManager; }
    public SoldManager getSoldManager() { return soldManager; }
    public PurchaseManager getPurchaseManager() { return purchaseManager; }
    public ExpiredManager getExpiredManager() { return expiredManager; }
}
