package mc.sseakk.ffa.game.kits;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import mc.sseakk.ffa.game.Kits;
import mc.sseakk.ffa.mainpackage.FFA;

public class Default extends Kits{
	public Default(Player player) {
		super(player);
		kitName = "Default";
		setKit();
	}
	
	public void setKit() {
		Bukkit.getServer().getScheduler().runTaskLater(FFA.getInstance(), new Runnable() {

			@Override
			public void run() {
				player.updateInventory();
				item = new ItemStack(Material.DIAMOND_HELMET);
				meta = item.getItemMeta();
				meta.spigot().setUnbreakable(true);
				item.setItemMeta(meta);
				item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
				armadura.setHelmet(item);
		
				item = new ItemStack(Material.DIAMOND_CHESTPLATE);
				meta = item.getItemMeta();
				meta.spigot().setUnbreakable(true);
				item.setItemMeta(meta);
				item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
				armadura.setChestplate(item);
				
				item = new ItemStack(Material.DIAMOND_LEGGINGS);
				meta = item.getItemMeta();
				meta.spigot().setUnbreakable(true);
				item.setItemMeta(meta);
				item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
				armadura.setLeggings(item);
		
				item = new ItemStack(Material.DIAMOND_BOOTS);
				meta = item.getItemMeta();
				meta.spigot().setUnbreakable(true);
				item.setItemMeta(meta);
				item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
				armadura.setBoots(item);
		
				item = new ItemStack(Material.DIAMOND_SWORD);
				meta = item.getItemMeta();
				meta.spigot().setUnbreakable(true);
				item.setItemMeta(meta);
				item.addEnchantment(Enchantment.DAMAGE_ALL, 2);
				inventario.setItem(0, item);
		
				item = new ItemStack(Material.ENDER_PEARL, 16);
				inventario.setItem(1, item);
		
				Potion potion = new Potion(PotionType.INSTANT_HEAL, 2);
				potion.setSplash(true);
				item = new ItemStack(potion.toItemStack(1));
				while(inventario.firstEmpty() != -1) {
					inventario.addItem(item);
				}
				player.updateInventory();
			}
			
		}, 1L);
		
	}
}
