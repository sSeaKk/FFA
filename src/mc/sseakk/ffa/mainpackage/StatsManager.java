package mc.sseakk.ffa.mainpackage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import mc.sseakk.ffa.game.player.Warrior;
import mc.sseakk.ffa.util.Messages;

public class StatsManager {
	private static FileManager fm;
	private ArrayList<Warrior> gameStats = new ArrayList<Warrior>();
	
	public StatsManager(){
		fm = FFA.getFileManager();
	}
	
	public void addToStatsList(Warrior player) {
		this.gameStats.add(player);
	}
	
	public Warrior getFromStatsList(String playerName) {
		for(Warrior fp : this.gameStats) {
			if(fp.getName().equals(playerName)) {
				return fp;
			}
		}
		
		return null;
	}
	
	public void saveAllStats() {
		Messages.infoMessage("Guardando estadisticas");
		for(Warrior player : this.gameStats) {
			player.saveActualStats();
			fm.createFile("\\stats", player.getPlayer().getUniqueId().toString());
			
			BufferedWriter writer = fm.getBufferedWriter();
			
			try {
				writer.write("name="+player.getName());
				writer.newLine();
				
				writer.write("kills="+player.getKills());
				writer.newLine();
				
				writer.write("deaths="+player.getDeaths());
				writer.newLine();
				
				writer.write("assists="+player.getAssists());
				writer.newLine();
				
				writer.write("maxKillStreak="+player.getMaxKillStreak());
				writer.newLine();
				
				writer.write("maxDeathStreak="+player.getMaxDeathStreak());
				writer.newLine();
				
				writer.write("maxDamageGiven="+player.getMaxDamageGiven());
				writer.newLine();
				
				writer.write("maxDamageTaken="+player.getDamageTaken());
				
				writer.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void loadStats(UUID playerUUID) {
		File folder = fm.getFolder("\\stats");
		
		if(folder == null) {
			fm.createFolder("\\stats");
			folder = fm.getFolder("\\stats");
		}
		
		for(File file : folder.listFiles()) {
			try (Scanner scn = new Scanner(file);){
				UUID uuid = UUID.fromString(file.getName().replace(".txt", ""));
				System.out.println(playerUUID.toString());
				System.out.println(uuid.toString());
				if(playerUUID.equals(uuid)) {
					System.out.println("cargando stats de " + Bukkit.getPlayer(uuid).getName());
					Warrior warrior = new Warrior(FFA.getInstance().getServer().getPlayer(playerUUID));
					while(scn.hasNextLine()) {
						String line = scn.nextLine(),
							   value = null;
						
						if(line.startsWith("kills")) {
							value = line.replaceFirst("kills=", "");
							warrior.setKills(Integer.valueOf(value));
						}
						
						if(line.startsWith("deaths")) {
							value = line.replaceFirst("deaths=", "");
							warrior.setDeaths(Integer.valueOf(value));
						}
						
						if(line.startsWith("assists")) {
							value = line.replaceFirst("assists=", "");
							warrior.setAssists(Integer.valueOf(value));
						}
						if(line.startsWith("maxKillStreak")) {
							value = line.replaceFirst("maxKillStreak=", "");
							warrior.setMaxKillStreak(Integer.valueOf(value));
						}
						
						if(line.startsWith("maxDeathStreak")) {
							value = line.replaceFirst("maxDeathStreak=", "");
							warrior.setMaxDeathStreak(Integer.valueOf(value));
						}
						
						if(line.startsWith("maxDamageGiven")) {
							value = line.replaceFirst("maxDamageGiven=", "");
							warrior.setMaxDamageGiven(Double.valueOf(value));
						}

						if(line.startsWith("maxDamageTaken")) {
							value = line.replaceFirst("maxDamageTaken=", "");
							warrior.setMaxDamageTaken(Double.valueOf(value));
						}
					}
					
					scn.close();
					warrior.calculateRatios();
					addToStatsList(warrior);
				}
			} catch (FileNotFoundException e) {e.printStackTrace();}
		}
	}
}