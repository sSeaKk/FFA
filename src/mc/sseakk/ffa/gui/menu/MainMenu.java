package mc.sseakk.ffa.gui.menu;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import mc.sseakk.ffa.gui.Menu;
import mc.sseakk.ffa.util.TextUtil;

public class MainMenu extends Menu{
	public MainMenu(Player player){
		super(player, TextUtil.colorText("Menu principal"), 18);
		
		item = new ItemStack(Material.PAPER, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(TextUtil.colorText("&6Stats"));	
		meta.setLore(Arrays.asList(TextUtil.colorText("&7Revisa tus estadisticas")));
		item.setItemMeta(meta);
		menu.setItem(13, item);
		
		
	}
}
