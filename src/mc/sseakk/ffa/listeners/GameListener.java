package mc.sseakk.ffa.listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import mc.sseakk.ffa.game.FFAPlayer;
import mc.sseakk.ffa.game.Stats;
import mc.sseakk.ffa.mainpackage.ArenasManager;
import mc.sseakk.ffa.mainpackage.FFA;
import mc.sseakk.ffa.mainpackage.StatsManager;
import mc.sseakk.ffa.util.Messages;
import mc.sseakk.ffa.util.TimeUtil;

public class GameListener implements Listener{
	private ArenasManager am = FFA.getArenasManager();
	private StatsManager sm = FFA.getStatsManager();
	
	private Map<Player, DamageCause> lastDamage = new HashMap<Player, DamageCause>();
	
	private ArrayList<Player> enderPearlThrowers = new ArrayList<Player>();
	private Map<Player, Long> enderPearlDeathCooldown = new HashMap<Player, Long>();
	
	private Map<Player, Player> lastDamager = new HashMap<Player, Player>();
	private Map<Player, Long> lastDamagerCooldown = new HashMap<Player, Long>();
	
	private Map<Player, Player> assister = new HashMap<Player, Player>();
	private Map<Player, Long> assisterCooldown = new HashMap<Player, Long>();
	
	private Map<Entry<Player, Player>, Double> damagePerPlayer = new HashMap<Entry<Player, Player>, Double>();
	
