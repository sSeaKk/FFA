package mc.sseakk.ffa.gui;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.meta.ItemMeta;

import mc.sseakk.ffa.gui.menu.CustomizationMenu;
import mc.sseakk.ffa.gui.menu.MainMenu;
import mc.sseakk.ffa.gui.menu.StatsMenu;
import mc.sseakk.ffa.gui.menu.customization.KSEffectMenu;
import mc.sseakk.ffa.gui.menu.customization.KSSoundMenu;
import mc.sseakk.ffa.gui.menu.customization.KillStreakMenu;
import mc.sseakk.ffa.gui.menu.customization.TitleMenu;
import mc.sseakk.ffa.mainpackage.FFA;
import mc.sseakk.ffa.reward.Reward;
import mc.sseakk.ffa.reward.rewards.Title;
import mc.sseakk.ffa.util.Messages;

public class GuiListener implements Listener{
	
	@EventHandler
	public void onGui(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		if(Menu.isGuiOpen(player) && event.getClickedInventory() != null) {
			event.setCancelled(true);
			
			if(event.getClickedInventory().getName() != "Menu Principal") {
				if(event.getSlot() == event.getClickedInventory().getSize()-9) {
					new MainMenu(player);
				}
			}
			
			if(event.getClickedInventory().getName().equalsIgnoreCase("Menu Principal")) {
				if(event.getSlot() == 13) {
					new StatsMenu(player);
				}
				if(event.getSlot() == 11) {
					new CustomizationMenu(player);
				}
			}
			
			if(event.getClickedInventory().getName().equalsIgnoreCase("Personalizacion")) {
				if(event.getSlot() == 11) {
					new TitleMenu(player);
				}
				
				if(event.getSlot() == 15) {
					new KillStreakMenu(player);
				}
			}
			
			if(event.getClickedInventory().getName().equalsIgnoreCase("Titulos")) {
				if(event.getCurrentItem().getType() == Material.AIR) {
					return;
				}
				
				ItemMeta itemInSlot = event.getCurrentItem().getItemMeta();
				Reward reward = FFA.getRewardsManager().getReward(itemInSlot.getDisplayName());
				if(reward != null && reward instanceof Title) {
					Title title = (Title) reward;
					
					if(FFA.getWarriorManager().get(player.getName()).getPlayerRewards().contains(title)) {
						
						if(FFA.getWarriorManager().get(player.getName()).getTitle() != title) {
							
							FFA.getWarriorManager().get(player.getName()).setTitle(title.getID());
							new TitleMenu(player);
							Messages.sendPlayerMessage(player, "&aTitulo seleccionado!");
							return;
						}
						
						Messages.sendPlayerMessage(player, "&cEste titulo ya esta seleccionado!");
						return;
					}
					
					if(ChatColor.stripColor(itemInSlot.getDisplayName()).equalsIgnoreCase("Proximamente...")) {
						Messages.sendPlayerMessage(player, "&7&oPronto añadiremos mas titulos ;)");
					}
					
					Messages.sendPlayerMessage(player, "&cNo tienes este titulo disponible, juega mas para desbloquearlo.");
					return;
				}
			}
			
			if(event.getClickedInventory().getName().equalsIgnoreCase("Rachas")) {
				if(event.getSlot() == 11) {
					new KSEffectMenu(player);
				}
				
				if(event.getSlot() == 15) {
					new KSSoundMenu(player);
				}
				
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