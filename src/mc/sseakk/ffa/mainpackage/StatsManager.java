package mc.sseakk.ffa.mainpackage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.bukkit.entity.Player;

import mc.sseakk.ffa.game.FFAPlayer;
import mc.sseakk.ffa.game.Stats;
import mc.sseakk.ffa.util.Messages;

public class StatsManager {
	private FileManager fm;
	private ArrayList<Stats> gameStats;
	
	public StatsManager(){
		this.fm = FFA.getFileManager();
		this.gameStats = new ArrayList<Stats>();
	}
	
	public void addStats(Stats stats) {
		this.gameStats.add(stats);
	}
	
	public void saveStats() {
		Messages.sendConsoleMessage("Guardando estadisticas");
		for(Stats stats : this.gameStats) {
			Player player = stats.getFplayer().getPlayer();
			fm.createFile("\\stats", player.getName());
			
			BufferedWriter writer = fm.getBufferedWriter();
			
			try {
				writer.write("nombre="+player.getName());
				writer.newLine();
				
				writer.write("kills="+stats.getKills());
				writer.newLine();
				
				writer.write("muertes="+stats.getDeaths());
				writer.newLine();
				
				writer.write("asistencias="+stats.getAssists());
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
	
	public Stats loadPlayerStats(FFAPlayer player) {
		File folder = fm.getFolder("\\stats");
		for(File file : folder.listFiles()) {
			Scanner scn;
			try {
				scn = new Scanner(file);
				if(scn.nextLine().replaceFirst("nombre=", "").equals(player.getPlayer().getName())) {
					Stats stats = new Stats(player);
					while(scn.hasNextLine()) {
						String line = scn.nextLine(),
							   value = null;
						
						if(line.startsWith("kills")) {
							value = line.replaceFirst("kills=", "");
							stats.setKills(Integer.valueOf(value));
						}
						
						if(line.startsWith("death")) {
							value = line.replaceFirst("death=", "");
							stats.setDeaths(Integer.valueOf(value));
						}
						
						if(line.startsWith("asistencias")) {
							value = line.replaceFirst("asistencias=", "");
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
							stats.setMaxDamageGiven(Integer.valueOf(value));
						}

						if(line.startsWith("maxDamageTaken")) {
							value = line.replaceFirst("maxDamageTaken=", "");
							stats.setMaxDamageTaken(Integer.valueOf(value));
						}
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
}