	@EventHandler
	public void onKillDeath(EntityDeathEvent event) {
		if(event.getEntityType().equals(EntityType.PLAYER) && event.getEntity().getKiller() != null) {
			Player playerKilled = (Player) event.getEntity(),
				   playerKiller = event.getEntity().getKiller();
			
			if(am.getPlayerArena(playerKilled.getName()) != null) {
				FFAPlayer ffaPlayerKilled = am.getPlayerArena(playerKilled.getName()).getFFAPlayer(playerKilled.getName()),
						  ffaPlayerKiller = am.getPlayerArena(playerKiller.getName()).getFFAPlayer(playerKiller.getName());
				
				Stats statsPlayerKilled = sm.getStats(playerKilled.getName()),
					  statsPlayerKiller = sm.getStats(playerKiller.getName());
				
				for(FFAPlayer ffaplayer : am.getPlayerArena(playerKilled.getName()).getPlayerList()) {
					Player player = ffaplayer.getPlayer();
					if(ffaplayer != ffaPlayerKiller && ffaplayer != ffaPlayerKilled) {
						Messages.sendPlayerMessage(player, "&c" + ffaPlayerKilled.getPlayer().getName() + " &6fue asesinado por &c" + ffaPlayerKiller.getPlayer().getName());
					}
				}
				
				if(lastDamage.get(playerKilled) == DamageCause.ENTITY_ATTACK) {
					//Player Killed
					statsPlayerKilled.increaseDeaths();
					event.getDrops().clear();
					event.setDroppedExp(0);
					Messages.sendPlayerMessage(playerKilled, "&6Has sido asesinado por &c" + playerKiller.getName() +"&6 [+1 Muerte]");
					
					//Player Killer
					statsPlayerKiller.increaseKills();
					if(playerKiller.hasPotionEffect(PotionEffectType.REGENERATION)) { playerKiller.removePotionEffect(PotionEffectType.REGENERATION); }
					playerKiller.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 80, 2));
					Messages.sendPlayerMessage(playerKiller, "&6Has asesinado a &c" + playerKiller.getName() +"&6 [+1 Asesinatos]");
					return;
				}
				
			}
		}
		
		if(event.getEntityType().equals(EntityType.PLAYER) && am.getPlayerArena(((Player) event.getEntity()).getName()) != null) {
			Player player = (Player) event.getEntity();
			Player lastDamagerPlayer = null;
			Stats stats = sm.getStats(player.getName());
			ArrayList<FFAPlayer> playerList = am.getPlayerArena(player.getName()).getPlayerList();
			
			if(lastDamage.containsKey(player)) {
				DamageCause cause = lastDamage.get(player);
				
				if(cause == DamageCause.FALL) {
					String enderPearlDeathMessage = "&c" + player.getName() + " &6murio por una ender pearl";
					String fallDeathMessage = "&c" + player.getName() + " &6murio por caida";
					
					if(lastDamagerCooldown.containsKey(player)) {
						if(lastDamagerCooldown.get(player) > TimeUtil.currentTime()) {
							lastDamagerPlayer = lastDamager.get(player);
							enderPearlDeathMessage = "&c" + player.getName() + " &6murio por una ender pearl escapando de &c" + lastDamagerPlayer.getName();
							fallDeathMessage = "&c" + player.getName() + " &6murio por caida escapando de &c" + lastDamagerPlayer.getName();
						} else {
							lastDamager.remove(player);
							lastDamagerCooldown.remove(player);
						}
					}
					
					if(enderPearlThrowers.contains(player)) {
						if(enderPearlDeathCooldown.containsKey(player)) {
							if(enderPearlDeathCooldown.get(player) > TimeUtil.currentTime()) {
								Messages.sendAllPlayerArenaMessage(playerList, enderPearlDeathMessage);
								if(lastDamagerPlayer != null) {
									Stats statsLastDamagerPlayer = sm.getStats(lastDamagerPlayer.getName());
									Messages.sendPlayerMessage(lastDamagerPlayer, "&6+1 Asesinatos");
									statsLastDamagerPlayer.increaseKills();
								}
								
								Messages.sendPlayerMessage(player, "&6+1 Muerte");
								stats.increaseDeaths();
								event.getDrops().clear();
								event.setDroppedExp(0);
								return;
							}
						}
					}
					
					Messages.sendAllPlayerArenaMessage(playerList, fallDeathMessage);
					if(lastDamagerPlayer != null) {
						Stats statsLastDamagerPlayer = sm.getStats(lastDamagerPlayer.getName());
						Messages.sendPlayerMessage(lastDamagerPlayer, "&6+1 Asesinatos");
						statsLastDamagerPlayer.increaseKills();
					}
					Messages.sendPlayerMessage(player, "&6+1 Muerte");
					stats.increaseDeaths();
					event.getDrops().clear();
					event.setDroppedExp(0);
					return;
				}
				
			}
		}
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent event) {
		if(event.getEntityType().equals(EntityType.PLAYER) && event.getDamager().getType().equals(EntityType.PLAYER) && event.getCause().equals(DamageCause.ENTITY_ATTACK)) {
			Player playerDamaged = (Player) event.getEntity(),
				   playerDamager = (Player) event.getDamager();
			
			if(am.getPlayerArena(playerDamaged.getName()).equals(am.getPlayerArena(playerDamager.getName()))) {
				Messages.sendPlayerMessage(playerDamager, "&6Atacaste a &c" + playerDamaged.getName());
				Messages.sendPlayerMessage(playerDamaged, "&6Te esta atacando &c" + playerDamager.getName());
				
				if(lastDamagerCooldown.containsKey(playerDamaged)) {
					lastDamagerCooldown.remove(playerDamaged);
					
					if(lastDamager.get(playerDamaged) != playerDamager) {
						lastDamager.remove(playerDamaged);
						lastDamager.put(playerDamaged, playerDamager);
					}
					
					lastDamagerCooldown.put(playerDamaged, TimeUtil.lastDamagerCooldown());
				} else {
					lastDamager.put(playerDamaged, playerDamager);
					lastDamagerCooldown.put(playerDamaged, TimeUtil.lastDamagerCooldown());
				}
				
				//Assister setup
				if(!assister.containsKey(playerDamager)) {
					if(event.getFinalDamage() >= 8) {
						
					}
				}
			}
		}
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
	}
}