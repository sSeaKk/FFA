package mc.sseakk.ffa.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import mc.sseakk.ffa.game.kits.Default;
import mc.sseakk.ffa.game.warrior.Warrior;
import mc.sseakk.ffa.mainpackage.FFA;
import mc.sseakk.ffa.util.Messages;
import net.md_5.bungee.api.chat.TextComponent;

public class Arena {
	//TODO: Heredar clase arena
	public enum ArenaStatus {
		DISABLED,
		MAINTENANCE,
		ENABLED;
	}
	
	private String name;
	private int currentPlayers;
	private Location spawn;
	private ArrayList<Warrior> playerList;
	private ArenaStatus status;
	private Player configurator;
	private boolean hasFile;
	
	public Arena(String name, Player configurator) {
		this.playerList = new ArrayList<Warrior>();
		this.name = name;
		this.setConfigurator(configurator);
		this.status = ArenaStatus.DISABLED;
		this.currentPlayers = 0;
		this.setHasFile(false);
		this.spawn = null;
		ArenaScoreboard.initScoreboard(this);
	}
	
	public Arena() {
		this.playerList = new ArrayList<Warrior>();
		this.status = ArenaStatus.DISABLED;
		this.currentPlayers = 0;
		ArenaScoreboard.initScoreboard(this);
	}
	
	public void addPlayer(Player player) {
		if(FFA.getArenasManager().getPlayerArena(player.getName()) != null) {
			Messages.sendPlayerMessage(player, "&cYa estas en una arena!");
			return;
		}
		
		if(!hasSpawn()) {
			Messages.sendPlayerMessage(player, "&cLa arena no tiene spawn");
			return;
		}
		
		if(!isEnabled()) {
			Messages.sendPlayerMessage(player, "&cLa arena esta desactivada o en mantenimiento");
			return;
		}
		
		Warrior fp = new Warrior(player, new Default(player));
		playerList.add(fp);
		this.currentPlayers++;
		player.teleport(spawn);
		
		Messages.sendPlayerMessage(player, "&aEntraste a " + this.name);
	}
	
	public void removePlayer(Player player) {
		Warrior fp = this.getWarrior(player);
		
		playerList.remove(fp);
		this.currentPlayers--;
		fp.removePlayer();
	}
	
	public void removePlayers() {
		while(!playerList.isEmpty()) {
			Warrior fp = playerList.get(0);
			fp.removePlayer();
			playerList.remove(fp);
		}
	}
	
	public Warrior getWarrior(String playerName) {
		for(int i=0; i<playerList.size(); i++) {
			if(playerList.get(i).getPlayer().getName().equals(playerName)) {
				return playerList.get(i);
			}
		}
		return null;
	}
	
	public Warrior getWarrior(Player player) {
		for(Warrior fplayer : this.playerList) {
			if(fplayer.getPlayer().equals(player)) {
				return fplayer;
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
	
	public boolean hasSpawn() {
		if(this.spawn != null) {
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

	public ArrayList<Warrior> getPlayerList() {
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
	
	public void broadcast(TextComponent... textList) {
		for(Warrior ffaplayer : this.playerList) {
			Messages.sendPlayerMessage(ffaplayer.getPlayer(), textList);
		}
	}
	
	public void broadcast(String text) {
		for(Warrior ffaplayer : this.playerList) {
			Messages.sendPlayerMessage(ffaplayer.getPlayer(), text);
		}
	}
	
	public void broadcastWithout(String text, Player... players) {
		List<Player> arr = Arrays.asList(players);
		for(Warrior ffaplayer : this.playerList) {
			if(!arr.contains(ffaplayer.getPlayer())) {
				Messages.sendPlayerMessage(ffaplayer.getPlayer(), text);
			}
		}
	}
	
	public void broadcastWithout(List<Player> players, TextComponent... text){
		for(Warrior ffaplayer : this.getPlayerList()) {
			if(!players.contains(ffaplayer.getPlayer())){
				Messages.sendPlayerMessage(ffaplayer.getPlayer(), text);
			}
		}
	}
}