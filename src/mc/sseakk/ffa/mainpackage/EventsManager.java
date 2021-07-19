package mc.sseakk.ffa.mainpackage;

import org.bukkit.plugin.PluginManager;

import mc.sseakk.ffa.listeners.GeneralListener;

public class EventsManager{
	PluginManager pm = FFA.getInstance().getServer().getPluginManager();
	
	public EventsManager() {
		pm.registerEvents(new GeneralListener(), FFA.getInstance());
	}
}
