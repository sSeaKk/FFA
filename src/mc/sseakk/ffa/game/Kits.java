package mc.sseakk.ffa.game;

import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public abstract class Kits {
	protected Player player;
	protected String kitName;
	protected static ItemStack item;
	protected static PlayerInventory inventario;
	protected static EntityEquipment armadura;
	
	protected Kits(Player player) {
		this.player = player;
		inventario = this.player.getInventory();
		armadura = this.player.getEquipment();
	}

	public String getKitName() {
		return this.kitName;
	}
	
	public void setKitName(String name){
		this.kitName = name;
	}
	
	public PlayerInventory getInventario() {
		return inventario;
	}

	public EntityEquipment getArmadura() {
		return armadura;
	}
	
	public abstract void setKit();
}
