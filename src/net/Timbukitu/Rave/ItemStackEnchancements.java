package net.Timbukitu.Rave;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemStackEnchancements {
	
	private static ItemStackEnchancements ise;
	
	public ItemStackEnchancements(){
    }
 
    public static ItemStackEnchancements getInstance(){
        if(ise == null)
            ise = new ItemStackEnchancements();
 
        return ise; // NOT THREAD SAFE!
    }
	
    public static ItemStack setItemName(ItemStack is, String name){
        ItemMeta m = is.getItemMeta();
        m.setDisplayName(name);
        is.setItemMeta(m);
        return is;
    }
    
    public static ItemStack makeDye(DyeColor color){
    	@SuppressWarnings("deprecation")
		ItemStack is = new ItemStack(Material.INK_SACK, 1, (short)0, color.getData());
    	return is;
    }
    
    public static ItemStack getGameBook(){
    	ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
    	BookMeta bm = (BookMeta)book.getItemMeta();
    	bm.setAuthor("Timbukitu");
    	bm.setTitle(ChatColor.DARK_PURPLE + "RAVE");
    	ArrayList<String> pages = new ArrayList<String>();
    	pages.add(ChatColor.DARK_PURPLE + "RAVE" + ChatColor.BLACK + " by Timbukitu\nThe fast paced game in which you are to run around franticly avoiding lasers, thus impeading your untimely death\n\n\nVERY EARLY ALPHA");
    	pages.add(ChatColor.BOLD + "How To Play" + ChatColor.RESET + "\n- Wait For Enough Players\n- When The Game Starts Lookup And Notice The Lasers Ready To Strike Down\n-Avoid Being Hit By A Falling Laser\n-Win!");
    	pages.add(ChatColor.BOLD + "Coming Soon" + ChatColor.RESET + "\n-Leaderboards\n-Better Lasers\n-Bug Fixes\n-Improvements\n-Major Code Improvements\n-Make the code less likely to enduce vomiting, nausea, diharrea, and severe aches, sometimes resulting in death or mutilation.");
    	bm.setPages(pages);
    	book.setItemMeta(bm);
    	return book;
    }
    
}
