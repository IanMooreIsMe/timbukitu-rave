package net.Timbukitu.Rave;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_7_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class GameEffects {
	
	private static GameEffects ge;
	
	public GameEffects(){
    }
 
    public static GameEffects getInstance(){
        if(ge == null)
            ge = new GameEffects();
 
        return ge; // NOT THREAD SAFE!
    }
	
	public static void obliteratePlayer(Player oPlayer){
		for (Player p : Bukkit.getOnlinePlayers()){
			Location l = oPlayer.getLocation();
			new CreateEffect().createEffect((CraftPlayer) p, "hugeexplosion", l, 0, 0, 0, 1f, 1);
			p.playSound(l, Sound.EXPLODE, 100, 1);
		}
	}
}
