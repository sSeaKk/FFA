package mc.sseakk.ffa.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import mc.sseakk.ffa.game.Arena;
import mc.sseakk.ffa.game.FFAPlayer;
import mc.sseakk.ffa.game.Kits;
import mc.sseakk.ffa.mainpackage.ArenasManager;
import mc.sseakk.ffa.mainpackage.FFA;

public class GeneralListener implements Listener{
	ArenasManager am = FFA.getArenasManager();
	
	@EventHandler
	public void onBreakBlock(BlockBreakEvent event) {
		Player player = event.getPlayer();
		
		if(am.getPlayerArena(player.getName()) != null) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onFoodDrop(FoodLevelChangeEvent event) {
		Player player = (Player) event.getEntity();
		
		if(am.getPlayerArena(player.getName()) != null) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		Arena arena = am.getPlayerArena(player.getName());
		Location spawnpoint = event.getRespawnLocation();
		
		if(arena != null) {
			event.setRespawnLocation(arena.getSpawn());
			
			player.getInventory().clear();
			player.getEquipment().clear();	
			Kits.setDefaultKit(player);
			
			Bukkit.getScheduler().runTaskLater(FFA.getInstance(), new Runnable() {
				public void run() {
					player.addPotionEffect(FFAPlayer.getPotionEffect());
				}
			}, 1L);
			
		} else {
			event.setRespawnLocation(spawnpoint);
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		Arena arena = am.getPlayerArena(player.getName());
		
		if(arena != null) {
			arena.removePlayer(player);
		}
	}
}