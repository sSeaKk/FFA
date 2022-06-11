package mc.sseakk.ffa.reward.rewards;

import mc.sseakk.ffa.mainpackage.RewardsManager;
import mc.sseakk.ffa.reward.Reward;

public class Title extends Reward{
	private String text;
	
	public Title(String text, RewardRarity rarity, int level) {
		super(RewardType.TITLE, rarity, level, RewardsManager.getRewardList().size()+1);
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
}
