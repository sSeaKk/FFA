package mc.sseakk.ffa.game;

import java.util.ArrayList;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import mc.sseakk.ffa.mainpackage.FFA;
import mc.sseakk.ffa.util.Messages;

public class Arena {
	
	private String name;
	private int currentPlayers;
	private Location spawn;
	private ArrayList<FFAPlayer> playerList;
	private ArenaStatus status;
	private Player configurator;
	private boolean hasFile;
	
	public Arena(String name, Player configurator) {
		this.playerList = new ArrayList<FFAPlayer>();
		this.name = name;
		this.setConfigurator(configurator);
		this.status = ArenaStatus.DISABLED;
		this.currentPlayers = 0;
		this.setHasFile(false);
		this.spawn = null;
	}
	
	public Arena() {
		this.playerList = new ArrayList<FFAPlayer>();
		this.status = ArenaStatus.DISABLED;
		this.currentPlayers = 0;
	}
	
	public void addPlayer(FFAPlayer ffaplayer) {
		if(FFA.getArenasManager().getPlayerArena(ffaplayer.getPlayer().getName()) != null) {
			Messages.sendPlayerMessage(ffaplayer.getPlayer(), "&cYa estas en una arena!");
			return;
		}
		
		if(this.spawn == null) {
			Messages.sendPlayerMessage(ffaplayer.getPlayer(), "&cLa arena no tiene spawn");
			return;
		}
		
		this.playerList.add(ffaplayer);
		ffaplayer.getPlayer().setFoodLevel(20);
		ffaplayer.getPlayer().setHealth(20);
		ffaplayer.getPlayer().setGameMode(GameMode.SURVIVAL);
		
		ffaplayer.getPlayer().getEquipment().setArmorContents(null);
		ffaplayer.getPlayer().getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
		
		ffaplayer.getPlayer().getInventory().clear();
		ffaplayer.getPlayer().getInventory().addItem(new ItemStack(Material.DIAMOND_SWORD));
		
		ffaplayer.getPlayer().teleport(spawn);
		
		Messages.sendPlayerMessage(ffaplayer.getPlayer(), "&aEntraste a " + this.name);
	}
	
	public void removePlayer(FFAPlayer ffaplayer) {
		if(this.playerList.contains(ffaplayer)) {
			Player player = ffaplayer.getPlayer();
			StoredElements stored = ffaplayer.getStored();
			
			player.getEquipment().clear();
			player.getEquipment().setArmorContents(stored.getStoredArmor());
			
			player.getInventory().clear();
			player.getInventory().setContents(stored.getStoredInventory());
			
			player.setGameMode(stored.getGamemode());
			player.setExp(stored.getStoredExp());
			player.setLevel(stored.getStoredLevel());
			player.setHealth(stored.getStoredHealth());
			player.setMaxHealth(stored.getStoredMaxHealth());
			player.setFoodLevel(stored.getStoredHunger());
			player.setFlying(ffaplayer.isFlying());
			
			player.teleport(ffaplayer.getPreviousLocation());
			this.playerList.remove(ffaplayer);
		} else {
			Messages.sendPlayerMessage(ffaplayer.getPlayer(), "&cNo estas en una arena!");
		}
	}
	
	public FFAPlayer getFFAPlayer(String playerName) {
		for(int i=0; i<playerList.size(); i++) {
			if(playerList.get(i).getPlayer().getName().equals(playerName)) {
				return playerList.get(i);
			}
		}
		return null;
	}
	
	public boolean isEnabled() {
		if(this.status.equals(ArenaStatus.ENABLED)) {
			return true;
		}
		return false;
	}
	
	public void increaseCurrentPlayers() {
		this.currentPlayers++;
	}
	
	public void decreaseCurrentPlayers() {
		this.currentPlayers--;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Location getSpawn() {
		return spawn;
	}

	public void setSpawn(Location spawn) {
		this.spawn = spawn;
	}

	public ArrayList<FFAPlayer> getPlayerList() {
		return playerList;
	}

	public ArenaStatus getStatus() {
		return status;
	}

	public void setStatus(ArenaStatus status) {
		this.status = status;
	}

	public int getCurrentPlayers() {
		return currentPlayers;
	}

	public boolean isHasFile() {
		return hasFile;
	}

	public void setHasFile(boolean hasFile) {
		this.hasFile = hasFile;
	}

	public Player getConfigurator() {
		return configurator;
	}

	public void setConfigurator(Player configurator) {
		this.configurator = configurator;
	}
}
