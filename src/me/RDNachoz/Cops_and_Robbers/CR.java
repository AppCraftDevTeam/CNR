package me.RDNachoz.Cops_and_Robbers;

import java.util.HashMap;

import me.RDNachoz.Cops_and_Robbers.Commands;
import me.RDNachoz.Cops_and_Robbers.managers.MessageM;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

public class CR extends JavaPlugin {
	public HashMap<String, Boolean> arenaOpen = new HashMap<String, Boolean>();
	public HashMap<String, String> serverAdmin = new HashMap<String, String>();
	public HashMap<String, Integer> playing = new HashMap<String, Integer>();

	public CommandExecutor Commands = new Commands(this);

	public Listener CNRListener = new CNRListener(this);

	public void onEnable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		W.config.setDefault("default_file", true);
		W.spawns.setDefault("default_file", true);
		
		W.messages.setDefault("error.nopermission", "%errYou don't have the permissions to do that!");
		W.messages.setDefault("error.notagame", "%errYou silly! That's not a game!");
		W.messages.setDefault("error.notingame", "%errYou aren't in game!");

		W.newFiles();
		this.getCommand("cr").setExecutor(Commands);
		this.getServer().getPluginManager().registerEvents(CNRListener, this);
		MessageM.log("%norm%name%" + ChatColor.GREEN + ChatColor.MAGIC + " +" + "%norm" +
				" v%version% is now Enabled.", true,
				"name-"+pdfFile.getName(), "version-"+pdfFile.getVersion());
	}

	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		MessageM.log("%norm%name%" + ChatColor.RED + ChatColor.MAGIC + " -" + "%norm" +
				" v%version% is now Disabled.", true,
				"name-"+pdfFile.getName(), "version-"+pdfFile.getVersion());
	}

	/*
	 * TODO Note! I removed the spawns get and setters,
	 * Use W.spawns.getFile() to get the stuff.
	 * If you do W.spawns.load(), it will reload the config.
	 * Hope you understand this otherwise, revert this commit.
	 * ~ Steffion
	 */

	public WorldEditPlugin hookWE() {
		Plugin wPlugin = getServer().getPluginManager().getPlugin("WorldEdit");

		if ((wPlugin == null) || (!(wPlugin instanceof WorldEditPlugin)))
			return null;

		return (WorldEditPlugin) wPlugin;
	}
}
