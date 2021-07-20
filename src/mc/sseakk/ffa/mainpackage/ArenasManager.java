package mc.sseakk.ffa.mainpackage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Location;

import mc.sseakk.ffa.game.Arena;
import mc.sseakk.ffa.game.ArenaStatus;
import mc.sseakk.ffa.game.FFAPlayer;

public class ArenasManager {
	private FileManager fm;
	
	private ArrayList<Arena> gameArenas = null;
	
	public ArenasManager() {
		this.gameArenas = new ArrayList<Arena>();
		this.fm = FFA.getFileManager();
	}
	
	public void addArena(Arena arena) {
		gameArenas.add(arena);
	}
	
	public Arena getArena(String arenaName) {
		for(Arena arena : this.gameArenas) {
			if(arena.getName().equals(arenaName)) {
				return arena;
			}
		}
	  
		return null;
	}
	
	public ArrayList<Arena> getArenas(){
		return this.gameArenas;
	}
	
	public Arena getPlayerArena(String playerName) {
		for(Arena arena : this.gameArenas) {
			ArrayList<FFAPlayer> players = arena.getPlayerList();
			for(FFAPlayer player : players) {
				if(player.getPlayer().getName().equals(playerName)) {
					return arena;
				}
			}
		}
	  
		return null;
	}
	
	public void removeArena(String arenaName) {
		for(Arena arena : this.gameArenas) {
			if(arena.getName().equals(arenaName)) {
				gameArenas.remove(arena);
				fm.deleteFile("\\arenas\\"+arenaName+".txt");
			}
		}
	}
	
	public void saveArenas() {
		for(Arena arena : this.gameArenas) {
			File arenaFile = fm.createFile("\\arenas", arena.getName());
			
			BufferedWriter writer = fm.getBufferedWriter();
			
			try {
				writer.write("nombre="+arena.getName());
				writer.newLine();
				if(!arena.getStatus().equals(ArenaStatus.DISABLED)) {
					writer.write("estado=activado");
				} else {
					writer.write("estado=desactivado");
				}
				
				Location spawn = arena.getSpawn();
				if(spawn != null) {
					writer.newLine();
					writer.newLine();
					
					writer.write("Spawn:");
					writer.newLine();
					
					writer.write("x="+spawn.getX());
					writer.newLine();
					
					writer.write("y="+spawn.getY());
					writer.newLine();
					
					writer.write("z="+spawn.getZ());
					writer.newLine();
					
					writer.write("yaw="+spawn.getYaw());
					writer.newLine();
					
					writer.write("pitch="+spawn.getPitch());
					writer.newLine();
					
					writer.write("world="+spawn.getWorld().getName());
				}
				writer.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}