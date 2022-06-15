package mc.sseakk.ffa;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

import mc.sseakk.ffa.game.Profile;
import mc.sseakk.ffa.reward.Reward;
import mc.sseakk.ffa.reward.Reward.RewardRarity;
import mc.sseakk.ffa.reward.rewards.Title;

public class RewardsManager {
	private static ArrayList<Reward> rewardList;
	private static FileManager fm;
	
	public RewardsManager() {
		fm = FFA.getFileManager();
		rewardList = new ArrayList<Reward>();
		createTitles();
	}
	
	public static void createTitles() {
		createTitle("Novato", RewardRarity.COMMON, 0);
		createTitle("Peleador", RewardRarity.COMMON, 0);
		createTitle("Asesino", RewardRarity.COMMON, 0);
		createTitle("Invencible", RewardRarity.COMMON, 2);
		createTitle("Promesa", RewardRarity.RARE, 5);
	}
	
	public static void createTitle(String title, RewardRarity rarity, int level) {
		rewardList.add(new Title(title, rarity, level));
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
		}
		
		return null;
	}
	
	public void assignPlayerRewards(Profile player) {
		for(Reward reward : rewardList) {
			if(player.getLevel() == reward.getLevelToUnlock()) {
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
								} 
							} else {
								assignPlayerRewards(profile);
							}
						}
					}
					
					scn.close();
					return true;
				}
			} catch (FileNotFoundException e) {e.printStackTrace();}
		}
		return false;
	}
}
