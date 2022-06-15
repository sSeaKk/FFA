package mc.sseakk.ffa.game;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.entity.Player;

import mc.sseakk.ffa.FFA;
import mc.sseakk.ffa.reward.Reward;
import mc.sseakk.ffa.reward.rewards.Title;
import mc.sseakk.ffa.util.TextUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public abstract class Profile{
	
	protected String name,
				   ore;
	protected int level,
			 	  rank;
	protected UUID uuid;
	protected Player player;
	protected TextComponent textProfile;
	protected Title title;
	protected ArrayList<Reward> playerRewards;
	
	public Profile(Player player) {
		this.player = player;
		this.playerRewards = new ArrayList<Reward>();
		this.uuid = this.player.getUniqueId();
		FFA.getRewardsManager().loadRewards(this);
		this.name = player.getName();
		this.textProfile = new TextComponent(TextUtil.colorText("&c"+name+"&r"));
		this.level = 0;
		this.title = null;
		
		if(!FFA.getWarriorManager().loadProfile(this)) {
			FFA.getRewardsManager().assignPlayerRewards(this);
			setTitle(1);
		}
	}
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Title getTitle() {
		return title;
	}

	public String getTitleText() {
		return title.getText();
	}
	
	public abstract void setTitle(int titleID);
	
	public String getOre() {
		return ore;
	}


	public void setOre(String ore) {
		this.ore = ore;
	}


	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}
	
	public TextComponent getText() {
		return this.textProfile;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public void addReward(Reward reward) {
		this.playerRewards.add(reward);
	}
	
	public ArrayList<Reward> getPlayerRewards(){
		return this.playerRewards;
	}
	
	public void resetHoverEvent() {
		this.textProfile.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(name + "\n")
				.color(ChatColor.AQUA).append(this.title.getText()).color(ChatColor.GOLD).italic(true)
				.create()));
	}
	
	public UUID getUUID() {
		return this.uuid;
	}
}
