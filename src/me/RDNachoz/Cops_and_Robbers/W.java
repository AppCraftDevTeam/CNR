package me.RDNachoz.Cops_and_Robbers;

import java.util.ArrayList;

import me.RDNachoz.Cops_and_Robbers.managers.ConfigM;
import me.RDNachoz.Cops_and_Robbers.managers.MessageManager;


public class W {
	public static ArrayList<String> newFiles = new ArrayList<String>();
	public static ConfigM config = new ConfigM("config");

	public static void newFiles() {
		for (String Filename : newFiles) {
			MessageManager.log("%warnCouldn't find '%arg%filename%.yml%warn' creating new one.", true,
					"filename-"+Filename);
		}
		newFiles.clear();
	}
}