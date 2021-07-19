package mc.sseakk.ffa.mainpackage;

import mc.sseakk.ffa.commands.FFACommand;
import mc.sseakk.ffa.commands.TestCommand;

public class CommandManager {
	
	public CommandManager(){
		FFA.getInstance().getCommand("ffa").setExecutor(new FFACommand(FFA.getInstance()));
		FFA.getInstance().getCommand("test").setExecutor(new TestCommand());
	}
}