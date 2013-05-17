package me.RDNachoz.Cops_and_Robbers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class CNRListener implements Listener {
	public CR plugin;
	public CNRListener(CR cr) {
		this.plugin=cr;
	}
	@EventHandler
	public void playerRespawn (PlayerRespawnEvent Event){
		if(plugin.playing.containsKey(Event.getPlayer().getName())){
			Location loc = new Location(Bukkit.getServer().getWorld(plugin.spawns.getString(plugin.playing.get(Event.getPlayer().getName()) + ".deadSpawn").split(",")[0]),Double.parseDouble(plugin.spawns.getString(plugin.playing.get(Event.getPlayer().getName()) + ".deadSpawn").split(",")[0]),Double.parseDouble(plugin.spawns.getString(plugin.playing.get(Event.getPlayer().getName()) + ".deadSpawn").split(",")[0]),Double.parseDouble(plugin.spawns.getString(plugin.playing.get(Event.getPlayer().getName()) + ".deadSpawn").split(",")[0]));
			Event.setRespawnLocation (loc);
		}
	}
	@EventHandler
	public void playerDeath (PlayerDeathEvent Event){
		if(plugin.playing.containsKey(Event.getEntity().getName())){
			Event.setDeathMessage("");
			int Arena = plugin.playing.get(Event.getEntity().getName());
			for (String Players:plugin.playing.keySet()){
				if(plugin.playing.get(Players) == Arena){
					Player p = Bukkit.getServer().getPlayer(Players);
					p.sendMessage(ChatColor.RED + Event.getEntity().getName() + " Has Been Brutaly Murdered By A Guard!");
				}
			}
		}
	}

}
