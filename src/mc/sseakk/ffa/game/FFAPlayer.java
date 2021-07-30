package mc.sseakk.ffa.game;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import mc.sseakk.ffa.mainpackage.FFA;
import mc.sseakk.ffa.mainpackage.StatsManager;

public class FFAPlayer {
	
	private Player player;
	private StoredElements stored;
	private Location previousLocation;
	private boolean flying;
	private static PotionEffect potionEffect;
	private StatsManager sm;
	private Stats stats;
	
	public FFAPlayer(Player player) {
		this.sm = FFA.getStatsManager();
		this.stats = new Stats(this);
		this.sm.addStats(stats);
		
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
		potionEffect = new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1);
		
		this.player.setFoodLevel(20);
		this.player.setHealth(20);
		this.player.setGameMode(GameMode.SURVIVAL);
		
		this.player.addPotionEffect(potionEffect);
		
		this.player.getInventory().clear();
		this.player.getEquipment().clear();
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
	
	public Player getPlayer() {
		return this.player;
	}

	public int getKills() {
		return this.stats.getKills();
	}

	public int getDeaths() {
		return this.stats.getDeaths();
	}

	public int getAssists() {
		return this.stats.getAssists();
	}
	
	public int getKillStreak() {
		return this.stats.getKillStreak();
	}
	
	public int getDeathStreak() {
		return this.stats.getDeathStreak();
	}
	
	public int getMaxKillStreak() {
		return this.stats.getMaxKillStreak();
	}
	
	public int getMaxDeathStreak() {
		return this.stats.getMaxDeathStreak();
	}
	
	public int getDamageGiven() {
		return this.stats.getDamageGiven();
	}
	
	public int getDamageTaken() {
		return this.stats.getDamageTaken();
	}
	
	public int getMaxDamageGiven() {
		return this.stats.getMaxDamageGiven();
	}
	
	public int getMaxDamageTaken() {
		return this.stats.getMaxDamageTaken();
	}
	
	public Location getPreviousLocation() {
		return previousLocation;
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
	
	public void increaseKills() {
		this.stats.increaseKills();
	}
	
	public void increaseDeaths() {
		this.stats.increaseDeaths();
	}
	
	public void setKills(int kills) {
		this.stats.setKills(kills);
	}

	public void setDeaths(int deaths) {
		 this.stats.setDeaths(deaths);
	}

	public void setAssists(int assists) {
		this.stats.setAssists(assists);
	}
	
	public void setKillStreak(int killStreak) {
		this.stats.setKillStreak(killStreak);
	}
	
	public void setDeathStreak(int deathStreak) {
		this.stats.setDeathStreak(deathStreak);
	}
	
	public void setMaxKillStreak(int maxKillStreak) {
		this.stats.setMaxKillStreak(maxKillStreak);
	}
	
	public void setMaxDeathStreak(int maxDeathStreak) {
		this.stats.setMaxDeathStreak(maxDeathStreak);
	}
	
	public void setDamageGiven(int damageGiven) {
		this.stats.setDamageGiven(damageGiven);
	}
	
	public void setDamageTaken(int damageTaken) {
		this.stats.setDamageTaken(damageTaken);
	}
	
	public void setMaxDamageGiven(int maxDamageGiven) {
		this.stats.setMaxDamageGiven(maxDamageGiven);
	}
	
	public void setMaxDamageTaken(int maxDamageTaken) {
		this.stats.setMaxDamageTaken(maxDamageTaken);
	}
}
