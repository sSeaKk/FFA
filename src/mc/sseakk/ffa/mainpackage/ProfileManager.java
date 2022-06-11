package mc.sseakk.ffa.mainpackage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

import org.bukkit.Bukkit;

import mc.sseakk.ffa.game.warrior.Warrior;
import mc.sseakk.ffa.reward.Reward;
import mc.sseakk.ffa.util.Messages;

public class ProfileManager {
	private static FileManager fm = FFA.getFileManager();
	private static ArrayList<Warrior> gameStats = new ArrayList<Warrior>();
	
	public static void addToStatsList(Warrior player) {
		gameStats.add(player);
	}
	
	public static Warrior get(String playerName) {
		for(Warrior fp : gameStats) {
			if(fp.getName().equals(playerName)) {
				return fp;
			}
		}
		
		return new Warrior(Bukkit.getPlayer(playerName));
	}
	
	public void saveAllStats() {
		Messages.infoMessage("Guardando estadisticas");
		for(Warrior player : gameStats) {
			player.saveActualStats();
			fm.createFile("\\profile", player.getPlayer().getUniqueId().toString());
			
			BufferedWriter writer = fm.getBufferedWriter();
			
			try {
				writer.write("name="+player.getName());
				writer.newLine();
				
				writer.write("level="+player.getLevel());
				writer.newLine();
				
				writer.newLine();
				writer.write("Stats:");
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
				
				writer.write("maxDamageTaken="+player.getMaxDamageTaken());
				writer.newLine();
				
				writer.newLine();
				writer.write("Rewards:");
				writer.newLine();
				
				for(Reward reward : player.getPlayerRewards()) {
					writer.write(reward.getID()+" ");
				}
				
				writer.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static boolean load(Warrior warrior) {
		File folder = fm.getFolder("\\profile");
		
		if(folder == null) {
			fm.createFolder("\\profile");
			folder = fm.getFolder("\\profile");
		}
		
		for(File file : folder.listFiles()) {
			try (Scanner scn = new Scanner(file);){
				UUID uuid = UUID.fromString(file.getName().replace(".txt", ""));
				if(warrior.getUUID().equals(uuid)) {
					while(scn.hasNextLine()) {
						String line = scn.nextLine(),
							   value = null;
						
						if(line.startsWith("level")) {
							value = line.replaceFirst("level=", "");
							warrior.setKills(Integer.valueOf(value));
						}
						
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
						
						if(line.startsWith("Rewards:")) {
							if(scn.hasNextLine()) {
								while(scn.hasNextInt()) {
									warrior.addReward(FFA.getRewardsManager().getReward(scn.nextInt()));
								}
							}
						}
					}
					
					scn.close();
					warrior.calculateRatios();
					addToStatsList(warrior);
					return true;
				}
			} catch (FileNotFoundException e) {e.printStackTrace();}
		}
		return false;
	}
	
	public ArrayList<Warrior> getWarriorList(){
		return this.getWarriorList();
	}
}