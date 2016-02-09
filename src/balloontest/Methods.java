package balloontest;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Methods {

	private static Methods instance = new Methods();

	Main plugin = Main.getMain();
	Messages msg = Messages.getInstance();

	public static Methods getInstance() {
		return instance;
	}

	public void giveBalloonItem(Player player) {
		// TODO Auto-generated method stub
		ItemStack is = new ItemStack(35, 1, (short) 14);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName("Toggle Balloon");
		is.setItemMeta(im);
		player.getInventory().addItem(is);
		msg.msg(player, "You've been given an item to toggle the balloon on/off.");

	}

	public void toggleBalloon(Player player) {
		if (Lists.hasBalloon.contains(player.getName())) {
			Lists.hasBalloon.remove(player.getName());
			List<LivingEntity> le = Lists.getBalloon.get(player.getName());
			for (LivingEntity l : le) {
				l.remove();
			}
			Lists.getBalloon.remove(player.getName());
			msg.msg(player, "Balloon toggled OFF");
		} else {
			Lists.hasBalloon.add(player.getName());
			spawnBalloon(player);
			msg.msg(player, "Balloon toggled ON");
		}

	}

	public void spawnBalloon(Player player) {
		Location ploc = player.getLocation();
		Location sloc = ploc;
		sloc.add(0, 2, 0);
		List<LivingEntity> les = new ArrayList<LivingEntity>();
		LivingEntity ent1 = (LivingEntity) Bukkit.getWorld(sloc.getWorld().getName()).spawnEntity(sloc, EntityType.BAT);
		les.add(ent1);
		ent1.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1));
		ent1.setRemoveWhenFarAway(false);
		LivingEntity ent2 = (LivingEntity) Bukkit.getWorld(sloc.getWorld().getName()).spawnEntity(sloc, EntityType.ZOMBIE);
		les.add(ent2);
		Lists.getBalloon.put(player.getName(), les);
		ent2.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1));
		ent2.setCustomName("balloon");
		ent1.setPassenger(ent2);
		ent2.setLeashHolder(player);
		follow(player);

	}

	private void follow(Player player) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			@Override
			public void run() {
				if (Lists.hasBalloon.contains(player.getName())) {
					LivingEntity bat = Lists.getBalloon.get(player.getName()).get(0);
					Location to = player.getLocation();
					to = to.add(1, 2, 1);
					Entity ent = bat.getPassenger();
					ent.eject();
					((LivingEntity) ent).getEquipment().clear();
					((LivingEntity) ent).getEquipment().setHelmet(new ItemStack(Material.WOOL));
					ent.teleport(to);
					bat.teleport(to);
					bat.setPassenger(ent);
					follow(player);
				}
			}
		}, 1);

	}

}
