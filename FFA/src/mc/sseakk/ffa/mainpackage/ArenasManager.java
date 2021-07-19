package mc.sseakk.ffa.mainpackage;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import mc.sseakk.ffa.game.Arena;
import mc.sseakk.ffa.game.ArenaStatus;
import mc.sseakk.ffa.game.FFAPlayer;
import mc.sseakk.ffa.util.Messages;

public class ArenasManager {
	private FileManager fm;
	
	private ArrayList<Arena> gameArenas;
	
	public ArenasManager() {
		this.fm = FFA.getFileManager();
	}
	
	public void addArena(Arena arena) {
		gameArenas.add(arena);
	}
	
	public Arena getArena(String arenaName) {
		for(int i=0; i<gameArenas.size(); i++) {
			if(gameArenas.get(i).getName().equals(arenaName)) {
				return gameArenas.get(i);
			}
		}
		return null;
	}
	
	public ArrayList<Arena> getArenas(){
		return this.gameArenas;
	}
	
	public Arena getPlayerArena(String playerName) {
		for(int i=0; i<gameArenas.size(); i++) {
			ArrayList<FFAPlayer> players = gameArenas.get(i).getPlayerList();
			for(int c=0; c<players.size();c++) {
				if(players.get(c).getPlayer().getName().equals(playerName)) {
					return gameArenas.get(i);
				}
			}
		}
		return null;
	}
	
	public void removeArena(String arenaName) {
		for(int i=0; i<gameArenas.size(); i++) {
			if(gameArenas.get(i).getName().equals(arenaName)) {
				gameArenas.remove(i);
			}
		}
	}
	
	public void loadArenas() {
		this.gameArenas = new ArrayList<Arena>();
		FileConfiguration arenas = fm.getArenas();
		
		if(arenas.contains("Arenas")) {
			for(String name : arenas.getConfigurationSection("Arenas").getKeys(false)) {
				
				Arena arena = new Arena(name);
				String path = "Arenas."+name;
				String status = arenas.getString(path+".status");
				
				if(arenas.contains(path+".spawn")) {
					arena.setSpawn(new Location(
							Bukkit.getWorld(arenas.getString(path+".spawn.world")),
							Double.valueOf(arenas.getString(path+".spawn.x")),
							Double.valueOf(arenas.getString(path+".spawn.y")),
							Double.valueOf(arenas.getString(path+".spawn.z")),
							Float.valueOf(arenas.getString(path+".spawn.yaw")),
							Float.valueOf(arenas.getString(path+".spawn.pitch"))
							));
				}
				
				if(status.equals("disabled")) {
					arena.setStatus(ArenaStatus.DISABLED);
				} else if(status.equals("enabled")){
					arena.setStatus(ArenaStatus.ENABLED);
				} else {
					Messages.warningMessage("The arena status of arena: "+name+" is invalid! The valid arguments are: enabled or disabled. Disabling arena.");
					arena = null;
					return;
				}
				
				gameArenas.add(arena);
			}
		}
	}
	
	public void saveArenas() {
		FileConfiguration arenas = fm.getArenas();
		for(Arena a : this.gameArenas) {
			Location spawn = a.getSpawn();
			String path = "Arenas."+a.getName();
			
			if(spawn != null) {
				arenas.set(path+".spawn.world", spawn.getWorld().getName());
				arenas.set(path+".spawn.x", spawn.getX());
				arenas.set(path+".spawn.y", spawn.getY());
				arenas.set(path+".spawn.z", spawn.getZ());
				arenas.set(path+".spawn.yaw", spawn.getYaw());
				arenas.set(path+".spawn.pitch", spawn.getPitch());
			}
			
			if(a.getStatus().equals(ArenaStatus.DISABLED)) {
				arenas.set(path+".status", "disabled");
			} else {
				arenas.set(path+".stauts", "enabled");
			}
			
		}
		
		fm.saveArenas();
	}
}
