package mc.sseakk.ffa.gui.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import mc.sseakk.ffa.gui.Menu;

public class StatsMenu extends Menu{
	public StatsMenu(Player player) {
		super(player, "Stats", 36);
		
		createIcon("&6Kills", 11,
					 new ItemStack(Material.DIAMOND_SWORD, 1), 
					 ItemFlag.HIDE_ATTRIBUTES,
					 "&b"+warrior.getKills());
		
	}
}
