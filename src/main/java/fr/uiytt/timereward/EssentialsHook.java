package fr.uiytt.timereward;

import org.bukkit.entity.Player;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.IUser;

public class EssentialsHook {

	public static boolean checkEssentialsAFK(Player player) {
		Essentials ess = (Essentials) Main.getInstance().getServer().getPluginManager().getPlugin("Essentials");
		IUser user = ess.getUser(player);
		return user.isAfk();
	}
	
}
