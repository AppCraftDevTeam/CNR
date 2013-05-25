package me.RDNachoz.Cops_and_Robbers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.logging.Level;


import me.RDNachoz.Cops_and_Robbers.Commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

public class CR extends JavaPlugin {
	public HashMap<String, Boolean> arenaOpen = new HashMap<String, Boolean>();
	public HashMap<String, String> serverAdmin = new HashMap<String, String>();
	public HashMap<String, Integer> playing = new HashMap<String, Integer>();

	public CommandExecutor Commands = new Commands(this);

	public FileConfiguration config;
	public FileConfiguration spawns = null;
	public File spawnsFile = null;

	public Listener CNRListener = new CNRListener(this);
	
	public void onEnable(){
		config = this.getConfig();
		config.options().copyDefaults(true);
		if(!new File(getDataFolder(), "config.yml").exists())
			saveDefaultConfig();
		spawns = this.getSpawns();
		spawns.options().copyDefaults(true);
		if(!new File(getDataFolder(), "spawns.yml").exists())
			this.saveSpawns();
		this.getCommand("cr").setExecutor(Commands);
		this.getServer().getPluginManager().registerEvents(CNRListener, this);
		System.out.println("[CNR]" + "Cops And Robbers has been Enabled!");
	}
	public void reloadSpawns() {
		if (spawnsFile == null) {
			spawnsFile = new File(getDataFolder(), "arenas.yml");
		}
		spawns = YamlConfiguration.loadConfiguration(spawnsFile);
		InputStream defConfigStream = this.getResource("arenas.yml");
		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			spawns.setDefaults(defConfig);
		}
	}
	public FileConfiguration getSpawns() {
		if (spawns == null) {
			this.reloadSpawns();
		}
		return spawns;
	}
	public void saveSpawns() {
		if (spawns == null || spawnsFile == null) {
			return;
		}
		try {
			getSpawns().save(spawnsFile);
		} catch (IOException ex) {
			this.getLogger().log(Level.SEVERE, "Could not save config to " + spawnsFile, ex);
		}
	}
	public WorldEditPlugin hookWE() {
		Plugin wPlugin = getServer().getPluginManager().getPlugin("WorldEdit");

		if ((wPlugin == null) || (!(wPlugin instanceof WorldEditPlugin)))
			return null;

		return (WorldEditPlugin) wPlugin;
	}
}
