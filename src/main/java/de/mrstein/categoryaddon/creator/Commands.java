package de.mrstein.categoryaddon.creator;

import de.mrstein.categoryaddon.CategoryAddon;
import de.mrstein.categoryaddon.editor.CategoryEditor;
import de.mrstein.customheads.CustomHeads;
import de.mrstein.customheads.category.Category;
import de.mrstein.customheads.category.SubCategory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Random;

/**
 * by MrStein =]
 */
public class Commands implements CommandExecutor {

    private String[] rA = new String[] {"Nah", "Consoles dont have Inventories", "Yes", "Hm", "I have no idea how I got here", "Example Text", "yay"};

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if((sender instanceof ConsoleCommandSender)) {
            sender.sendMessage("CategoryAddon says: " + rA[new Random().nextInt(rA.length - 1)]);
            return true;
        }
        Player player = (Player) sender;
        if (command.getName().equalsIgnoreCase("categoryaddon")) {
            player.sendMessage("§eCategoryAddon v§e" + CategoryAddon.getInstance().getDescription().getVersion() + "\n§7Makes it easier to create and manage Categories from §bCustomHeads");
            return true;
        }
        if(command.getName().equalsIgnoreCase("categoryeditor") || command.getName().equalsIgnoreCase("cedit")) {
            if(player.hasPermission("cc.editor.open")) {
                if (args.length > 0) {
                    if(args[0].contains(":")) {
                        SubCategory category = CustomHeads.getCategoryImporter().getSubCategory(args[0]);
                        if(category != null) {
                            new CategoryEditor(category, player).showCategoryInfos();
                            return true;
                        }
                        player.sendMessage("noSuchCategory");
                        return true;
                    } else {
                        Category category = CustomHeads.getCategoryImporter().getCategory(args[0]);
                        if(category != null) {
                            new CategoryEditor(category, player).showCategoryInfos();
                            return true;
                        }
                        player.sendMessage("noSuchCategory");
                        return true;
                    }
                } else {
                    player.openInventory(CategoryEditor.getCategoryOverview(player));
                    return true;
                }
            }
            player.sendMessage("noPerm");
        }
        return false;
    }

}
