package me.monowii.mwParkourReborn.commands;

import me.monowii.mwParkourReborn.Checkpoint;
import me.monowii.mwParkourReborn.Parkour;
import me.monowii.mwParkourReborn.ParkourReborn;
import me.monowii.mwParkourReborn.managers.ParkoursManager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ParkourCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		Player p = null;
		if (sender instanceof Player) p = (Player) sender;
		
		if (p == null) {
			sender.sendMessage("§cYou can't use parkour commands in console !");
			return false;
		}
		
		if (args.length == 0)
		{
			p.sendMessage("§8----[ mwParkourReborn v"+ParkourReborn.getInstance().getDescription().getVersion()+" by monowii ]----");
			p.sendMessage("§8/pk new <parkourName> <authors> - Create a parkour");
			p.sendMessage("§8/pk delete <parkourName> - Delete a parkour");
			p.sendMessage("§8/pk add <parkourName> - Add a checkpoint");
			p.sendMessage("§8/pk rem <parkourName> - Remove last checkpoint");
			p.sendMessage("§8/pk info <parkourName> - Parkour informations");
			p.sendMessage("§8/pk list - List parkours");
		}
		else
		{
			if (args[0].equalsIgnoreCase("new") && args.length == 3 && p.hasPermission("mwParkourReborn.admin"))
			{	
				String parkourName = args[1];
				String authors = args[2];
				
				if (ParkoursManager.getParkours().containsKey(parkourName))
				{
					p.sendMessage("§cThis parkour name is already taken !");
					return false;
				}
				
				ParkoursManager.createParkour(parkourName, authors, p.getLocation());
				
				p.sendMessage("§aParkour created !");
			}
			else if (args[0].equalsIgnoreCase("delete") && args.length == 2 && p.hasPermission("mwParkourReborn.admin"))
			{
				String parkourName = args[1];
				
				if (!ParkoursManager.getParkours().containsKey(parkourName))
				{
					p.sendMessage("§cThis parkour don't exist !");
					return false;
				}
				
				ParkoursManager.deleteParkour(parkourName);
				
				p.sendMessage("§aParkour deleted !");
			}
			else if (args[0].equalsIgnoreCase("add") && args.length == 2 && p.hasPermission("mwParkourReborn.admin"))
			{
				String parkourName = args[1];
				
				if (!ParkoursManager.getParkours().containsKey(parkourName))
				{
					p.sendMessage("§cThis parkour don't exist !");
					return false;
				}
				
				Checkpoint checkpoint = ParkoursManager.getCheckpoint(p.getLocation());
				
				if (checkpoint != null && checkpoint.getParkourName().equals(parkourName))
				{
					p.sendMessage("§cThere is already a checkpoint here from this parkour !");
					return false;
				}
				
				ParkoursManager.addCheckpoint(parkourName, p.getLocation());
				
				p.sendMessage("§aCheckpoint added !");
			}
			else if (args[0].equalsIgnoreCase("rem") && args.length == 2 && p.hasPermission("mwParkourReborn.admin"))
			{
				String parkourName = args[1];
				
				if (!ParkoursManager.getParkours().containsKey(parkourName))
				{
					p.sendMessage("§cThis parkour don't exist!");
					return false;
				}
				
				if (ParkoursManager.removeLastCheckpoint(parkourName))
					p.sendMessage("§aLast checkpoint removed !");
				else
					p.sendMessage("§cThere no checkpoints on this parkour !");
			}
			else if (args[0].equalsIgnoreCase("info") && args.length == 2)
			{
				Parkour parkour = ParkoursManager.getParkour(args[1]);
				
				if (parkour == null)
				{
					p.sendMessage("§cThis parkour don't exist!");
					return false;
				}
				
				p.sendMessage("§d--[ Parkour info: " + parkour.getName() + " by "+parkour.getAuthors() + " ]--");
				p.sendMessage("§dcheckpoints: " + parkour.getCheckpoints().size());
			}
			else if (args[0].equalsIgnoreCase("list"))
			{
				p.sendMessage("§3--[ Parkours list ]--");
				
				for (Parkour parkour : ParkoursManager.getParkours().values())
				{
					p.sendMessage(parkour.getName() + " by " + parkour.getAuthors() + " | " + parkour.getCheckpoints().size() + " checkpoints");
				}
			}
		}
		
		return false;
	}

}
