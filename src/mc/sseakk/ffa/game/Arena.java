package mc.sseakk.ffa.game;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import mc.sseakk.ffa.mainpackage.FFA;
import mc.sseakk.ffa.util.Messages;

public class Arena {
	
	private String name;
	private int currentPlayers;
	private Location spawn;
	private ArrayList<FFAPlayer> playerList;
	private ArenaStatus status;
	private Player configurator;
	private boolean hasFile;
	
	public Arena(String name, Player configurator) {
		this.playerList = new ArrayList<FFAPlayer>();
		this.name = name;
		this.setConfigurator(configurator);
		this.status = ArenaStatus.DISABLED;
		this.currentPlayers = 0;
		this.setHasFile(false);
		this.spawn = null;
	}
	
	public Arena() {
		this.playerList = new ArrayList<FFAPlayer>();
		this.status = ArenaStatus.DISABLED;
		this.currentPlayers = 0;
	}
	
	public void addPlayer(Player player) {
		if(FFA.getArenasManager().getPlayerArena(player.getName()) != null) {
			Messages.sendPlayerMessage(player, "&cYa estas en una arena!");
			return;
		}
		
		if(this.spawn == null) {
			Messages.sendPlayerMessage(player, "&cLa arena no tiene spawn");
			return;
		}
		
		FFAPlayer fp = new FFAPlayer(player);
		playerList.add(fp);
		this.currentPlayers++;
		player.teleport(spawn);
		
		Messages.sendPlayerMessage(player, "&aEntraste a " + this.name);
	}
	
	public void removePlayer(Player player) {
		FFAPlayer fp = getFFAPlayer(player.getName());
		
		playerList.remove(fp);
		this.currentPlayers--;
		fp.removePlayer();
	}
	
	public void removePlayers() {
		for(FFAPlayer fp : this.playerList) {
			fp.removePlayer();
			playerList.remove(fp);
		}
	}
	
	public FFAPlayer getFFAPlayer(String playerName) {
		for(int i=0; i<playerList.size(); i++) {
			if(playerList.get(i).getPlayer().getName().equals(playerName)) {
				return playerList.get(i);
			}
		}
		return null;
	}
	
	public boolean isEnabled() {
		if(this.status.equals(ArenaStatus.ENABLED)) {
			return true;
		}
		return false;
	}
	
	public void increaseCurrentPlayers() {
		this.currentPlayers++;
	}
	
	public void decreaseCurrentPlayers() {
		this.currentPlayers--;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Location getSpawn() {
		return spawn;
	}

	public void setSpawn(Location spawn) {
		this.spawn = spawn;
	}

	public ArrayList<FFAPlayer> getPlayerList() {
		return playerList;
	}

	public ArenaStatus getStatus() {
		return status;
	}

	public void setStatus(ArenaStatus status) {
		this.status = status;
	}

	public int getCurrentPlayers() {
		return currentPlayers;
	}

	public boolean isHasFile() {
		return hasFile;
	}

	public void setHasFile(boolean hasFile) {
		this.hasFile = hasFile;
	}

	public Player getConfigurator() {
		return configurator;
	}

	public void setConfigurator(Player configurator) {
		this.configurator = configurator;
	}
}
