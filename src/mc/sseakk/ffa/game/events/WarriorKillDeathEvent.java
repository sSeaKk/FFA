package mc.sseakk.ffa.game.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import mc.sseakk.ffa.game.Arena;
import mc.sseakk.ffa.game.warrior.Warrior;

public class WarriorKillDeathEvent extends Event{
	private static final HandlerList handlers = new HandlerList();
	
	public enum DeathCause{
		KILLED,
		FALL,
		ENDERPEARL,
		VOID;
	}
	
	private Arena arena;
	private DeathCause cause;
	private Warrior killer,
					killed,
				    assister;
	
	public WarriorKillDeathEvent(Arena arena, DeathCause cause, Warrior killer, Warrior killed, Warrior assister) {
		this.arena = arena;
		this.killer = killer;
		this.killed = killed;
		this.assister = assister;
		this.cause = cause;
	}
	
	public Warrior getKiller() {
		return this.killer;
	}
	
	public Warrior getKilled() {
		return this.killed;
	}
	
	public Warrior getAssister() {
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