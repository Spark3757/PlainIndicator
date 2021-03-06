package ru.spark.plainindicator.listener;

import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import ru.spark.plainindicator.BeautyIndicator;

public class CombatListener implements Listener {
    private BeautyIndicator beautyIndicator;

    public CombatListener(BeautyIndicator beautyIndicator) {
        this.beautyIndicator = beautyIndicator;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityHitByEntity(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof LivingEntity || e.getEntity() instanceof ArmorStand))
            return;
        if (!beautyIndicator.getCombatController().isHitByItself())
            beautyIndicator.getCombatController().onHit(e.getEntity());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityHit(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof LivingEntity || e.getEntity() instanceof ArmorStand))
            return;
        if (beautyIndicator.getCombatController().isHitByItself())
            beautyIndicator.getCombatController().onHit(e.getEntity());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityHitByEntityCheck(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof LivingEntity || e.getEntity() instanceof ArmorStand))
            return;
        if (!beautyIndicator.getCombatController().isHitByItself()) {
            LivingEntity livingEntity = (LivingEntity) e.getEntity();
            if (livingEntity.getHealth() <= 0)
                beautyIndicator.getCombatController().removeFromCombat(livingEntity);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityHitCheck(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof LivingEntity))
            return;
        if (beautyIndicator.getCombatController().isHitByItself()) {
            LivingEntity livingEntity = (LivingEntity) e.getEntity();
            if (livingEntity.getHealth() <= 0)
                beautyIndicator.getCombatController().removeFromCombat(livingEntity);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDeath(EntityDeathEvent e) {
        beautyIndicator.getCombatController().removeFromCombat(e.getEntity());
    }

    @EventHandler
    public void onEntityAim(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            if (e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.BOW) || e.getPlayer().getInventory().getItemInOffHand().getType().equals(Material.BOW)) {
                if (e.getPlayer().getInventory().contains(Material.ARROW)) {
                    new BukkitRunnable() {
                        public void run() {
                            LivingEntity livingEntity = beautyIndicator.getPlayerController().getEntityLookingAt(e.getPlayer());
                            if (livingEntity != null)
                                beautyIndicator.getCombatController().onHit(livingEntity);
                        }
                    }.runTaskAsynchronously(beautyIndicator);
                }
            }
        }
    }
}
