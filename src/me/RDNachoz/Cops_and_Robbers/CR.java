package me.RDNachoz.Cops_and_Robbers;

import java.util.HashMap;
import me.RDNachoz.Cops_and_Robbers.Commands;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

public class CR extends JavaPlugin {
	public HashMap<String, Boolean> arenaOpen = new HashMap<String, Boolean>();
	public HashMap<String, String> serverAdmin = new HashMap<String, String>();
	public HashMap<String, Integer> playing = new HashMap<String, Integer>();

	public CommandExecutor Commands = new Commands(this);

	public Listener CNRListener = new CNRListener(this);

	public void onEnable(){
		W.config.setDefault("default_file", true);
		W.spawns.setDefault("default_file", true);

		W.newFiles();
		this.getCommand("cr").setExecutor(Commands);
		this.getServer().getPluginManager().registerEvents(CNRListener, this);
		System.out.println("[CNR]" + "Cops And Robbers has been Enabled!");
	}

	/*
	 * TODO Note! I removed the spawns get and setters,
	 * Use W.spawns.getFile() to get the stuff.
	 * If you do W.spawns.load(), it will reload the config.
	 * Hope you understand this otherwise, revert this commit.
	 */

	public WorldEditPlugin hookWE() {
		Plugin wPlugin = getServer().getPluginManager().getPlugin("WorldEdit");

		if ((wPlugin == null) || (!(wPlugin instanceof WorldEditPlugin)))
			return null;

		return (WorldEditPlugin) wPlugin;
	}
}
