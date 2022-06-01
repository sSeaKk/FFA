package mc.sseakk.ffa.game.kits;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import mc.sseakk.ffa.game.Kits;

public class Default extends Kits{
	public Default(Player player) {
		super(player);
		kitName = "default";
		setKit();
	}
	
	public void setKit() {

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
}
