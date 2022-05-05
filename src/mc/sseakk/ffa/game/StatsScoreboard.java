package mc.sseakk.ffa.game;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.scheduler.BukkitScheduler;	
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import mc.sseakk.ffa.mainpackage.FFA;
import mc.sseakk.ffa.util.TextUtil;

public class StatsScoreboard {
	private static int taskID;
	
	public static void initScoreboard(Arena arena) { 
		BukkitScheduler scheduler = FFA.getInstance().getServer().getScheduler();
		setTaskID(scheduler.scheduleSyncRepeatingTask(FFA.getInstance(), new Runnable(){
			@Override
			public void run() {
				for(FFAPlayer player : arena.getPlayerList()) {
					updateStatsScoreboard(player);
				}
			}
		}, 0, 20L));
	}
	
	public static void updateStatsScoreboard(FFAPlayer player) {
			ScoreboardManager manager = FFA.getInstance().getServer().getScoreboardManager();
			Scoreboard scoreboard = manager.getNewScoreboard();
			Objective obj = scoreboard.registerNewObjective("FFA", "dummy");
			Score score = null;
			String spacebars = "         ";
			Stats stats = player.getStats();
			List<String> lines = new ArrayList<String>();
			
			
			obj.setDisplaySlot(DisplaySlot.SIDEBAR);
			
			lines.add(TextUtil.colorText(" "));
			lines.add(TextUtil.colorText(spacebars+"&6Ping: &7"+ ((CraftPlayer)player.getPlayer()).getHandle().ping)+"ms");
			lines.add(TextUtil.colorText("  "));
			lines.add(TextUtil.colorText(spacebars+"&6Kills: &7" + stats.getKills()));
			lines.add(TextUtil.colorText(spacebars+"&6Deaths: &7" + stats.getDeaths()));
			lines.add(TextUtil.colorText(spacebars+"&6Assists: &7" + stats.getAssists()));
			lines.add(TextUtil.colorText("   "));
			lines.add(TextUtil.colorText(spacebars+"&6KDA: &7" + stats.getKdaRatio()));
			lines.add(TextUtil.colorText(spacebars+"&6KDR: &7" + stats.getKdRatio()));
			lines.add(TextUtil.colorText("    "));
			lines.add(TextUtil.colorText(spacebars+"&6Kill Streak: &7" + stats.getKillStreak()));
			lines.add(TextUtil.colorText("     "));
			lines.add(TextUtil.colorText("     "+"&6Damage Given: &7" + TextUtil.decimalFormat(stats.getDamageGiven())));
			lines.add(TextUtil.colorText("     "+"&6Damage Taken: &7" + TextUtil.decimalFormat(stats.getDamageTaken())));
			lines.add(TextUtil.colorText("&6---------------------"));
			
			obj.setDisplayName(TextUtil.colorText("&6SpaceMan Network &cFFA"));
			for(int i=0; i<lines.size(); i++) {
				score = obj.getScore(lines.get(i));
				score.setScore(lines.size()-(i));
			}
			
			player.getPlayer().setScoreboard(scoreboard);
	}

	private static void setTaskID(int taskID) {
		StatsScoreboard.taskID = taskID;
	}
	
	@SuppressWarnings("unused")
	private static int getTaskID() {
		return taskID;
	}
}