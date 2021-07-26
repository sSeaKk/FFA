package mc.sseakk.ffa.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import mc.sseakk.ffa.game.Arena;
import mc.sseakk.ffa.game.ArenaStatus;
import mc.sseakk.ffa.game.FFAPlayer;
import mc.sseakk.ffa.mainpackage.ArenasManager;
import mc.sseakk.ffa.mainpackage.FFA;
import mc.sseakk.ffa.util.Messages;

public class FFACommand implements CommandExecutor{
	
	public FFACommand() {
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) {
			Messages.sendConsoleMessage("Este comando solo puede ser ejecutado dentro del juego");
			return true;
		}
		
		Player player = (Player) sender;
		ArenasManager am = FFA.getArenasManager();
		
		if(player.isOp() || player.hasPermission("ffa.admin")) {
			
			if(args.length > 0) {
				
				if(args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("crear")) {
					if(args.length > 1) {
						String name = args[1];
						if(am.getArena(name) == null) {
							Arena arena = new Arena(name, player);
							am.addArena(arena);
							Messages.sendPlayerMessage(player, "&aArena created");
							return true;
						}
						
						Messages.sendPlayerMessage(player, "&cThat arena already exist!");
						return true;
					}
				}
				
				if(args[0].equalsIgnoreCase("delete") || args[0].equalsIgnoreCase("eliminar")) {
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
				
				if(args[0].equalsIgnoreCase("setSpawn") || args[0].equalsIgnoreCase("determinarSpawn")) {
					if(args.length > 1) {
						String name = args[1];
						Arena arena = am.getArena(name);
						if(arena != null) {
							if(arena.getSpawn() != null) {
								arena.setSpawn(player.getLocation().clone());
								Messages.sendPlayerMessage(player, "&aSpawn setted");
								return true;
							}
							
							Messages.sendPlayerMessage(player, "&cEsta arena no tiene spawn!");
							return true;
						}
						
						Messages.sendPlayerMessage(player, "&cThat arena doesn't exist!");
						return true;
					}
				}
				
				if(args[0].equalsIgnoreCase("setStatus") || args[0].equalsIgnoreCase("determinarEstado")) {
					if(args.length > 1) {
						String name = args[1];
						Arena arena = am.getArena(name);
						if(args.length > 2) {
							String status = args[2];
							
							if(status.equalsIgnoreCase("activado") || status.equalsIgnoreCase("enabled")) {
								arena.setStatus(ArenaStatus.ENABLED);
								Messages.sendPlayerMessage(player, "&aArena activada");
								return true;
							}
							
							if(status.equalsIgnoreCase("desactivado") || status.equalsIgnoreCase("disabled")) {
								arena.setStatus(ArenaStatus.DISABLED);
								Messages.sendPlayerMessage(player, "&aArena desactivada");
								return true;
							}
							
							Messages.sendPlayerMessage(player, "&cEntrada incorrecta. Usa 'activado' o 'desactivado'!");
							return true;
						}
						
						Messages.sendPlayerMessage(player, "&cComando erroneo. Usa /ffa status <arena> <activado/desactivado>");
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
						
						Messages.sendPlayerMessage(player, "&6Esta arena no esta activada");
						return true;
					}
					
					Messages.sendPlayerMessage(player, "&cEsta arena no existe");
					return true;
				}
				
				Messages.sendPlayerMessage(player, "&cUsa correctamente el comando: \n"
						+ "&6/ffa join <arena>");
				return true;
			}
		}
		
		if(args.length > 0) {
			if(args[0].equalsIgnoreCase("leave")) {
				Arena arena = FFA.getArenasManager().getPlayerArena(player.getName());
				if(arena != null){
					arena.removePlayer(arena.getFFAPlayer(player.getName()));
					Messages.sendPlayerMessage(player, "&6Saliste de " + arena.getName());
					return true;
				}
				
				Messages.sendPlayerMessage(player, "&cNo estas en una arena");
				return true;
			}
		}
		
		
		Messages.sendPlayerMessage(player, "&aUser Commands: \n"
				+ "&6/ffa join <arena> \n"
				+ "&6/ffa leave");
		return true;
	}
}