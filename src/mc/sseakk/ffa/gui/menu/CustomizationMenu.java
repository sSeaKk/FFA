package mc.sseakk.ffa.gui.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import mc.sseakk.ffa.gui.Menu;

public class CustomizationMenu extends Menu{
	public CustomizationMenu(Player player) {
		super(player, "Personalizacion", 27);
		putOpenedMenuToPlayer(player, this);
		
		createIcon("&6Titulo", 11,
					new ItemStack(Material.NAME_TAG),
					"&7Personaliza tu Titulo");	
		
		createIcon("&6Racha", 15,
				new ItemStack(Material.BLAZE_POWDER),
				ItemFlag.HIDE_ATTRIBUTES,
				"&7Personaliza tus Rachas");
	}
}
