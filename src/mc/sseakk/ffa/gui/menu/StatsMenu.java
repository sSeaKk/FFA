package mc.sseakk.ffa.gui.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import mc.sseakk.ffa.gui.Menu;

public class StatsMenu extends Menu{
	public StatsMenu(Player player) {
		super(player, "Estadisticas", 36);
		addOpenedMenuToPlayer(player, this);
		
		createIcon("&6Kills", 11,
					 new ItemStack(Material.DIAMOND_SWORD, 1), 
					 ItemFlag.HIDE_ATTRIBUTES,
					 "&b"+warrior.getKills());
		
		createIcon("&6Assists", 13,
				   new ItemStack(Material.IRON_HOE, 1),
				   "&b"+warrior.getAssists());
		
		createIcon("&6Deaths", 15,
					new ItemStack(Material.EYE_OF_ENDER, 1),
					"&b"+warrior.getDeaths());
		
		
		
		createIcon("&6Max Kill Streak", 19,
					new ItemStack(Material.BLAZE_POWDER, 1),
					"&b"+warrior.getMaxKillStreak());
		
		createIcon("&6Max Damage Given", 21,
				new ItemStack(Material.BLAZE_ROD, 1),
				"&b"+warrior.getMaxDamageGiven());
		
		createIcon("&6Max Damage Taken", 23,
				new ItemStack(Material.BONE, 1),
				"&b"+warrior.getMaxDamageTaken());
		
		createIcon("&6Max Death Streak", 25,
				new ItemStack(Material.REDSTONE, 1),
				"&b"+warrior.getMaxDeathStreak());
	}
}