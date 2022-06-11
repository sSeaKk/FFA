package mc.sseakk.ffa.mainpackage;

import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import mc.sseakk.ffa.util.Messages;
import mc.sseakk.ffa.util.TextUtil;
import net.md_5.bungee.api.chat.TextComponent;

public class FFA extends JavaPlugin {
	private static FFA instance;
	private static FileManager fm;
	private static CommandManager cm;
	private static ArenasManager am;
	private static EventsManager em;
	private static ProfileManager pm;
	private static RewardsManager rm;
	private static PluginManager plugin;
	
	public void onEnable() {
		Messages.infoMessage("Incializando plugin");
		instance = this;
		plugin = this.getServer().getPluginManager();
		fm = new FileManager();
		am = new ArenasManager();
		pm = new ProfileManager();
		rm = new RewardsManager();
		em = new EventsManager();
		cm = new CommandManager();
		Messages.infoMessage("Plugin inicializado");
	}
	
	public void onDisable() {
		pm.saveAllStats();
		am.saveArenas();
		Messages.infoMessage("Deshabilitando plugin");
	}
	
	public static TextComponent getTextTag() {
		return new TextComponent(TextUtil.colorText(ChatColor.GOLD + "[FFA] " + ChatColor.RESET));
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

	public static ProfileManager getProfileManager() {
		return pm;
	}
	
	public static PluginManager getPluginManager() {
		return plugin;
	}

	public static RewardsManager getRewardsManager() {
		return rm;
	}
}