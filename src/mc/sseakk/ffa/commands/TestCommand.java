package mc.sseakk.ffa.commands;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import mc.sseakk.ffa.mainpackage.FFA;
import mc.sseakk.ffa.mainpackage.FileManager;

public class TestCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		FFA.getArenasManager().loadArenas();
		return true;
	}
}