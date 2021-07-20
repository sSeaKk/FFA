package mc.sseakk.ffa.mainpackage;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import mc.sseakk.ffa.util.Messages;

public class FileManager {
	private FFA plugin;
	
	@SuppressWarnings("unused")
	private String rutaConfig;
	
	private FileConfiguration arenas = null;
	private File arenasFile = null, 
				 path,
				 file = null;
	private FileWriter filewriter = null;
	private PrintWriter printwriter = null;
	private BufferedWriter bufferedWriter = null;
	private Scanner scanner = null;
	
	public FileManager() {
		this.plugin = FFA.getInstance();
		registerConfig();
		this.path = null;
	}
  
	public void registerConfig() {
		File config = new File(this.plugin.getDataFolder(), "config.yml");
		rutaConfig = config.getPath();
		if(!config.exists()) {
			this.plugin.getConfig().options().copyDefaults(true);
			this.plugin.saveConfig();
		}
	}
	
	/*public FileConfiguration getArenas() {
		if(arenas == null) {
			reloadArenas();
		}
		return arenas;
	}
	
	/*@SuppressWarnings("unused") (ARENAS.YML no usado)
	public void reloadArenas() {
		if(arenas == null) {
			arenasFile = new File(this.plugin.getDataFolder(), "arenas.yml");
		}
		arenas = YamlConfiguration.loadConfiguration(arenasFile);
		Reader defConfigStream;
		try {
			defConfigStream = new InputStreamReader(this.plugin.getResource("arenas.yml"), "UTF8");
			if(defConfigStream == null) {
				YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
				arenas.setDefaults(defConfig);
			}
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public void saveArenas() {
		try{
			arenas.save(arenasFile);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	

	/*public void registerArenas() {
		arenasFile = new File(this.plugin.getDataFolder(), "arenas.yml");
		if(!arenasFile.exists()) {
			this.getArenas().options().copyDefaults(true);
			saveArenas();
		}
	}*/
	
	public File createFile(String path, String fileName){
        this.path = new File(plugin.getDataFolder().getPath() + path);
        if(!this.path.exists()){
            this.path.mkdirs();
        }
        
        try {
        	this.file = new File(this.path, fileName + ".txt");
        	this.filewriter = new FileWriter(file);
        	this.printwriter = new PrintWriter(this.filewriter);
        	this.bufferedWriter = new BufferedWriter(printwriter);
        	this.scanner = new Scanner(this.file);
        
        	return this.file;
        } catch(IOException e) {
        	e.printStackTrace();
        }
        
        return null;
    }
	
	public void deleteFile(String path){
		File f = new File(plugin.getDataFolder().getPath() + path);
		if(!f.delete()) {
			Messages.warningMessage("El archivo no existe! (PATH: " + this.file.getPath() + ")");
			return;
		}
	}
	
	public FileWriter getFileWriter() {
		return filewriter;
	}

	public PrintWriter getPrintWriter() {
		return printwriter;
	}
	
	public BufferedWriter getBufferedWriter() {
		return this.bufferedWriter;
	}
	
	public Scanner getScanner() {
		return this.scanner;
	}
}