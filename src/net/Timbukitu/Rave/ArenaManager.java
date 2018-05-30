package net.Timbukitu.Rave;

import net.minecraft.server.v1_7_R3.EntityLightning;
import net.minecraft.server.v1_7_R3.PacketPlayOutSpawnEntityWeather;

import org.bukkit.craftbukkit.v1_7_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_7_R3.CraftWorld;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

public class ArenaManager{
	
    private static ArenaManager am;
    static boolean inGame = false;
    static String raveTag = ChatColor.GOLD + "[" + ChatColor.DARK_PURPLE + "RAVE" + ChatColor.GOLD + "] ";
 
    public ArenaManager(){
    }
 
    //we want to get an instance of the manager to work with it statically
    public static ArenaManager getManager(){
        if(am == null)
            am = new ArenaManager();
 
        return am; // NOT THREAD SAFE!
    }

    public static void setInGame(){
    	inGame = true;
    }
    
    public static boolean isInGame(){
    	return inGame;
    }
    
    public static void winGame(){
    	inGame = false;
    	Bukkit.broadcastMessage(raveTag + "You have won!");
    	Bukkit.broadcastMessage(raveTag + "In future versions you will get a reward!");
    	try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
		for(Player players : Bukkit.getOnlinePlayers()){
			players.kickPlayer("Kicked to HUB");
		}
		Bukkit.broadcastMessage(ChatColor.GOLD + "RAVE SERVER RESTARTING");
		Bukkit.getScheduler().cancelAllTasks();
		Bukkit.reload();
    }
    
    public static void beginGame(){
    	for(Player player : Bukkit.getOnlinePlayers()){
    		player.teleport(new Location(player.getWorld(), 100, 200, 0));
    		player.setExp(1f);
    		player.setTicksLived(1);
    	}
    	
    	BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("Rave"), new Runnable() {
            @Override
            public void run() {
            	Bukkit.broadcastMessage(raveTag + "The game has begun! RUN FOR YOUR LIVES!");
            	BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
                scheduler.scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("Rave"), new Runnable() {
                    @Override
                    public void run() {
                    	World world = Bukkit.getWorlds().get(0);
                        int rand = (int) Math.round(Math.random()*40);
                        Location location = new Location(world, 80+rand, 60, -20+rand);
                        EntityLightning el = new EntityLightning(((CraftWorld) world).getHandle(), location.getX(), location.getY(), location.getZ(), true);
                        PacketPlayOutSpawnEntityWeather packet = new PacketPlayOutSpawnEntityWeather(el);
                        for (Player p : Bukkit.getOnlinePlayers()){
                            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
                            Location l = p.getLocation();
                            p.playSound(l, Sound.NOTE_BASS_DRUM, 100, 2);
                            if(location.getBlockX()==l.getBlockX()&&location.getBlockZ()==l.getBlockZ()){
                        		GameEffects.getInstance();
                        		GameEffects.obliteratePlayer(p);
                        		p.sendMessage(raveTag + ChatColor.YELLOW + "Rave Failed! You have been obliterated!");
                        		try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
                        		p.kickPlayer("Kicked to HUB");
                        		try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
                        		if(Bukkit.getOnlinePlayers().length<2) ArenaManager.winGame();
                            }
                        }
                    }
                }, 20L, 20L);
            }
        }, 20L);  
    }

    public static void startCountdown(){
    	//Devise a much better system
    	BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("Rave"), new Runnable() {
            @Override
            public void run() {
            	Bukkit.broadcastMessage(raveTag + "Game will begin in less 30 seconds");
                BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
                scheduler.scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("Rave"), new Runnable() {
                    @Override
                    public void run() {
                    	Bukkit.broadcastMessage(raveTag + "Game will begin in less than 20 seconds");
                    	BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
                        scheduler.scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("Rave"), new Runnable() {
                            @Override
                            public void run() {
                            	Bukkit.broadcastMessage(raveTag + "Game will begin in less than 10 seconds");
                            	BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
                                scheduler.scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("Rave"), new Runnable() {
                                    @Override
                                    public void run() {
                                    	Bukkit.broadcastMessage(raveTag + "Game will begin in less than 5 seconds");
                                    	BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
                                        scheduler.scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("Rave"), new Runnable() {
                                            @Override
                                            public void run() {
                                            	Bukkit.broadcastMessage(raveTag + "Game will begin in less than 4 seconds");
                                            	BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
                                                scheduler.scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("Rave"), new Runnable() {
                                                    @Override
                                                    public void run() {
                                                    	Bukkit.broadcastMessage(raveTag + "Game will begin in less than 3 seconds");
                                                    	BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
                                                        scheduler.scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("Rave"), new Runnable() {
                                                            @Override
                                                            public void run() {
                                                            	Bukkit.broadcastMessage(raveTag + "Game will begin in less than 2 seconds");
                                                            	BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
                                                                scheduler.scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("Rave"), new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                    	Bukkit.broadcastMessage(raveTag + "Game will begin in less than 1 second");
                                                                    	beginGame();
                                                                    }
                                                                }, 20L);
                                                            }
                                                        }, 20L);
                                                    }
                                                }, 20L);
                                            }
                                        }, 20L);
                                    }
                                }, 100L);
                            }
                        }, 200L);
                    }
                }, 200L);
            }
        }, 600L);
    }
}
