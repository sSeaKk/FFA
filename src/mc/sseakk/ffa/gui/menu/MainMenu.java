package mc.sseakk.ffa.gui.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import mc.sseakk.ffa.gui.Menu;
import mc.sseakk.ffa.util.TextUtil;

public class MainMenu extends Menu{
	public MainMenu(Player player){
		super(player, TextUtil.colorText("Menu principal"), 18);
		
		ItemStack item = new ItemStack(Material.SKULL_ITEM);
		SkullMeta skmeta = (SkullMeta) item.getItemMeta();
		skmeta.setOwner(player.getName());
		skmeta.setDisplayName(TextUtil.colorText("&b" + warrior.getName()));
		item.setDurability((short) 3);
		item.setItemMeta(skmeta);
		menu.setItem(4, item);
		
		
		
		player.openInventory(menu);
	}
}
