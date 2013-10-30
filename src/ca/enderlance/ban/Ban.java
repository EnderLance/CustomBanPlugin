package ca.enderlance.ban;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Ban extends JavaPlugin
{
	public static Ban plugin;
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if(cmd.getName().equalsIgnoreCase("ban"))
		{
			if(sender instanceof Player)
			{
				Player player = (Player) sender;
				{
					if(player.hasPermission("ban.ban"))
					{
						if(args.length == 2)
						{
							if(!(args[1] == null)){
								Player tplayer = player.getServer().getPlayer(args[0]);
								tplayer.setBanned(true);
								tplayer.kickPlayer("Reason: " + args[1]);
								player.sendMessage(ChatColor.GOLD + "You have banned " + tplayer.getName() + " for " + args[1]);
								logToFile("- Player " + tplayer.getName() + " was banned by " + player.getName() + " for " + args[1]);
							}
						}
					}
				}	
			}
			if(!(sender instanceof Player))
			{
				if(args.length == 2)
				{
					if(!(args[1] == null))
					{
						Player tplayer = Bukkit.getServer().getPlayer(args[0]);
						tplayer.setBanned(true);
						tplayer.kickPlayer("Reason: " + args[1]);
						logToFile("- Player " + tplayer.getName() + " was banned by the console for " + args[1]);
					}
				}
			}
		}
		return false;
	}
	
	public boolean onCommand1(CommandSender sender, Command cmd, String label, String[] args)
	{
		if(cmd.getName().equalsIgnoreCase("pardon"))
		{
			if(sender instanceof Player)
			{
				Player player = (Player) sender;
				if(player.hasPermission("ban.pardon"))
				{
					if(!(args[1] == null))
					{
						OfflinePlayer tplayer = player.getServer().getOfflinePlayer(args[0]);
						tplayer.setBanned(false);
						player.sendMessage(ChatColor.GOLD + "You have pardoned " + tplayer.getName());
						logToFile("- Player " + tplayer.getName() + " was pardoned by " + player.getName());
					}
				}	
			}
			if(!(sender instanceof Player))
			{
				if(!(args[1] == null))
				{
					OfflinePlayer tplayer = Bukkit.getServer().getOfflinePlayer(args[0]);
					tplayer.setBanned(false);
					logToFile("- Player " + tplayer.getName() + " was pardoned by the console.");
				}
			}
		}
		return false;
	}
	
	//Method below from https://forums.bukkit.org/threads/making-a-log-file-for-your-plugins.85430/
	
	public void logToFile(String message)
	 
    {
 
        try
        {
            File dataFolder = getDataFolder();
            if(!dataFolder.exists())
            {
                dataFolder.mkdir();
            }
 
            File saveTo = new File(getDataFolder(), "banLog.txt");
            if (!saveTo.exists())
            {
                saveTo.createNewFile();
            }
 
 
            FileWriter fw = new FileWriter(saveTo, true);
 
            PrintWriter pw = new PrintWriter(fw);
 
            pw.println(message);
 
            pw.flush();
 
            pw.close();
 
        } catch (IOException e)
        {
 
            e.printStackTrace();
 
        }
 
    }
}
