package de.mrstein.categoryaddon;

import de.mrstein.categoryaddon.creator.Commands;
import de.mrstein.categoryaddon.editor.EditorListener;
import de.mrstein.customheads.reflection.TagEditor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;


public class CategoryAddon extends JavaPlugin {

    private static JavaPlugin instance;

    private static TagEditor tagEditor;

    public void onEnable() {

        instance = this;
        if(!getServer().getPluginManager().isPluginEnabled("CustomHeads")) {
            getServer().getConsoleSender().sendMessage("§cI searched everywhere but I couldnt find CustomHeads on your Server =(");
            getServer().getPluginManager().disablePlugin(instance);
            return;
        } else {
            Plugin pl = getServer().getPluginManager().getPlugin("CustomHeads");
            if(Integer.parseInt(pl.getDescription().getVersion().replace(".", "")) < 290) {
                getServer().getConsoleSender().sendMessage("§cWell I found CustomHeads... but I only work with Version 2.9+");
                getServer().getPluginManager().disablePlugin(instance);
                return;
            }
        }
        tagEditor = new TagEditor("ccTags");

        getCommand("categoryaddon").setExecutor(new Commands());
        getCommand("categoryeditor").setExecutor(new Commands());

        getServer().getPluginManager().registerEvents(new EditorListener(), this);
//        getServer().getPluginManager().disablePlugin(this);
    }

    public void onDisable() {}

    public static JavaPlugin getInstance() { return instance; }

    public static TagEditor getTagEditor() { return tagEditor; }
}
