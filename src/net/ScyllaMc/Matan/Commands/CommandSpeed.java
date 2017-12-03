package net.ScyllaMc.Matan.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.ScyllaMc.Matan.MelonCore.MelonCore;
import net.ScyllaMc.Matan.MelonCore.Msg.Message;
import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;
import net.md_5.bungee.api.ChatColor;

public class CommandSpeed {

	private float getMoveSpeed(String moveSpeed) {
		float userSpeed;

		userSpeed = Float.parseFloat(moveSpeed);
		if (userSpeed > 13.0F) {
			userSpeed = 13.0F;
		} else if (userSpeed < 1.0E-4F) {
			userSpeed = 1.0E-4F;
		}

		return userSpeed;
	}

	private float getRealMoveSpeed(float userSpeed, boolean isFly) {
		float defaultSpeed = isFly ? 0.1F : 0.2F;
		float maxSpeed = 1.0F;

		if (userSpeed < 1.0F) {
			return defaultSpeed * userSpeed;
		}

		float ratio = (userSpeed - 1.0F) / 9.0F * (maxSpeed - defaultSpeed);
		return ratio + defaultSpeed;
	}

	public void run(CommandSender sender, String[] args) throws Exception {
		if (!(sender instanceof Player)) {
			return;
		}

		Player op = (Player) sender;
		MelonPlayer p = MelonPlayer.getInstanceOfPlayer(op);
		
		if (!p.hasPermission("server.speed")) {
			p.sendMessage(Message.GLOBAL_COMMAND_NOPERM);
			return;
		}
		if (args.length < 1) {
			p.sendMessage(Message.GLOBAL_COMMAND_INVALIDUSE);
			return;
		}

		float speed = getMoveSpeed(args[0]);

		if (op.isFlying()) {
			op.setFlySpeed(getRealMoveSpeed(speed, true));
			p.sendMessage(MelonCore.prefix + ChatColor.GRAY + "Flying speed set to " + speed);
		} else {
			op.setWalkSpeed(getRealMoveSpeed(speed, false));
			p.sendMessage(MelonCore.prefix + ChatColor.GRAY + "Walking speed set to " + speed);
		}
	}

}
