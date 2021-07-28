package mc.sseakk.ffa.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import mc.sseakk.ffa.game.Arena;
import mc.sseakk.ffa.game.FFAPlayer;
import mc.sseakk.ffa.mainpackage.ArenasManager;
import mc.sseakk.ffa.mainpackage.FFA;
import mc.sseakk.ffa.util.Messages;

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
	public void onKillDeath(EntityDeathEvent event) {
		if(event.getEntityType().equals(EntityType.PLAYER) && event.getEntity().getKiller() != null) {
			Player playerKilled = (Player) event.getEntity(),
				   playerKiller = event.getEntity().getKiller();
			
			if(am.getPlayerArena(playerKilled.getName()).equals(am.getPlayerArena(playerKiller.getName()))) {
				FFAPlayer ffaPlayerKilled = am.getPlayerArena(playerKilled.getName()).getFFAPlayer(playerKilled.getName()),
						  ffaPlayerKiller = am.getPlayerArena(playerKiller.getName()).getFFAPlayer(playerKiller.getName());
				
				Messages.broadcastMessage("&c" + ffaPlayerKilled.getPlayer().getName() + " &6fue asesinado por &c" + ffaPlayerKiller.getPlayer().getName());
				
				ffaPlayerKilled.increaseDeaths();
				event.getDrops().clear();
				event.setDroppedExp(0);
				Messages.sendPlayerMessage(playerKilled, "&6+1 Muerte");
				
				ffaPlayerKiller.increaseKills();
				if(playerKiller.hasPotionEffect(PotionEffectType.REGENERATION)) { playerKiller.removePotionEffect(PotionEffectType.REGENERATION); }
				playerKiller.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 80, 2));
				Messages.sendPlayerMessage(playerKiller, "&6+1 Asesinatos");
			}
		}
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		Arena arena = am.getPlayerArena(player.getName());
		Location spawnpoint = event.getRespawnLocation();
		
		if(arena != null) {
			event.setRespawnLocation(arena.getSpawn());
			player.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
			player.getInventory().addItem(new ItemStack(Material.DIAMOND_SWORD));
		} else {
			event.setRespawnLocation(spawnpoint);
		}
	}
}