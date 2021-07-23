package mc.sseakk.ffa.mainpackage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.bukkit.Location;
import org.bukkit.World;

import mc.sseakk.ffa.game.Arena;
import mc.sseakk.ffa.game.ArenaStatus;
import mc.sseakk.ffa.game.FFAPlayer;
import mc.sseakk.ffa.util.Messages;
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
			fm.createFile("\\arenas", arena.getName());
			
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
					
					writer.write("spawn:");
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
	
	public void loadArenas() {
		File folder = fm.getFolder("\\arenas");
		File[] files = folder.listFiles();
		
		for(File file : files) {
			try {
				Scanner scn = new Scanner(file);
				boolean hasSpawn = false;
				Arena arena = new Arena();
				
				World world = null;
				double x = 0, y = 0, z = 0;
				float yaw = 0, pitch = 0;
				
				while(scn.hasNextLine()) {
					 String line = scn.nextLine(),
							value = null;
					 
					 if(line.startsWith("nombre")) {
						 value = line.replaceFirst("nombre=", "");
						 arena.setName(value);
					 }
					 
					 if(line.startsWith("estado")) {
						 value = line.replaceFirst("estado=", "");
						 
						 if(value.equalsIgnoreCase("activado") || value.equalsIgnoreCase("habilitado") || value.equalsIgnoreCase("on")) {
							 arena.setStatus(ArenaStatus.ENABLED);
						 } else {
							 arena.setStatus(ArenaStatus.DISABLED);
						 }
					 }
					 
					 if(line.startsWith("spawn:") || hasSpawn == true) {
						 hasSpawn = true;
						 
						 if(line.startsWith("x=")) {
							 value = line.replaceFirst("x=", "");
							 x = Double.valueOf(value);
						 }
						 
						 if(line.startsWith("y=")) {
							 value = line.replaceFirst("y=", "");
							 y = Double.valueOf(value);
						 }
						 
						 if(line.startsWith("z=")) {
							 value = line.replaceFirst("z=", "");
							 z = Double.valueOf(value);
						 }

						 if(line.startsWith("yaw=")) {
							 value = line.replaceFirst("yaw=", "");
							 yaw = Float.valueOf(value);
						 }
						 
						 if(line.startsWith("pitch=")) {
							 value = line.replaceFirst("pitch=", "");
							 pitch = Float.valueOf(value);
						 }

						 if(line.startsWith("world=")) {
							 value = line.replaceFirst("world=", "");
							 world = FFA.getInstance().getServer().getWorld(value);
						 }
					 }
				 }
				
				if(world != null) {
					Location spawn = new Location(world, x, y, z, yaw, pitch);
					arena.setSpawn(spawn);
				}
				
				addArena(arena);
			} catch (FileNotFoundException e) {
				Messages.warningMessage("Un archivo no fue encontrado, y esta causando errores\n" + e.getStackTrace());
			}
		}
	}
}