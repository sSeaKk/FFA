package mc.sseakk.ffa.game;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.scheduler.BukkitScheduler;	
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import mc.sseakk.ffa.FFA;
import mc.sseakk.ffa.util.TextUtil;

public class ArenaScoreboard {
	private static int taskID;
	
	public static void initScoreboard(Arena arena) { 
		BukkitScheduler scheduler = FFA.getInstance().getServer().getScheduler();
		setTaskID(scheduler.scheduleSyncRepeatingTask(FFA.getInstance(), new Runnable(){
			@Override
			public void run() {
				for(Warrior player : arena.getPlayerList()) {
					updateStatsScoreboard(player);
				}
			}
		}, 0, 20L));
	}
	
	public static void updateStatsScoreboard(Warrior player) {
			ScoreboardManager manager = FFA.getInstance().getServer().getScoreboardManager();
			Scoreboard scoreboard = manager.getNewScoreboard();
			Objective obj = scoreboard.registerNewObjective("FFA", "dummy");
			Score score = null;
			String spacebars = "         ";
			List<String> lines = new ArrayList<String>();
			
			obj.setDisplaySlot(DisplaySlot.SIDEBAR);
			obj.setDisplayName(TextUtil.colorText("&6SpaceMan Network &cFFA"));
			
			lines.add(TextUtil.colorText(" "));
			lines.add(TextUtil.colorText(spacebars+"&6Ping: &7"+ ((CraftPlayer) Bukkit.getPlayer(player.getName())).getHandle().ping+"ms"));
			lines.add(TextUtil.colorText("  "));
			lines.add(TextUtil.colorText(spacebars+"&6Kills: &7" + player.getKills()));
			lines.add(TextUtil.colorText(spacebars+"&6Deaths: &7" + player.getDeaths()));
			lines.add(TextUtil.colorText(spacebars+"&6Assists: &7" + player.getAssists()));
			lines.add(TextUtil.colorText("   "));
			lines.add(TextUtil.colorText(spacebars+"&6KDA: &7" + player.getKdaRatio()));
			lines.add(TextUtil.colorText(spacebars+"&6KDR: &7" + player.getKdRatio()));
			lines.add(TextUtil.colorText("    "));
			lines.add(TextUtil.colorText(spacebars+"&6Kill Streak: &7" + player.getKillStreak()));
			lines.add(TextUtil.colorText("     "));
			lines.add(TextUtil.colorText("     "+"&6Damage Given: &7" + TextUtil.decimalFormat(player.getDamageGiven())));
			lines.add(TextUtil.colorText("     "+"&6Damage Taken: &7" + TextUtil.decimalFormat(player.getDamageTaken())));
			lines.add(TextUtil.colorText("&6---------------------"));
			
			for(int i=0; i<lines.size(); i++) {
				score = obj.getScore(lines.get(i));
				score.setScore(lines.size()-(i));
			}
			
			player.getPlayer().setScoreboard(scoreboard);
	}

	private static void setTaskID(int taskID) {
		ArenaScoreboard.taskID = taskID;
	}
	
	@SuppressWarnings("unused")
	private static int getTaskID() {
		return taskID;
	}
}