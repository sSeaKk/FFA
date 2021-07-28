package mc.sseakk.ffa.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Messages {
	
	public static void sendConsoleMessage(String message) {
		System.out.println("[FFA] "+message);
	}
	
	public static void sendPlayerMessage(Player player, String message) {
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', "[FFA] "+message));
	}
	
	public static void broadcastMessage(String message) {
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "[FFA] " + message));
	}
	
	public static void warningMessage(String message) {
		Bukkit.getServer().getLogger().warning("[FFA] " + message);
	}
}