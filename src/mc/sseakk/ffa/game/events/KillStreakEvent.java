package mc.sseakk.ffa.game.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import mc.sseakk.ffa.game.warrior.Warrior;

public class KillStreakEvent extends Event{
	private static final HandlerList handlers = new HandlerList();
	
	public enum KillStreakType{
		fiveKS,
		tenKS,
		fifthteenKS,
		twentyKS,
		twentyfiveKS,
		thirtyKS;
	}
	
	private Warrior ffaplayer;
	private Player player;
	private KillStreakType type;
	
	public KillStreakEvent(Warrior FFAPlayer, KillStreakType type) {
		this.ffaplayer = FFAPlayer;
		this.player = this.ffaplayer.getPlayer();
		this.type = type;
	}
	
	public KillStreakEvent(Player player, KillStreakType type) {
		this.ffaplayer = null;
		this.player = player;
		this.type = type;
	}
	
	public Warrior getFFAPlayer() {
		return this.ffaplayer;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public KillStreakType getType() {
		return this.type;
	}
	
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
}
