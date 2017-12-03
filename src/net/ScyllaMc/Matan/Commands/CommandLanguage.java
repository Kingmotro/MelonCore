package net.ScyllaMc.Matan.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.ScyllaMc.Matan.MelonCore.Msg.Language;
import net.ScyllaMc.Matan.MelonCore.Msg.Message;
import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;

public class CommandLanguage {

	public void run(CommandSender sender, String[] args) throws Exception {
		if (!(sender instanceof Player)) {
			return;
		}

		Player op = (Player) sender;
		MelonPlayer p = MelonPlayer.getInstanceOfPlayer(op);
		
	
		if (args.length == 1) {
			Language lan = Language.fromString(args[0]);
			p.lan = lan;
			
			p.sendMessage(Message.GLOBAL_COMMAND_CHANGELAN, new String[] { lan.toString() });

			return;
		}


		p.sendMessage(Message.GLOBAL_COMMAND_INVALIDUSE);
	}

}
