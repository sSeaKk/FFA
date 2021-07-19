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

public class FileManager {
	private FFA plugin;
	
	@SuppressWarnings("unused")
	private String rutaConfig;
	
	private FileConfiguration arenas = null;
	private File arenasFile = null, 
			path;
	
	
	public FileManager() {
		this.plugin = FFA.getInstance();
		registerConfig();
		registerArenas();
		this.path = null;
	}
	
	public FileConfiguration getArenas() {
		if(arenas == null) {
			reloadArenas();
		}
		return arenas;
	}
	
	public void registerConfig() {
		File config = new File(this.plugin.getDataFolder(), "config.yml");
		rutaConfig = config.getPath();
		if(!config.exists()) {
			this.plugin.getConfig().options().copyDefaults(true);
			this.plugin.saveConfig();
		}
	}
	
	@SuppressWarnings("unused")
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
	
	public void registerArenas() {
		arenasFile = new File(this.plugin.getDataFolder(), "arenas.yml");
		if(!arenasFile.exists()) {
			this.getArenas().options().copyDefaults(true);
			saveArenas();
		}
	}
	
	public void createFile(String fileName, String path) throws IOException {
        this.path = new File(plugin.getDataFolder().getPath() + path);
        if(!this.path.exists()){
            this.path.mkdirs();
        }
        
        File file = new File(this.path, fileName);
        FileWriter fw = new FileWriter(file);
        PrintWriter pw = new PrintWriter(fw);
        
        pw.close();
    }
	
	public void deleteFile(String fileName, String path){
		this.path = new File(plugin.getDataFolder().getPath() + path);
		File file = new File(this.path, fileName);
        
		if(!file.delete()) {
			System.out.println("El archivo " + "'" + fileName + "' "+ "no existe" 
					+ "\n Path: " + this.path);
			return;
		}
		
		System.out.println("Completado");
	}
	
	public void writeFile(String fileName, String path, String text) throws IOException {
		this.path = new File(plugin.getDataFolder().getPath() + path);
		File file = new File(this.path, fileName);
		
		
		if(file.exists()) {
	        FileWriter fw = new FileWriter(file);
	        PrintWriter pw = new PrintWriter(fw);
	        
	        pw.write(text);
	        pw.close();
		} else {
			System.out.println("El archivo " + "'" + fileName + "' "+ "no existe" 
								+ "\n Path: " + this.path);
		}
	}
	
	public void readFile(String path) throws Exception {
		File file = new File(plugin.getDataFolder().getPath()+path);
		Scanner sc = new Scanner(file);
		String st = "";
		
		while(sc.hasNextLine()) {
			st = sc.nextLine();
			System.out.println(st);
			
			if(st.contains("hola") || st.contains("Hola")) {
				System.out.println(true);
			} else {
				System.out.println(false);
			}
		}
	}
}