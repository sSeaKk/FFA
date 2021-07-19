package mc.sseakk.ffa.game;

import java.util.ArrayList;

import org.bukkit.Location;

import mc.sseakk.ffa.util.Messages;

public class Arena {
	
	private String name;
	private int currentPlayers;
	private Location spawn;
	private ArrayList<FFAPlayer> playerList;
	private ArenaStatus status;
	
	public Arena(String name) {
		this.playerList = new ArrayList<FFAPlayer>();
		this.name = name;
		this.status = ArenaStatus.DISABLED;
		this.currentPlayers = 0;
	}
	
	public void addPlayer(FFAPlayer ffaplayer) {
		this.playerList.add(ffaplayer);
		ffaplayer.getPlayer().teleport(spawn);
	}
	
	public void removePlayer(FFAPlayer ffaplayer) {
		if(this.playerList.contains(ffaplayer)) {
			this.playerList.remove(ffaplayer);
		} else {
			Messages.sendPlayerMessage(ffaplayer.getPlayer(), "You are not in a arena!");
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
}
