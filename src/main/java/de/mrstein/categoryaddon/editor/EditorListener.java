package de.mrstein.categoryaddon.editor;

import de.mrstein.categoryaddon.CategoryAddon;
import de.mrstein.customheads.CustomHeads;
import de.mrstein.customheads.category.Category;
import de.mrstein.customheads.utils.ItemEditor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
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

            switch (args[0]) {
                case "create":
                    event.setCancelled(true);
                    break;
                case "edit":
                    event.setCancelled(true);
                    break;
                case "delete":
                    event.setCancelled(true);
                    if (args.length < 2) {
                        // Delete Mode
                        if (player.hasPermission("cc.editor.category.delete")) {
//                            event.setCurrentItem(new ItemEditor(CategoryAddon.getTagEditor().addTags(event.getCurrentItem(), "active#>")).addEnchantment());
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
                                Category category = CustomHeads.getCategoryLoader().getCategory(args[2]);
                                if (category != null) {
                                    Inventory confirmDelete = Bukkit.createInventory(null, 9 * 3, "Delete " + category.getName() + "?");
                                    confirmDelete.setItem(11, CategoryAddon.getTagEditor().setTags(new ItemEditor(Material.SKULL, (short) 3).setDisplayName("§aYes").setLore("§cThis cannot be undone!").setTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzYxZTViMzMzYzJhMzg2OGJiNmE1OGI2Njc0YTI2MzkzMjM4MTU3MzhlNzdlMDUzOTc3NDE5YWYzZjc3In19fQ==").getItem(), "cEditor", "confirmDelete#>category#>" + category.getId()));
                                    confirmDelete.setItem(13, CategoryAddon.getTagEditor().setTags(new ItemEditor(category.getCategoryIcon()).setLore("").getItem(), "cEditor", "blockMoving"));
                                    confirmDelete.setItem(15, CategoryAddon.getTagEditor().setTags(new ItemEditor(Material.SKULL, (short) 3).setDisplayName("§cNo").setTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGJhYzc3NTIwYjllZWU2NTA2OGVmMWNkOGFiZWFkYjAxM2I0ZGUzOTUzZmQyOWFjNjhlOTBlNDg2NjIyNyJ9fX0=").getItem(), "cEditor", "openInfo#>" + category.getId()));
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
                    break;
                case "open":
                    event.setCancelled(true);
                    if (args[1].equals("cOverview")) {
                        player.openInventory(CategoryEditor.getCategoryOverview(player));
                    }
                    break;
                case "convert":
                    event.setCancelled(true);
                    break;
                case "blockMoving":
                    event.setCancelled(true);
                    break;
                case "openInfo":
                    event.setCancelled(true);
                    Category category = CustomHeads.getCategoryLoader().getCategory(args[1]);
                    if (category != null) {
                        player.sendMessage(args[1]);
                        CategoryEditor editor = new CategoryEditor(category, player);
                        editor.showCategoryInfos();
                    }
                    break;
                case "confirmDelete":
                    // cEditor confirmDelete#>category/subCategory#>id
                    if (args[1].equals("category")) {
                        player.openInventory(CategoryEditor.getCategoryOverview(player));
                        if (player.hasPermission("cc.editor.category.delete")) {

                            return;
                        }
                        player.sendMessage("§cYou don't have enough Permission to delete this.");
                    } else if (args[1].equals("subCategory")) {
                        player.openInventory(CategoryEditor.getCategoryOverview(player));
                        if (player.hasPermission("cc.editor.subCategory.delete")) {

                            return;
                        }
                        player.sendMessage("§cYou don't have enough Permission to delete this.");
                    }
                    break;
                case "selectCategory":

                    break;
            }
        }
    }

}
