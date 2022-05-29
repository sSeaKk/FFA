package mc.sseakk.ffa.game.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import mc.sseakk.ffa.game.Arena;
import mc.sseakk.ffa.game.player.FFAPlayer;

public class PlayerKillDeathEvent extends Event{
	private static final HandlerList handlers = new HandlerList();
	
	public enum DeathCause{
		KILLED,
		FALL,
		ENDERPEARL,
		VOID;
	}
	
	private Arena arena;
	private DeathCause cause;
	private FFAPlayer killer,
					  killed,
					  assister;
	
	public PlayerKillDeathEvent(Arena arena, DeathCause cause, FFAPlayer killer, FFAPlayer killed, FFAPlayer assister) {
		this.arena = arena;
		this.killer = killer;
		this.killed = killed;
		this.assister = assister;
		this.cause = cause;
	}
	
	public FFAPlayer getKiller() {
		return this.killer;
	}
	
	public FFAPlayer getKilled() {
		return this.killed;
	}
	
	public FFAPlayer getAssister() {
		return this.assister;
	}
	
	public Arena getArena() {
		return this.arena;
	}
	
	public DeathCause getCause() {
		return this.cause;
	}
	
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
}