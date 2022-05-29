package mc.sseakk.ffa.game;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

public class Kits {
	private static String kitName;
	private static ItemStack item;
	private static PlayerInventory inventario;
	private static EntityEquipment armadura;
	private static Kits defaultKit;
	
	private Kits(Player player) {
		setKitName("default");
		inventario = player.getInventory();
		armadura = player.getEquipment();

		item = new ItemStack(Material.DIAMOND_HELMET);
		item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
		item.addEnchantment(Enchantment.DURABILITY, 3);
		armadura.setHelmet(item);
		
		item = new ItemStack(Material.DIAMOND_CHESTPLATE);
		item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
		item.addEnchantment(Enchantment.DURABILITY, 3);
		armadura.setChestplate(item);
		
		item = new ItemStack(Material.DIAMOND_LEGGINGS);
		item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
		item.addEnchantment(Enchantment.DURABILITY, 3);
		armadura.setLeggings(item);
		
		item = new ItemStack(Material.DIAMOND_BOOTS);
		item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
		item.addEnchantment(Enchantment.DURABILITY, 3);
		armadura.setBoots(item);
		
		
		
		item = new ItemStack(Material.DIAMOND_SWORD);
		item.addEnchantment(Enchantment.DAMAGE_ALL, 2);
		item.addEnchantment(Enchantment.DURABILITY, 3);
		inventario.setItem(0, item);
		
		item = new ItemStack(Material.ENDER_PEARL, 16);
		inventario.setItem(1, item);
		
		Potion potion = new Potion(PotionType.INSTANT_HEAL, 2);
		potion.setSplash(true);
		item = new ItemStack(potion.toItemStack(1));
		while(inventario.firstEmpty() != -1) {
			inventario.addItem(item);
		}
	}
	
	public static Kits setDefaultKit(Player player){
		defaultKit = new Kits(player);
		return defaultKit;
	}

	public static String getKitName() {
		return kitName;
	}
	
	public static void setKitName(String name){
		Kits.kitName = name;
	}
	
	public static PlayerInventory getInventario() {
		return inventario;
	}

	public static EntityEquipment getArmadura() {
		return armadura;
	}
	
}
