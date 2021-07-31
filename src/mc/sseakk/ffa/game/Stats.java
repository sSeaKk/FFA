package mc.sseakk.ffa.game;

public class Stats {
	private FFAPlayer fplayer;
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
	private String name;
	
	public Stats(FFAPlayer fplayer) {
		this.fplayer = fplayer;
		this.name = fplayer.getPlayer().getName();
		
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

	public int getAssists() {
		return assists;
	}

	public void setAssists(int assists) {
		this.assists = assists;
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
	
	public void increaseKills() {
		kills++;
	}
	
	public void increaseDeaths() {
		deaths++;
	}
	
	public void increaseAssists() {
		assists++;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
