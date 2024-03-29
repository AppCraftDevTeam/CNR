package me.RDNachoz.Cops_and_Robbers.managers;

import org.bukkit.entity.Player;

public class PlayerM {
	/*
	 * PlayerM - A Player manager for your plugin.
	 * Made by Steffion (c) 2013.
	 */
	
	public static String main = "cr.";

	public enum PType {
		PLAYER,
		MODERATOR,
		ADMIN,
		OP;
	}
	
	/**
	 * Check if the player has the permission.
	 * Also check second permission.
	 * @param player The player.
	 * @param perm The main permission.
	 * @param type The second permission type.
	 * @param message Return "nopermission" message to player.
	 * @return true/false.
	 */
	public static boolean hasPerm(Player player, String perm, PType type, Boolean message) {
		if (player == null) {
			return true;
		}
		if (type == PType.OP) {
			if (player.isOp()) {
				return true;
			}
		} else if (type == PType.ADMIN) {
			if (player.hasPermission(main + "admin")) {
				return true;
			}
		} else if (type == PType.MODERATOR) {
			if (player.hasPermission(main + "moderator")) {
				return true;
			}
		} else if (type == PType.PLAYER) {
			if (player.hasPermission(main + "player")) {
				return true;
			}
		}
		
		if (player.hasPermission(main + perm)) {
			return true;
		} else {
			if (message) {
				MessageM.sendFMessage(player, "error.nopermission", "messages", true);
			}
		}
		return false;
	}
}
