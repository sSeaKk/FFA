package mc.sseakk.ffa.game;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Sound;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import mc.sseakk.ffa.game.events.KillStreakEvent;
import mc.sseakk.ffa.game.events.KillStreakEvent.KillStreakType;
import mc.sseakk.ffa.game.events.WarriorKillDeathEvent;
import mc.sseakk.ffa.game.events.WarriorKillDeathEvent.DeathCause;
import mc.sseakk.ffa.mainpackage.ArenasManager;
import mc.sseakk.ffa.mainpackage.FFA;
import mc.sseakk.ffa.util.Messages;
import mc.sseakk.ffa.util.SoundUtil;
import mc.sseakk.ffa.util.TextUtil;
import net.md_5.bungee.api.chat.TextComponent;

public class ArenaListener implements Listener{
	private ArenasManager am = FFA.getArenasManager();
	
	private TextComponent 
		killTag = TextUtil.stringToTextComponent("&6 [&a+1 &6Asesinato]"),
		minimalKillTag = TextUtil.stringToTextComponent("&a+1 Asesinato"),
		deathTag = TextUtil.stringToTextComponent("&6 [&c+1 &6Muerte]"),
		minimalDeathTag = TextUtil.stringToTextComponent("&c+1 Muerte"),
		assistTag = TextUtil.stringToTextComponent("&b+1 &6Asistencia"),
		
		killedMessage = TextUtil.stringToTextComponent("&6Has sido asesinado por "),
		killerMessage = TextUtil.stringToTextComponent("&6Has asesinado a "),
		globalMessage = TextUtil.stringToTextComponent(" &6ha asesinado a "),
		
		enderPearlDeathMessage = TextUtil.stringToTextComponent(" &6murio por una ender pearl"),
		fallDeathMessage = TextUtil.stringToTextComponent(" &6murio por caida"),
		voidDeathMessage = TextUtil.stringToTextComponent(" &6cayo al vacio");
	
	@EventHandler
	public void onPlayerKillDeath(WarriorKillDeathEvent event) {
		if(event.getCause() == DeathCause.KILLED) {
			TextComponent chainer = TextUtil.stringToTextComponent(""),
						  assisterProfile = TextUtil.stringToTextComponent("");
			
			if(event.getAssister() != null) {
				chainer = TextUtil.stringToTextComponent("\n&6con la ayuda de ");
				assisterProfile = event.getAssister().getProfile().getText();
				
				//Assister
				event.getAssister().increaseAssists();
				SoundUtil.assistSound(event.getAssister().getPlayer());
				Messages.delayedMessage(event.getAssister().getPlayer(), 1, 
						FFA.getTextTag(), this.assistTag);
				
			}
			
			//Global
			event.getArena().broadcastWithout(Arrays.asList(event.getKiller().getPlayer(), event.getKilled().getPlayer()), 
						FFA.getTextTag(), event.getKiller().getProfile().getText(), this.globalMessage, event.getKilled().getProfile().getText(), chainer, assisterProfile);
			//Killer
			event.getKiller().getPlayer().removePotionEffect(PotionEffectType.REGENERATION);
			event.getKiller().getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 80, 2));
			event.getKiller().increaseKills();
			SoundUtil.killSound(event.getKiller().getPlayer());
			Messages.sendPlayerMessage(event.getKiller().getPlayer(), 
					FFA.getTextTag(), this.killerMessage, event.getKilled().getProfile().getText(), chainer, assisterProfile, this.killTag);
			
			//Killed
			event.getKilled().increaseDeaths();
			SoundUtil.deathSound(event.getKilled().getPlayer());
			Messages.sendPlayerMessage(event.getKilled().getPlayer(), 
					FFA.getTextTag(), this.killedMessage, event.getKiller().getProfile().getText(), chainer, assisterProfile, this.deathTag);

