package balloontest;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	private static Main main;

	public static Main getMain() {
		return main;
	}

	public void onEnable() {
		main = this;
		Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
		getCommand("balloon").setExecutor(new BalloonCommand());
	}

}
