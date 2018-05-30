package net.Timbukitu.Rave;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class Rave extends JavaPlugin{
	
	static String raveTag = ChatColor.GOLD + "[" + ChatColor.DARK_PURPLE + "RAVE" + ChatColor.GOLD + "] "; 
    private static Rave rave;
 
    public Rave(){
    }

    public static Rave getInstance(){
        if(rave == null)
            rave = new Rave();
        return rave;
    }
	
	@Override
	public void onEnable(){
		new ArenaManager();
        ArenaManager.getManager();
        getServer().getPluginManager().registerEvents(new Listeners(), this);
        Bukkit.broadcastMessage(ChatColor.GOLD + "RAVE SERVER STARTED");
	}
	
	@Override
	public void onDisable(){}
	
}
