package me.RDNachoz.Cops_and_Robbers.managers;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MessageManager {
	private HashMap<PrefixType, String> pr = new HashMap<PrefixType, String>();
	static String begin = "\u00A7";
	String normal;
	String warning;
	String error;
	String arg;
	String head;

	public MessageManager (String normal, String warning, String error, String arg, String head) {
		this.normal = normal;
		this.warning = warning;
		this.error = error;
		this.arg = arg;
		this.head = head;
	}

	public enum PrefixType {
		NORMAL, WARNING, ERROR, ARG, HEAD;
	}

	public void setup() {
		pr.put(PrefixType.NORMAL, begin + this.normal);
		pr.put(PrefixType.WARNING, begin + this.warning);
		pr.put(PrefixType.ERROR, begin + this.error);
		pr.put(PrefixType.ARG, begin + this.arg);
		pr.put(PrefixType.HEAD, begin + this.head);
	}

	public void sendMessage(Player player, PrefixType type, String message) {
		if (player == null) {
			Bukkit.getConsoleSender().sendMessage(pr.get(type) + message);
			return;
		}
		player.sendMessage(pr.get(type) + message);
	}
	
	public void broadcastMessage(PrefixType type, String message) {
		Bukkit.broadcastMessage(pr.get(type) + message);
	}
}
