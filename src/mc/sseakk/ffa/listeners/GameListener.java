package mc.sseakk.ffa.listeners;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import mc.sseakk.ffa.game.Arena;
import mc.sseakk.ffa.game.FFAPlayer;
import mc.sseakk.ffa.game.Stats;
import mc.sseakk.ffa.mainpackage.ArenasManager;
import mc.sseakk.ffa.mainpackage.FFA;
import mc.sseakk.ffa.mainpackage.StatsManager;
import mc.sseakk.ffa.util.Messages;

public class GameListener implements Listener{
	private ArenasManager am = FFA.getArenasManager();
	private StatsManager sm = FFA.getStatsManager();
	private boolean isContinueDamaging;
	private HashMap<Player, Player> assists = new HashMap<Player, Player>();
	private BukkitTask task = null;
	
	@EventHandler
	public void onKillDeath(EntityDeathEvent event) {
		if(event.getEntityType().equals(EntityType.PLAYER) && event.getEntity().getKiller() != null) {
			Player playerKilled = (Player) event.getEntity(),
				   playerKiller = event.getEntity().getKiller();
			
			if(am.getPlayerArena(playerKilled.getName()) != null) {
				FFAPlayer ffaPlayerKilled = am.getPlayerArena(playerKilled.getName()).getFFAPlayer(playerKilled.getName()),
						  ffaPlayerKiller = am.getPlayerArena(playerKiller.getName()).getFFAPlayer(playerKiller.getName());
				
				for(FFAPlayer ffaplayer : am.getPlayerArena(playerKilled.getName()).getPlayerList()) {
					Player player = ffaplayer.getPlayer();
					if(ffaplayer != ffaPlayerKiller && ffaplayer != ffaPlayerKilled) {
						Messages.sendPlayerMessage(player, "&c" + ffaPlayerKilled.getPlayer().getName() + " &6fue asesinado por &c" + ffaPlayerKiller.getPlayer().getName());
					}
				}
				
				ffaPlayerKilled.increaseDeaths();
				event.getDrops().clear();
				event.setDroppedExp(0);
				Messages.sendPlayerMessage(playerKilled, "&6Has sido asesinado por &c" + playerKiller.getName() +"&6 [+1 Muerte]");
				
				ffaPlayerKiller.increaseKills();
				if(playerKiller.hasPotionEffect(PotionEffectType.REGENERATION)) { playerKiller.removePotionEffect(PotionEffectType.REGENERATION); }
				playerKiller.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 80, 2));
				Messages.sendPlayerMessage(playerKilled, "&6Has asesinado a " + playerKiller.getName() +" [+1 Asesinatos]");
				return;
			}
		}
		
		if(event.getEntityType().equals(EntityType.PLAYER)) {
			Player player = (Player) event.getEntity();
			
			if(am.getPlayerArena(player.getName()) != null) {
				FFAPlayer playerKilled = am.getPlayerArena(player.getName()).getFFAPlayer(player.getName());
				Messages.sendPlayerMessage(playerKilled.getPlayer(), "&6+1 Muerte");
				playerKilled.increaseDeaths();
				event.getDrops().clear();
				event.setDroppedExp(0);
			}
		}
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event) {
		if(event.getEntityType().equals(EntityType.PLAYER) && event.getDamager().getType().equals(EntityType.PLAYER) && event.getCause().equals(DamageCause.ENTITY_ATTACK)) {
			Player playerDamaged = (Player) event.getEntity(),
				   playerDamager = (Player) event.getDamager();
			
			if(am.getPlayerArena(playerDamaged.getName()).equals(am.getPlayerArena(playerDamager.getName()))) {
				Arena arena = am.getPlayerArena(playerDamaged.getName());
				Stats statsPlayerDamaged = sm.getStats(playerDamaged.getName()),
					  statsPlayerDamager = sm.getStats(playerDamager.getName());
				
				Messages.sendPlayerMessage(playerDamager, "&6Atacaste a &c" + playerDamager.getName());
				Messages.sendPlayerMessage(playerDamaged, "&6Te esta atacando &c" + playerDamaged.getName());
			}
		}
	}
}