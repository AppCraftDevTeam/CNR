package me.RDNachoz.Cops_and_Robbers;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
					sender.sendMessage(ChatColor.DARK_AQUA + "/cr setcell <Game #> <Cell #>" + ChatColor.GOLD + " - Saves A Spawn To The Config");
					sender.sendMessage(ChatColor.DARK_AQUA + "/cr addgame <game #>" + ChatColor.GOLD + " - Adds and Arena to The Config");
					sender.sendMessage(ChatColor.DARK_AQUA + "/cr addescape <Arena #>" + ChatColor.GOLD + " - Adds A Deathmatch Arena to The Config");
					sender.sendMessage(ChatColor.DARK_AQUA + "/cr setlobby <Lobby #>" + ChatColor.GOLD + " - Saves Your Surrent Postion For The Lobby");
					sender.sendMessage(ChatColor.DARK_AQUA + "/cr setspec <Arena #>" + ChatColor.GOLD + " - Sets the Spawn For Spectators");
					sender.sendMessage(ChatColor.DARK_AQUA + "/cr setdeadspawn" + ChatColor.GOLD + " - Sets The Spawnpoint For Dead Players");
					sender.sendMessage(ChatColor.DARK_AQUA + "/cr setguard" + ChatColor.GOLD + " - Sets The Chest Tier For The Chest You Are Looking At");
					sender.sendMessage(ChatColor.DARK_AQUA + "/cr saveguardinv" + ChatColor.GOLD + " - Saves All Items In Your Inventory For A Tier");
					sender.sendMessage(ChatColor.DARK_AQUA + "/cr disable" + ChatColor.GOLD + " - Disables All Arenas");
					sender.sendMessage(ChatColor.DARK_AQUA + "/cr enable" + ChatColor.GOLD + " - Enables All Arenas");
				}
				return true;
			}
			if (args.length == 1){
				int Game = 0;
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
				}
				return true;
			}
		}
		return true;
	}
}