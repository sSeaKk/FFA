package mc.sseakk.ffa.listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import mc.sseakk.ffa.game.Arena;
import mc.sseakk.ffa.game.FFAPlayer;
import mc.sseakk.ffa.game.Stats;
import mc.sseakk.ffa.game.events.KillStreakEvent;
import mc.sseakk.ffa.game.events.KillStreakEvent.KillStreakType;
import mc.sseakk.ffa.mainpackage.ArenasManager;
import mc.sseakk.ffa.mainpackage.FFA;
import mc.sseakk.ffa.mainpackage.StatsManager;
import mc.sseakk.ffa.util.Messages;
import mc.sseakk.ffa.util.SoundUtil;
import mc.sseakk.ffa.util.TimeUtil;

public class GameListener implements Listener{
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
			
			if(am.getPlayerArena(playerKilled.getName()) != null) {
				FFAPlayer ffaPlayerKilled = am.getPlayerArena(playerKilled.getName()).getFFAPlayer(playerKilled.getName()),
						  ffaPlayerKiller = am.getPlayerArena(playerKiller.getName()).getFFAPlayer(playerKiller.getName()),
						  ffaPlayerAssister = am.getPlayerArena(playerAssister.getName()).getFFAPlayer(playerAssister.getName());
				
				Stats statsPlayerKilled = ffaPlayerKilled.getStats(),
					  statsPlayerKiller = ffaPlayerKiller.getStats(),
					  statsPlayerAssister = ffaPlayerAssister.getStats();
				
				Assister assister = assisterMap.get(playerKilled);
				
				String killedMessage = "&6Has sido asesinado por &c" + playerKiller.getName() +"&6 [&c+1 &6Muerte]",
					   killerMessage = "&6Has asesinado a &c" + playerKilled.getName() +"&6 [&a+1 &6Asesinatos]",
					   globalMessage = "&c" + ffaPlayerKilled.getPlayer().getName() + " &6fue asesinado por &c" + ffaPlayerKiller.getPlayer().getName();
				
				if(lastDamage.get(playerKilled) == DamageCause.ENTITY_ATTACK) {
					//Player Assist
					if(playerKiller != playerAssister) {
						if(assisterCooldown.get(assister) > TimeUtil.currentTime()) {
							killedMessage = "&6Has sido asesinado por &c" + playerKiller.getName() + " &6con la ayuda de &c"+ playerAssister.getName() +"&6 [&c+1 &6Muerte]";
							killerMessage = "&6Has asesinado a &c" + playerKilled.getName() + " &6con la ayuda de &c"+ playerAssister.getName() +"&6 [&a+1 &6Asesinatos]";
							globalMessage = "&c" + playerKilled.getName() + " &6fue asesinado por &c" + playerKiller.getName() + " &6con la ayuda de &c" + playerAssister.getName();
							
							statsPlayerAssister.increaseAssists();
							Bukkit.getScheduler().runTaskLater(FFA.getInstance(), new Runnable() {
								@Override
								public void run() {
									Messages.sendPlayerMessage(assister.getPlayer(), "&b+1 &6Asistencia");
								}
								
							}, 1L);
							
						}
					}
					
					//Player Killed
					statsPlayerKilled.increaseDeaths();
					event.getDrops().clear();
					event.setDroppedExp(0);
					SoundUtil.deathSound(playerKilled);
					Messages.sendPlayerMessage(playerKilled, killedMessage);
					
					//Player Killer
					statsPlayerKiller.increaseKills();
					if(playerKiller.hasPotionEffect(PotionEffectType.REGENERATION)) { playerKiller.removePotionEffect(PotionEffectType.REGENERATION); }
					playerKiller.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 80, 2));
					SoundUtil.killSound(playerKiller);
					Messages.sendPlayerMessage(playerKiller, killerMessage);
					
