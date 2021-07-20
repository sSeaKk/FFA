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
		FileManager fm = FFA.getFileManager();
		
		try {
			File file = fm.createFile("\\test", "test");
			BufferedWriter writer = fm.getBufferedWriter();
			
			for(int i=0; i <= 6; i++) {
				String st = "Hola " + i;
				writer.write(st);
				System.out.println("Escribiendo: '" + st + "'");
				if(!(i == 6)) {
					writer.newLine();
				}
			}
			
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return true;
	}
}