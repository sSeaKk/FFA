package mc.sseakk.ffa.commands;

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
		
		if(args[0].equalsIgnoreCase("create")) {
			String fileName, path;
			
			if(args.length > 1) {
				fileName = args[1];
				
				if(args.length > 2) {
					path = args[2];
					try {
						fm.createFile(fileName, path);
						System.out.println("Completado");
					} catch (IOException e) {
						e.printStackTrace();
					}
					return true;
				}
				
				try {
					fm.createFile(fileName, "\\default");
					System.out.println("Completado");
				} catch (IOException e) {
					e.printStackTrace();
				}
				return true;
			}
			
			System.out.println("/test create <archivo> [path]");
			return true;
		}
		
		if(args[0].equalsIgnoreCase("delete")) {	
			if(args.length > 2) {
				String fileName = args[1], path = args[2];
				fm.deleteFile(fileName, path);
				return true;
			}
			
			System.out.println("/test delete <archivo> <path>");
			return true;
		}
		
		if(args[0].equalsIgnoreCase("write")) {
			if(args.length > 3) {
				String fileName = args[1], path = args[2], text="";
				for(int i=3; i<args.length; i++) {
					text = text + args[i] + " ";
				}
				
				try {
					fm.writeFile(fileName, path, text);
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("Error");
				}
				
				return true;
			}
			
			System.out.println("/test write <archivo <path> <texto>");
			return true;
		}
		
		if(args[0].equalsIgnoreCase("read")) {
			if(args.length > 1) {
				String path = args[1];
				try {
					fm.readFile(path);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return true;
			}
			
			System.out.println("/test read <path>");
			return true;
		}
		
		return true;
	}
}