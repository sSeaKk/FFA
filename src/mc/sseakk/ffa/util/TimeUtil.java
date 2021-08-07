package mc.sseakk.ffa.util;

public class TimeUtil {
	public static Long lastDamagerCooldown() {
		return System.currentTimeMillis() + (5 * 1000);
	}
	
	public static Long enderPearlDeathCooldown() {
		return System.currentTimeMillis() + 2500;
	}
	
	public static Long assistrerCooldown() {
		return System.currentTimeMillis() + 5000;
	}
	
	public static Long currentTime() {
		return System.currentTimeMillis();
	}
}