			return;
		}
		
		TextComponent scaping = TextUtil.stringToTextComponent(""),
					  killerProfile = TextUtil.stringToTextComponent("");
		
		//Check Killer
		if(event.getKiller() != null) {
			scaping = TextUtil.stringToTextComponent(" &6escapando de ");
			killerProfile = event.getKiller().getProfile().getText();
			
			event.getKiller().getPlayer().removePotionEffect(PotionEffectType.REGENERATION);
			event.getKiller().getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 80, 2));
			event.getKiller().increaseKills();
			
			SoundUtil.killSound(event.getKiller().getPlayer());
			Messages.delayedMessage(event.getKiller().getPlayer(), 1L, 
					FFA.getTextTag(), this.minimalKillTag);
		} 
		
		if(event.getCause() == DeathCause.FALL) {
			event.getArena().broadcast(FFA.getTextTag(), event.getKilled().getProfile().getText(), this.fallDeathMessage, scaping, killerProfile);
		}
		
		if(event.getCause() == DeathCause.ENDERPEARL) {
			event.getArena().broadcast(FFA.getTextTag(), event.getKilled().getProfile().getText(), this.enderPearlDeathMessage, scaping, killerProfile);
		}
		
		if(event.getCause() == DeathCause.VOID) {
			event.getArena().broadcast(FFA.getTextTag(), event.getKilled().getProfile().getText(), this.voidDeathMessage, scaping, killerProfile);
		}
		
		//Killed
		event.getKilled().increaseDeaths();
		SoundUtil.deathSound(event.getKilled().getPlayer());
		Messages.sendPlayerMessage(event.getKilled().getPlayer(), 
				FFA.getTextTag(), this.minimalDeathTag);
		return;
	}
	
	@EventHandler
	public void onKillStreak(KillStreakEvent event) {
		Arena arena = am.getPlayerArena(event.getPlayer().getName());
		Firework firework = (Firework) event.getPlayer().getWorld().spawnEntity(event.getPlayer().getLocation(), EntityType.FIREWORK);
		FireworkMeta meta = firework.getFireworkMeta();
		ArrayList<Color> colors = new ArrayList<Color>();

		firework.setFireworkMeta(meta);
		
		if(event.getType().equals(KillStreakType.fiveKS)) {
			colors.add(Color.WHITE);
			
			meta.setPower(0);
			meta.addEffect(FireworkEffect.builder().with(Type.BURST).withColor(colors).build());
			firework.setFireworkMeta(meta);
			
			arena.broadcast(FFA.getTextTag(), event.getFFAPlayer().getProfile().getText(), TextUtil.stringToTextComponent(" &6lleva 5 kills sin morir!"));
			SoundUtil.killStreakSound(arena, Sound.SILVERFISH_HIT);
			
			
			colors.clear();
			return;
		}
		
		if(event.getType().equals(KillStreakType.tenKS)) {
			colors.add(Color.WHITE); colors.add(Color.SILVER);
			
			meta.setPower(1);
			meta.addEffect(FireworkEffect.builder().trail(true).with(Type.BALL).withColor(colors).build());
			firework.setFireworkMeta(meta);
			
			arena.broadcast(FFA.getTextTag(), event.getFFAPlayer().getProfile().getText(), TextUtil.stringToTextComponent(" &6lleva 10 kills sin morir!!"));
			SoundUtil.killStreakSound(arena, Sound.BLAZE_DEATH);
			
			colors.clear();
			return;
		}
		
		if(event.getType().equals(KillStreakType.fifthteenKS)) {
			colors.add(Color.WHITE); colors.add(Color.SILVER); colors.add(Color.PURPLE);
			
			meta.setPower(1);
			meta.addEffect(FireworkEffect.builder().flicker(true).with(Type.BALL_LARGE).withColor(colors).withFade(Color.RED).build());
			firework.setFireworkMeta(meta);
			
			arena.broadcast(FFA.getTextTag(), event.getFFAPlayer().getProfile().getText(), TextUtil.stringToTextComponent(" &6lleva 15 kills sin morir!!"));
			SoundUtil.killStreakSound(arena, Sound.GHAST_SCREAM);
			
			colors.clear();
			return;
		}

		if(event.getType().equals(KillStreakType.twentyKS)) {
			colors.add(Color.WHITE); colors.add(Color.SILVER); colors.add(Color.ORANGE);
			
			meta.setPower(1);
			meta.addEffect(FireworkEffect.builder().flicker(true).trail(true).with(Type.STAR).withColor(colors).withFade(Color.RED).build());
			firework.setFireworkMeta(meta);
			
			arena.broadcast(FFA.getTextTag(), event.getFFAPlayer().getProfile().getText(), TextUtil.stringToTextComponent(" &6lleva 20 kills sin morir!!!!"));
			SoundUtil.killStreakSound(arena, Sound.ENDERMAN_DEATH);
			
			colors.clear();
			return;
		}
		
		if(event.getType().equals(KillStreakType.twentyfiveKS)) {
			colors.add(Color.GRAY); colors.add(Color.SILVER); colors.add(Color.FUCHSIA);
			
			meta.setPower(2);
			meta.addEffect(FireworkEffect.builder().flicker(true).trail(true).with(Type.STAR).withColor(colors).withFade(Color.RED).build());
			firework.setFireworkMeta(meta);
			
			arena.broadcast(FFA.getTextTag(), event.getFFAPlayer().getProfile().getText(), TextUtil.stringToTextComponent(" &6lleva 25 kills sin morir!!!!"));
			SoundUtil.killStreakSound(arena, Sound.WITHER_DEATH);
			
			colors.clear();
			return;
		}
		
		if(event.getType().equals(KillStreakType.thirtyKS)) {
			colors.add(Color.GRAY); colors.add(Color.WHITE); colors.add(Color.RED);
			
			meta.setPower(1);
			meta.addEffect(FireworkEffect.builder().flicker(true).trail(true).with(Type.STAR).withColor(colors).withFade(Color.RED).build());
			firework.setFireworkMeta(meta);
			
			Bukkit.getServer().getScheduler().runTaskLater(FFA.getInstance(), new Runnable() {
				
				public void run() {
					Firework firework = (Firework) event.getPlayer().getWorld().spawnEntity(event.getPlayer().getLocation(), EntityType.FIREWORK);
					FireworkMeta meta = firework.getFireworkMeta();
					
					meta.setPower(1);
					meta.addEffect(FireworkEffect.builder().flicker(true).trail(true).with(Type.CREEPER).withColor(Color.RED).withColor(Color.BLACK).withFade(Color.GRAY).build());
					firework.setFireworkMeta(meta);
				}
				
			}, 3L);
			
			arena.broadcast(FFA.getTextTag(), event.getFFAPlayer().getProfile().getText(), TextUtil.stringToTextComponent(" &6lleva 30 kills sin morir!!!!!!"));
			SoundUtil.killStreakSound(arena, Sound.ENDERDRAGON_GROWL);
			
			colors.clear();
			return;
		}
		
		return;
	}
}
