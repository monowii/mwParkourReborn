package me.monowii.mwParkourReborn.listeners;

import me.monowii.mwParkourReborn.Parkour;
import me.monowii.mwParkourReborn.managers.ParkoursManager;

import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener
{
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e)
	{
		if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
			return;
		
		if (e.getClickedBlock().getType() != Material.WALL_SIGN && e.getClickedBlock().getType() != Material.SIGN_POST)
			return;
		
		Player p = e.getPlayer();
		Sign s = (Sign) e.getClickedBlock().getState();
		
		if (!s.getLine(0).equalsIgnoreCase("§8[mwParkourRe]"))
			return;

		if (s.getLine(1).equalsIgnoreCase("§9Tp"))
		{
			Parkour parkour = ParkoursManager.getParkour(s.getLine(2));
			
			if (parkour != null)
			{
				if (p.hasPermission("mwParkourReborn.tp.*") || p.hasPermission("mwParkourReborn.tp."+parkour.getName()))
					p.teleport(parkour.getSpawn());
				else
					p.sendMessage("§cYou don't have permission to teleport to this parkour !");
				
				e.setCancelled(true);
			}
			else
			{
				p.sendMessage("§cThis parkour don't exist !");
			}
		}
	}
}
