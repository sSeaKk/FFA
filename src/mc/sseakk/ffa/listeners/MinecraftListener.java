package mc.sseakk.ffa.listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import mc.sseakk.ffa.game.Arena;
import mc.sseakk.ffa.game.events.PlayerKillDeathEvent;
import mc.sseakk.ffa.game.events.PlayerKillDeathEvent.DeathCause;
import mc.sseakk.ffa.game.player.Warrior;
import mc.sseakk.ffa.game.player.Stats;
import mc.sseakk.ffa.mainpackage.ArenasManager;
import mc.sseakk.ffa.mainpackage.FFA;
import mc.sseakk.ffa.mainpackage.StatsManager;
import mc.sseakk.ffa.util.TimeUtil;

public class MinecraftListener implements Listener{
	private ArenasManager am = FFA.getArenasManager();
	private StatsManager sm = FFA.getStatsManager();
	
	private Map<Player, DamageCause> lastDamage = new HashMap<Player, DamageCause>();
	
	private ArrayList<Player> enderPearlThrowers = new ArrayList<Player>();
	private Map<Player, Long> enderPearlDeathCooldown = new HashMap<Player, Long>();
	
	private Map<Player, Player> lastDamager = new HashMap<Player, Player>();
	private Map<Player, Long> lastDamagerCooldown = new HashMap<Player, Long>();
	private Map<Player, Long> lastDamagerCooldown_voidCause = new HashMap<Player, Long>();
	
	private Map<Player, Assister> assisterMap = new HashMap<Player, Assister>();
	private Map<Assister, Long> assisterCooldown = new HashMap<Assister, Long>();
	
	//Assister Class
	private static class Assister{
		private Player player;
		private double damageGiven;
		private static ArrayList<Assister> posibleAssistersList = new ArrayList<Assister>();
		
		private Assister(Player player) {
			this.player = player;
			this.damageGiven = 0;
			posibleAssistersList.add(this);
		}
		
		private void increaseDamageGiven(double damage) {
			this.damageGiven += damage;
		}
		
		private String getName() {
			return player.getName();
		}
		
		private double getDamage() {
			return damageGiven;
		}
		
		private static Assister getPosibleAssister(Player player) {
			for(Assister a : posibleAssistersList) {
				if(a.getName().equals(player.getName())) {
					return a;
				}
			}
			
			return null;
		}
		
		private static void removePosibleAssister(Player player) {
			if(getPosibleAssister(player) != null) {
				posibleAssistersList.remove(getPosibleAssister(player));
			}
		}

		public Player getPlayer() {
			return this.player;
		}
	}
	
