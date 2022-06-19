package mc.sseakk.ffa.gui;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import mc.sseakk.ffa.FFA;
import mc.sseakk.ffa.gui.menu.CustomizationMenu;
import mc.sseakk.ffa.gui.menu.StatsMenu;
import mc.sseakk.ffa.gui.menu.customization.KSEffectMenu;
import mc.sseakk.ffa.gui.menu.customization.KSSoundMenu;
import mc.sseakk.ffa.gui.menu.customization.KillStreakMenu;
import mc.sseakk.ffa.gui.menu.customization.TitleMenu;
import mc.sseakk.ffa.reward.Reward;
import mc.sseakk.ffa.reward.rewards.Title;
import mc.sseakk.ffa.util.Messages;
import mc.sseakk.ffa.util.SoundUtil;

public class GuiListener implements Listener{
	
	@EventHandler
	public void onGui(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		if(Menu.isGuiOpen(player) && event.getClickedInventory() != null) {
			event.setCancelled(true);
			
			if(event.getClickedInventory().getName() != "Menu Principal") {
				if(event.getSlot() == event.getClickedInventory().getSize()-9) {
					Menu.openPrevMenu(player, event.getClickedInventory().getName());
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
				
				String displayName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
				Reward reward = FFA.getRewardsManager().getReward(displayName);
				if(reward != null && reward instanceof Title) {
					Title title = (Title) reward;
					
					if(FFA.getWarriorManager().get(player.getName()).getPlayerRewards().contains(title)) {
						
						if(displayName.equals(player.getName())) {
							return;
						}
						
						if(FFA.getWarriorManager().get(player.getName()).getTitle() != title) {
							
							FFA.getWarriorManager().get(player.getName()).setTitle(title.getID());
							new TitleMenu(player);
							Messages.sendPlayerMessage(player, "&aTitulo seleccionado!");
							SoundUtil.confirmSound(player);
							return;
						}
						
						Messages.sendPlayerMessage(player, "&cEste titulo ya esta seleccionado!");
						SoundUtil.rejectSound(player);
						return;
					}
					
					Messages.sendPlayerMessage(player, "&cNo tienes este titulo disponible, juega mas para desbloquearlo.");
					SoundUtil.rejectSound(player);
					return;
				}
				
				if(displayName.equalsIgnoreCase("Proximamente...")) {
					Messages.sendPlayerMessage(player, "&7&oPronto agregasremos mas titulos ;)");
					player.playSound(player.getLocation(), Sound.VILLAGER_YES, 10, 1);
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