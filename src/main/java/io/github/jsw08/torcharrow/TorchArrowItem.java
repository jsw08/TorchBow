package io.github.jsw08.torcharrow;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class TorchArrowItem extends ItemStack {
    public static final NamespacedKey key = new NamespacedKey(TorchArrow.getPlugin(TorchArrow.class), "isTorchArrowItem");
    private static final NamespacedKey recipeKey = new NamespacedKey(TorchArrow.getPlugin(TorchArrow.class),"torch_arrow");
    public TorchArrowItem() {
        super(Material.ARROW, 1);
        ItemMeta meta = getItemMeta();

        meta.displayName(Component.text("Torch Arrow"));
        PersistentDataContainer PDC = meta.getPersistentDataContainer();
        PDC.set(key, PersistentDataType.BOOLEAN, true);

        setItemMeta(meta);
    }

    public static boolean isTorchArrow(ItemStack item) {
        if (item == null || item.getType() != Material.ARROW || !item.hasItemMeta()) return false;
        PersistentDataContainer PDC = item.getItemMeta().getPersistentDataContainer();
        if (!PDC.has(key)) return false;

        return Boolean.TRUE.equals(PDC.get(key, PersistentDataType.BOOLEAN));
    }

    public static void setRecipe() {
        ShapelessRecipe TorchArrowRecipe = new ShapelessRecipe(recipeKey, new TorchArrowItem());
        TorchArrowRecipe.addIngredient(2, Material.TORCH);
        TorchArrowRecipe.addIngredient(1, Material.ARROW);
        Bukkit.addRecipe(TorchArrowRecipe, false);
    }
}
