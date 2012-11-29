package eu.Wundark.BadPotion;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class BPPlayerListener implements Listener
{

	public static BadPotion pl;

	public BPPlayerListener(BadPotion instance)
	{
		pl = instance;
	}

	public Material[] TileEntity =
	{ Material.BED, Material.BED_BLOCK, Material.BREWING_STAND, Material.CHEST,
			Material.DISPENSER, Material.ENCHANTMENT_TABLE, Material.LEVER,
			Material.STONE_BUTTON, Material.TRAP_DOOR,
			Material.DIODE_BLOCK_OFF, Material.DIODE_BLOCK_ON,
			Material.WOOD_DOOR, Material.IRON_DOOR, Material.CAULDRON,
			Material.FENCE_GATE, Material.CAKE_BLOCK, Material.BEACON};

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
		{
			Player player = event.getPlayer();
			
			if (event.getPlayer().getItemInHand().getTypeId() == 373)
			{
				int potionDurability = event.getPlayer().getItemInHand().getDurability();
				
				if (player.hasPermission("badpotion.bypass") || player.hasPermission("badpotion.bypass." + potionDurability) || in_array(TileEntity, event.getClickedBlock()))
				{
					return;
				}

				boolean badPotionUsed = false;

				if (in_array(pl.PotionsOptions, potionDurability))
				{
					badPotionUsed = true;
				}
				
				boolean potionblocked = false;

				if (badPotionUsed || Boolean.parseBoolean(String.valueOf(pl.config.get("blockall"))))
				{
					potionblocked = true;
				}
				
				// v4.0: Feature added by 'clusidc'
		        if (Boolean.parseBoolean(String.valueOf(pl.config.get("blockall"))) || Boolean.parseBoolean(String.valueOf(pl.config.get("blockexperiencepotion")))) 
		        {
		        	potionblocked = true;
		        }
		        
		        if(potionblocked)
		        {
					event.getPlayer().sendMessage((String) pl.config.get("blockmsg"));
					event.setCancelled(true);
					event.getPlayer().updateInventory();
		        }
			}
		}
	}

	public boolean in_array(Map<Integer, Boolean> potionList, int needle)
	{
		if (potionList.containsKey(needle))
		{
			return potionList.get(needle);
		} else
		{
			return false;
		}
	}

	public boolean in_array(Material[] list, Block contains)
	{
		if (contains == null)
			return false;

		for (int i = 0; i < list.length; i++)
		{
			if (list[i] == contains.getType())
			{
				return true;
			}
		}
		return false;
	}
}
