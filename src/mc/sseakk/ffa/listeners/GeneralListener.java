package mc.sseakk.ffa.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import mc.sseakk.ffa.game.Arena;
import mc.sseakk.ffa.mainpackage.FFA;
import mc.sseakk.ffa.util.Messages;

public class GeneralListener implements Listener{
	
	@EventHandler
	public void onBreakBlock(BlockBreakEvent event) {
		Player player = event.getPlayer();
		Arena arenas = FFA.getArenasManager().getPlayerArena(player.getName());
		
		if(arenas != null) {
			event.setCancelled(true);
		}
	}
}
