package mc.sseakk.ffa.util;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import mc.sseakk.ffa.game.FFAPlayer;

public class Messages {
	
	public static void sendConsoleMessage(String message) {
		System.out.println("[FFA] "+message);
	}
	
	public static void sendPlayerMessage(Player player, String message) {
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[FFA] &r"+message));
	}
	
	public static void broadcastMessage(String message) {
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&6[FFA] &r" + message));
	}
	
	public static void warningMessage(String message) {
		Bukkit.getServer().getLogger().warning("[FFA] " + message);
	}
	
	public static void sendAllPlayerArenaMessage(ArrayList<FFAPlayer> playerList, String message) {
		for(FFAPlayer player : playerList) { 
			player.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[FFA]&r " + message));
		}
	}
}