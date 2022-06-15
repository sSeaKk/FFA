package mc.sseakk.ffa.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import mc.sseakk.ffa.ArenasManager;
import mc.sseakk.ffa.FFA;
import mc.sseakk.ffa.game.Arena;
import mc.sseakk.ffa.game.Warrior;
import mc.sseakk.ffa.game.Arena.ArenaStatus;
import mc.sseakk.ffa.gui.menu.MainMenu;
import mc.sseakk.ffa.util.Messages;
import mc.sseakk.ffa.util.TextUtil;

public class FFACommand implements CommandExecutor{
	
	public FFACommand() {
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) {
			Messages.infoMessage("Este comando solo puede ser ejecutado dentro del juego");
			return true;
		}
		
		Player player = (Player) sender;
		ArenasManager am = FFA.getArenasManager();
		
		//Player commands
		if(args.length > 0) {
			
			if(args[0].equalsIgnoreCase("join")) {
				if(args.length > 1 ) {
					String name = args[1];
					Arena arena = am.getArena(name);
					if(arena != null) {
						if(arena.isEnabled()) {
							arena.addPlayer(player);
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
			
			if(args[0].equalsIgnoreCase("leave")) {
				Arena arena = FFA.getArenasManager().getPlayerArena(player.getName());
				if(arena != null){
					arena.removePlayer(player);
					Messages.sendPlayerMessage(player, "&6Saliste de " + arena.getName());
					player.updateInventory();
					return true;
				}
				
				Messages.sendPlayerMessage(player, "&cNo estas en una arena");
				return true;
			}
			
			if(args[0].equalsIgnoreCase("stats")) {
				Arena arena = FFA.getArenasManager().getPlayerArena(player.getName());
				if(arena != null) {
					Warrior stats = FFA.getWarriorManager().get(player.getName());
					Messages.sendPlayerMessage(player, "&6Estadisticas:"
													 + "\nAsesinatos: " + stats.getKills()
													 + "\nMuertes: " + stats.getDeaths()
													 + "\nAsistencias: " + stats.getAssists()
													 + "\n"
													 + "\nKDA: " + stats.getKdaRatio()
													 + "\nKDR: " + stats.getKdRatio()
													 + "\n"
													 + "\nDeath Streak: " + stats.getDeathStreak()
													 + "\nMax Kill Streak: " + stats.getMaxKillStreak()
													 + "\nMax Death Streak: " + stats.getMaxDeathStreak()
												     + "\n"
												     + "\nMax Damage Given: " + TextUtil.decimalFormat(stats.getMaxDamageGiven())
												     + "\nMax Damage Taken: " + TextUtil.decimalFormat(stats.getMaxDamageTaken()));
					
					return true;
				}
				
				Messages.sendPlayerMessage(player, "&cNo estas en una arena");
				return true;
			}
			
			if(args[0].equalsIgnoreCase("profile")) {
				Arena arena = FFA.getArenasManager().getPlayerArena(player.getName());
				if(arena != null) {
					new MainMenu(player);
					return true;
				}
				
				Messages.sendPlayerMessage(player, "&cNo estas en una arena");
				return true;
			}
		}
		
		//Admin commands
		if(args.length > 0) {
			
			if(args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("crear")) {
				if(player.isOp() || player.hasPermission("ffa.admin")) {
					if(args.length > 1) {
						String name = args[1];
						if(am.getArena(name) == null) {
							Arena arena = new Arena(name, player);
							am.addArena(arena);
							Messages.sendPlayerMessage(player, "&aArena creada");
							return true;
						}
						
						Messages.sendPlayerMessage(player, "&cEsa arena ya existe!");
						return true;
					}
					
					Messages.sendPlayerMessage(player, "&cComando erroneo. Usa /ffa create <arena>");
					return true;
				}
				
				Messages.sendPlayerMessage(player, "&4No tienes permiso para ejecutar este comando.");
				return true;
			}
			
			if(args[0].equalsIgnoreCase("delete") || args[0].equalsIgnoreCase("eliminar")) {
				if(player.isOp() || player.hasPermission("ffa.admin")) {
					if(args.length > 1) {
						String name = args[1];
						if(am.getArena(name) != null) {
							am.removeArena(name);
							Messages.sendPlayerMessage(player, "&aArena borrada");
							return true;
						}
						
						Messages.sendPlayerMessage(player, "&cEsa arena no existe!");
						return true;
					}
					
					Messages.sendPlayerMessage(player, "&cComando erroneo. Usa /ffa delete <arena>");
					return true;
				}
				
				Messages.sendPlayerMessage(player, "&4No tienes permiso para ejecutar este comando.");
				return true;
			}
			
			if(args[0].equalsIgnoreCase("setSpawn") || args[0].equalsIgnoreCase("determinarSpawn")) {
				if(player.isOp() || player.hasPermission("ffa.admin")) {
					if(args.length > 1) {
						String name = args[1];
						Arena arena = am.getArena(name);
						if(arena != null) {
							if(arena.getSpawn() == null) {
								arena.setSpawn(player.getLocation().clone());
								Messages.sendPlayerMessage(player, "&aSpawn determinado");
								return true;
							}
							
							Messages.sendPlayerMessage(player, "&cEsta arena no tiene spawn!");
							return true;
						}
						
						Messages.sendPlayerMessage(player, "&cEsa arena no existe!");
						return true;
					}
					
					Messages.sendPlayerMessage(player, "&cComando erroneo. Usa /ffa setSpawn <arena>");
					return true;
				}
				
				Messages.sendPlayerMessage(player, "&4No tienes permiso para ejecutar este comando.");
				return true;
			}
			
			if(args[0].equalsIgnoreCase("setStatus") || args[0].equalsIgnoreCase("determinarEstado")) {
				if(player.isOp() || player.hasPermission("ffa.admin")) {
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
					}
					
					Messages.sendPlayerMessage(player, "&cComando erroneo. Usa /ffa setStatus <arena> <activado/desactivado");
					return true;
				}
				
				Messages.sendPlayerMessage(player, "&4No tienes permiso para ejecutar este comando.");
				return true;
			}
			
		}
		
		//Command info
		if(player.isOp() || player.hasPermission("ffa.admin")) {
			Messages.sendPlayerMessage(player, "&cComandos de Admin:&6"
					+ "\n/ffa create <arena>"
					+ "\n/ffa delete <arena>"
					+ "\n/ffa setSpawn <arena>"
					+ "\n/ffa setStatus <estado> <arena>");
		}
		
		Messages.sendPlayerMessage(player, "&aComandos de usuario: \n"
				+ "&6/ffa join <arena> \n"
				+ "&6/ffa leave \n"
				+ "&6/ffa stats \n"
				+ "&6/ffa profile");
		return true;
	}
}