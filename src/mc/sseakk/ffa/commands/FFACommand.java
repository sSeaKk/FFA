package mc.sseakk.ffa.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import mc.sseakk.ffa.game.Arena;
import mc.sseakk.ffa.game.FFAPlayer;
import mc.sseakk.ffa.mainpackage.ArenasManager;
import mc.sseakk.ffa.mainpackage.FFA;
import mc.sseakk.ffa.util.Messages;

public class FFACommand implements CommandExecutor{
	private FFA plugin;
	
	public FFACommand(FFA plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) {
			Messages.sendConsoleMessage("This command can be only executed in game");
			return true;
		}
		
		Player player = (Player) sender;
		ArenasManager am = FFA.getArenasManager();
		
		if(player.isOp() || player.hasPermission("ffa.admin")) {
			
			if(args.length > 0) {
				
				if(args[0].equalsIgnoreCase("create")) {
					if(args.length > 1) {
						String name = args[1];
						if(am.getArena(name) == null) {
							Arena arena = new Arena(name);
							am.addArena(arena);
							Messages.sendPlayerMessage(player, "&aArena created");
							return true;
						}
						
						Messages.sendPlayerMessage(player, "&cThat arena already exist!");
						return true;
					}
				}
				
				if(args[0].equalsIgnoreCase("delete")) {
					if(args.length > 1) {
						String name = args[1];
						if(am.getArena(name) != null) {
							am.removeArena(name);
							Messages.sendPlayerMessage(player, "&aArena deleted");
							return true;
						}
						
						Messages.sendPlayerMessage(player, "&cThat arena doesn't exist!");
						return true;
					}
				}
				
				if(args[0].equalsIgnoreCase("setSpawn")) {
					if(args.length > 1) {
						String name = args[1];
						Arena arena = am.getArena(name);
						if(arena != null) {
							arena.setSpawn(player.getLocation().clone());
							Messages.sendPlayerMessage(player, "&aSpawn setted");
							return true;
						}
						
						Messages.sendPlayerMessage(player, "&cThat arena doesn't exist!");
						return true;
					}
				}
			}
		}
		
		if(args.length > 0) {
			if(args[0].equalsIgnoreCase("join")) {
				if(args.length > 1 ){
					String name = args[1];
					Arena arena = am.getArena(name);
					if(arena != null) {
						if(arena.isEnabled()) {
							FFAPlayer fp = new FFAPlayer(player);
							arena.addPlayer(fp);
							return true;
						}
						
						Messages.sendPlayerMessage(player, "&6This arena is not enabled");
						return true;
					}
					
					Messages.sendPlayerMessage(player, "&cThis arena doesn't exist");
					return true;
				}
				
				Messages.sendPlayerMessage(player, "&cYou have to use correctly the command: \n"
						+ "&6/ffa join <arena>");
				return true;
			}
		}
		
		if(args.length > 0) {
			if(args[0].equalsIgnoreCase("leave")) {
				Arena arena = FFA.getArenasManager().getPlayerArena(player.getName());
				if(arena != null){
					arena.removePlayer(arena.getFFAPlayer(player.getName()));
					return true;
				}
				
				Messages.sendPlayerMessage(player, "&cYou are not in an arena");
				return true;
			}
		}
		
		if(player.isOp() || player.hasPermission("ffa.admin")) {
			//TODO: Mensaje de argumentos para admins (HACER ESTO CUANDO TODOS LOS COMANDOS ESTEN LISTOS)
		}
		
		Messages.sendPlayerMessage(player, "&aUser Commands: \n"
				+ "&6/ffa join <arena> \n"
				+ "&6/ffa leave");
		return true;
	}
}