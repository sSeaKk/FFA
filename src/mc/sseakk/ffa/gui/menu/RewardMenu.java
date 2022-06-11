package mc.sseakk.ffa.gui.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import mc.sseakk.ffa.gui.Menu;

public class RewardMenu extends Menu{
	public RewardMenu(Player player) {
		super(player, "Personalizacion", 27);
		
		createIcon("&6Titulo", 11,
					new ItemStack(Material.PAPER),
					"&7Personaliza tu Titulo");	
	}
}
