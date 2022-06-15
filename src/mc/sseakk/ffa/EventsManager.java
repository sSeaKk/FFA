package mc.sseakk.ffa;

import org.bukkit.plugin.PluginManager;

import mc.sseakk.ffa.game.ArenaListener;
import mc.sseakk.ffa.gui.GuiListener;

public class EventsManager{
	PluginManager pm = FFA.getInstance().getServer().getPluginManager();
	
	public EventsManager() {
		pm.registerEvents(new MinecraftListener(), FFA.getInstance());
		pm.registerEvents(new ArenaListener(), FFA.getInstance());
		pm.registerEvents(new GuiListener(), FFA.getInstance());
	}
}