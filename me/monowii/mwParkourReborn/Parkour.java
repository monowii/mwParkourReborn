package me.monowii.mwParkourReborn;

import java.util.HashMap;

import org.bukkit.Location;

public class Parkour {
	
	private String name;
	private String authors;
	private Location spawn;
	private HashMap<Location, Checkpoint> checkpoints;
	
	public Parkour(String name, String authors, Location spawn)
	{
		this.setName(name);
		this.setAuthors(authors);
		this.setSpawn(spawn);
		checkpoints = new HashMap<Location, Checkpoint>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthors() {
		return authors;
	}

	public void setAuthors(String authors) {
		this.authors = authors;
	}

	public Location getSpawn() {
		return spawn;
	}

	public void setSpawn(Location spawn) {
		this.spawn = spawn;
	}
	
	public Checkpoint getCheckpoint(Location loc)
	{
		for (Location checkpoint : checkpoints.keySet())
		{
			if (Utils.isSameLocation(loc, checkpoint))
				return checkpoints.get(checkpoint);
		}
		
		return null;
	}
	
	public HashMap<Location, Checkpoint> getCheckpoints() {
		return checkpoints;
	}
}
