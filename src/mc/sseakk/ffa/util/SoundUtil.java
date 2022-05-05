package mc.sseakk.ffa.util;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundUtil {
	
	public static void killSound(Player player) {
		player.playSound(player.getLocation(), Sound.ORB_PICKUP, 10, 2);
	}
	
	public static void deathSound(Player player) {
		player.playSound(player.getLocation(), Sound.ITEM_BREAK, 10, 1);
	}
}
