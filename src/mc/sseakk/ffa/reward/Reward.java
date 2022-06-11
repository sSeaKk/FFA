package mc.sseakk.ffa.reward;

import org.bukkit.ChatColor;

import mc.sseakk.ffa.util.TextUtil;

public class Reward {
	public enum RewardType{
		TITLE,
		KILLSTREAKSOUND;
	}
	
	public enum RewardRarity{
		COMMON,
		RARE,
		EPIC,
		LEGENDARY;
		
		public String getName() {
			if(this == COMMON) {
				return TextUtil.colorText("&7&lCOMUN");
			}
			
			if(this == RARE) {
				return TextUtil.colorText("&b&lRARO");
			}
			
			if(this == EPIC) {
				return TextUtil.colorText("&d&lEPICO");
			}
			
			if(this == LEGENDARY) {
				return TextUtil.colorText("&6&lLEGENDARIO");
			}
			
			return null;
		}
		
		public ChatColor getColor() {
			if(this == COMMON) {
				return ChatColor.GRAY;
			}
			
			if(this == RARE) {
				return ChatColor.AQUA;
			}
			
			if(this == EPIC) {
				return ChatColor.LIGHT_PURPLE;
			}
			
			if(this == LEGENDARY) {
				return ChatColor.GOLD;
			}
			
			return null;
		}
	}
	
	protected RewardType type;
	protected RewardRarity rarity;
	protected int levelToUnlock,
				  id;
	
	public Reward(RewardType type, RewardRarity rarity, int levelToUnlock, int id) {
		this.type = type;
		this.rarity = rarity;
		this.levelToUnlock = levelToUnlock;
		this.id = id;
	}

	public RewardType getType() {
		return this.type;
	}

	public RewardRarity getRarity() {
		return this.rarity;
	}

	public int getLevelToUnlock() {
		return this.levelToUnlock;
	}
	
	public int getID() {
		return this.id;
	}
}
