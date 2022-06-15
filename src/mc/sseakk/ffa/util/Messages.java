package mc.sseakk.ffa.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import mc.sseakk.ffa.FFA;
import net.md_5.bungee.api.chat.TextComponent;


public class Messages {
	
	public static void infoMessage(String message) {
		Bukkit.getServer().getLogger().info("[FFA] " + message);
	}
	
	public static void sendPlayerMessage(Player player, String message) {
		player.sendMessage(TextUtil.colorText(FFA.getTextTag().toLegacyText() + message));
	}
	
	public static void sendPlayerMessage(Player player, TextComponent... text) {
		player.spigot().sendMessage(text);
	}
	
	public static void broadcastMessage(String message) {
		Bukkit.getServer().broadcastMessage("[FFA] " + message);
	}
	
	public static void broadcastMessage(TextComponent... text) {
		Bukkit.getServer().spigot().broadcast(text);
	}
	
	public static void warningMessage(String message) {
		Bukkit.getServer().getLogger().warning("[FFA] " + ChatColor.DARK_RED + message);
	}
	
	public static void delayedMessage(Player player, int secs, String message) {
		Bukkit.getServer().getScheduler().runTaskLater(FFA.getInstance(), new Runnable() {
			public void run() {
				sendPlayerMessage(player, message);
			}
		}, (secs * 20));
	}
	
	public static void delayedMessage(Player player, int secs, TextComponent... text) {
		Bukkit.getServer().getScheduler().runTaskLater(FFA.getInstance(), new Runnable() {
			public void run() {
				sendPlayerMessage(player, text);
			}
		}, (secs * 20));
	}
	
	public static void delayedMessage(Player player, Long mili, String message) {
		Bukkit.getServer().getScheduler().runTaskLater(FFA.getInstance(), new Runnable() {
			public void run() {
				sendPlayerMessage(player, message);
			}
		}, mili);
	}
	
	public static void delayedMessage(Player player, Long mili, TextComponent... text) {
		Bukkit.getServer().getScheduler().runTaskLater(FFA.getInstance(), new Runnable() {
			public void run() {
				sendPlayerMessage(player, text);
			}
		}, mili);
	}
}