package mc.sseakk.ffa.reward.rewards;

import org.bukkit.Sound;

import mc.sseakk.ffa.RewardsManager;
import mc.sseakk.ffa.game.events.WarriorKillStreakEvent.KillStreakType;
import mc.sseakk.ffa.reward.Reward;

public class KillStreakSound extends Reward{
	private String name;
	private Sound sound;
	private KillStreakType kstype;
	
	public KillStreakSound(Sound sound, RewardRarity rarity, int levelToUnlock, KillStreakType kstype) {
		super(RewardType.KILLSTREAKSOUND, rarity, levelToUnlock, RewardsManager.getRewardList().size()+1);
		this.sound = sound;
		this.name = this.sound.name();
		System.out.println(this.sound.name() + " | " + this.name);
		this.kstype = kstype;
	}
	
	public Sound getSound() {
		return this.sound;
	}
	
	public String getName() {
		return this.name;
	}
	
	public KillStreakType getKSType() {
		return this.kstype;
	}
}