package mc.sseakk.ffa.mainpackage;

import org.bukkit.plugin.java.JavaPlugin;

import mc.sseakk.ffa.util.Messages;

public class FFA extends JavaPlugin {
	private static FFA instance;
	private static FileManager fm;
	private static CommandManager cm;
	private static ArenasManager am;
	private static EventsManager em;
	private static StatsManager sm;
	
	public void onEnable() {
		Messages.sendConsoleMessage("Incializando plugin");
		instance = this;
		fm = new FileManager();
		am = new ArenasManager();
		sm = new StatsManager();
		em = new EventsManager();
		cm = new CommandManager();
		Messages.sendConsoleMessage("Plugin inicializado");
	}
	
	public void onDisable() {
		sm.saveStats();
		am.saveArenas();
		Messages.sendConsoleMessage("Deshabilitando plugin");
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

	public static StatsManager getStatsManager() {
		return sm;
	}
}