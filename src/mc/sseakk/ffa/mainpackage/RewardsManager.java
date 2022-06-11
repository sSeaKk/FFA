package mc.sseakk.ffa.mainpackage;

import java.util.ArrayList;

import mc.sseakk.ffa.game.warrior.Profile;
import mc.sseakk.ffa.reward.Reward;
import mc.sseakk.ffa.reward.Reward.RewardRarity;
import mc.sseakk.ffa.reward.rewards.Title;

public class RewardsManager {
	private static ArrayList<Reward> rewardList;
	
	public RewardsManager() {
		rewardList = new ArrayList<Reward>();
		createTitles();
	}
	
	public static void createTitles() {
		createTitle("Novato", RewardRarity.COMMON, 0);
		createTitle("Peleador", RewardRarity.COMMON, 0);
		createTitle("Asesino", RewardRarity.COMMON, 0);
		createTitle("Promesa", RewardRarity.COMMON, 2);
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
	
	public void assignPlayerRewards(Profile warrior) {
		for(Reward reward : rewardList) {
			if(warrior.getLevel() == reward.getLevelToUnlock()) {
				warrior.addReward(reward);
			}
		}
	}
}
