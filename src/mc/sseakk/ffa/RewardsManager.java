package mc.sseakk.ffa;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

import org.bukkit.Sound;

import mc.sseakk.ffa.game.Profile;
import mc.sseakk.ffa.game.events.WarriorKillStreakEvent.KillStreakType;
import mc.sseakk.ffa.reward.Reward;
import mc.sseakk.ffa.reward.Reward.RewardRarity;
import mc.sseakk.ffa.reward.rewards.KillStreakSound;
import mc.sseakk.ffa.reward.rewards.Title;

public class RewardsManager {
	private static ArrayList<Reward> rewardList;
	private static FileManager fm;
	
	public RewardsManager() {
		fm = FFA.getFileManager();
		rewardList = new ArrayList<Reward>();
		initTitles();
		initKSSounds();
	}
	
	public static void createTitle(String title, RewardRarity rarity, int level) {
		rewardList.add(new Title(title, rarity, level));
	}
	
	public static void createKSSound(Sound sound, RewardRarity rarity, int level, KillStreakType type) {
		rewardList.add(new KillStreakSound(sound, rarity, level, type));
	}
	
	public static void initTitles() {
		createTitle("Novato", RewardRarity.COMMON, 0);
		createTitle("Peleador", RewardRarity.COMMON, 0);
		createTitle("Asesino", RewardRarity.COMMON, 0);
		createTitle("Invencible", RewardRarity.COMMON, 2);
		createTitle("Promesa", RewardRarity.RARE, 5);
		createTitle("El Pichula", RewardRarity.COMMON, 0);
	}
	
	public static void initKSSounds() {
		createKSSound(Sound.SILVERFISH_KILL, RewardRarity.COMMON, 0, KillStreakType.fiveKS);
		createKSSound(Sound.BLAZE_DEATH, RewardRarity.COMMON, 0, KillStreakType.tenKS);
		createKSSound(Sound.GHAST_SCREAM, RewardRarity.COMMON, 0, KillStreakType.fifthteenKS);
		createKSSound(Sound.ENDERMAN_DEATH, RewardRarity.COMMON, 0, KillStreakType.twentyKS);
		createKSSound(Sound.WITHER_DEATH, RewardRarity.COMMON, 0, KillStreakType.twentyfiveKS);
		createKSSound(Sound.ENDERDRAGON_GROWL, RewardRarity.COMMON, 0, KillStreakType.thirtyKS);
	}
	
	public static ArrayList<Reward> getRewardList(){
		return rewardList;
	}
	
	public Reward getReward(int id) {
		for(Reward reward : rewardList) {
			if(reward.getID() == id) {
				return reward;
			}
		}
		
		return null;
	}
	
	public Reward getReward(String rewardName) {
		for(Reward reward : rewardList) {
			if(reward instanceof Title) {
				if(((Title) reward).getText().equalsIgnoreCase(rewardName)) {
					return reward;
				}
			}
			
			if(reward instanceof KillStreakSound) {
				if(((KillStreakSound) reward).getName().equalsIgnoreCase(rewardName)) {
					return reward;
				}
			}
		}
		
		return null;
	}
	
	public void assignPlayerRewards(Profile player) {
		for(Reward reward : rewardList) {
			if(player.getLevel() == reward.getLevelToUnlock() && !player.getPlayerRewards().contains(reward)) {
				System.out.println("asignando " + reward.getType().name());
				player.addReward(reward);
			}
		}
	}
	
	public boolean loadRewards(Profile profile) {
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
						String line = scn.nextLine();
						
						if(line.startsWith("Rewards:")) {
							if(scn.hasNextLine()) {
								while(scn.hasNextInt()) {
									profile.addReward(FFA.getRewardsManager().getReward(scn.nextInt()));
									assignPlayerRewards(profile);
								} 
							}
						}
					}
					
					scn.close();
					return true;
				}
			} catch (FileNotFoundException e) {e.printStackTrace();}
		}
		
		assignPlayerRewards(profile);
		return false;
	}
}