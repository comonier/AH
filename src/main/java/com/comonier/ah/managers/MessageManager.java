package com.comonier.ah.managers;

import com.comonier.ah.AH;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;

public class MessageManager {

    private final AH plugin;
    private FileConfiguration messages;

    public MessageManager(AH plugin) {
        this.plugin = plugin;
        loadMessages();
    }

    /**
     * Carrega o arquivo de tradução definido na config.yml
     */
    public void loadMessages() {
        String lang = plugin.getConfig().getString("language", "en");
        String fileName = "messages_" + lang + ".yml";
        File file = new File(plugin.getDataFolder(), fileName);

        // Se o arquivo não existe, tenta carregar o padrão 'en'
        if (!file.exists()) {
            file = new File(plugin.getDataFolder(), "messages_en.yml");
        }

        this.messages = YamlConfiguration.loadConfiguration(file);
    }

    /**
     * Retorna uma mensagem formatada com cores
     */
    public String getMessage(String path) {
        String msg = messages.getString(path);
        
        // Verifica se a mensagem é nula antes de colorir
        if (msg == null) {
            return ChatColor.RED + "Missing message: " + path;
        }

        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    /**
     * Retorna o prefixo do plugin configurado
     */
    public String getPrefix() {
        return getMessage("prefix");
    }
}
