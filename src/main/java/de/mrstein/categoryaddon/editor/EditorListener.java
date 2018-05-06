package de.mrstein.categoryaddon.editor;

import de.mrstein.categoryaddon.CategoryAddon;
import de.mrstein.customheads.CustomHeads;
import de.mrstein.customheads.api.CustomHead;
import de.mrstein.customheads.category.Category;
import de.mrstein.customheads.utils.ItemEditor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;


public class EditorListener implements Listener {

    @EventHandler
    public void onEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getInventory() == null || event.getRawSlot() > event.getInventory().getSize() || event.getCurrentItem() == null || !player.hasPermission("cc.editor"))
            return;
        if (CategoryAddon.getTagEditor().getTags(event.getCurrentItem()).contains("cEditor")) {
            String[] args = CategoryAddon.getTagEditor().getTags(event.getCurrentItem()).get(CategoryAddon.getTagEditor().indexOf(event.getCurrentItem(), "cEditor") + 1).split("#>");

            player.sendMessage("CEditor: §7" + CategoryAddon.getTagEditor().getTags(event.getCurrentItem()));

            if (args[0].equals("create")) {
                event.setCancelled(true);
            } else if (args[0].equals("edit")) {
                event.setCancelled(true);
            } else if (args[0].equals("delete")) {
                event.setCancelled(true);
                if (args.length < 2) {
                    // Delete Mode
                    if(player.hasPermission("cc.editor.category.delete")) {

                    }
                } else {

                    /*
                     * ----------------------------
                     * |0 |1 |2 |3 |4 |5 |6 |7 |8 |
                     * |9 |10|11|12|13|14|15|16|17|
                     * |18|19|20|21|22|23|24|25|26|
                     * ----------------------------
                     * */

                    if (args[1].equals("category")) {
                        if (player.hasPermission("cc.editor.category.delete")) {
                            Category category = CustomHeads.getCategoryImporter().getCategory(args[2]);
                            if (category != null) {
                                Inventory confirmDelete = Bukkit.createInventory(null, 9 * 3, "Delete " + category.getName() + "?");
                                confirmDelete.setItem(11, CategoryAddon.getTagEditor().setTags(new CustomHead("http://textures.minecraft.net/texture/361e5b333c2a3868bb6a58b6674a2639323815738e77e053977419af3f77").setDisplayName("§aYes").setLore("§cThis cannot be undone!").toItem(), "cEditor", "confirmDelete#>category#>" + category.getId()));
                                confirmDelete.setItem(13, CategoryAddon.getTagEditor().setTags(new ItemEditor(category.getCategoryIcon()).setLore(null).getItem(), "cEditor", "blockMoving"));
                                confirmDelete.setItem(15, CategoryAddon.getTagEditor().setTags(new CustomHead("http://textures.minecraft.net/texture/4bac77520b9eee65068ef1cd8abeadb013b4de3953fd29ac68e90e4866227").setDisplayName("§cNo").toItem(), "cEditor", "openInfo#>" + category.getId()));
                                player.openInventory(confirmDelete);
                            }
                            return;
                        }
                        player.sendMessage("You don't have enough Permission to delete this.");
                    } else if (args[1].equals("subCategory")) {
                        if (player.hasPermission("cc.editor.subCategory.delete")) {

                            return;
                        }
                        player.sendMessage("You don't have enough Permission to delete this.");
                    }

                }
            } else if (args[0].equals("open")) {
                event.setCancelled(true);
                if (args[1].equals("cOverview")) {
                    player.openInventory(CategoryEditor.getCategoryOverview(player));
                }
            } else if (args[0].equals("convert")) {
                event.setCancelled(true);
            } else if (args[0].equals("blockMoving")) {
                event.setCancelled(true);
            } else if (args[0].equals("openInfo")) {
                event.setCancelled(true);
                Category category = CustomHeads.getCategoryImporter().getCategory(args[1]);
                if (category != null) {
                    player.sendMessage(args[1]);
                    CategoryEditor editor = new CategoryEditor(category, player);
                    editor.showCategoryInfos();
                }
            } else if (args[0].equals("confirmDelete")) {
                // cEditor confirmDelete#>category/subCategory#>id
                if(args[1].equals("category")) {
                    player.openInventory(CategoryEditor.getCategoryOverview(player));
                    if(player.hasPermission("cc.editor.category.delete")) {

                        return;
                    }
                    player.sendMessage("§cYou don't have enough Permission to delete this.");
                } else if(args[1].equals("subCategory")) {
                    player.openInventory(CategoryEditor.getCategoryOverview(player));
                    if(player.hasPermission("cc.editor.subCategory.delete")) {

                        return;
                    }
                    player.sendMessage("§cYou don't have enough Permission to delete this.");
                }
            } else if (args[0].equals("")) {

            }
        }
    }

}
