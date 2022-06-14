package mc.sseakk.ffa.gui.menu.customization;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import mc.sseakk.ffa.gui.Menu;


public class KillStreakMenu extends Menu{
	public KillStreakMenu(Player player) {
		super(player, "Rachas", 27);
		
		createIcon("&6Efectos", 11, 
					new ItemStack(Material.FIREWORK),
					"&7Personaliza los efectos de una Racha");

		createIcon("&6Sonidos", 15,
					new ItemStack(Material.GREEN_RECORD),
					ItemFlag.HIDE_ATTRIBUTES,
					"&7Personaliza los sonidos de una Racha");
		
	}
}
