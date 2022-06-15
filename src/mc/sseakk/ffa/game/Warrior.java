package mc.sseakk.ffa.game;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;

import mc.sseakk.ffa.FFA;
import mc.sseakk.ffa.game.events.WarriorKillStreakEvent;
import mc.sseakk.ffa.game.events.WarriorKillStreakEvent.KillStreakType;
import mc.sseakk.ffa.game.kits.Default;
import mc.sseakk.ffa.game.warrior.Stats;
import mc.sseakk.ffa.game.warrior.StoredElements;
import mc.sseakk.ffa.reward.Reward;
import mc.sseakk.ffa.reward.Reward.RewardType;
import mc.sseakk.ffa.reward.rewards.Title;
import mc.sseakk.ffa.util.Messages;

public class Warrior extends Profile implements Stats, StoredElements{
	
	private OfflinePlayer offplayer;
	private StoredElements stored;
	private Location previousLocation;
	private boolean flying;
	private static PotionEffect spawnPotionEffect;
	private Scoreboard previousScoreboard;
	private Profile profile = null;
	private Kits kit;
	
	protected int kills = 0,
				  deaths = 0,
				  assists = 0,
				  killStreak = 0, 
				  deathStreak = 0, 
				  maxKillStreak = 0, 
				  maxDeathStreak = 0;
	
	protected double kdaRatio = 0.0,
				   kdRatio = 0.0,
				   damageGiven = 0.0,
				   damageTaken = 0.0,
				   maxDamageGiven = 0.0,
				   maxDamageTaken = 0.0;
	
	private ItemStack[] storedInventory;
	private ItemStack[] storedArmor;
	private GameMode gamemode;
	private float storedExp;
	private int storedLevel;
	private int storedHunger;
	private double storedHealth;
	private double storedMaxHealth;
	
	public Warrior(Player player, Kits kit) {
		super(player);
		this.player = player;
		
		this.storedInventory = this.player.getInventory().getContents();
		this.storedArmor = this.player.getInventory().getArmorContents();
		
		this.gamemode = this.player.getGameMode();
		this.storedExp = this.player.getExp();
		this.storedLevel = this.player.getLevel();
		this.storedHunger = this.player.getFoodLevel();
		this.storedHealth = this.player.getHealth();
		this.storedMaxHealth = this.player.getMaxHealth();
		
		this.previousLocation = this.player.getLocation();
		this.previousScoreboard = this.player.getScoreboard();
		
		this.flying = this.player.isFlying();
		spawnPotionEffect = new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1);
		
		this.player.setFoodLevel(20);
		this.player.setHealth(20);
		this.player.setGameMode(GameMode.SURVIVAL);
		this.player.setExp(0.9999f);
		
		this.player.addPotionEffect(spawnPotionEffect);
		
		this.player.getInventory().clear();
		this.player.getEquipment().clear();
		this.kit = kit;
		this.player.updateInventory();
		
		if(!FFA.getWarriorManager().loadStats(this)) {
			Messages.warningMessage("No se pudo cargar las estadisticas de: '" + player.getName() + "'");
		}
		
