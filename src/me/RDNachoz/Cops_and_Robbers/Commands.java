package me.RDNachoz.Cops_and_Robbers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import me.RDNachoz.Cops_and_Robbers.managers.MessageM;
import me.RDNachoz.Cops_and_Robbers.managers.PlayerM;
import me.RDNachoz.Cops_and_Robbers.managers.PlayerM.PType;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;

public class Commands implements CommandExecutor {
	public CR plugin;
	public Commands(CR cr) {
		this.plugin = cr;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = null;
		if (sender instanceof Player) {
			p = (Player) sender;
		}
		if(label.equalsIgnoreCase("cr") || label.equalsIgnoreCase("c") || label.equalsIgnoreCase("copsandrobbers")){
			if (args.length == 0 || args[0].equalsIgnoreCase("help")){
				MessageM.sendMessage(p, "%argCops and Robbers %normby Travja, RDNachoz, and Steffion!", true);
				if (PlayerM.hasPerm(p, "join", PType.PLAYER, false)) {
					MessageM.sendMessage(p, "%arg/cr join <Arena #>%norm - Joins the selected Arena.", false);
					MessageM.sendMessage(p, "%arg/cr leave%norm - Leave the Lobby.", false);
				}
				if (PlayerM.hasPerm(p, "vote", PType.PLAYER, false)) {
					MessageM.sendMessage(p, "%arg/cr vote%norm - Votes for Map.", false);
				}
				if (PlayerM.hasPerm(p, "start", PType.MODERATOR, false)) {
					MessageM.sendMessage(p, "%arg/cr start <Game #>%norm - Start the selected Arena.", false);
					MessageM.sendMessage(p, "%arg/cr stop <Game #>%norm - Stop the selected Arena.", false);
				}
				if (PlayerM.hasPerm(p, "admin", PType.ADMIN, false)) {
					MessageM.sendMessage(p, "%arg/cr setcell <Game #> <Cell #>%norm - Save a Spawn point to the Config file.", false);
					MessageM.sendMessage(p, "%arg/cr addgame <Game #>%norm - Add an Arena to the Config file.", false);
					MessageM.sendMessage(p, "%arg/cr setlobby <Lobby #>%norm - Save your current postion as Arena lobby.", false);
					MessageM.sendMessage(p, "%arg/cr setspec <Arena #>%norm - Set the spawn point for the spectators.", false);
					MessageM.sendMessage(p, "%arg/cr setdeadspawn%norm - Set the spawn point for the dead players.", false);
					MessageM.sendMessage(p, "%arg/cr setguard%norm - Set a chest to a Guard chest.", false);
					MessageM.sendMessage(p, "%arg/cr saveguardinv%norm - Save your inventory as Guard inventory.", false);
					MessageM.sendMessage(p, "%arg/cr disable%norm - Disable all Arenas.", false);
					MessageM.sendMessage(p, "%arg/cr enable%norm - Enable all Arenas.", false);
				}
				return true;
			}
			int Game = 0;
			int Cell = 0;
			if(args[0].equalsIgnoreCase("join")){
				if(sender instanceof Player){
					p = (Player) sender;
					if(args.length >= 2){
						try{
							Game = Integer.valueOf(args[1]);
						}catch(Exception e){
							MessageM.sendFMessage(p, "error.notagame", "messages", true);
							return true;
						}
						if(Game> 0){
							plugin.playing.put(p.getName(), Game);
							MessageM.sendMessage(p, "You have joined the game!", true);
							MessageM.sendMessage(p, "You'll be teleported when the game starts!", true);
							return true;
						}else{
							MessageM.sendFMessage(p, "error.notagame", "messages", true);
							return true;
						}
					}else
						return false;
				}else{
					MessageM.sendMessage(p, ChatColor.GREEN +"That can only be run in game", true);
					return true;
				}
			}else if(args[0].equalsIgnoreCase("leave")){
				/**
				 * If player is in game and @lobbySpawn is set
				 * teleport them to the @lobbySpawn location
				 * if not, teleport them to the @world spawn
				 * @author Travja
				 */
				if(sender instanceof Player){
					p = (Player) sender;
					if(plugin.playing.containsKey(p.getName())){
						if(W.spawns.getFile().getString("lobbySpawn")!= null){
							plugin.playing.remove(p.getName());
							String[] coords = W.spawns.getFile().getString("lobbySpawn").split(",");
							p.teleport(new Location(Bukkit.getWorld(coords[0]), Double.parseDouble(coords[1]),
									Double.parseDouble(coords[2]), Double.parseDouble(coords[3])));
							MessageM.sendMessage(p, "You have left the game!", true);
							return true;
						}else{
							p.teleport(p.getLocation().getWorld().getSpawnLocation());
							MessageM.sendMessage(p, "You have left the game!", true);
							return true;
						}
					}else{
						MessageM.sendFMessage(p, "error.notingame", "messages", true);
						return true;
					}
				}
				return true;
			}else if(args[0].equalsIgnoreCase("vote")){
				if(args.length>= 2){
					try{
						Game = Integer.valueOf(args[1]);
					}catch(Exception e){
						MessageM.sendFMessage(p, "error.notagame", "messages", true);
						return true;
					}
					if(Game> 0){
						//TODO Add Vote
						return true;
					}else{
						MessageM.sendFMessage(p, "error.notagame", "messages", true);
						return true;
					}
				}else
					return false;
			}else if(args[0].equalsIgnoreCase("start")){
				if(args.length>= 2){
					try{
						Game = Integer.valueOf(args[1]);
					}catch(Exception e){
						MessageM.sendFMessage(p, "error.notagame", "messages", true);
						return true;
					}
					if(Game> 0){
						List<Player> in = new ArrayList<Player>();
						for(String pname: plugin.playing.keySet()){
							if(plugin.playing.get(pname) == Game){
								p = Bukkit.getPlayer(pname);
								in.add(p);
							}
						}
						int guard = -1;
						for(String pname: plugin.playing.keySet()){
							if(plugin.playing.get(pname) == Game){
								Location loc = null;
								Random r = new Random();
								if(guard != -1){
									guard = r.nextInt(in.size());
									p = in.get(guard);
									try{
										String[] coords = W.spawns.getFile().getString(Game + ".guardSpawn").split(",");
										String world = coords[0];
										double x = Double.parseDouble(coords[1]);
										double y = Double.parseDouble(coords[2]);
										double z = Double.parseDouble(coords[3]);
										loc = new Location(Bukkit.getWorld(world), x, y, z);
									}catch(Exception e){
										p.sendMessage(ChatColor.RED + "Something went wrong upon trying to teleport you, contact an admin.");
									}
									if(loc != null){
										p.teleport(loc);
										in.remove(p);
									}
								}else{
									List<Location> locs = new ArrayList<Location>();
									if(W.spawns.getFile().getConfigurationSection(String.valueOf(Game))!= null){
										Map<String, Object> temp = W.spawns.getFile().getConfigurationSection(String.valueOf(Game)).getValues(false);
										for(Entry<String, Object> entry: temp.entrySet()){
											if(W.spawns.getFile().getConfigurationSection(String.valueOf(Game) + "." + entry.getKey())!= null){
												String[] coords = ((String) W.spawns.getFile().get(String.valueOf(Game) + "." + entry.getKey())).split(",");
												try{
													World world = Bukkit.getWorld(coords[0]);
													double x = Double.parseDouble(coords[1]);
													double y = Double.parseDouble(coords[2]);
													double z = Double.parseDouble(coords[3]);
													loc = new Location(world, x, y, z);
													locs.add(loc);
												}catch(Exception e){
													p.sendMessage(ChatColor.RED + "Something went wrong upon trying to teleport you, contact an admin.");
												}
											}
										}
									}
									p.teleport(locs.get(r.nextInt(locs.size())));
								}
							}
						}
						return true;
					}else{
						MessageM.sendFMessage(p, "error.notagame", "messages", true);
						return true;
					}
				}else
					return false;
			}else if(args[0].equalsIgnoreCase("stop")){
				if(args.length>= 2){
					try{
						Game = Integer.valueOf(args[1]);
					}catch(Exception e){
						MessageM.sendFMessage(p, "error.notagame", "messages", true);
						return true;
					}
					if(Game> 0){
						//TODO Make it stop the game
						return true;
					}else{
						MessageM.sendFMessage(p, "error.notagame", "messages", true);
						return true;
					}
				}else
					return false;
			}else if(args[0].equalsIgnoreCase("addgame")){
				//TODO fix commands /*Nothing works*/
				if(args.length>= 2){
					try{
						Game = Integer.valueOf(args[1]);
					}catch(Exception e){
						MessageM.sendFMessage(p, "error.notagame", "messages", true);
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
								if(p.hasPermission("cr.setarena")){
									Location min = sel.getMinimumPoint();
									Location max = sel.getMaximumPoint();
									W.config.getFile().set("Arena." + args[1] + ".Max", max.getWorld().getName() + "," + max.getX() + "," 
											+ max.getY() + "," + max.getZ());
									W.config.getFile().set("Arena." + args[1] + ".Min", min.getWorld().getName() + "," + min.getX() + "," 
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
						MessageM.sendFMessage(p, "error.notagame", "messages", true);
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
						MessageM.sendFMessage(p, "error.notagame", "messages", true);
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
							W.spawns.getFile().set(String.valueOf(Game + "." + Spawn), coords);
							W.spawns.save();
						}else
							sender.sendMessage(ChatColor.BLUE + "This Can Only Be Sent As A Player");
						return true;
					}else{
						MessageM.sendFMessage(p, "error.notagame", "messages", true);
						return true;
					}
				}else if(args.length >= 2){
					if (sender instanceof Player){
						p = (Player) sender;
						if (W.config.getFile().get(args[1]) != null){
							int start = 1;
							if (W.spawns.getFile().get(args[1]) != null)
								while(start<25){
									if (W.spawns.getFile().get(args[1] + "." + start) != null){
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
						MessageM.sendFMessage(p, "error.notagame", "messages", true);
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
							W.spawns.getFile().set(String.valueOf(Game + ".guardSpawn"), coords);
							W.spawns.save();
							sender.sendMessage(ChatColor.GREEN + "Guard Spawn Set For Arena" + args[1] + ChatColor.GREEN + "!");
						}else
							sender.sendMessage(ChatColor.BLUE + "This Can Only Be Sent As A Player");
						return true;
					}else{
						MessageM.sendFMessage(p, "error.notagame", "messages", true);
						return true;
					}
				}
				return true;
			}else if(args[0].equalsIgnoreCase("saveguardinv")){
				if(sender instanceof Player){
					p = (Player) sender;
					PlayerInventory inv = p.getInventory();
					ItemStack[] guarditems = inv.getContents();
					ItemStack[] guardarmor = inv.getArmorContents();
					W.config.getFile().set("Guard.Items", guarditems);
					W.config.getFile().set("Guard.Armor", guardarmor);
					plugin.saveConfig();
					p.sendMessage(ChatColor.DARK_AQUA + "Guard" + ChatColor.GREEN + " inventory saved!");
					return true;
				}
				return true;
			}else if(args[0].equalsIgnoreCase("setdeadspawn")){
				//All good
				if(args.length>= 2){
					try{
						Game = Integer.valueOf(args[1]);
					}catch(Exception e){
						MessageM.sendFMessage(p, "error.notagame", "messages", true);
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
							W.spawns.getFile().set(String.valueOf(Game + ".deadSpawn"), coords);
							W.spawns.save();
							sender.sendMessage(ChatColor.GREEN + "Dead Spawn Set!");
						}else
							sender.sendMessage(ChatColor.BLUE + "This Can Only Be Sent As A Player");
						return true;
					}else{
						MessageM.sendFMessage(p, "error.notagame", "messages", true);
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
						MessageM.sendFMessage(p, "error.notagame", "messages", true);
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
							W.spawns.getFile().set(String.valueOf(Game + ".specSpawn"), coords);
							W.spawns.save();
							sender.sendMessage(ChatColor.GREEN + "Spectator Spawn Set!");
						}else
							sender.sendMessage(ChatColor.BLUE + "This Can Only Be Sent As A Player");
						return true;
					}else{
						MessageM.sendFMessage(p, "error.notagame", "messages", true);
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
						MessageM.sendFMessage(p, "error.notagame", "messages", true);
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
									W.config.getFile().set("Cell." + args[2] + ".Max", p.getWorld().getName() + "," + p.getLocation().getX() + "," 
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
						MessageM.sendFMessage(p, "error.notagame", "messages", true);
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
						W.spawns.getFile().set("lobbySpawn", coords);
						W.spawns.save();
						sender.sendMessage(ChatColor.GREEN + "Lobby Spawn Set!");
					}else
						sender.sendMessage(ChatColor.BLUE + "This Can Only Be Sent As A Player");
					return true;
				}
			}
			return false;
		}
		return true;
	}
}