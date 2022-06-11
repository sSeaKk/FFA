package mc.sseakk.ffa.gui.menu;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import mc.sseakk.ffa.gui.Menu;
import mc.sseakk.ffa.mainpackage.RewardsManager;
import mc.sseakk.ffa.reward.Reward;
import mc.sseakk.ffa.reward.Reward.RewardType;
import mc.sseakk.ffa.reward.rewards.Title;

public class TitleMenu extends Menu{
	private ArrayList<Title> titleList = new ArrayList<Title>();
	private ArrayList<Integer> slotList = new ArrayList<Integer>();
	
	public TitleMenu(Player player) {
		super(player, "Personalizacion de Titulo", 45);
		System.out.println("entrando menu title");
		
		for(int i=0; i<menu.getSize(); i++) {
			if(i > 8 && i < 36 && i % 9 != 0 && (i+1) % 9 != 0){
				for(Reward reward : RewardsManager.getRewardList()) {
					if(reward.getType() == RewardType.TITLE) {
						if(warrior.getPlayerRewards().contains(reward)) {
							if(!this.titleList.contains(reward) && !this.slotList.contains(i)) {
								createIcon(((Title) reward).getText(), i,
										new ItemStack(Material.STAINED_GLASS, 1, (short) 13),
										reward.getRarity().getName(),
										"&aClick para seleccionar.");
								
								this.titleList.add((Title) reward);
								this.slotList.add(i);
							}
						} else if(!warrior.getPlayerRewards().contains(reward)) {
							if(!this.titleList.contains(reward) && !this.slotList.contains(i)) {
								createIcon(((Title) reward).getText(), i,
											new ItemStack(Material.STAINED_GLASS, 1, (short) 14),
											reward.getRarity().getName(),
										"&cNo tienes este reward.");
							
								this.titleList.add((Title) reward);
								this.slotList.add(i);
							}
						}
					}
				}
				
				if(!this.slotList.contains(i)) {
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
		return this.slotList;
	}
}