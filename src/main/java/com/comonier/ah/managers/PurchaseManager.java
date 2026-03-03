package com.comonier.ah.managers;
import com.comonier.ah.AH;
import com.comonier.ah.models.AuctionItem;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
public class PurchaseManager {
    private final AH plugin;
    private final File file;
    private final YamlConfiguration config;
    public PurchaseManager(AH plugin) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), "purchases.yml");
        this.config = YamlConfiguration.loadConfiguration(file);
    }
    public void recordPurchase(UUID buyer, AuctionItem item) {
        String path = buyer.toString() + "." + System.currentTimeMillis();
        config.set(path + ".item", item.getItemStack());
        config.set(path + ".price", item.getPrice());
        config.set(path + ".seller", item.getSellerUUID().toString());
        config.set(path + ".category", item.getCategory());
        try { config.save(file); } catch (IOException e) { plugin.getLogger().severe("Erro ao salvar purchases.yml!"); }
    }
    public List<AuctionItem> getPurchasedItems(UUID buyer) {
        List<AuctionItem> purchases = new ArrayList<>();
        String uuidStr = buyer.toString();
        if (config.contains(uuidStr) == false) return purchases;
        for (String timestamp : config.getConfigurationSection(uuidStr).getKeys(false)) {
            String path = uuidStr + "." + timestamp;
            purchases.add(new AuctionItem(
                UUID.fromString(config.getString(path + ".seller")),
                config.getItemStack(path + ".item"),
                config.getDouble(path + ".price"),
                0L,
                config.getString(path + ".category")
            ));
        }
        return purchases;
    }
}
