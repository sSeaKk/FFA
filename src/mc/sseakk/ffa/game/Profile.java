package mc.sseakk.ffa.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import mc.sseakk.ffa.FFA;
import mc.sseakk.ffa.game.events.WarriorKillStreakEvent.KillStreakType;
import mc.sseakk.ffa.reward.Reward;
import mc.sseakk.ffa.reward.Reward.RewardType;
import mc.sseakk.ffa.reward.rewards.KillStreakSound;
import mc.sseakk.ffa.reward.rewards.Title;
import mc.sseakk.ffa.util.Messages;
import mc.sseakk.ffa.util.TextUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class Profile{
	
	protected String name,
				   ore;
	protected int level,
			 	  rank;
	protected UUID uuid;
	protected Player player;
	protected TextComponent textProfile;
	protected Title title;
	protected Map<KillStreakType, KillStreakSound> killStreakSounds;
	protected ArrayList<Reward> playerRewards;
	
	public Profile(Player player) {
		this.player = player;
		this.name = player.getName();
		this.uuid = this.player.getUniqueId();
		this.textProfile = new TextComponent(TextUtil.colorText("&c"+name+"&r"));
		
		this.playerRewards = new ArrayList<Reward>();
		this.killStreakSounds = new HashMap<KillStreakType, KillStreakSound>();
		FFA.getRewardsManager().loadRewards(this);
		setDefaults();
		
		FFA.getWarriorManager().loadProfile(this);
		
		System.out.println(killStreakSounds.containsKey(KillStreakType.fiveKS));
		System.out.println(killStreakSounds.containsKey(KillStreakType.tenKS));
		System.out.println(killStreakSounds.containsKey(KillStreakType.fifthteenKS));
		System.out.println(killStreakSounds.containsKey(KillStreakType.twentyKS));
		System.out.println(killStreakSounds.containsKey(KillStreakType.twentyfiveKS));
		System.out.println(killStreakSounds.containsKey(KillStreakType.thirtyKS));
		
		System.out.println(getKSSound(5).getName());
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Title getTitle() {
		return title;
	}

	public String getTitleText() {
		return title.getText();
	}
	
	public void setTitle(int titleID) {
		for(Reward reward : this.playerRewards) {
			if(reward.getType() == RewardType.TITLE && reward.getID() == titleID) {
				this.title = (Title) reward;
				resetHoverEvent();
			}
		}
	}
	
	public void setKSSound(KillStreakType type, int soundID) {
		System.out.println("seteando");
		for(Reward reward : this.playerRewards) {
			if(reward.getType() == RewardType.KILLSTREAKSOUND && reward.getID() == soundID) {
				KillStreakSound kss = (KillStreakSound) reward;
				System.out.println(reward.getID() + " " + reward.getType());
				if(this.killStreakSounds.containsKey(type)) {
					this.killStreakSounds.remove(type);
				}
				
				this.killStreakSounds.put(type, kss);
				System.out.println("insertado: " + kss.getSound().name() + " a " + type.name());
				return;
			}
		}
		
		if(!isKSSTypeSet(type)) {
			Messages.warningMessage("Se ha intendado cargar un sonido a la racha de " + type.getKills() + " kills, y no hay un sonido cargado previamente.");
			Messages.warningMessage("Cargando sonido default.");
			
		}
		
		System.out.println(false);
	}
	
	public KillStreakSound getKSSound(KillStreakType type) {
		System.out.println("retornando " + this.killStreakSounds.get(type).getSound().name());
		return this.killStreakSounds.get(type);
	}
	
	public KillStreakSound getKSSound(int killTrigger) {
		if(killTrigger == 5) { System.out.println("retornando " + this.killStreakSounds.get(KillStreakType.fiveKS).getSound().name()); return this.killStreakSounds.get(KillStreakType.fiveKS);  }
		if(killTrigger == 10) { return this.killStreakSounds.get(KillStreakType.tenKS); }
		if(killTrigger == 15) { return this.killStreakSounds.get(KillStreakType.fifthteenKS); }
		if(killTrigger == 20) { return this.killStreakSounds.get(KillStreakType.twentyKS); }
		if(killTrigger == 25) { return this.killStreakSounds.get(KillStreakType.twentyfiveKS); }
		if(killTrigger == 30) { return this.killStreakSounds.get(KillStreakType.thirtyKS); }
		return null;
	}
	
	public String getOre() {
		return ore;
	}

	public void setOre(String ore) {
		this.ore = ore;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}
	
	public TextComponent getText() {
		return this.textProfile;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public void addReward(Reward reward) {
		this.playerRewards.add(reward);
	}
	
	public ArrayList<Reward> getPlayerRewards(){
		return this.playerRewards;
	}
	
	public void resetHoverEvent() {
		this.textProfile.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(name + "\n")
				.color(ChatColor.AQUA).append(this.title.getText()).color(ChatColor.GOLD).italic(true)
				.create()));
	}
	
	public UUID getUUID() {
		return this.uuid;
	}
	
	public boolean isKSSTypeSet(KillStreakType type) {
		if(this.killStreakSounds.containsKey(type)) {
			return true;
		}
		
		return false;
	}
	
	public void setDefaultKSSounds(KillStreakType type, boolean all) {
		System.out.println("seteando ks defaults");
		for(Reward r : this.playerRewards) {
			if(r instanceof KillStreakSound) {
				 KillStreakSound kss = (KillStreakSound) r;
				 System.out.println("seteando " + kss.getName() + " a " + kss.getKSType().name());
				 if((all || type == KillStreakType.fiveKS) && kss.getSound() == Sound.SILVERFISH_KILL) { setKSSound(KillStreakType.fiveKS, kss.getID()); }
				 if((all || type == KillStreakType.tenKS) && kss.getSound() == Sound.BLAZE_DEATH) { setKSSound(KillStreakType.tenKS, kss.getID()); }
				 if((all || type == KillStreakType.fifthteenKS) && kss.getSound() == Sound.GHAST_SCREAM) { setKSSound(KillStreakType.fifthteenKS, kss.getID()); }
				 if((all || type == KillStreakType.twentyKS) && kss.getSound() == Sound.ENDERMAN_DEATH) { setKSSound(KillStreakType.twentyKS, kss.getID()); }
				 if((all || type == KillStreakType.twentyfiveKS) && kss.getSound() == Sound.WITHER_DEATH) { setKSSound(KillStreakType.twentyfiveKS, kss.getID()); }
				 if((all || type == KillStreakType.thirtyKS) && kss.getSound() == Sound.ENDERDRAGON_GROWL) { setKSSound(KillStreakType.thirtyKS, kss.getID()); }
			}
		}
	}
	
	public void setDefaults() {
		setTitle(1);
		setDefaultKSSounds(null, true);
	}
	
	public Map<KillStreakType, KillStreakSound> getLst(){
		return this.killStreakSounds;
	}
}