package mc.sseakk.ffa.game;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class FFAPlayer {
	
	private Player player;
	private int kills, deaths, killStreak;
	private StoredElements stored;
	private Location previousLocation;
	private boolean flying;
	
	public FFAPlayer(Player player) {
		
		this.player = player;
		this.stored = new StoredElements(
				player.getInventory().getContents(),
				player.getInventory().getArmorContents(),
				player.getGameMode(),
				player.getExp(),
				player.getLevel(),
				player.getFoodLevel(),
				player.getHealth(),
				player.getMaxHealth()
				);
		this.previousLocation = player.getLocation();
		this.flying = player.isFlying();
		
		this.kills = 0;
		this.deaths = 0;
		this.killStreak = 0;
		
		player.setFoodLevel(20);
		player.setHealth(20);
		player.setGameMode(GameMode.SURVIVAL);
		
		player.getInventory().clear();
		player.getEquipment().clear();
		Kits.setDefaultKit(player);
	}
	
	public void removePlayer() {
		this.player.getEquipment().clear();
		this.player.getEquipment().setArmorContents(this.stored.getStoredArmor());
		
		this.player.getInventory().clear();
		this.player.getInventory().setContents(this.stored.getStoredInventory());
		
		this.player.setGameMode(this.stored.getGamemode());
		this.player.setExp(this.stored.getStoredExp());
		this.player.setLevel(this.stored.getStoredLevel());
		this.player.setHealth(this.stored.getStoredHealth());
		this.player.setMaxHealth(this.stored.getStoredMaxHealth());
		this.player.setFoodLevel(this.stored.getStoredHunger());
		this.player.setFlying(this.isFlying());
		
		player.teleport(this.getPreviousLocation());
	}
	
	public StoredElements getStored() {
		return this.stored;
	}
	
	public void increaseKills() {
		this.kills++;
	}
	
	public void increaseDeaths() {
		this.deaths++;
	}
	
	public Player getPlayer() {
		return this.player;
	}

	public int getKills() {
		return kills;
	}

	public int getDeaths() {
		return deaths;
	}

	public int getKillStreak() {
		return killStreak;
	}
	
	public Location getPreviousLocation() {
		return previousLocation;
	}

	public boolean isFlying() {
		return flying;
	}
}
