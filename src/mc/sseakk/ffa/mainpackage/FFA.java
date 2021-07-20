package mc.sseakk.ffa.mainpackage;

import org.bukkit.plugin.java.JavaPlugin;

import mc.sseakk.ffa.util.Messages;

public class FFA extends JavaPlugin{
	private static FFA instance;
	private static FileManager fm;
	private static CommandManager cm;
	private static ArenasManager am;
	private static EventsManager em;
	
	public void onEnable() {
		Messages.sendConsoleMessage("Initializing plugin");
		instance = this;
		fm = new FileManager();
		
		am = new ArenasManager();
		//am.loadArenas();
		
		em = new EventsManager();
		cm = new CommandManager();
		Messages.sendConsoleMessage("Plugin initialized");
	}
	
	public void onDisable() {
		am.saveArenas();
		Messages.sendConsoleMessage("Disabling plugin");
	}
	
	public static FFA getInstance() {
		return instance;
	}
	
	public static FileManager getFileManager() {
		return fm;
	}
	
	public static CommandManager getCommandManager() {
		return cm;
	}

	public static ArenasManager getArenasManager() {
		return am;
	}
	
	public static EventsManager getEventsManager() {
		return em;
	}
}
