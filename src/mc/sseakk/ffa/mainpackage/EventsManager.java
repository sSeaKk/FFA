package mc.sseakk.ffa.mainpackage;

import org.bukkit.plugin.PluginManager;

import mc.sseakk.ffa.game.ArenaListener;
import mc.sseakk.ffa.listeners.GeneralListener;
import mc.sseakk.ffa.listeners.MinecraftListener;

public class EventsManager{
	PluginManager pm = FFA.getInstance().getServer().getPluginManager();
	
	public EventsManager() {
		pm.registerEvents(new GeneralListener(), FFA.getInstance());
		pm.registerEvents(new MinecraftListener(), FFA.getInstance());
		pm.registerEvents(new ArenaListener(), FFA.getInstance());
	}
}