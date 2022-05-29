package mc.sseakk.ffa.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import org.bukkit.ChatColor;

import net.md_5.bungee.api.chat.TextComponent;

public class TextUtil {
	
	public static String colorText(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}
	
	public static String decimalFormat(Double decimal) {
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator('.'); 
		DecimalFormat df = new DecimalFormat("0.00", dfs);
		
		return df.format(decimal);
	}
	
	public static TextComponent stringToTextComponent(String message) {
		return new TextComponent(ChatColor.translateAlternateColorCodes('&', message));
	}
}
