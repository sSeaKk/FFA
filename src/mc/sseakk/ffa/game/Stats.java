package mc.sseakk.ffa.game;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class Stats{
	private FFAPlayer fplayer;
	private OfflinePlayer player;
	private int kills, 
				deaths,
				assists,
				killStreak, 
				deathStreak, 
				maxKillStreak, 
				maxDeathStreak, 
				damageGiven,
				damageTaken,
				maxDamageGiven,
				maxDamageTaken;
	private double kdaRatio,
				   kdRatio;
	private UUID uuid;
	
	public Stats(UUID uuid) {
		this.fplayer = null;
		this.uuid = uuid;
		this.setPlayer(Bukkit.getOfflinePlayer(uuid));
		
		this.kills = 0;
		this.deaths = 0;
		this.assists = 0;
		this.killStreak = 0;
		this.deathStreak = 0;
		this.maxKillStreak = 0;
		this.maxDeathStreak = 0;
		this.damageGiven = 0;
		this.damageTaken = 0;
		this.maxDamageGiven = 0;
		this.maxDamageTaken = 0;
	}
	
	public Stats(FFAPlayer fplayer) {
		this.fplayer = fplayer;
		this.uuid = fplayer.getPlayer().getUniqueId();
		this.setPlayer(fplayer.getPlayer());
		
		this.kills = 0;
		this.deaths = 0;
		this.assists = 0;
		this.killStreak = 0;
		this.deathStreak = 0;
		this.maxKillStreak = 0;
		this.maxDeathStreak = 0;
		this.damageGiven = 0;
		this.damageTaken = 0;
		this.maxDamageGiven = 0;
		this.maxDamageTaken = 0;
	}
	
	public int getKills() {
		return kills;
	}

	public void setKills(int kills) {
		this.kills = kills;
	}

	public int getDeaths() {
		return deaths;
	}

	public void setDeaths(int death) {
		this.deaths = death;
	}

	public int getKillStreak() {
		return killStreak;
	}

	public void setKillStreak(int killStreak) {
		this.killStreak = killStreak;
	}

	public FFAPlayer getFplayer() {
		return fplayer;
	}

	public void setFplayer(FFAPlayer fplayer) {
		this.fplayer = fplayer;
	}

	public int getDeathStreak() {
		return deathStreak;
	}

	public void setDeathStreak(int deathStreak) {
		this.deathStreak = deathStreak;
	}

	public int getMaxKillStreak() {
		return maxKillStreak;
	}

	public void setMaxKillStreak(int maxKillStreak) {
		this.maxKillStreak = maxKillStreak;
	}

	public int getMaxDeathStreak() {
		return maxDeathStreak;
	}

	public void setMaxDeathStreak(int maxDeathStreak) {
		this.maxDeathStreak = maxDeathStreak;
	}

	public int getDamageGiven() {
		return damageGiven;
	}

	public void setDamageGiven(int damageGiven) {
		this.damageGiven = damageGiven;
	}

	public int getDamageTaken() {
		return damageTaken;
	}

	public void setDamageTaken(int damageTaken) {
		this.damageTaken = damageTaken;
	}

	public int getMaxDamageGiven() {
		return maxDamageGiven;
	}

	public void setMaxDamageGiven(int maxDamageGiven) {
		this.maxDamageGiven = maxDamageGiven;
	}

	public int getMaxDamageTaken() {
		return maxDamageTaken;
	}

	public void setMaxDamageTaken(int maxDamageTaken) {
		this.maxDamageTaken = maxDamageTaken;
	}
	
	public int getAssists() {
		return assists;
	}

	public void setAssists(int assists) {
		this.assists = assists;
	}
	
	public void calculateRatios() {
		if(this.deaths == 0) {
			this.kdaRatio = (double) Math.round(((double) kills + (assists / 2)) * 100d) / 100;
			this.kdRatio = (double) Math.round(((double) kills) * 100d) /100;

			return;
		}
		
		this.kdaRatio = (double) Math.round(((double) (kills + (assists / 2)) / deaths) * 100d) / 100;
		this.kdRatio = (double) Math.round(((double) kills / deaths) * 100d) / 100;
	}
	
	public void increaseKills() {
		this.kills++;
		calculateRatios();
		this.killStreak++;
		
		if(this.deathStreak > this.maxDeathStreak) {
			this.maxDeathStreak = this.deathStreak;
		}
		
		if(this.deathStreak != 0) {
			this.deathStreak = 0;
		}
	}
	
	public void increaseDeaths() {
		this.deaths++;
		calculateRatios();
		this.deathStreak++;
		
		if(this.killStreak > this.maxKillStreak) {
			this.maxKillStreak = this.killStreak;
		}
		
		this.killStreak = 0;
	}
	
	public void increaseAssists() {
		this.assists++;
		calculateRatios();
	}
	
	public double getKdaRatio() {
		return kdaRatio;
	}
	
	public double getKdRatio() {
		return kdRatio;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public OfflinePlayer getPlayer() {
		return player;
	}

	public void setPlayer(OfflinePlayer player) {
		this.player = player;
	}
}