package com.comonier.ah.managers;

import com.comonier.ah.AH;
import org.bukkit.Bukkit;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class WebhookManager {
    private final AH plugin;
    private final String url;

    public WebhookManager(AH plugin) {
        this.plugin = plugin;
        this.url = plugin.getConfig().getString("discord-webhook", "");
    }

    public void announce(String msg, String discordMsg) {
        Bukkit.broadcastMessage(plugin.getMessageManager().getPrefix() + msg.replace("&", "§"));
        if (url.isEmpty()) return;
        
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "application/json");
                String json = "{\"content\": \"" + discordMsg + "\"}";
                try (OutputStream os = conn.getOutputStream()) {
                    os.write(json.getBytes(StandardCharsets.UTF_8));
                }
                conn.getResponseCode();
            } catch (Exception e) { Bukkit.getLogger().warning("Erro no Webhook AH!"); }
        });
    }
}
