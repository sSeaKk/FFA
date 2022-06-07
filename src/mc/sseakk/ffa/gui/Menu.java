package mc.sseakk.ffa.gui;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import mc.sseakk.ffa.game.warrior.Warrior;
import mc.sseakk.ffa.gui.menu.MainMenu;
import mc.sseakk.ffa.mainpackage.FFA;
import mc.sseakk.ffa.mainpackage.WarriorManager;
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
	
	protected Menu(Player player, String name, int slots) {
		this.warrior = WarriorManager.get(player.getName());
		this.menuName = name;
		this.slots = slots;
		
		this.menu = FFA.getInstance().getServer().createInventory(null, this.slots, this.menuName);
		
		createIcon("&b"+this.warrior.getName(), 4,
				   new ItemStack(Material.SKULL_ITEM));
		
		/*this.item = new ItemStack(Material.SKULL_ITEM);
		this.skmeta = (SkullMeta) item.getItemMeta();
		this.skmeta.setOwner(player.getName());
		this.skmeta.setDisplayName(TextUtil.colorText("&b" + warrior.getName()));
		this.item.setDurability((short) 3);
		this.item.setItemMeta(skmeta);
		this.menu.setItem(4, item);*/
		
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
	
	public ItemMeta generateItemMeta(ItemStack item, String displayName, String... lore) {
		ItemMeta m = item.getItemMeta();
		m.setDisplayName(TextUtil.colorText(displayName));
		m.setLore(Arrays.asList(lore));
		return m;
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