package com.comonier.ah.managers;

import com.comonier.ah.AH;
import com.comonier.ah.models.AuctionItem;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SoldManager {

    private final AH plugin;
    private final File file;
    private final YamlConfiguration config;

    public SoldManager(AH plugin) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), "sold.yml");
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    /**
     * Registra um item vendido no histórico do vendedor
     */
    public void recordSale(UUID seller, AuctionItem item) {
        String path = seller.toString() + "." + System.currentTimeMillis();
        config.set(path + ".item", item.getItemStack());
        config.set(path + ".price", item.getPrice());
        
        try {
            config.save(file);
        } catch (IOException e) {
            plugin.getLogger().severe("Erro ao salvar sold.yml!");
        }
    }

    /**
     * Retorna a lista de itens vendidos por um jogador específico
     */
    public List<AuctionItem> getSoldItems(UUID seller) {
        List<AuctionItem> sold = new ArrayList<>();
        String uuidStr = seller.toString();

        if (config.contains(uuidStr) == false) {
            return sold;
        }

        for (String timestamp : config.getConfigurationSection(uuidStr).getKeys(false)) {
            String path = uuidStr + "." + timestamp;
            sold.add(new AuctionItem(
                seller, 
                config.getItemStack(path + ".item"), 
                config.getDouble(path + ".price"), 
                0
            ));
        }
        return sold;
    }
}
