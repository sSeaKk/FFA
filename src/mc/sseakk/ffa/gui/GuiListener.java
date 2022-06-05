package mc.sseakk.ffa.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class GuiListener implements Listener{
	
	@EventHandler
	public void onGui(InventoryClickEvent event) {
		if(Menu.isGuiOpen((Player) event.getWhoClicked())) {
			event.setCancelled(true);
			return;
		}
	}
	
	@EventHandler
	public void onClose(InventoryCloseEvent event) {
		if(Menu.isGuiOpen((Player) event.getPlayer())){
			Menu.openedGui.remove((Player) event.getPlayer());
		}
	}
}
