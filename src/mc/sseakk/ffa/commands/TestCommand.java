package mc.sseakk.ffa.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import mc.sseakk.ffa.game.events.KillStreakEvent;
import mc.sseakk.ffa.game.events.KillStreakEvent.KillStreakType;
import mc.sseakk.ffa.game.player.Warrior;
import mc.sseakk.ffa.mainpackage.FFA;
import mc.sseakk.ffa.util.Messages;

public class TestCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			Messages.infoMessage("Este comando solo puede ser ejecutado dentro del juego");
			return true;
		}
		
		Player player = (Player) sender;
		Warrior fp= FFA.getArenasManager().getPlayerArena(player.getName()).getFFAPlayer(player.getName());
		
		
		if(args.length > 0) {
			if(args[0].equals("5")) {
				Bukkit.getServer().getPluginManager().callEvent(new KillStreakEvent(fp, KillStreakType.fiveKS));
			}
			
			if(args[0].equals("10")) {
				Bukkit.getServer().getPluginManager().callEvent(new KillStreakEvent(fp, KillStreakType.tenKS));
			}
			
			if(args[0].equals("15")) {
				Bukkit.getServer().getPluginManager().callEvent(new KillStreakEvent(fp, KillStreakType.fifthteenKS));
			}
			
			if(args[0].equals("20")) {
				Bukkit.getServer().getPluginManager().callEvent(new KillStreakEvent(fp, KillStreakType.twentyKS));
			}
			
			if(args[0].equals("25")) {
				Bukkit.getServer().getPluginManager().callEvent(new KillStreakEvent(fp, KillStreakType.twentyfiveKS));
			}
			
			if(args[0].equals("30")) {
				Bukkit.getServer().getPluginManager().callEvent(new KillStreakEvent(fp, KillStreakType.thirtyKS));
			}
		}
		
		return false;
	}
}