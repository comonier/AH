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
            return completions.stream().filter(s -> s.startsWith(args[0].toLowerCase())).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}
