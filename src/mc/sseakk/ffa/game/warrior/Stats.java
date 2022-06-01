package mc.sseakk.ffa.game.warrior;

public interface Stats{

	int getKills();

	void setKills(int kills);

	int getDeaths();

	void setDeaths(int deaths);

	int getKillStreak();

	void setKillStreak(int killStreak);

	int getDeathStreak();

	void setDeathStreak(int deathStreak);

	int getMaxKillStreak();

	void setMaxKillStreak(int maxKillStreak);

	int getMaxDeathStreak();

	void setMaxDeathStreak(int maxDeathStreak);

	double getDamageGiven();

	void setDamageGiven(double damageGiven);

	double getDamageTaken();

	void setDamageTaken(double damageTaken);

	double getMaxDamageGiven();

	void setMaxDamageGiven(double maxDamageGiven);

	double getMaxDamageTaken();

	void setMaxDamageTaken(double maxDamageTaken);
	
	int getAssists();

	void setAssists(int assists);
	
	void calculateRatios();
	
	void increaseKills();
	
	void increaseDeaths();
	
	void increaseAssists();
	
	double getKdaRatio();
	
	double getKdRatio();
	
	void increaseDamageGiven(double damage);
	
	void increaseDamageTaken(double damage);
	
	void resetDamages();
	
	void resetSessionStats();
	
	void saveActualStats();
	
	void detectEvents();
}