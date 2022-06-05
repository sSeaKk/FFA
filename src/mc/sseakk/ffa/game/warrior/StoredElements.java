package mc.sseakk.ffa.game.warrior;

import org.bukkit.GameMode;
import org.bukkit.inventory.ItemStack;

public interface StoredElements {
	public ItemStack[] getStoredInventory();

	public void setStoredInventory(ItemStack[] storedInventory);

	public ItemStack[] getStoredArmor();

	public void setStoredArmort(ItemStack[] storedEquipment);

	public GameMode getGamemode();

	public void setGamemode(GameMode gamemode);

	public float getStoredExp();
	
	public void setStoredExp(float storedExp);

	public int getStoredLevel();

	public void setStoredLevel(int storedLevel);

	public int getStoredHunger();

	public void setStoredHunger(int storedHunger);

	public double getStoredHealth();

	public void setStoredHealth(double storedHealth);

	public double getStoredMaxHealth();

	public void setStoredMaxHealth(double storedMaxHealth);
}