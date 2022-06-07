package mc.sseakk.ffa.gui;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import mc.sseakk.ffa.gui.menu.MainMenu;
import mc.sseakk.ffa.gui.menu.StatsMenu;

public class GuiListener implements Listener{
	
	@EventHandler
	public void onGui(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		if(Menu.isGuiOpen(player) && event.getClickedInventory() != null) {
			event.setCancelled(true);
			
			if(ChatColor.stripColor(event.getClickedInventory().getName()) != "Menu Principal") {
				if(event.getSlot() == event.getClickedInventory().getSize()-9) {
					new MainMenu(player);
				}
			}
			
			if(event.getSlot() == 13) {
				new StatsMenu(player);
			}
		}
	}
	
	@EventHandler
	public void onClose(InventoryCloseEvent event) {
		Player player = (Player) event.getPlayer();
		if(Menu.isGuiOpen(player) && event.getPlayer().getInventory() != event.getInventory()){
			Menu.openedGui.remove(player);
			player.updateInventory();
		}
	}
}