package me.RDNachoz.Cops_and_Robbers.managers;

import me.RDNachoz.Cops_and_Robbers.W;
import me.RDNachoz.Cops_and_Robbers.managers.ConfigM;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MessageManager {
	public static String code = "\u00A7";
	public static String NORMAL = "&b";
	public static String WARNING = "&c";
	public static String ERROR = "&4";
	public static String ARG = "&e";
	public static String HEADER = "&9";
	public static String TAG = HEADER + "[Cops And Robbers] ";
	public static String mtag = c(CType.TAG);

	/**
	 * Send a plain message to a player.
	 * @param player If null, send to console.
	 * @param message
	 * @param vars
	 */
	public static void sendMessage(Player player, String message, Boolean tag, String ... vars) {
		tag(tag);
		if (player == null) {
			Bukkit.getConsoleSender().sendMessage(MessageManager.replaceAll(mtag + message, vars));
		} else {
			player.sendMessage(MessageManager.replaceAll(mtag + message, vars));
		}
	}

	/**
	 * Sends a message from a Config to a player.
	 * @param player If null, message console.
	 * @param location
	 * @param Filename
	 * @param vars Can be empty.
	 */
	public static void sendFMessage(Player player, String location, String Filename, Boolean tag, String ... vars) {
		tag(tag);
		if (player == null) {
			Bukkit.getConsoleSender().sendMessage(MessageManager.replaceAll(mtag + new ConfigM(Filename).getFile()
					.getString(location), vars));
		} else {
			player.sendMessage(MessageManager.replaceAll(mtag + new ConfigM(Filename).getFile()
					.getString(location), vars));
		}
	}

	/**
	 * Sends a plain message to all online players.
	 * Replaces %player% tag.
	 * @param message
	 * @param vars Can be empty.
	 */
	public static void broadcastMessage(String message, Boolean tag, String ... vars) {
		tag(tag);
		for (Player player : Bukkit.getOnlinePlayers()) {
			String pMessage = message.replaceAll("%player%", player.getDisplayName());
			player.sendMessage(MessageManager.replaceAll(mtag + pMessage, vars));
		}
		message = message.replaceAll("%player%", "Console");
		Bukkit.getConsoleSender().sendMessage(MessageManager.replaceAll(mtag + message, vars));
	}

	/**
	 * Sends a message from a Config to all online players.
	 * Replaces %player% tag.
	 * @param message
	 * @param vars Can be empty.
	 */
	public static void broadcastFMessage(String location, String Filename, Boolean tag, String ... vars) {
		tag(tag);
		for (Player player : Bukkit.getOnlinePlayers()) {
			String pMessage = MessageManager.replaceAll(mtag + new ConfigM(Filename).getFile()
					.getString(location), vars).replaceAll("%player%", player.getDisplayName());
			player.sendMessage(MessageManager.replaceAll(mtag + pMessage, vars));
		}
		String message = MessageManager.replaceAll(mtag + new ConfigM(Filename).getFile()
				.getString(location),
				vars).replaceAll("%player%", "Console");
		Bukkit.getConsoleSender().sendMessage(MessageManager.replaceAll(mtag + message, vars));
	}

	/**
	 * Log a line to the console.
	 * @param message
	 * @param vars
	 */
	public static void log(String message, Boolean tag, String ... vars) {
		tag(tag);
		Bukkit.getConsoleSender().sendMessage(MessageManager.replaceAll(mtag + message, vars));
	}

	/**
	 * Replace every variable.
	 * @param message
	 * @param vars
	 * @return
	 */
	public static String replaceAll(String message, String ... vars) {
		return MessageManager.replaceColours(
				MessageManager.replaceColourVars(
						MessageManager.replaceVars(message, vars)));
	}

	/**
	 * Replace all colour codes.
	 * @param message
	 * @return Replaced String.
	 */
	public static String replaceColours(String message) {
		return message.replaceAll("(&([a-fk-or0-9]))", "\u00A7$2");
	}

	/**
	 * Replace all variables.
	 * @param message
	 * @param vars
	 * @return Replaced String.
	 */
	public static String replaceVars(String message, String ... vars) {
		for (String var : vars) {
			String[] split = var.split("-");
			message = message.replaceAll("%" + split[0] + "%", split[1]);
		}
		return message;
	}

	/**
	 * Replace Colour variables.
	 * @param message
	 * @param vars
	 * @return Replaced String.
	 */
	public static String replaceColourVars(String message) {
		message = message.replaceAll("%norm", c(CType.NORMAL));
		message = message.replaceAll("%warn", c(CType.WARNING));
		message = message.replaceAll("%err", c(CType.ERROR));
		message = message.replaceAll("%arg", c(CType.ARG));
		message = message.replaceAll("%head", c(CType.HEADER));
		return message;
	}

	/**
	 * Enable/Disable the tag in front of a message.	
	 * @param tag
	 */
	public static void tag(Boolean tag) {
		if (!tag) {
			mtag = "";
		} else {
			mtag = c(CType.TAG);
		}
	}

	public enum CType {
		NORMAL,
		WARNING,
		ERROR,
		ARG,
		HEADER,
		TAG;
	}

	/**
	 * Return colour string from config.
	 * @param colour
	 * @return
	 */
	public static String c(CType colour) {
		W.config.load();
		switch (colour) {
		case NORMAL: return W.config.getFile().getString("chat.normal");
		case WARNING: return W.config.getFile().getString("chat.warning");
		case ERROR: return W.config.getFile().getString("chat.error");
		case ARG: return W.config.getFile().getString("chat.arg");
		case HEADER: return W.config.getFile().getString("chat.header");
		case TAG: return W.config.getFile().getString("chat.header") +
				W.config.getFile().getString("chat.tag");
		}
		return "&b";
	}
}