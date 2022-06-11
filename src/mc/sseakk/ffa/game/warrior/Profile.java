package mc.sseakk.ffa.game.warrior;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import mc.sseakk.ffa.mainpackage.FFA;
import mc.sseakk.ffa.reward.Reward;
import mc.sseakk.ffa.util.TextUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class Profile{
	
	protected String name,
				   lema,
				   ore;
	
	protected int level,
			 	  rank;
	
	protected TextComponent playerProfile;
	
	protected ArrayList<Reward> playerRewards;
	
	public Profile(Player player) {
		this.playerRewards = new ArrayList<Reward>();
		this.name = player.getName();
		this.lema = "Novato";
		this.level = 0;
		
		FFA.getRewardsManager().assignPlayerRewards(this);				
		this.playerProfile = new TextComponent(TextUtil.colorText("&c"+name+"&r"));
		this.playerProfile.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(name + "\n").color(ChatColor.AQUA).append(lema).color(ChatColor.GOLD).create()));
	}
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getLema() {
		return lema;
	}


	public void setLema(String lema) {
		this.lema = lema;
	}


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
		return this.playerProfile;
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
}
