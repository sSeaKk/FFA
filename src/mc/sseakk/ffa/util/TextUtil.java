package mc.sseakk.ffa.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import org.bukkit.ChatColor;

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
}
