package me.RDNachoz.Cops_and_Robbers;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;

import me.RDNachoz.Cops_and_Robbers.CR;

public class Commands implements CommandExecutor {
	public CR plugin;
	public Commands(CR cr) {
		this.plugin = cr;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = null;
		if(label.equalsIgnoreCase("cr")){
			if (args.length == 0 || args[0].equalsIgnoreCase("help")){
				if (!(sender instanceof Player) || (sender instanceof Player && ((Player) sender).hasPermission("cr.join"))){
					sender.sendMessage(ChatColor.DARK_AQUA + "/cr join <Arena #>" + ChatColor.GOLD + " - Joins The Selected Arena");
					sender.sendMessage(ChatColor.DARK_AQUA + "/cr leave" + ChatColor.GOLD + " - Leaves The Lobby");
				}
				if (!(sender instanceof Player) || (sender instanceof Player && ((Player) sender).hasPermission("cr.vote"))){
					sender.sendMessage(ChatColor.DARK_AQUA + "/cr vote" + ChatColor.GOLD + " - Votes For Map 1-4");
				}
				if (!(sender instanceof Player) || (sender instanceof Player && ((Player) sender).hasPermission("cr.start"))){
					sender.sendMessage(ChatColor.DARK_AQUA + "/cr start <Game #>" + ChatColor.GOLD + " - Starts The Selected Arena");
					sender.sendMessage(ChatColor.DARK_AQUA + "/cr stop <Game #>" + ChatColor.GOLD + " - Stops The Selected Arena");
				}
				if (!(sender instanceof Player) || (sender instanceof Player && ((Player) sender).hasPermission("cr.admin"))){
					//done sender.sendMessage(ChatColor.DARK_AQUA + "/cr setcell <Game #> <Cell #>" + ChatColor.GOLD + " - Saves A Spawn To The Config");
					//done sender.sendMessage(ChatColor.DARK_AQUA + "/cr addgame <Game #>" + ChatColor.GOLD + " - Adds and Arena to The Config");
					//done sender.sendMessage(ChatColor.DARK_AQUA + "/cr setlobby <Lobby #>" + ChatColor.GOLD + " - Saves Your Surrent Postion For The Lobby");
					//done sender.sendMessage(ChatColor.DARK_AQUA + "/cr setspec <Arena #>" + ChatColor.GOLD + " - Sets the Spawn For Spectators");
					//done sender.sendMessage(ChatColor.DARK_AQUA + "/cr setdeadspawn" + ChatColor.GOLD + " - Sets The Spawnpoint For Dead Players");
					//done sender.sendMessage(ChatColor.DARK_AQUA + "/cr setguard" + ChatColor.GOLD + " - Sets The Chest Tier For The Chest You Are Looking At");
					sender.sendMessage(ChatColor.DARK_AQUA + "/cr saveguardinv" + ChatColor.GOLD + " - Saves All Items In Your Inventory For A Tier");
					sender.sendMessage(ChatColor.DARK_AQUA + "/cr disable" + ChatColor.GOLD + " - Disables All Arenas");
					sender.sendMessage(ChatColor.DARK_AQUA + "/cr enable" + ChatColor.GOLD + " - Enables All Arenas");
				}
				return true;
			}
			if (args.length == 1){
				int Game = 0;
				int Cell = 0;
				if(args[0].equalsIgnoreCase("join")){
					if(args.length>= 2){
						try{
							Game = Integer.valueOf(args[1]);
						}catch(Exception e){
							sender.sendMessage(ChatColor.DARK_AQUA + "[CR]" + ChatColor.RED + " You Silly! That's Not An Game!");
							return true;
						}
						if(Game> 0){
							//TODO Add them to the game!
							return true;
						}else{
							sender.sendMessage(ChatColor.DARK_AQUA + "[HG]" + ChatColor.RED + " You Silly! That's Not An Game!");
							return true;
						}
					}else
						return false;
				}else if(args[0].equalsIgnoreCase("leave")){
					//TODO remove from the arena!
					return true;
				}else if(args[0].equalsIgnoreCase("vote")){
					if(args.length>= 2){
						try{
							Game = Integer.valueOf(args[1]);
						}catch(Exception e){
							sender.sendMessage(ChatColor.DARK_AQUA + "[HG]" + ChatColor.RED + " You Silly! That's Not An Arena!");
							return true;
						}
						if(Game> 0){
							//TODO Add Vote
							return true;
						}else{
							sender.sendMessage(ChatColor.DARK_AQUA + "[HG]" + ChatColor.RED + " You Silly! That's Not An Arena!");
							return true;
						}
					}else
						return false;
				}else if(args[0].equalsIgnoreCase("start")){
					if(args.length>= 2){
						try{
							Game = Integer.valueOf(args[1]);
						}catch(Exception e){
							sender.sendMessage(ChatColor.DARK_AQUA + "[HG]" + ChatColor.RED + " You Silly! That's Not An Arena!");
							return true;
						}
						if(Game> 0){
							//TODO Make it start the game
							return true;
						}else{
							sender.sendMessage(ChatColor.DARK_AQUA + "[HG]" + ChatColor.RED + " You Silly! That's Not An Arena!");
							return true;
						}
					}else
						return false;
				}else if(args[0].equalsIgnoreCase("stop")){
					if(args.length>= 2){
						try{
							Game = Integer.valueOf(args[1]);
						}catch(Exception e){
							sender.sendMessage(ChatColor.DARK_AQUA + "[HG]" + ChatColor.RED + " You Silly! That's Not An Arena!");
							return true;
						}
						if(Game> 0){
							//TODO Make it stop the game
							return true;
						}else{
							sender.sendMessage(ChatColor.DARK_AQUA + "[HG]" + ChatColor.RED + " You Silly! That's Not An Arena!");
							return true;
						}
					}else
						return false;
				}else if(args[0].equalsIgnoreCase("addgame")){
					if(args.length>= 2){
						try{
							Game = Integer.valueOf(args[1]);
						}catch(Exception e){
							sender.sendMessage(ChatColor.DARK_AQUA + "[CNR]" + ChatColor.RED + " You Silly! That's Not A Valid Game Number!");
							return true;
						}
						if(Game> 0){
							if (sender instanceof Player)
								p = (Player) sender;
							WorldEditPlugin worldedit = plugin.hookWE(); 
							if(args.length<= 1)
								return false;
							else{
								Selection sel = worldedit.getSelection(p);
								if(sel== null)
									p.sendMessage(ChatColor.DARK_RED + "You must make a WorldEdit selection first!");
								else{
									if(p.hasPermission("hg.setarena")){
										Location min = sel.getMinimumPoint();
										Location max = sel.getMaximumPoint();
										plugin.config.set("Arena." + args[1] + ".Max", max.getWorld().getName() + "," + max.getX() + "," 
												+ max.getY() + "," + max.getZ());
										plugin.config.set("Arena." + args[1] + ".Min", min.getWorld().getName() + "," + min.getX() + "," 
												+ min.getY() + "," + min.getZ());
										plugin.saveConfig();
										p.sendMessage(ChatColor.GREEN + "Arena " + ChatColor.DARK_AQUA + args[1] 
												+ ChatColor.GREEN + " created!");
										plugin.arenaOpen.put(args[1], false);
										return true;
									}else{
										p.sendMessage(ChatColor.DARK_RED + "You don't have permission!");
									}
								}
							}
							return true;
						}else{
							sender.sendMessage(ChatColor.DARK_AQUA + "[CNR]" + ChatColor.RED + " You Silly! That's Not A Valid Arena Number!");
							return true;
						}
					}else
						return false;
				}else if(args[0].equalsIgnoreCase("setinmatespawn")){
					if(args.length>= 3){
						int Spawn = 0;
						try{
							Game = Integer.valueOf(args[1]);
							Spawn = Integer.valueOf(args[2]);
						}catch(Exception e){
							sender.sendMessage(ChatColor.DARK_AQUA + "[CNR]" + ChatColor.RED + " You Silly! That's Not An Arena or valid tribute spawn!");
							return true;
						}
						if(Game> 0 || Spawn> 0){
							if(sender instanceof Player){
								p = (Player) sender;
								String w = p.getLocation().getWorld().getName();
								double x = p.getLocation().getX();
								double y = p.getLocation().getY();
								double z = p.getLocation().getZ();
								String coords = w + "," + x + "," + y + "," + z;
								plugin.spawns.set(String.valueOf(Game + "." + Spawn), coords);
								plugin.saveSpawns();
							}else
								sender.sendMessage(ChatColor.BLUE + "This Can Only Be Sent As A Player");
							return true;
						}else{
							sender.sendMessage(ChatColor.DARK_AQUA + "[CNR]" + ChatColor.RED + " You Silly! That's Not An Arena or valid tribute spawn");
							return true;
						}
					}else if(args.length == 2){
						if (sender instanceof Player){
							p = (Player) sender;
							if (plugin.config.get(args[1]) != null){
								int start = 1;
								if (plugin.spawns.get(args[1]) != null)
									while(start<25){
										if (plugin.spawns.get(args[1] + "." + start) != null){
											start = start+1;
										}else{
											int loc = start;
											start = 25;
											sender.sendMessage(ChatColor.DARK_AQUA + "[CNR]" + ChatColor.RED + "Begin Setting For Arena " + args[1] + " Starting From Point " + loc);
											plugin.serverAdmin.put(p.getName(), args[1] + "-" + loc);
										}
									}
								return true;
							}
							sender.sendMessage(ChatColor.RED + "That is Not A Valid Arena");
							return true;
						}else{
							sender.sendMessage(ChatColor.BLUE + "This Can Only Be Sent As A Player");
						}
					}else
						return false;
				}else if(args[0].equalsIgnoreCase("setguardspawn")){
					//All good
					if(args.length>= 2){
						try{
							Game = Integer.valueOf(args[1]);
						}catch(Exception e){
							sender.sendMessage(ChatColor.DARK_AQUA + "[CNR]" + ChatColor.RED + " You Silly! That's Not An Arena or valid tribute spawn!");
							return true;
						}
						if(Game> 0){
							if(sender instanceof Player){
								p = (Player) sender;
								String w = p.getLocation().getWorld().getName();
								double x = p.getLocation().getX();
								double y = p.getLocation().getY();
								double z = p.getLocation().getZ();
								String coords = w + "," + x + "," + y + "," + z;
								plugin.spawns.set(String.valueOf(Game + ".guardSpawn"), coords);
								plugin.saveSpawns();
							}else
								sender.sendMessage(ChatColor.BLUE + "This Can Only Be Sent As A Player");
							return true;
						}else{
							sender.sendMessage(ChatColor.DARK_AQUA + "[CNR]" + ChatColor.RED + " You Silly! That's Not An Arena or valid tribute spawn");
							return true;
						}
					}
					return true;
				}else if(args[0].equalsIgnoreCase("setdeadspawn")){
					//All good
					if(args.length>= 2){
						try{
							Game = Integer.valueOf(args[1]);
						}catch(Exception e){
							sender.sendMessage(ChatColor.DARK_AQUA + "[CNR]" + ChatColor.RED + " You Silly! That's Not An Arena or valid tribute spawn!");
							return true;
						}
						if(Game> 0){
							if(sender instanceof Player){
								p = (Player) sender;
								String w = p.getLocation().getWorld().getName();
								double x = p.getLocation().getX();
								double y = p.getLocation().getY();
								double z = p.getLocation().getZ();
								String coords = w + "," + x + "," + y + "," + z;
								plugin.spawns.set(String.valueOf(Game + ".deadSpawn"), coords);
								plugin.saveSpawns();
							}else
								sender.sendMessage(ChatColor.BLUE + "This Can Only Be Sent As A Player");
							return true;
						}else{
							sender.sendMessage(ChatColor.DARK_AQUA + "[CNR]" + ChatColor.RED + " You Silly! That's Not An Arena or valid tribute spawn");
							return true;
						}
					}
					return true;
				}else if(args[0].equalsIgnoreCase("setspec")){
					//All good
					if(args.length>= 2){
						try{
							Game = Integer.valueOf(args[1]);
						}catch(Exception e){
							sender.sendMessage(ChatColor.DARK_AQUA + "[CNR]" + ChatColor.RED + " You Silly! That's Not An Arena or valid tribute spawn!");
							return true;
						}
						if(Game> 0){
							if(sender instanceof Player){
								p = (Player) sender;
								String w = p.getLocation().getWorld().getName();
								double x = p.getLocation().getX();
								double y = p.getLocation().getY();
								double z = p.getLocation().getZ();
								String coords = w + "," + x + "," + y + "," + z;
								plugin.spawns.set(String.valueOf(Game + ".specSpawn"), coords);
								plugin.saveSpawns();
							}else
								sender.sendMessage(ChatColor.BLUE + "This Can Only Be Sent As A Player");
							return true;
						}else{
							sender.sendMessage(ChatColor.DARK_AQUA + "[CNR]" + ChatColor.RED + " You Silly! That's Not An Arena or valid tribute spawn");
							return true;
						}
					}
					return true;
				}else if(args[0].equalsIgnoreCase("addcell")){
					if(args.length>= 2){
						try{
							Game = Integer.valueOf(args[1]);
							Cell = Integer.valueOf(args[2]);
						}catch(Exception e){
							sender.sendMessage(ChatColor.DARK_AQUA + "[CNR]" + ChatColor.RED + " You Silly! That's Not A Valid Game Number!");
							return true;
						}
						if(Cell> 0){
							if (sender instanceof Player)
								p = (Player) sender;
							WorldEditPlugin worldedit1 = plugin.hookWE(); 
							if(args.length<= 1)
								return false;
							else{
								Selection sel1 = worldedit1.getSelection(p);
								if(sel1== null)
									p.sendMessage(ChatColor.DARK_RED + "You must make a WorldEdit selection first!");
								else{
									if(p.hasPermission("hg.setarena")){
										plugin.config.set("Cell." + args[2] + ".Max", p.getWorld().getName() + "," + p.getLocation().getX() + "," 
												+ p.getLocation().getY() + "," + p.getLocation().getZ());
										plugin.saveConfig();
										p.sendMessage(ChatColor.GREEN + "Cell " + ChatColor.DARK_AQUA + args[2] 
												+ ChatColor.GREEN + " created in Arena " + args[1]);
										return true;
									}else{
										sender.sendMessage(ChatColor.RED + "You Do Not Have Permission to Execute This Command!");
									}
									return true;
								}
							}
						}else{
							sender.sendMessage(ChatColor.DARK_AQUA + "[CNR]" + ChatColor.RED + " You Silly! That's Not A Valid Arena Number!");
							return true;
						}
					}else
						return false;
				}else if(args[0].equalsIgnoreCase("setlobby")){
					if(args.length>= 2){
						if(sender instanceof Player){
							p = (Player) sender;
							String w = p.getLocation().getWorld().getName();
							double x = p.getLocation().getX();
							double y = p.getLocation().getY();
							double z = p.getLocation().getZ();
							String coords = w + "," + x + "," + y + "," + z;
							plugin.spawns.set("lobbySpawn", coords);
							plugin.saveSpawns();
						}else
							sender.sendMessage(ChatColor.BLUE + "This Can Only Be Sent As A Player");
						return true;
					}
				}
				return false;
			}
		}
		return true;
	}
}