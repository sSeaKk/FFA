package mc.sseakk.ffa.game;

import org.bukkit.entity.Player;

public class FFAPlayer {
	
	private Player player;
	private int kills, deaths, killStreak;
	private StoredElements stored;
	
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
		this.kills = 0;
		this.deaths = 0;
		this.killStreak = 0;
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
}
