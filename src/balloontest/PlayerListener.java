package balloontest;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerListener implements Listener {

	private static PlayerListener instance = new PlayerListener();

	Main plugin = Main.getMain();
	Messages msg = Messages.getInstance();

	public static PlayerListener getInstance() {
		return instance;
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (player.getItemInHand() != null) {
				ItemStack is = player.getItemInHand();
				if (is.hasItemMeta()) {
					ItemMeta im = is.getItemMeta();
					String dname = im.getDisplayName();
					if (dname.equalsIgnoreCase("Toggle Balloon")) {
						event.setCancelled(true);
						Methods.getInstance().toggleBalloon(player);
						player.updateInventory();
					}
				}
			}
		}
	}

	@EventHandler
	public void EntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (event.getDamager().getCustomName() != null) {
			if (event.getDamager().getCustomName().equalsIgnoreCase("balloon")) {
				event.setCancelled(true);
			}
		}
	}
}