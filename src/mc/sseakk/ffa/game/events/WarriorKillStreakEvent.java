package mc.sseakk.ffa.game.events;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import mc.sseakk.ffa.game.Warrior;

public class WarriorKillStreakEvent extends Event{
	private static final HandlerList handlers = new HandlerList();
	
	public enum KillStreakType{
		fiveKS,
		tenKS,
		fifthteenKS,
		twentyKS,
		twentyfiveKS,
		thirtyKS;
		
		public int getKills() {
			if(this == fiveKS) {
				return 5;
			}
			
			if(this == tenKS) {
				return 10;
			}
			
			if(this == fifthteenKS) {
				return 15;
			}
			
			if(this == twentyKS) {
				return 20;
			}
			
			if(this == twentyfiveKS) {
				return 25;
			}
			
			if(this == thirtyKS) {
				return 30;
			}
			
			return -1;
		}
	}
	
	private Warrior warrior;
	private Player player;
	private KillStreakType type;
	
	public WarriorKillStreakEvent(Warrior warrior, KillStreakType type) {
		this.warrior = warrior;
		this.player = this.warrior.getPlayer();
		this.type = type;
	}
	
	public Warrior getWarrior() {
		return this.warrior;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public KillStreakType getType() {
		return this.type;
	}
	
	public Sound getSound() {
		return this.warrior.getKSSound(this.type).getSound();
	}
	
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
}
