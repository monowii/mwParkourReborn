package me.monowii.mwParkourReborn;

import org.bukkit.Location;

public class Utils 
{
	public static boolean isInteger(String number)
	{
		try
		{
			Integer.parseInt(number);
		}
		catch (NumberFormatException e)
		{
			return false;
		}
		
		return true;
	}
	
	public static String formatTime(long time)
	{
		// 2h:37m:2s:30ms // 25m:32s:89ms // 16s:7ms
		
        int ms1 = (int) time;
        int secs = ms1 / 1000;
        int mins = secs / 60;
        int hours = mins / 60;

        hours %= 24;
        secs %= 60;
        mins %= 60;
        ms1 %= 1000;

        String hoursS = ""+hours;
        String secsS = ""+secs;
        String minsS = ""+mins;
        String ms2 = ""+ms1;

        if (secs < 10)
            secsS = "0" + secsS;
        if (mins < 10)
            minsS = "0" + minsS;
        if (hours < 10)
            hoursS = "0" + hoursS;

        return hoursS + "h:" + minsS + "m:" + secsS + "s:" + ms2 + "ms";
	}
	
	public static boolean isSameLocation(Location loc1, Location loc2)
	{
		if (loc1.getWorld().getUID() != loc2.getWorld().getUID()) {
			return false;
		}
		
		if (loc1.getBlockX() != loc2.getBlockX())
			return false;
		if (loc1.getBlockY() != loc2.getBlockY())
			return false;
		if (loc1.getBlockZ() != loc2.getBlockZ())
			return false;
		
		return true;
	}
}
