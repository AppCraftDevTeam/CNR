package me.RDNachoz.Cops_and_Robbers.managers;

import java.io.File;

import me.RDNachoz.Cops_and_Robbers.W;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigM {
	/*
	 * ConfigM - A Configuration manager for your plugin.
	 * Made by Steffion (c) 2013.
	 */

	File File;
	FileConfiguration FileC;
	ConfigurationSection FileCS;
	String Filename;

	public ConfigM (String Filename) {
		this.Filename = Filename;
		this.File = new File ("plugins/Cops And Robbers", Filename + ".yml");
		this.FileC = new YamlConfiguration();
		this.checkFile();
		this.FileCS = FileC.getConfigurationSection("");
		this.load();
	}

	/**
	 * Set standard message colours.
	 * Only use in main config file.
	 */
	public void setup() {
		this.setDefault("chat.tag", "[Cops And Robbers] ");
		this.setDefault("chat.normal", "&b");
		this.setDefault("chat.warning", "&c");
		this.setDefault("chat.error", "&4");
		this.setDefault("chat.arg", "&e");
		this.setDefault("chat.header", "&9");
	}

	/**
	 * Check if file exists, if not create one.
	 */
	public void checkFile() {
		if (!this.File.exists()) {
			try {
				this.File.getParentFile().mkdirs();
				this.File.createNewFile();
				W.newFiles.add(this.Filename);
				if (this.Filename == "config") {
					this.setup();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Save the file.
	 */
	public void save() {
		try {
			this.FileC.save(this.File);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Load the file.
	 */
	public void load() {
		if (this.File.exists()) {
			try {
				this.FileC.load(this.File);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Add to file if it doesn't exists.
	 */
	public void setDefault(String location, Object value) {
		this.load();
		if (this.FileC.get(location) == null) {
			this.FileC.set(location, value);
			this.save();
		}
	}

	/**
	 * Get the File.
	 */
	public FileConfiguration getFile() {
		return this.FileC;
	}
}
