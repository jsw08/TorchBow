package io.github.jsw08.torcharrow;

import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class TorchArrowListener implements Listener {
    public static NamespacedKey key = new NamespacedKey(TorchArrow.getPlugin(TorchArrow.class), "isTorchArrowItem");

    @EventHandler
    public void onEntityShootBow(EntityShootBowEvent e) {
        if (e.getEntityType() != EntityType.PLAYER || !TorchArrowItem.isTorchArrow(e.getConsumable())) return;
        Projectile arrow = (Projectile) e.getProjectile();
        PersistentDataContainer PDC = arrow.getPersistentDataContainer();
        PDC.set(key, PersistentDataType.BOOLEAN, true);
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent e) {
        if (
                !e.getEntity().getPersistentDataContainer().has(key, PersistentDataType.BOOLEAN) ||
                Boolean.FALSE.equals(e.getEntity().getPersistentDataContainer().get(key, PersistentDataType.BOOLEAN))
        ) return;
        if (e.getHitBlockFace() == null || e.getHitBlock() == null) return;

        Location loc = e.getHitBlock().getLocation();
        World world = loc.getWorld();
        Block block = world.getBlockAt(loc);
        BlockFace blockF = e.getHitBlockFace();

        Block torchBlock = block.getRelative(blockF);
        if (torchBlock.getType() != Material.AIR) return;

        if (blockF == BlockFace.DOWN) return;
        else if (blockF == BlockFace.UP) {
            torchBlock.setType(Material.TORCH);
        } else {
            Directional torchF = (Directional) Material.WALL_TORCH.createBlockData();
            torchF.setFacing(blockF);
            torchBlock.setBlockData(torchF);
        }

        e.getEntity().remove();
    }

    @EventHandler
    public void onPlayer(PlayerJoinEvent e) {
        e.getPlayer().getInventory().setItemInMainHand(new TorchArrowItem().asQuantity(64));
    }
}