	@EventHandler
	public void onKillDeath(EntityDeathEvent event) {
		if(event.getEntityType().equals(EntityType.PLAYER) && event.getEntity().getKiller() != null) {
			Player playerKilled = (Player) event.getEntity(),
				   playerKiller = event.getEntity().getKiller(),
				   playerAssister = null;
			
				   try {
					   playerAssister = assisterMap.get(playerKilled).getPlayer();
				   } catch(NullPointerException e){}
				   
			Arena arena = am.getPlayerArena(playerKiller.getName());
			
			if(am.getPlayerArena(playerKilled.getName()) != null) {
				event.getDrops().clear();
				event.setDroppedExp(0);
				
				Warrior ffaPlayerKilled = arena.getFFAPlayer(playerKilled.getName()),
						  ffaPlayerKiller = arena.getFFAPlayer(playerKiller.getName()),
						  ffaPlayerAssister = null;
				
				Assister assister = assisterMap.get(playerKilled);
				
				if(lastDamage.get(playerKilled) == DamageCause.FALL) {
					//Player Assist
					if(playerKiller != playerAssister) {
						if(assisterCooldown.get(assister) > TimeUtil.currentTime()) {
							ffaPlayerAssister = arena.getFFAPlayer(playerAssister.getName());
						}
					}
					
					FFA.getPluginManager().callEvent(new PlayerKillDeathEvent(arena, DeathCause.KILLED, ffaPlayerKiller, ffaPlayerKilled, ffaPlayerAssister));
				} else if(lastDamage.get(playerKilled) == DamageCause.FALL) {
					if(enderPearlThrowers.contains(playerKilled) && enderPearlDeathCooldown.containsKey(playerKilled) && enderPearlDeathCooldown.get(playerKilled) > TimeUtil.currentTime()) {
						FFA.getPluginManager().callEvent(new PlayerKillDeathEvent(arena, DeathCause.ENDERPEARL, ffaPlayerKiller, ffaPlayerKilled, ffaPlayerAssister));
					} else {
						FFA.getPluginManager().callEvent(new PlayerKillDeathEvent(arena, DeathCause.FALL, ffaPlayerKiller, ffaPlayerKilled, ffaPlayerAssister));
					}
				} else if(lastDamage.get(playerKilled) == DamageCause.VOID){
					FFA.getPluginManager().callEvent(new PlayerKillDeathEvent(arena, DeathCause.VOID, ffaPlayerKiller, ffaPlayerKilled, ffaPlayerAssister));
				}
			}
			
			return;
		}
		
		if(event.getEntityType().equals(EntityType.PLAYER) && am.getPlayerArena(((Player) event.getEntity()).getName()) != null) {
			event.getDrops().clear();
			event.setDroppedExp(0);
			DeathCause cause = null;
			
			Player player = (Player) event.getEntity();
			Player lastDamagerPlayer = null;
			Player playerAssister = null;
			
			Arena arena = am.getPlayerArena(player.getName());
			Warrior ffaPlayerAssister = null;
			
			try {
				   playerAssister = assisterMap.get(player).getPlayer();
			   } catch(NullPointerException e){}
			
			if(lastDamage.containsKey(player)) {
				DamageCause damageCause = lastDamage.get(player);
				
				if(damageCause == DamageCause.FALL) {
					cause = DeathCause.FALL;
					
					if(lastDamagerCooldown.containsKey(player)) {
						if(lastDamagerCooldown.get(player) > TimeUtil.currentTime()) {
							lastDamagerPlayer = lastDamager.get(player);
						} else {
							lastDamager.remove(player);
							lastDamagerCooldown.remove(player);
							lastDamagerCooldown_voidCause.remove(player);
						}
					}
					
					if(lastDamagerPlayer != playerAssister) {
						Assister assister = assisterMap.get(player);
						if(assisterCooldown.get(assister) > TimeUtil.currentTime()) {
							ffaPlayerAssister = arena.getFFAPlayer(assister.getPlayer().getName());
						}
					}
					
					if(enderPearlThrowers.contains(player)) {
						if(enderPearlDeathCooldown.containsKey(player)) {
							if(enderPearlDeathCooldown.get(player) > TimeUtil.currentTime()) {
								cause = DeathCause.ENDERPEARL;
							}
						}
					}
					
				} if(damageCause == DamageCause.VOID) {
					cause = DeathCause.VOID;
					
					if(lastDamagerCooldown_voidCause.containsKey(player)) {
						if(lastDamagerCooldown_voidCause.get(player) > TimeUtil.currentTime()) {
							lastDamagerPlayer = lastDamager.get(player);
						} else {
							lastDamager.remove(player);
							lastDamagerCooldown.remove(player);
							lastDamagerCooldown_voidCause.remove(player);
						}
					}
				}
			}
			
			FFA.getPluginManager().callEvent(new PlayerKillDeathEvent(arena, cause, arena.getFFAPlayer(lastDamagerPlayer), arena.getFFAPlayer(player.getName()), ffaPlayerAssister));
			return;
		}
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent event) {
		if(event.getEntityType().equals(EntityType.PLAYER) && event.getDamager().getType().equals(EntityType.PLAYER) && event.getCause().equals(DamageCause.ENTITY_ATTACK)) {
			Player playerDamaged = (Player) event.getEntity(),
				   playerDamager = (Player) event.getDamager();
			
			Stats damagerStats = sm.getFromStatsList(playerDamager),
				  damagedStats = sm.getFromStatsList(playerDamaged);
			
			damagerStats.increaseDamageGiven(event.getFinalDamage());
			damagedStats.increaseDamageTaken(event.getFinalDamage());
			
			if(am.getPlayerArena(playerDamaged.getName()).equals(am.getPlayerArena(playerDamager.getName()))) {
				
				if(lastDamagerCooldown.containsKey(playerDamaged)) {
					lastDamagerCooldown.remove(playerDamaged);
					lastDamagerCooldown_voidCause.remove(playerDamaged);
					
					if(lastDamager.get(playerDamaged) != playerDamager) {
						lastDamager.remove(playerDamaged);
						lastDamager.put(playerDamaged, playerDamager);
					}
					
					lastDamagerCooldown.put(playerDamaged, TimeUtil.lastDamagerCooldown());
					lastDamagerCooldown_voidCause.put(playerDamaged, TimeUtil.lastDamagerCooldown_voidCause());
				} else {
					lastDamager.put(playerDamaged, playerDamager);
					lastDamagerCooldown.put(playerDamaged, TimeUtil.lastDamagerCooldown());
					lastDamagerCooldown_voidCause.put(playerDamaged, TimeUtil.lastDamagerCooldown_voidCause());
				}
				
				//Assister setup
				if(!assisterMap.containsKey(playerDamaged)) {
					Assister assister;
					
					if(Assister.getPosibleAssister(playerDamager) != null) {	
						assister = Assister.getPosibleAssister(playerDamager);
					} else {
						assister = new Assister(playerDamager);
					}
					
					if(!(assister.getDamage() > 10.5)) {
						assister.increaseDamageGiven(event.getFinalDamage());
						return;
					}
					
					
					assisterMap.put(playerDamaged, assister);
					assisterCooldown.put(assister, TimeUtil.assistrerCooldown());
					Assister.removePosibleAssister(playerDamager);
				} else {
					Assister assister = assisterMap.get(playerDamaged);
					
					if(assister.getName() != playerDamager.getName()) {
						assister = Assister.getPosibleAssister(playerDamager);
						
						if(assister == null) {
							assister = new Assister(playerDamager);
						}
						
						if(!(assister.getDamage() > 10.5)) {
							assister.increaseDamageGiven(event.getFinalDamage());
							return;
						}
						assisterMap.remove(playerDamaged);
						
						Assister.removePosibleAssister(playerDamager);
						assisterMap.put(playerDamaged, assister);
						assisterCooldown.put(assister, TimeUtil.assistrerCooldown());
						return;
					}
					
					assisterCooldown.remove(assister);
					assisterCooldown.put(assister, TimeUtil.assistrerCooldown());
				}
			}
		}
		return;
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			
			if(lastDamage.containsKey(player)) {
				lastDamage.remove(player);
			}
			
			lastDamage.put(player, event.getCause());
		}
		return;
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		
		if(player.getItemInHand().getType() == Material.ENDER_PEARL && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
			if(!enderPearlThrowers.contains(player)) {
				enderPearlThrowers.add(player);
				enderPearlDeathCooldown.put(player, TimeUtil.enderPearlDeathCooldown());
			} else {
				enderPearlDeathCooldown.remove(player);
				enderPearlDeathCooldown.put(player, TimeUtil.enderPearlDeathCooldown());
			}
		} else {
			if(enderPearlThrowers.contains(player)) {
				enderPearlThrowers.remove(player);
				if(enderPearlDeathCooldown.containsKey(player)) {
					enderPearlDeathCooldown.remove(player);
				}
			}
		}
		
		return;
	}
}