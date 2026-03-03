package com.comonier.ah.commands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
public class AuctionTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            completions.add("sell");
            completions.add("active");
            completions.add("expired");
            completions.add("sales");
            completions.add("purchase");
            if (sender.hasPermission("ah.admin")) {
                completions.add("reload");
                completions.add("remove");
            }
            return completions.stream()
                .filter(s -> s.toLowerCase().startsWith(args[0].toLowerCase()))
                .collect(Collectors.toList());
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("remove") && sender.hasPermission("ah.admin")) {
            return null;
        }
        return new ArrayList<>();
    }
}
