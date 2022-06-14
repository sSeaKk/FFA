package mc.sseakk.ffa.gui.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import mc.sseakk.ffa.gui.Menu;
import mc.sseakk.ffa.util.TextUtil;

public class MainMenu extends Menu{
	public MainMenu(Player player){
		super(player, TextUtil.colorText("Menu Principal"), 18);
		addOpenedMenuToPlayer(player, this);
		
		createIcon("&6Estadisticas", 13,
				   new ItemStack(Material.PAPER, 1),
				   "&7Revisa tus estadisticas");
		
		createIcon("&6Personalizacion", 11,
					new ItemStack(Material.COMPASS),
					"&7Personaliza tu perfil");
		
	}
}
