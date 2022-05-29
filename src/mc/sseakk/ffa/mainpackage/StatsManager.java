package mc.sseakk.ffa.mainpackage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

import org.bukkit.OfflinePlayer;

import mc.sseakk.ffa.game.player.Stats;
import mc.sseakk.ffa.util.Messages;

public class StatsManager {
	private FileManager fm;
	private ArrayList<Stats> gameStats;
	
	public StatsManager(){
		this.fm = FFA.getFileManager();
		this.gameStats = new ArrayList<Stats>();
		loadAllStats();
	}
	
	public void addStats(Stats stats) {
		this.gameStats.add(stats);
	}
	
	public Stats getStats(UUID uuid) {
		for(Stats stats : this.gameStats){
			if(stats.getUuid().equals(uuid)) {
				return stats;
			}
		}
		
		return null;
	}
	
	public void saveAllStats() {
		Messages.infoMessage("Guardando estadisticas");
		for(Stats stats : this.gameStats) {
			stats.saveActualStats();
			OfflinePlayer player = stats.getOfflinePlayer();
			fm.createFile("\\stats", player.getUniqueId().toString());
			
			BufferedWriter writer = fm.getBufferedWriter();
			
			try {
				writer.write("name="+player.getName());
				writer.newLine();
				
				writer.write("kills="+stats.getKills());
				writer.newLine();
				
				writer.write("deaths="+stats.getDeaths());
				writer.newLine();
				
				writer.write("assists="+stats.getAssists());
				writer.newLine();
				
				writer.write("maxKillStreak="+stats.getMaxKillStreak());
				writer.newLine();
				
				writer.write("maxDeathStreak="+stats.getMaxDeathStreak());
				writer.newLine();
				
				writer.write("maxDamageGiven="+stats.getMaxDamageGiven());
				writer.newLine();
				
				writer.write("maxDamageTaken="+stats.getDamageTaken());
				
				writer.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void loadAllStats() {
		File folder = fm.getFolder("\\stats");
		
		if(folder == null) {
			fm.createFolder("\\stats");
			folder = fm.getFolder("\\stats");
		}
		
		for(File file : folder.listFiles()) {
			try (Scanner scn = new Scanner(file);){
				UUID uuid = UUID.fromString(file.getName().replace(".txt", ""));
				Stats stats = new Stats(uuid);
				while(scn.hasNextLine()) {
					String line = scn.nextLine(),
						   value = null;
					
					if(line.startsWith("kills")) {
						value = line.replaceFirst("kills=", "");
						stats.setKills(Integer.valueOf(value));
					}
					
					if(line.startsWith("deaths")) {
						value = line.replaceFirst("deaths=", "");
						stats.setDeaths(Integer.valueOf(value));
					}
					
					if(line.startsWith("assists")) {
						value = line.replaceFirst("assists=", "");
						stats.setAssists(Integer.valueOf(value));
					}
					if(line.startsWith("maxKillStreak")) {
						value = line.replaceFirst("maxKillStreak=", "");
						stats.setMaxKillStreak(Integer.valueOf(value));
					}
					
					if(line.startsWith("maxDeathStreak")) {
						value = line.replaceFirst("maxDeathStreak=", "");
						stats.setMaxDeathStreak(Integer.valueOf(value));
					}
					
					if(line.startsWith("maxDamageGiven")) {
						value = line.replaceFirst("maxDamageGiven=", "");
						stats.setMaxDamageGiven(Double.valueOf(value));
					}

					if(line.startsWith("maxDamageTaken")) {
						value = line.replaceFirst("maxDamageTaken=", "");
						stats.setMaxDamageTaken(Double.valueOf(value));
					}
				}
				
				scn.close();
				stats.calculateRatios();
				addStats(stats);
			} catch (FileNotFoundException e) {e.printStackTrace();}
		}
	}
}