package eu.Wundark.BadPotion;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class BPPlayerListener implements Listener {

	public static BadPotion pl;

	public BPPlayerListener(BadPotion instance) {
		pl = instance;
	}

	public Material[] TileEntity = { Material.BED, Material.BED_BLOCK,
			Material.BREWING_STAND, Material.CHEST, Material.DISPENSER,
			Material.ENCHANTMENT_TABLE, Material.LEVER, Material.STONE_BUTTON,
			Material.TRAP_DOOR, Material.DIODE_BLOCK_OFF,
			Material.DIODE_BLOCK_ON, Material.WOOD_DOOR, Material.IRON_DOOR, Material.CAULDRON, Material.FENCE_GATE, Material.CAKE_BLOCK };

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_AIR
				|| event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (event.getPlayer().getItemInHand().getTypeId() == 373) {

				if (event.getPlayer().hasPermission("badpotion.bypass")
						|| in_array(TileEntity, event.getClickedBlock()))
					return;

				boolean badPotionUsed = false;
				int potionDurability = event.getPlayer().getItemInHand()
						.getDurability();

				if (in_array(pl.PotionsOptions, potionDurability)) {
					badPotionUsed = true;
				}

				if (badPotionUsed
						|| Boolean.parseBoolean(String.valueOf(pl.config
								.get("blockall")))) {
					event.getPlayer().sendMessage(
							(String) pl.config.get("blockmsg"));
					event.setCancelled(true);
					event.getPlayer().updateInventory();
				}
			}
			if (event.getPlayer().getItemInHand().getTypeId() == 384) {
			  
			  if (event.getPlayer().hasPermission("badpotion.bypass")
            || in_array(TileEntity, event.getClickedBlock()))
          return;
			  
        if (Boolean.parseBoolean(String.valueOf(pl.config.get("blockall"))) ||
            Boolean.parseBoolean(String.valueOf(pl.config.get("blockexperiencepotion")))) {
          event.getPlayer().sendMessage(
              (String) pl.config.get("blockmsg"));
          event.setCancelled(true);
          event.getPlayer().updateInventory();
        }
			}
		}
	}

	public boolean in_array(Map<Integer, Boolean> potionList, int needle) {
		if (potionList.containsKey(needle)) {
			return potionList.get(needle);
		} else {
			return false;
		}
	}

	public boolean in_array(Material[] list, Block contains) {
		if (contains == null)
			return false;

		for (int i = 0; i < list.length; i++) {
			if (list[i] == contains.getType()) {
				return true;
			}
		}
		return false;
	}
}
