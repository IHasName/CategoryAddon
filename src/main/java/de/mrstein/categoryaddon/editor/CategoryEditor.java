package de.mrstein.categoryaddon.editor;

import de.mrstein.categoryaddon.CategoryAddon;
import de.mrstein.customheads.CustomHeads;
import de.mrstein.customheads.api.CustomHead;
import de.mrstein.customheads.category.BaseCategory;
import de.mrstein.customheads.category.Category;
import de.mrstein.customheads.category.SubCategory;
import de.mrstein.customheads.utils.ItemEditor;
import de.mrstein.customheads.utils.ScrollableInventory;
import de.mrstein.customheads.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CategoryEditor {

    private Player editor;

    private BaseCategory category;

    public CategoryEditor(BaseCategory category, Player editor) {
        this.category = category;
        this.editor = editor;
    }

    public static Inventory getCategoryOverview(Player editor) {
        List<ItemStack> categories = new ArrayList<>();
        for (Category category : CustomHeads.getCategoryImporter().getCategoryList()) {
            categories.add(CategoryAddon.getTagEditor().setTags(new ItemEditor(category.hasCategoryIcon() ? category.getCategoryIcon() : category.getIcons().get(0)).setDisplayName("§e" + category.getName())
                    .setLore(Arrays.asList(
                            "§7ID: §e" + category.getId(),
                            "§7Permission: §e" + category.getPermission(),
                            "§7Has Subcategories: §e" + (category.hasSubCategories() ? "Yes" : "No")
                    )).getItem(), "cEditor", "openInfo#>" + category.getId()));
        }

        ScrollableInventory scrollableInventory = new ScrollableInventory("§eAll Categories", categories);

        scrollableInventory.setBarItem(1, editor.hasPermission("cc.editor.category.create") ? CategoryAddon.getTagEditor().setTags(new CustomHead("http://textures.minecraft.net/texture/3edd20be93520949e6ce789dc4f43efaeb28c717ee6bfcbbe02780142f716").setDisplayName("§aCreate").toItem(), "cEditor", "create#>") : CategoryAddon.getTagEditor().setTags(new CustomHead("http://textures.minecraft.net/texture/3edd20be93520949e6ce789dc4f43efaeb28c717ee6bfcbbe02780142f716").setDisplayName("§7Create").setLore("§cYou don't have enough Permission to do this!").toItem(), "cEditor", "blockMoving#>"));
        scrollableInventory.setBarItem(2, editor.hasPermission("cc.editor.category.edit") ? CategoryAddon.getTagEditor().setTags(Utils.createItem(Material.PAPER, 1, "§bEdit"), "cEditor", "edit#>") : CategoryAddon.getTagEditor().setTags(Utils.createItem(Material.PAPER, 1, "§7Edit", "§cYou don't have enough Permission to do this!"), "cEditor", "edit#>"));
        scrollableInventory.setBarItem(3, editor.hasPermission("cc.editor.category.delete") ? CategoryAddon.getTagEditor().setTags(new CustomHead("http://textures.minecraft.net/texture/91f211c6c32e77faf911278b8c729ca50c5d279d598fdb8719875f784def6ea").setDisplayName("§cDelete").toItem(), "cEditor", "delete#>") : CategoryAddon.getTagEditor().setTags(new CustomHead("http://textures.minecraft.net/texture/91f211c6c32e77faf911278b8c729ca50c5d279d598fdb8719875f784def6ea").setDisplayName("§7Delete").setLore("§cYou don't have enough Permission to do this!").toItem(), "cEditor", "blockMoving#>"));

        return scrollableInventory.getAsInventory();
    }

    /*
     * ----------------------------
     * |0 |1 |2 |3 |4 |5 |6 |7 |8 |
     * |9 |10|11|12|13|14|15|16|17|
     * |18|19|20|21|22|23|24|25|26|
     * ----------------------------
     *
     * 09 :
     * 10 :
     * 11 : Category Icon
     * 12 : SKULL_ITEM -> Edit Heads
     * 13 : PAPER -> Rename
     * 14 : EMERALD -> Convert (only when not Subcategory)
     * 15 :
     * 16 : BARRIER -> Delete
     * 17 :
     * */

    public Player getEditor() {
        return editor;
    }

    /*
     * -------------------
     * | Categories      |
     * |                 |
     * |                 |
     * |      2 3 1      |
     * -------------------
     *
     * 1: Delete
     * 2: Create
     * 3: Edit
     *
     * Create:
     *   Default: Normal
     *   -> Can later be changed to Sub Category
     *
     * */

    public void showCategoryInfos() {
        if (editor.hasPermission("cc.editor.showinfos")) {
            Inventory categoryEdit;

            CustomHead editheads = getRandomHeadFromCategory(category);

            if(category.isCategory()) {
                categoryEdit = Bukkit.createInventory(editor, 27, "§eCategory Info §8: §e" + category.getName());
                categoryEdit.setItem(11, CategoryAddon.getTagEditor().setTags(new ItemEditor(category.getAsCategory().getCategoryIcon()).setDisplayName("§7" + category.getAsCategory().getCategoryIcon().getItemMeta().getDisplayName()).getItem(), "blockMoving"));
                categoryEdit.setItem(12, CategoryAddon.getTagEditor().setTags((editheads == null ? new CustomHead("http://textures.minecraft.net/texture/badc048a7ce78f7dad72a07da27d85c0916881e5522eeed1e3daf217a38c1a") : editheads).setDisplayName("§aEdit Heads").setLore("").toItem(), "blockMoving"));
                categoryEdit.setItem(13, CategoryAddon.getTagEditor().setTags(Utils.createItem(Material.PAPER, 1, "§aRename"), "cEditor", "rename#>category#>" + category.getId()));
                categoryEdit.setItem(14, CategoryAddon.getTagEditor().setTags(Utils.createItem(category.getAsCategory().hasSubCategories() ? Material.REDSTONE : Material.EMERALD, 1, category.getAsCategory().hasSubCategories() ? "§cAlready converted" : "§aCovert to Subcategory"), "cEditor", category.getAsCategory().hasSubCategories() ? "convert#>" : "blockMoving#>"));
            } else {
                categoryEdit = Bukkit.createInventory(editor, 27, "§eSubCategory Info §8: §e" + category.getName());
                categoryEdit.setItem(11, CategoryAddon.getTagEditor().setTags(new ItemEditor(category.getAsSubCategory().getCategoryIcon()).setDisplayName("§7" + category.getAsSubCategory().getCategoryIcon().getItemMeta().getDisplayName()).getItem(), "blockMoving"));
                categoryEdit.setItem(12, CategoryAddon.getTagEditor().setTags((editheads == null ? new CustomHead("http://textures.minecraft.net/texture/badc048a7ce78f7dad72a07da27d85c0916881e5522eeed1e3daf217a38c1a") : editheads).setDisplayName("§aEdit Heads").setLore("").toItem(), "blockMoving"));
                categoryEdit.setItem(13, CategoryAddon.getTagEditor().setTags(Utils.createItem(Material.PAPER, 1, "§aRename"), "cEditor", "rename#>category#>" + category.getId()));
            }

            categoryEdit.setItem(16, CategoryAddon.getTagEditor().setTags(Utils.createItem(Material.BARRIER, 1, "§cDelete"), "cEditor", "delete#>category#>" + category.getId()));
            categoryEdit.setItem(18, CategoryAddon.getTagEditor().setTags(Utils.createHead(CustomHeads.getLanguageManager().BACK_GENERAL, CustomHeads.getLanguageManager().BACK_TO_PREVIOUS, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzM3NjQ4YWU3YTU2NGE1Mjg3NzkyYjA1ZmFjNzljNmI2YmQ0N2Y2MTZhNTU5Y2U4YjU0M2U2OTQ3MjM1YmNlIn19fQ=="), "cEditor", (category.isCategory() ? "open#>cOverview" : "openInfo#>" + category.getAsSubCategory().getOriginCategory().getId())));

            editor.openInventory(categoryEdit);
        }
    }

    private CustomHead getRandomHeadFromCategory(BaseCategory category) {
        List<CustomHead> heads = new ArrayList<>();
        if(category.isCategory()) {
            if (category.getAsCategory().hasSubCategories()) {
                for (SubCategory sub : category.getAsCategory().getSubCategories()) {
                    heads.addAll(sub.getHeads());
                }
            } else {
                heads.addAll(category.getAsCategory().getHeads());
            }
        } else {
            heads.addAll(category.getAsSubCategory().getHeads());
        }
        return heads.isEmpty() ? null : heads.get(new Random().nextInt(heads.size() - 1));
    }

}