					for(FFAPlayer ffaplayer : am.getPlayerArena(playerKilled.getName()).getPlayerList()) {
						Player player = ffaplayer.getPlayer();
						if(ffaplayer != ffaPlayerKiller && ffaplayer != ffaPlayerKilled) {
							Messages.sendPlayerMessage(player, globalMessage);
						}
					}
				}
			}
		}
		
		if(event.getEntityType().equals(EntityType.PLAYER) && am.getPlayerArena(((Player) event.getEntity()).getName()) != null) {

			Player player = (Player) event.getEntity();
			Player lastDamagerPlayer = null;
			Stats stats = sm.getStats(player.getUniqueId());
			Arena arena = am.getPlayerArena(player.getName());
			
			Player playerAssister = null;
			try {
				   playerAssister = assisterMap.get(player).getPlayer();
			   } catch(NullPointerException e){}
			
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
							lastDamagerCooldown_voidCause.remove(player);
						}
					}
					
					if(lastDamagerPlayer != playerAssister) {
						Assister assister = assisterMap.get(player);
						
						if(assisterCooldown.get(assister) > TimeUtil.currentTime()) {
							fallDeathMessage = "&c" + player.getName() + " &6murio por caida escapando de &c" + lastDamagerPlayer.getName() + "&6 con la ayuda de &c" + playerAssister.getName();
							enderPearlDeathMessage = "&c" + player.getName() + " &6murio por una ender pearl escapando de &c" + lastDamagerPlayer.getName() + "&6 con la ayuda de &c" + playerAssister.getName();
							sm.getStats(playerAssister.getUniqueId()).increaseAssists();
						}
						
						Bukkit.getScheduler().runTaskLater(FFA.getInstance(), new Runnable() {
							@Override
							public void run() {
								Messages.sendPlayerMessage(assister.getPlayer(), "&b+1 &6Asistencia");
							}
							
						}, 1L);
					}
					
					if(enderPearlThrowers.contains(player)) {
						if(enderPearlDeathCooldown.containsKey(player)) {
							if(enderPearlDeathCooldown.get(player) > TimeUtil.currentTime()) {
								arena.broadcast(enderPearlDeathMessage);
								if(lastDamagerPlayer != null) {
									Stats statsLastDamagerPlayer = sm.getStats(lastDamagerPlayer.getUniqueId());
									Messages.sendPlayerMessage(lastDamagerPlayer, "&a+1 &6Asesinatos");
									statsLastDamagerPlayer.increaseKills();
									SoundUtil.killSound(lastDamagerPlayer);
								}
								
								Messages.sendPlayerMessage(player, "&c+1 &6Muerte");
								stats.increaseDeaths();
								SoundUtil.deathSound(player);
								return;
							}
						}
					}
					
					arena.broadcast(fallDeathMessage);
					if(lastDamagerPlayer != null) {
						Stats statsLastDamagerPlayer = sm.getStats(lastDamagerPlayer.getUniqueId());
						Messages.sendPlayerMessage(lastDamagerPlayer, "&a+1 &6Asesinatos");
						statsLastDamagerPlayer.increaseKills();
					}
					Messages.sendPlayerMessage(player, "&c+1 &6Muerte");
					stats.increaseDeaths();
					SoundUtil.deathSound(player);
					event.getDrops().clear();
					event.setDroppedExp(0);
					return;
				} if(cause == DamageCause.VOID) {
					String voidDeathMessage = "&c" + player.getName() + " &6cayo al vacio";
					if(lastDamagerCooldown_voidCause.containsKey(player)) {
						if(lastDamagerCooldown_voidCause.get(player) > TimeUtil.currentTime()) {
							lastDamagerPlayer = lastDamager.get(player);
							voidDeathMessage = "&c" + player.getName() + " &6cayo al vacio escapando de &c" + lastDamagerPlayer.getName();
							
							Stats statsLastDamagerPlayer = sm.getStats(lastDamagerPlayer.getUniqueId());
							Messages.sendPlayerMessage(lastDamagerPlayer, "&a+1 &6Asesinatos");
							statsLastDamagerPlayer.increaseKills();
							SoundUtil.killSound(lastDamagerPlayer);
						} else {
							lastDamager.remove(player);
							lastDamagerCooldown.remove(player);
							lastDamagerCooldown_voidCause.remove(player);
						}
					}
					
					
					arena.broadcast(voidDeathMessage);
					stats.increaseDeaths();
					SoundUtil.deathSound(player);
					Messages.sendPlayerMessage(player, "&c+1 &6Muerte");
					event.getDrops().clear();
					event.setDroppedExp(0);
					return;
				}
			}
		}
		return;
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent event) {
		if(event.getEntityType().equals(EntityType.PLAYER) && event.getDamager().getType().equals(EntityType.PLAYER) && event.getCause().equals(DamageCause.ENTITY_ATTACK)) {
			Player playerDamaged = (Player) event.getEntity(),
				   playerDamager = (Player) event.getDamager();
			
			Stats damagerStats = sm.getStats(playerDamager.getUniqueId()),
				  damagedStats = sm.getStats(playerDamaged.getUniqueId());
			
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
	
	@EventHandler
	public void onKillStreak(KillStreakEvent event) {
		Arena arena = am.getPlayerArena(event.getPlayer().getName());
		Firework firework = (Firework) event.getPlayer().getWorld().spawnEntity(event.getPlayer().getLocation(), EntityType.FIREWORK);
		FireworkMeta meta = firework.getFireworkMeta();
		ArrayList<Color> colors = new ArrayList<Color>();

		firework.setFireworkMeta(meta);
		
		if(event.getType().equals(KillStreakType.fiveKS)) {
			colors.add(Color.WHITE);
			
			meta.setPower(0);
			meta.addEffect(FireworkEffect.builder().with(Type.BURST).withColor(colors).build());
			firework.setFireworkMeta(meta);
			arena.broadcast("&c" + event.getPlayer().getName() + " &6lleva 5 kills sin morir!");
			colors.clear();
			return;
		}
		
		if(event.getType().equals(KillStreakType.tenKS)) {
			colors.add(Color.WHITE); colors.add(Color.SILVER);
			
			meta.setPower(1);
			meta.addEffect(FireworkEffect.builder().trail(true).with(Type.BALL).withColor(colors).build());
			firework.setFireworkMeta(meta);
			arena.broadcast("&c" + event.getPlayer().getName() + " &6lleva 10 kills sin morir!");
			colors.clear();
			return;
		}
		
		if(event.getType().equals(KillStreakType.fifthteenKS)) {
			colors.add(Color.WHITE); colors.add(Color.SILVER); colors.add(Color.PURPLE);
			
			meta.setPower(1);
			meta.addEffect(FireworkEffect.builder().flicker(true).with(Type.BALL_LARGE).withColor(colors).withFade(Color.RED).build());
			firework.setFireworkMeta(meta);
			arena.broadcast("&c" + event.getPlayer().getName() + " &6lleva 15 kills sin morir!!");
			colors.clear();
			return;
		}

		if(event.getType().equals(KillStreakType.twentyKS)) {
			colors.add(Color.WHITE); colors.add(Color.SILVER); colors.add(Color.ORANGE);
			
			meta.setPower(1);
			meta.addEffect(FireworkEffect.builder().flicker(true).trail(true).with(Type.STAR).withColor(colors).withFade(Color.RED).build());
			firework.setFireworkMeta(meta);
			arena.broadcast("&c" + event.getPlayer().getName() + " &6lleva 20 kills sin morir!!!!");
			colors.clear();
			return;
		}
		
		if(event.getType().equals(KillStreakType.twentyfiveKS)) {
			colors.add(Color.GRAY); colors.add(Color.SILVER); colors.add(Color.FUCHSIA);
			
			meta.setPower(2);
			meta.addEffect(FireworkEffect.builder().flicker(true).trail(true).with(Type.STAR).withColor(colors).withFade(Color.RED).build());
			firework.setFireworkMeta(meta);
			arena.broadcast("&c" + event.getPlayer().getName() + " &6lleva 25 kills sin morir!!!!");
			colors.clear();
			return;
		}
		
		if(event.getType().equals(KillStreakType.thirtyKS)) {
			colors.add(Color.GRAY); colors.add(Color.WHITE); colors.add(Color.RED);
			
			meta.setPower(1);
			meta.addEffect(FireworkEffect.builder().flicker(true).trail(true).with(Type.STAR).withColor(colors).withFade(Color.RED).build());
			firework.setFireworkMeta(meta);
			
			firework = (Firework) event.getPlayer().getWorld().spawnEntity(event.getPlayer().getLocation(), EntityType.FIREWORK);
			meta = firework.getFireworkMeta();
			
			meta.setPower(1);
			meta.addEffect(FireworkEffect.builder().flicker(true).trail(true).with(Type.CREEPER).withColor(Color.RED).withColor(Color.BLACK).withFade(Color.GRAY).build());
			firework.setFireworkMeta(meta);
			
			arena.broadcast("&c" + event.getPlayer().getName() + " &6lleva 30 kills sin morir!!!!!!!!");
			colors.clear();
			return;
		}
		
		return;
	}
}