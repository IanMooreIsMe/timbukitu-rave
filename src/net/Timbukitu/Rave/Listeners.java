package net.Timbukitu.Rave;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.PlayerInventory;

public final class Listeners implements Listener {
	
	private org.bukkit.event.player.PlayerLoginEvent.Result LoginResult;
	private org.bukkit.event.Event.Result EventResult;
	String raveTag = ChatColor.GOLD + "[" + ChatColor.DARK_PURPLE + "RAVE" + ChatColor.GOLD + "] ";
	int minPlayers = 2;
	
    @SuppressWarnings("static-access")
	@EventHandler
    public void onLogin(PlayerLoginEvent event) {
    	Player player = event.getPlayer();
        ArenaManager.getManager();
		if(ArenaManager.isInGame()){
        	StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("Sorry ");
			stringBuilder.append(player.getDisplayName());
			stringBuilder.append(", but a game is currently in progress!");
			event.disallow(LoginResult.KICK_OTHER, stringBuilder.toString());
        } else {
        	event.allow();
        }
    }
    
    @EventHandler
    public void onJoin(PlayerJoinEvent event) throws InterruptedException{
    	ArenaManager.getManager();
    	Player player = event.getPlayer();
    	PlayerInventory inventory = player.getInventory();
    	new ItemStackEnchancements();
    	ItemStackEnchancements.getInstance();
    	event.setJoinMessage(raveTag + player.getDisplayName() + " has joined the lobby! ");
    	try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
    	player.sendMessage(raveTag + ChatColor.YELLOW + "Hello " + player.getDisplayName() + ", and welcome to Rave!");
    	try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
    	player.sendMessage(raveTag + ChatColor.YELLOW + "The game of avoiding deadly lasers!");
    	try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
    	player.sendMessage(raveTag + ChatColor.YELLOW + "The game is currently in lobby.");
    	Bukkit.broadcastMessage(raveTag + Bukkit.getOnlinePlayers().length + "/" + minPlayers + " required players for game to begin.");
    	player.teleport(new Location(player.getWorld(), 0, 75, 0));
    	player.setMaxHealth(2);
    	player.setMaximumAir(2);
    	player.setExp(.999f);
    	inventory.clear();
        inventory.setHeldItemSlot(0);
        inventory.setItem(0, ItemStackEnchancements.getGameBook());
        inventory.setItem(8, ItemStackEnchancements.setItemName(ItemStackEnchancements.makeDye(DyeColor.SILVER), ChatColor.RED + "Quit To HUB"));
        
        if (Bukkit.getOnlinePlayers().length>minPlayers-1 && !ArenaManager.inGame){
        	Bukkit.broadcastMessage(raveTag + "Minimum players need for game to begin (" + minPlayers + ") has been reached!");
        	Bukkit.broadcastMessage(raveTag + "Game will begin in less than a minute.");
        	Bukkit.broadcastMessage(raveTag + "Quiting now will result in lossing.");
        	ArenaManager.setInGame();
        	ArenaManager.startCountdown();
        }
    }
    
    @EventHandler
    public void onQuit(PlayerQuitEvent event) throws InterruptedException {
    	Player player = event.getPlayer();
        ArenaManager.getManager();
		if(ArenaManager.isInGame()){
        	event.setQuitMessage(raveTag + player.getDisplayName() + " has been obliterated!");
        	if(Bukkit.getOnlinePlayers().length<2) ArenaManager.winGame();
        } else {
        	event.setQuitMessage(raveTag + player.getDisplayName() + " has left the lobby!");
        }
    }
    
    @EventHandler
    public void onKick(PlayerKickEvent event) throws InterruptedException{
    	Player player = event.getPlayer();
        ArenaManager.getManager();
		if(ArenaManager.isInGame()){
        	event.setLeaveMessage(raveTag + player.getDisplayName() + " has been obliterated!");
        } else {
        	event.setLeaveMessage(raveTag + player.getDisplayName() + " has left the lobby!");
        }
    }
    
    @EventHandler
	public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event){
		event.setFormat(ChatColor.BLUE + event.getPlayer().getDisplayName() + ChatColor.GRAY + " » " + ChatColor.WHITE + event.getMessage());
	}
    
	@EventHandler
	public void onBlockBreakEvent(BlockBreakEvent event){
		if(!event.getPlayer().hasPermission("build")){event.setCancelled(true);}
	}
	
	@EventHandler
	public void onHangingBreakByEntityEvent(HangingBreakByEntityEvent event){
		Player player = (Player) event.getRemover();
		if(!player.hasPermission("build")){event.setCancelled(true);}
	}
	
	@EventHandler
	public void onBlockPlaceEvent(BlockPlaceEvent event){
		if(!event.getPlayer().hasPermission("build")){event.setCancelled(true);}
	}
	
	@EventHandler
	public void onHangingPlaceEvent(HangingPlaceEvent event){
		if(!event.getPlayer().hasPermission("build")){event.setCancelled(true);}
	}
	
	@EventHandler
	public void onEntityDamageEvent(EntityDamageEvent event){
		Entity e = event.getEntity();
		DamageCause d = event.getCause();
		DamageCause l = DamageCause.LIGHTNING;
		if(e instanceof Player && !d.equals(l)) {
			event.setCancelled(true);
		} else if(e instanceof Player && d.equals(l)) {
			Player p = (Player) e;
			p.setHealth(0);
		}
	}
	
	@EventHandler
	public void onCreatureSpawnEvent(CreatureSpawnEvent event){
		if(event.getSpawnReason() == SpawnReason.NATURAL){
			event.setCancelled(true);
		}
	}
    
    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (event.isCancelled())
            return;
        event.getItemDrop().remove();
        event.setCancelled(true);
    }
 
    @SuppressWarnings("static-access")
	@EventHandler
    public void onClickInventory(InventoryClickEvent event){
        event.setResult(EventResult.DENY);
    }
    
    @EventHandler
    public void onPlayerUse(PlayerInteractEvent event){
        Player p = event.getPlayer();
        new ItemStackEnchancements();
    	ItemStackEnchancements.getInstance();
        if(p.getItemInHand().getType().equals(Material.INK_SACK) && (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR))){
            p.kickPlayer("Kicked to HUB");
        }

    }
    
    @EventHandler
    public void onFoodLevelChangeEvent(FoodLevelChangeEvent event){
    	event.setFoodLevel(20);
    }
}
