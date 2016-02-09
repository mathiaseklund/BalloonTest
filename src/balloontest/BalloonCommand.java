package balloontest;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BalloonCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {
		if(sender instanceof Player){
			Player player = (Player)sender;
			Methods.getInstance().giveBalloonItem(player);
		}
		return false;
	}

}
