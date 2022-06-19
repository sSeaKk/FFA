package mc.sseakk.ffa.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import mc.sseakk.ffa.FFA;
import mc.sseakk.ffa.game.Warrior;
import mc.sseakk.ffa.gui.menu.CustomizationMenu;
import mc.sseakk.ffa.gui.menu.MainMenu;
import mc.sseakk.ffa.gui.menu.customization.KillStreakMenu;
import mc.sseakk.ffa.util.TextUtil;
import net.md_5.bungee.api.ChatColor;

public class Menu{
	protected String menuName;
	protected int slots;
	protected Inventory menu;
	protected Warrior warrior;
	protected static ArrayList<Player> openedGui = new ArrayList<Player>();
	protected static Map<Player, Menu> playerMenuOpened = new HashMap<Player, Menu>();
	
	protected ItemStack item;
	protected SkullMeta skmeta;
	
	//TODO: Detectar la personalizacion, mas paginas de Menu de Titulos, botones para voler a atras dinamicos.
	protected Menu(Player player, String name, int slots) {
		this.warrior = FFA.getWarriorManager().get(player.getName());
		this.menuName = name;
		this.slots = slots;
		
		this.menu = FFA.getInstance().getServer().createInventory(null, this.slots, this.menuName);
		
		createIcon("&6"+this.warrior.getName(), 4,
				   new ItemStack(Material.SKULL_ITEM));
		
		if(!(this instanceof MainMenu)) {
			createIcon("&fVolver", this.slots-9,
						 new ItemStack(Material.FEATHER, 1),
						 "&7Clickea para volver atras");
		}
		
		if(!playerMenuOpened.containsKey(player)) {
			playerMenuOpened.put(player, this);
		}
		
		openedGui.add(player);
		player.openInventory(menu);
	}
	
	public Inventory getMenu() {
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
		
		if(item.getType() == Material.GREEN_RECORD) {
			meta.addItemFlags(ItemFlag.values());
		}
		
		meta.setLore(lore);
		item.setItemMeta(meta);
		
		if(item.getType() == Material.SKULL_ITEM) {
			SkullMeta skmeta = (SkullMeta) item.getItemMeta();
			skmeta.setOwner(ChatColor.stripColor(skmeta.getDisplayName()));
			skmeta.setLore(Arrays.asList(TextUtil.colorText("&b&o"+warrior.getTitleText())));
			item.setDurability((short) 3);
			item.setItemMeta(skmeta);
		}
		
		this.menu.setItem(slot, item);
	}
	
	protected void putOpenedMenuToPlayer(Player player, Menu menu) {
		if(playerMenuOpened.containsKey(player)) {
			playerMenuOpened.remove(player);
		}
		
		playerMenuOpened.put(player, menu);
	}
	
	public static Menu getPlayerOpenMenu(Player player) {
		return playerMenuOpened.get(player);
	}
	
	public static void openPrevMenu(Player player, String menuName) {
		if(menuName.equals("Estadisticas") || menuName.equals("Personalizacion")) {
			new MainMenu(player);
		}
		
		if(menuName.equals("Titulos") || menuName.equals("Rachas")) {
			new CustomizationMenu(player);
		}
		
		if(menuName.equals("Rachas: Efectos") || menuName.equals("Rachas: Sonidos")) {
			new KillStreakMenu(player);
		}
	}
}