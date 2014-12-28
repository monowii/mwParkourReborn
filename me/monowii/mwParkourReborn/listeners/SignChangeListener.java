package me.monowii.mwParkourReborn.listeners;

import me.monowii.mwParkourReborn.Parkour;
import me.monowii.mwParkourReborn.managers.ParkoursManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignChangeListener implements Listener
{
	@EventHandler
	public void onSignChange(SignChangeEvent e)
	{
		Player p = e.getPlayer();
		
		if (e.getLine(0).equalsIgnoreCase("[pk]"))
		{
			if (p.hasPermission("mwParkourReborn.admin"))
			{
				if (e.getLine(1).equalsIgnoreCase("info"))
				{
					Parkour parkour = ParkoursManager.getParkour(e.getLine(2));
					
					if (parkour != null)
					{
						e.setLine(0, "§8[mwParkourRe]");
						e.setLine(1, "§3Info");
						e.setLine(2, parkour.getName() + " by");
						e.setLine(3, parkour.getAuthors());
					}
					else
					{
						p.sendMessage("§cThis parkour don't exist !");
						e.setCancelled(true);
					}
				}
				else if (e.getLine(1).equalsIgnoreCase("tp"))
				{
					Parkour parkour = ParkoursManager.getParkour(e.getLine(2));
					
					if (parkour != null)
					{
						e.setLine(0, "§8[mwParkourRe]");
						e.setLine(1, "§9Tp");
						e.setLine(2, parkour.getName());
					}
					else
					{
						p.sendMessage("§cThis parkour don't exist !");
						e.setCancelled(true);
					}
				}
			}
			else
			{
				p.sendMessage("§cYou don't have permission to place parkour sign !");
				e.setCancelled(true);
			}
		}
	}
}
