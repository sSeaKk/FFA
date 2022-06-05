package mc.sseakk.ffa.gui;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import mc.sseakk.ffa.game.warrior.Warrior;
import mc.sseakk.ffa.mainpackage.FFA;
import mc.sseakk.ffa.mainpackage.WarriorManager;

public class Menu{
	protected String menuName;
	protected int slots;
	protected Inventory menu;
	protected Warrior warrior;
	protected final static ArrayList<Player> openedGui = new ArrayList<Player>();
	
	public Menu(Player player, String name, int slots) {
		this.warrior = WarriorManager.get(player.getName());
		this.menuName = name;
		this.slots = slots;
		openedGui.add(player);
		
		this.menu = FFA.getInstance().getServer().createInventory(null, this.slots, this.menuName);
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
}
