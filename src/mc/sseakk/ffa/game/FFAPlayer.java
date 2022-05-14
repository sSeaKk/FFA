package mc.sseakk.ffa.game;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;

import mc.sseakk.ffa.mainpackage.FFA;
import mc.sseakk.ffa.mainpackage.StatsManager;

public class FFAPlayer{
	
	private Player player;
	private StoredElements stored;
	private Location previousLocation;
	private boolean flying;
	private static PotionEffect potionEffect;
	private StatsManager sm;
	private Stats stats;
	private Scoreboard previousScoreboard;
	
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
		this.previousScoreboard = player.getScoreboard();
		
		this.flying = player.isFlying();
		potionEffect = new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1);
		
		this.player.setFoodLevel(20);
		this.player.setHealth(20);
		this.player.setGameMode(GameMode.SURVIVAL);
		
		this.player.addPotionEffect(potionEffect);
		
		this.player.getInventory().clear();
		this.player.getEquipment().clear();
		Kits.setDefaultKit(player);
		
		this.sm = FFA.getStatsManager();
		
		this.stats = sm.getStats(player.getUniqueId());
		if(this.stats == null) {
			System.out.println("null stats");
			this.stats = new Stats(this);
			this.sm.addStats(stats);
		} else {
			this.stats.setFplayer(this);
		}
		
		StatsScoreboard.updateStatsScoreboard(this);
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
		this.player.removePotionEffect(potionEffect.getType());
		this.player.setExp(0.9999f);
		
		this.player.setScoreboard(previousScoreboard);
		this.player.teleport(this.previousLocation);
		
		this.stats.saveActualStats();
		this.stats.resetSessionStats();
	}
	
	public Stats getStats() {
		return this.stats;
	}
	
	public StoredElements getStored() {
		return this.stored;
	}
	
	public Player getPlayer() {
		return this.player;
	}

	public boolean isFlying() {
		return flying;
	}

	public static PotionEffect getPotionEffect() {
		return potionEffect;
	}

	public static void setPotionEffect(PotionEffect effect) {
		potionEffect = effect;
	}
}