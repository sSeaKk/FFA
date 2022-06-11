package mc.sseakk.ffa.gui;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import mc.sseakk.ffa.game.warrior.Warrior;
import mc.sseakk.ffa.gui.menu.MainMenu;
import mc.sseakk.ffa.mainpackage.FFA;
import mc.sseakk.ffa.mainpackage.ProfileManager;
import mc.sseakk.ffa.util.TextUtil;
import net.md_5.bungee.api.ChatColor;

public class Menu{
	protected String menuName;
	protected int slots;
	protected Inventory menu;
	protected Warrior warrior;
	protected static ArrayList<Player> openedGui = new ArrayList<Player>();
	
	protected ItemStack item;
	protected SkullMeta skmeta;
	//TODO: Detectar la personalizacion, mas paginas de Menu de Tituls, botones para voler a atras dinamicos.
	protected Menu(Player player, String name, int slots) {
		this.warrior = ProfileManager.get(player.getName());
		this.menuName = name;
		this.slots = slots;
		
		this.menu = FFA.getInstance().getServer().createInventory(null, this.slots, this.menuName);
		
		createIcon("&b"+this.warrior.getName(), 4,
				   new ItemStack(Material.SKULL_ITEM),
				   Enchantment.DAMAGE_ALL,
				   ItemFlag.HIDE_ENCHANTS);
		
		if(!(this instanceof MainMenu)) {
			createIcon("&fVolver", this.slots-9,
						 new ItemStack(Material.PAPER, 1),
						 "&7Clickea para volver al Menu Principal");
		}
		
		openedGui.add(player);
		player.openInventory(menu);
	}
	
	public Inventory getMenu(){
		return this.menu;
	}
	
	public static boolean isGuiOpen(Player player) {
		if(openedGui.contains(player)) {
			return true;
		}
		return false;
	}
	
	public void createIcon(String name, int slot, ItemStack item, Object... args) {
		ArrayList<String> lore = new ArrayList<String>();
		ItemMeta meta = item.getItemMeta();
		
		meta.setDisplayName(TextUtil.colorText(name));
		for(Object obj : args) {
			if(obj instanceof String) {
				lore.add(TextUtil.colorText(obj.toString()));
			}
			if(obj instanceof ItemFlag) {
				meta.addItemFlags((ItemFlag) obj);
			}
			if(obj instanceof Enchantment) {
				item.addUnsafeEnchantment((Enchantment) obj, 1);
			}
		}
		
		meta.setLore(lore);
		item.setItemMeta(meta);
		
		if(item.getType() == Material.SKULL_ITEM) {
			SkullMeta skmeta = (SkullMeta) item.getItemMeta();
			skmeta.setOwner(ChatColor.stripColor(skmeta.getDisplayName()));
			item.setDurability((short) 3);
			item.setItemMeta(skmeta);
		}
		
		this.menu.setItem(slot, item);
	}
}