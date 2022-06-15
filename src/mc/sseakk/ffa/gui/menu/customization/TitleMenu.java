package mc.sseakk.ffa.gui.menu.customization;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import mc.sseakk.ffa.RewardsManager;
import mc.sseakk.ffa.gui.Menu;
import mc.sseakk.ffa.reward.Reward;
import mc.sseakk.ffa.reward.Reward.RewardType;
import mc.sseakk.ffa.reward.rewards.Title;

public class TitleMenu extends Menu{
	private ArrayList<Title> titleList = new ArrayList<Title>();
	private static ArrayList<Integer> slotList;
	
	public TitleMenu(Player player) {
		super(player, "Titulos", 45);
		slotList = new ArrayList<Integer>();
		addOpenedMenuToPlayer(player, this);
		
		for(int i=0; i<menu.getSize(); i++) {
			if(i > 8 && i < 36 && i % 9 != 0 && (i+1) % 9 != 0){
				for(Reward reward : RewardsManager.getRewardList()) {
					if(reward.getType() == RewardType.TITLE) {
						if(warrior.getPlayerRewards().contains(reward)) {
							if(!this.titleList.contains(reward) && !slotList.contains(i)) {
								if(warrior.getTitle() == reward) {
									createIcon(((Title) reward).getText(), i,
											new ItemStack(Material.STAINED_GLASS, 1, (short) 1),
											Enchantment.KNOCKBACK,
											ItemFlag.HIDE_ENCHANTS,
											reward.getRarity().getName(),
											"&6Titulo seleccionado");
								} else {
									createIcon(((Title) reward).getText(), i,
											new ItemStack(Material.STAINED_GLASS, 1, (short) 13),
											reward.getRarity().getName(),
											"&aClick para seleccionar");
								}
								
								titleList.add((Title) reward);
								slotList.add(i);
							}
						} else if(!warrior.getPlayerRewards().contains(reward)) {
							if(!this.titleList.contains(reward) && !slotList.contains(i)) {
								createIcon(((Title) reward).getText(), i,
											new ItemStack(Material.STAINED_GLASS, 1, (short) 14),
											reward.getRarity().getName(),
											"&cNo tienes este reward",
											"&fDesbloquealo al nivel &9"+reward.getLevelToUnlock());
								
								this.titleList.add((Title) reward);
								slotList.add(i);
							}
						}
					}
				}
				
				if(!slotList.contains(i)) {
					createIcon("&7&oProximamente...", i,
							new ItemStack(Material.STAINED_GLASS, 1, (short) 8));
				}
			}
		}
	}
	
	public ArrayList<Title> getTitleList(){
		return this.titleList;
	}
	
	public ArrayList<Integer> getSlotList(){
		return slotList;
	}
	
	public static boolean isSlotRegistred(int slot) {
		if(slotList.contains(slot)) {
			return true;
		}
		
		return false;
	}
}