		ArenaScoreboard.updateStatsScoreboard(this);
	}
	
	public void removePlayer() {
		this.player.updateInventory();
		
		this.player.getEquipment().clear();
		this.player.getEquipment().setArmorContents(this.getStoredArmor());
		
		this.player.getInventory().clear();
		this.player.getInventory().setContents(this.getStoredInventory());
		
		this.player.setGameMode(this.getGamemode());
		this.player.setExp(this.getStoredExp());
		this.player.setLevel(this.getStoredLevel());
		this.player.setHealth(this.getStoredHealth());
		this.player.setMaxHealth(this.getStoredMaxHealth());
		this.player.setFoodLevel(this.getStoredHunger());
		this.player.removePotionEffect(spawnPotionEffect.getType());
		
		this.player.setScoreboard(this.previousScoreboard);
		this.player.teleport(this.previousLocation);
		this.player.setFlying(this.isFlying());
		saveActualStats();
		resetSessionStats();
	}
	
	public void reset() {
		this.kit = new Default(this.player);
		this.player.setExp(0.9999f);
	}
	
	public void setTitle(int titleID) {
		for(Reward reward : this.playerRewards) {
			if(reward.getType() == RewardType.TITLE && reward.getID() == titleID) {
				this.title = (Title) reward;
				FFA.getWarriorManager().saveProfile(this);
				super.resetHoverEvent();
			}
		}
	}
	
	public StoredElements getStored() {
		return this.stored;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}

	public boolean isFlying() {
		return flying;
	}
	
	public static PotionEffect getSpawnPotionEffect() {
		return spawnPotionEffect;
	}
	
	public Profile getProfile() {
		return profile;
	}

	public OfflinePlayer getOffplayer() {
		return offplayer;
	}

	@Override
	public int getKills() {
		return this.kills;
	}

	@Override
	public void setKills(int kills) {
		this.kills = kills;
	}

	@Override
	public int getDeaths() {
		return this.deaths;
	}

	@Override
	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}

	@Override
	public int getKillStreak() {
		return this.killStreak;
	}

	@Override
	public void setKillStreak(int killStreak) {
		this.killStreak = killStreak;
	}

	@Override
	public int getDeathStreak() {
		return this.deathStreak;
	}

	@Override
	public void setDeathStreak(int deathStreak) {
		this.deathStreak = deathStreak;
	}

	@Override
	public int getMaxKillStreak() {
		return this.maxKillStreak;
	}

	@Override
	public void setMaxKillStreak(int maxKillStreak) {
		this.maxKillStreak = maxKillStreak;
	}

	@Override
	public int getMaxDeathStreak() {
		return this.maxDeathStreak;
	}

	@Override
	public void setMaxDeathStreak(int maxDeathStreak) {
		this.maxDeathStreak = maxDeathStreak;
	}

	@Override
	public double getDamageGiven() {
		return this.damageGiven;
	}

	@Override
	public void setDamageGiven(double damageGiven) {
		this.damageGiven = damageGiven;
	}

	@Override
	public double getDamageTaken() {
		return this.damageTaken;
	}

	@Override
	public void setDamageTaken(double damageTaken) {
		this.damageTaken = damageTaken;
	}

	@Override
	public double getMaxDamageGiven() {
		return this.maxDamageGiven;
	}

	@Override
	public void setMaxDamageGiven(double maxDamageGiven) {
		this.maxDamageGiven = maxDamageGiven;
	}

	@Override
	public double getMaxDamageTaken() {
		return this.maxDamageTaken;
	}

	@Override
	public void setMaxDamageTaken(double maxDamageTaken) {
		this.maxDamageTaken = maxDamageTaken;
	}

	@Override
	public int getAssists() {
		return this.assists;
	}

	@Override
	public void setAssists(int assists) {
		this.assists = assists;
	}

	@Override
	public void calculateRatios() {
		if(this.deaths == 0) {
			this.kdaRatio = (double) Math.round(((double) kills + (assists / 2)) * 100d) / 100;
			this.kdRatio = (double) Math.round(((double) kills) * 100d) /100;

			return;
		}
		
		this.kdaRatio = (double) Math.round(((double) (kills + (assists / 2)) / deaths) * 100d) / 100;
		this.kdRatio = (double) Math.round(((double) kills / deaths) * 100d) / 100;
	}

	@Override
	public void increaseKills() {
		this.kills++;
		calculateRatios();
		this.killStreak++;
		detectEvents();
		this.getPlayer().setLevel(this.killStreak);
		this.getPlayer().setExp(0.9999f);
		
		if(this.deathStreak > this.maxDeathStreak) {
			this.maxDeathStreak = this.deathStreak;
		}
		
		this.deathStreak = 0; 
		
	}

	@Override
	public void increaseDeaths() {
		this.deaths++;
		calculateRatios();
		resetDamages();
		this.deathStreak++;
		
		if(this.killStreak > this.maxKillStreak) {
			this.maxKillStreak = this.killStreak;
		}
		
		this.killStreak = 0;
	}

	@Override
	public void increaseAssists() {
		this.assists++;
		calculateRatios();
	}

	@Override
	public double getKdaRatio() {
		return this.kdaRatio;
	}

	@Override
	public double getKdRatio() {
		return this.kdRatio;
	}

	@Override
	public void increaseDamageGiven(double damage) {
		this.damageGiven += damage;
	}

	@Override
	public void increaseDamageTaken(double damage) {
		this.damageTaken += damage;
	}

	@Override
	public void resetDamages() {
		if(this.damageGiven > this.maxDamageGiven) {
			System.out.println("DG: " + true);
			this.maxDamageGiven = this.damageGiven;
			System.out.println(this.maxDamageGiven);
		}
		
		if(this.damageTaken >  this.maxDamageTaken) {
			this.maxDamageTaken = this.damageTaken;
			System.out.println("DT: " + true);
		}
		
		this.damageGiven = 0;
		this.damageTaken = 0;
	}

	@Override
	public void resetSessionStats() {
		this.killStreak = 0;
		this.deathStreak = 0;
		
		resetDamages();
	}

	@Override
	public void saveActualStats() {
		calculateRatios();
		resetDamages();
	}

	@Override
	public void detectEvents() {
		if(this.killStreak == 5) {
			FFA.getPluginManager().callEvent(new WarriorKillStreakEvent(this, KillStreakType.fiveKS));
		}
		
		if(this.killStreak == 10) {
			FFA.getPluginManager().callEvent(new WarriorKillStreakEvent(this, KillStreakType.tenKS));
		}
		
		if(this.killStreak == 15) {
			FFA.getPluginManager().callEvent(new WarriorKillStreakEvent(this, KillStreakType.fifthteenKS));
		}
		
		if(this.killStreak == 20) {
			FFA.getPluginManager().callEvent(new WarriorKillStreakEvent(this, KillStreakType.twentyKS));
		}
		
		if(this.killStreak == 25) {
			
			FFA.getPluginManager().callEvent(new WarriorKillStreakEvent(this, KillStreakType.twentyfiveKS));
		}
		
		if(this.killStreak == 30) {
			FFA.getPluginManager().callEvent(new WarriorKillStreakEvent(this, KillStreakType.thirtyKS));
		}
	}

	public Kits getKit() {
		return kit;
	}

	public void setKit(Kits kit) {
		this.kit = kit;
	}

	public ItemStack[] getStoredInventory() {
		return storedInventory;
	}

	public void setStoredInventory(ItemStack[] storedInventory) {
		this.storedInventory = storedInventory;
	}

	public ItemStack[] getStoredArmor() {
		return storedArmor;
	}

	public void setStoredArmort(ItemStack[] storedEquipment) {
		this.storedArmor = storedEquipment;
	}

	public GameMode getGamemode() {
		return gamemode;
	}

	public void setGamemode(GameMode gamemode) {
		this.gamemode = gamemode;
	}

	public float getStoredExp() {
		return storedExp;
	}

	public void setStoredExp(float storedExp) {
		this.storedExp = storedExp;
	}

	public int getStoredLevel() {
		return storedLevel;
	}

	public void setStoredLevel(int storedLevel) {
		this.storedLevel = storedLevel;
	}

	public int getStoredHunger() {
		return storedHunger;
	}

	public void setStoredHunger(int storedHunger) {
		this.storedHunger = storedHunger;
	}

	public double getStoredHealth() {
		return storedHealth;
	}

	public void setStoredHealth(double storedHealth) {
		this.storedHealth = storedHealth;
	}

	public double getStoredMaxHealth() {
		return storedMaxHealth;
	}

	public void setStoredMaxHealth(double storedMaxHealth) {
		this.storedMaxHealth = storedMaxHealth;
	}
}