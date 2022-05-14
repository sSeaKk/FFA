package mc.sseakk.ffa.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import mc.sseakk.ffa.mainpackage.FFA;
import mc.sseakk.ffa.util.Messages;

public class TestCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			Messages.sendConsoleMessage("Este comando solo puede ser ejecutado dentro del juego");
			return true;
		}
		
		Player player = (Player) sender;
		FFA.getStatsManager().getStats(player.getUniqueId()).setKillStreak(FFA.getStatsManager().getStats(player.getUniqueId()).getKillStreak() + 4);
		return false;
	}
}