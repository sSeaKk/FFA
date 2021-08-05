package mc.sseakk.ffa.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import mc.sseakk.ffa.util.Messages;

public class TestCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Messages.broadcastMessage("\u2739 = campeon\n"
				+ "✷ = segundo\n"
				+ "✶ = tercero\n"
				+ "✦ = cuarto");
		return true;
	}
}