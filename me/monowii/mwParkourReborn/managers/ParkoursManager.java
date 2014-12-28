package me.monowii.mwParkourReborn.managers;

import java.util.HashMap;
import java.util.Map.Entry;

import me.monowii.mwParkourReborn.Checkpoint;
import me.monowii.mwParkourReborn.Parkour;
import me.monowii.mwParkourReborn.ParkourReborn;
import me.monowii.mwParkourReborn.Utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

public class ParkoursManager
{
	private static HashMap<String, Parkour> parkours = new HashMap<String, Parkour>(); 
	
	public static void loadParkours()
	{
		parkours.clear();
		
		FileConfiguration cfg = ParkourReborn.getInstance().getConfig();
		
		if (!cfg.isConfigurationSection("parkours")) {
			return;
		}
		
		for (String parkourName : cfg.getConfigurationSection("parkours").getKeys(false))
		{
			String rootNode = "parkours." + parkourName;
			
			String authors = cfg.getString(rootNode + ".authors");
			
			World w = Bukkit.getWorld(cfg.getString(rootNode+".spawn.world"));
			
			Location spawn = new Location(w, cfg.getDouble(rootNode + ".spawn.x"), cfg.getDouble(rootNode + ".spawn.y"), cfg.getDouble(rootNode + ".spawn.z"));
			spawn.setPitch((float)cfg.getDouble(rootNode + ".spawn.pitch"));
			spawn.setYaw((float)cfg.getDouble(rootNode + ".spawn.yaw"));
			
			Parkour parkour = new Parkour(parkourName, authors, spawn);
			
			if (cfg.isConfigurationSection(rootNode+".checkpoints"))
			{
				for (String checkpointId : cfg.getConfigurationSection(rootNode+".checkpoints").getKeys(false))
				{
					rootNode = "parkours." + parkourName + ".checkpoints." + checkpointId;
					
					Location loc = new Location(w, cfg.getDouble(rootNode+".x"), cfg.getDouble(rootNode + ".y"), cfg.getDouble(rootNode + ".z"));
					loc.setPitch((float)cfg.getDouble(rootNode + ".pitch"));
					loc.setYaw((float)cfg.getDouble(rootNode + ".yaw"));
					
					Checkpoint checkpoint = new Checkpoint(parkourName, Integer.valueOf(checkpointId));
					
					parkour.getCheckpoints().put(loc, checkpoint);
				}
			}
			
			parkours.put(parkourName, parkour);
		}
	}
	
	public static void createParkour(String parkourName, String authors, Location spawn)
	{
		parkours.put(parkourName, new Parkour(parkourName, authors, spawn));
		
		FileConfiguration cfg = ParkourReborn.getInstance().getConfig();
		String rootNode = "parkours."+parkourName;
		
		cfg.set(rootNode+".authors", authors);
		
		cfg.set(rootNode+".spawn.world", spawn.getWorld().getName());
		cfg.set(rootNode+".spawn.x", spawn.getX());
		cfg.set(rootNode+".spawn.y", spawn.getY());
		cfg.set(rootNode+".spawn.z", spawn.getZ());
		cfg.set(rootNode+".spawn.pitch", spawn.getPitch());
		cfg.set(rootNode+".spawn.yaw", spawn.getYaw());
		
		ParkourReborn.getInstance().saveConfig();
	}
	
	public static void deleteParkour(String parkourName)
	{
		parkours.remove(parkourName);
		
		FileConfiguration cfg = ParkourReborn.getInstance().getConfig();
		cfg.set("parkours."+parkourName, null);
		ParkourReborn.getInstance().saveConfig();
	}
	
	public static void addCheckpoint(String parkourName, Location loc)
	{
		Checkpoint checkpoint = new Checkpoint(parkourName, getParkour(parkourName).getCheckpoints().size());
		parkours.get(parkourName).getCheckpoints().put(loc, checkpoint);
		
		FileConfiguration cfg = ParkourReborn.getInstance().getConfig();
		String rootNode = "parkours."+parkourName+".checkpoints."+checkpoint.getId();
		cfg.set(rootNode+".x", loc.getX());
		cfg.set(rootNode+".y", loc.getY());
		cfg.set(rootNode+".z", loc.getZ());
		cfg.set(rootNode+".pitch", loc.getPitch());
		cfg.set(rootNode+".yaw", loc.getYaw());
		ParkourReborn.getInstance().saveConfig();
	}
	
	public static boolean removeLastCheckpoint(String parkourName)
	{
		Parkour parkour = parkours.get(parkourName); 
		
		if (parkour.getCheckpoints().size() == 0)
			return false;
		
		Location lastCheckpointLocation = null;
		Checkpoint lastCheckpoint = null;
		for (Entry<Location, Checkpoint> entry : parkour.getCheckpoints().entrySet()) {
			if (lastCheckpoint == null || entry.getValue().getId() > lastCheckpoint.getId()) {
				lastCheckpoint = entry.getValue();
				lastCheckpointLocation = entry.getKey();
			}
		}
		
		parkour.getCheckpoints().remove(lastCheckpointLocation);
		
		FileConfiguration cfg = ParkourReborn.getInstance().getConfig();
		cfg.set("parkours."+parkourName+".checkpoints."+lastCheckpoint.getId(), null);
		ParkourReborn.getInstance().saveConfig();
		
		return true;
	}
	
	public static HashMap<String, Parkour> getParkours() {
		return parkours;
	}
	
	public static Parkour getParkour(String parkourName) {
		return parkours.get(parkourName);
	}
	
	public static Checkpoint getCheckpoint(Location location)
	{
		for (Entry<String, Parkour> parkourEntry : parkours.entrySet())
		{
			for (Entry<Location, Checkpoint> checkpointEntry : parkourEntry.getValue().getCheckpoints().entrySet())
			{
				if (Utils.isSameLocation(checkpointEntry.getKey(), location))
					return checkpointEntry.getValue();
			}
		}
		
		return null;
	}
}
