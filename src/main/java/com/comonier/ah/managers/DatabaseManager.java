package com.comonier.ah.managers;

import com.comonier.ah.AH;
import com.comonier.ah.models.AuctionItem;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DatabaseManager {

    private final AH plugin;
    private final File file;
    private FileConfiguration config;

    public DatabaseManager(AH plugin) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), "auctions.yml");
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    /**
     * Salva a lista de itens ativos no arquivo
     */
    public void saveAuctions(List<AuctionItem> items) {
        config.set("auctions", null); // Limpa para sobrescrever
        
        int index = 0;
        for (AuctionItem item : items) {
            String path = "auctions." + index;
            config.set(path + ".seller", item.getSellerUUID().toString());
            config.set(path + ".item", item.getItemStack());
            config.set(path + ".price", item.getPrice());
            config.set(path + ".expiry", item.getExpiryTime());
            index++;
        }

        try {
            config.save(file);
        } catch (IOException e) {
            plugin.getLogger().severe("Erro ao salvar auctions.yml!");
        }
    }

    /**
     * Carrega os itens do arquivo para a memória
     */
    public List<AuctionItem> loadAuctions() {
        List<AuctionItem> items = new ArrayList<>();
        if (!config.contains("auctions")) {
            return items;
        }

        for (String key : config.getConfigurationSection("auctions").getKeys(false)) {
            String path = "auctions." + key;
            UUID seller = UUID.fromString(config.getString(path + ".seller"));
            ItemStack stack = config.getItemStack(path + ".item");
            double price = config.getDouble(path + ".price");
            long expiry = config.getLong(path + ".expiry");
            long duration = expiry - System.currentTimeMillis();

            items.add(new AuctionItem(seller, stack, price, duration));
        }
        return items;
    }
}
