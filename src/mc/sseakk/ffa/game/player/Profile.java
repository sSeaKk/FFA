package mc.sseakk.ffa.game.player;

import org.bukkit.entity.Player;

import mc.sseakk.ffa.util.TextUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class Profile{
	
	private String name,
				   lema,
				   ore,
				   rank;
	private Player player;
	private TextComponent playerProfile;
	
	public Profile(FFAPlayer ffaplayer) {
		this.player = ffaplayer.getPlayer();
		this.lema = "Novato";
		this.name = player.getName();
		
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


	public String getRank() {
		return rank;
	}


	public void setRank(String rank) {
		this.rank = rank;
	}
	
	public TextComponent getText() {
		return this.playerProfile;
	}
}