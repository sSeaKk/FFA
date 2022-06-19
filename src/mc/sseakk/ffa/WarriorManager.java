package mc.sseakk.ffa;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

import mc.sseakk.ffa.game.Profile;
import mc.sseakk.ffa.game.Warrior;
import mc.sseakk.ffa.reward.Reward;
import mc.sseakk.ffa.reward.rewards.KillStreakSound;
import mc.sseakk.ffa.util.Messages;

public class WarriorManager {
	private static FileManager fm = FFA.getFileManager();
	private static ArrayList<Warrior> warriorLoadedList = new ArrayList<Warrior>();
	
	public static void addToStatsList(Warrior player) {
		warriorLoadedList.add(player);
	}
	
	public Warrior get(String playerName) {
		for(Warrior fp : warriorLoadedList) {
			if(fp.getName().equals(playerName)) {
				return fp;
			}
		}
		
		return null;
	}
	
	public Warrior get(UUID uuid) {
		for(Warrior warrior : warriorLoadedList) {
			if(warrior.getUUID().equals(uuid)) {
				return warrior;
			}
		}
		
		return null;
	}
	
	public void saveAllStats() {
		Messages.infoMessage("Guardando estadisticas");
		for(Warrior player : warriorLoadedList) {
			player.saveActualStats();
			fm.createFile("\\stats", player.getPlayer().getUniqueId().toString());
			
			BufferedWriter writer = fm.getBufferedWriter();
			
			try {
				writer.write("name="+player.getName());
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
				
				writer.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean loadStats(Warrior warrior) {
		File folder = fm.getFolder("\\stats");
		
		if(isLoaded(warrior.getUUID())) {
			return true;
		}
		
		if(folder == null) {
			fm.createFolder("\\stats");
			folder = fm.getFolder("\\stats");
		}
		
		for(File file : folder.listFiles()) {
			try (Scanner scn = new Scanner(file);){
				UUID uuid = UUID.fromString(file.getName().replace(".txt", ""));
				if(warrior.getUUID().equals(uuid)) {
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
					return true;
				}
			} catch (FileNotFoundException e) {e.printStackTrace();}
		}
		
		return false;
	}
	
	public boolean loadProfile(Profile profile) {
		File folder = fm.getFolder("\\profile");
		
		if(folder == null) {
			fm.createFolder("\\profile");
			folder = fm.getFolder("\\profile");
		}
		
		for(File file : folder.listFiles()) {
			try (Scanner scn = new Scanner(file);){
				UUID uuid = UUID.fromString(file.getName().replace(".txt", ""));
				if(profile.getUUID().equals(uuid)) {
					while(scn.hasNextLine()) {
						String line = scn.nextLine(),
							   value = null;
						
						if(line.startsWith("level")) {
							value = line.replaceFirst("level=", "");
							profile.setLevel(Integer.valueOf(value));
						}
						
						if(line.startsWith("titleID")) {
							value = line.replaceFirst("titleID=", "");
							profile.setTitle(Integer.valueOf(value));
						}
					}
					
					scn.close();
					return true;
				}
			} catch (FileNotFoundException e) {e.printStackTrace();}
		}
		return false;
	}
	
	public void saveAllProfiles() {
		Messages.infoMessage("Guardando prefiles");
		for(Warrior profile : warriorLoadedList) {
			saveProfile(profile);
		}
	}
	
	public void saveProfile(Profile profile) {
		fm.createFile("\\profile", profile.getUUID().toString());
		
		BufferedWriter writer = fm.getBufferedWriter();
		
		try {
			writer.write("name="+profile.getName());
			writer.newLine();
			
			writer.write("level="+profile.getLevel());
			writer.newLine();
			
			writer.write("titleID="+profile.getTitle().getID());
			writer.newLine();
			
			for(int i=5; i<30; i++) {
				if(i % 5 == 0) {
					System.out.println("iteracion " + i);
					KillStreakSound kss = profile.getKSSound(i);
					System.out.println("guardando " + kss.getSound().name() + " id:" + kss.getID());
					writer.write(kss.getKSType().name()+"="+kss.getID());
				}
			}
			
			writer.newLine();
			writer.write("Rewards:");
			
			if(!profile.getPlayerRewards().isEmpty()) {
				writer.newLine();
				for(Reward reward : profile.getPlayerRewards()) {
					writer.write(reward.getID() + " ");
				}
			}
			
			writer.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Warrior> getWarriorList(){
		return warriorLoadedList;
	}
	
	public boolean isLoaded(UUID uuid) {
		for(Warrior w : warriorLoadedList) {
			if(w.getUUID() == uuid) {
				return true;
			}
		}
		
		return false;
	}
}