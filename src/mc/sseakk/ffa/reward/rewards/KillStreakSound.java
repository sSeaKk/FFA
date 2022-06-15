package mc.sseakk.ffa.reward.rewards;

import org.bukkit.Sound;

import mc.sseakk.ffa.RewardsManager;
import mc.sseakk.ffa.reward.Reward;

public class KillStreakSound extends Reward{
	private String name;
	private Sound sound;
	
	public KillStreakSound(Sound sound, RewardRarity rarity, int levelToUnlock) {
		super(RewardType.KILLSTREAKSOUND, rarity, levelToUnlock, RewardsManager.getRewardList().size()+1);
		this.sound = sound;
		this.name = this.sound.name();
	}
	
	public Sound getSound() {
		return this.sound;
	}
	
	public String getName() {
		return this.name;
	}
}