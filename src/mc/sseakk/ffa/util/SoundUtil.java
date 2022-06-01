package mc.sseakk.ffa.util;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import mc.sseakk.ffa.game.Arena;
import mc.sseakk.ffa.game.warrior.Warrior;

public class SoundUtil {
	
	public static void killSound(Player player) {
		player.playSound(player.getLocation(), Sound.ORB_PICKUP, 10, 2);
	}
	
	public static void deathSound(Player player) {
		player.playSound(player.getLocation(), Sound.ITEM_BREAK, 10, 1);
	}
	
	public static void assistSound(Player player) {
		player.playSound(player.getLocation(), Sound.ORB_PICKUP, 7, 5);
	}
	
	public static void killStreakSound(Arena arena, Sound sound) {
		for(Warrior player : arena.getPlayerList()) {
			player.getPlayer().playSound(player.getPlayer().getLocation(), sound, 10, 1);
		}
	}
}
