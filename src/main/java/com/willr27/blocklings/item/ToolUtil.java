package com.willr27.blocklings.item;

import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class ToolUtil
{
    private static final List<Item> WEAPONS = new ArrayList<>();
    private static final List<Item> PICKAXES = new ArrayList<>();
    private static final List<Item> AXES = new ArrayList<>();
    private static final List<Item> HOES = new ArrayList<>();
    private static final List<Item> TOOLS = new ArrayList<>();

    public static void init()
    {
        WEAPONS.clear();
        PICKAXES.clear();
        AXES.clear();
        HOES.clear();

        WEAPONS.addAll(Registry.ITEM.stream().filter(item -> item instanceof SwordItem).collect(Collectors.toList()));
        PICKAXES.addAll(Registry.ITEM.stream().filter(item -> item.getToolTypes(item.getDefaultInstance()).contains(ToolType.PICKAXE)).collect(Collectors.toList()));
        AXES.addAll(Registry.ITEM.stream().filter(item -> item.getToolTypes(item.getDefaultInstance()).contains(ToolType.AXE)).collect(Collectors.toList()));
        HOES.addAll(Registry.ITEM.stream().filter(item -> item.getToolTypes(item.getDefaultInstance()).contains(ToolType.HOE)).collect(Collectors.toList()));

        TOOLS.addAll(WEAPONS);
        TOOLS.addAll(PICKAXES);
        TOOLS.addAll(AXES);
        TOOLS.addAll(HOES);
    }

    public static boolean isWeapon(ItemStack stack)
    {
        return isWeapon(stack.getItem());
    }

    public static boolean isWeapon(Item item)
    {
        return WEAPONS.contains(item);
    }

    public static boolean isPickaxe(ItemStack stack)
    {
        return isPickaxe(stack.getItem());
    }

    public static boolean isPickaxe(Item item)
    {
        return PICKAXES.contains(item);
    }

    public static boolean isAxe(ItemStack stack)
    {
        return isAxe(stack.getItem());
    }

    public static boolean isAxe(Item item)
    {
        return AXES.contains(item);
    }

    public static boolean isHoe(ItemStack stack)
    {
        return isHoe(stack.getItem());
    }

    public static boolean isHoe(Item item)
    {
        return HOES.contains(item);
    }

    public static boolean isTool(ItemStack stack)
    {
        return isTool(stack.getItem());
    }

    public static boolean isTool(Item item)
    {
        return TOOLS.contains(item);
    }

    public static float findToolAttackSpeed(ItemStack stack)
    {
        Multimap<Attribute, AttributeModifier> multimap = stack.getAttributeModifiers(EquipmentSlotType.MAINHAND);

        for (Map.Entry<Attribute, AttributeModifier> entry : multimap.entries())
        {
            AttributeModifier attributemodifier = entry.getValue();
            UUID baseAttackSpeedAttributeId = ObfuscationReflectionHelper.getPrivateValue(Item.class, Items.ACACIA_BOAT, "BASE_ATTACK_SPEED_UUID");

            if (attributemodifier.getId() == baseAttackSpeedAttributeId)
            {
                return (float) attributemodifier.getAmount();
            }
        }

        return 0.0f;
    }

    public static float findToolBaseDamage(ItemStack stack)
    {
        Multimap<Attribute, AttributeModifier> multimap = stack.getAttributeModifiers(EquipmentSlotType.MAINHAND);

        for (Map.Entry<Attribute, AttributeModifier> entry : multimap.entries())
        {
            AttributeModifier attributemodifier = entry.getValue();
            UUID baseAttackDamageAttributeId = ObfuscationReflectionHelper.getPrivateValue(Item.class, Items.ACACIA_BOAT, "BASE_ATTACK_DAMAGE_UUID");

            if (attributemodifier.getId() == baseAttackDamageAttributeId)
            {
                return (float) attributemodifier.getAmount();
            }
        }

        return 0.0f;
    }

    public static float findToolEnchantmentDamage(ItemStack stack, CreatureAttribute creatureAttribute)
    {
        return EnchantmentHelper.getDamageBonus(stack, creatureAttribute);
    }

    public static float findToolKnockback(ItemStack stack)
    {
        return EnchantmentHelper.getItemEnchantmentLevel(Enchantments.KNOCKBACK, stack);
    }

    public static float findToolFireAspect(ItemStack stack)
    {
        return EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FIRE_ASPECT, stack);
    }

    /**
     * Returns the mining speed for the given tool against stone.
     * For reference a wooden pickaxe is 2.0f and diamond pickaxe is 8.0f;
     */
    public static float findToolMiningSpeed(ItemStack stack)
    {
        return stack.getDestroySpeed(Blocks.STONE.defaultBlockState());
    }

    /**
     * Returns the mining speed for the given tool from only its enchantments.
     */
    public static float findToolEnchantmentMiningSpeed(ItemStack stack)
    {
        int level = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_EFFICIENCY, stack);

        if (level > 0)
        {
            return level * level + 1.0f;
        }

        return 0.0f;
    }

    /**
     * Returns the mining speed for the given tool against stone, including enchantments.
     */
    public static float findToolMiningSpeedWithEnchantments(ItemStack stack)
    {
        return findToolMiningSpeed(stack) + findToolEnchantmentMiningSpeed(stack);
    }

    /**
     * Returns true if the given tool can harvest the given block.
     */
    public static boolean canToolHarvestBlock(ItemStack stack, BlockState blockState)
    {
        ToolType harvestTool = blockState.getHarvestTool();

        for (ToolType toolType : stack.getToolTypes())
        {
            if (toolType == harvestTool)
            {
                if (stack.getHarvestLevel(toolType, null, blockState) >= blockState.getHarvestLevel())
                {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Returns a list of all the enchantments on the given item.
     */
    public static List<Enchantment> findToolEnchantments(ItemStack stack)
    {
        List<Enchantment> enchantments = new ArrayList<>();
        ListNBT listNBT = stack.getEnchantmentTags();

        for (int i = 0; i < listNBT.size(); i++)
        {
            CompoundNBT tag = listNBT.getCompound(i);
            ResourceLocation enchantmentResource = ResourceLocation.tryParse(tag.getString("id"));

            if (enchantmentResource != null)
            {
                enchantments.add(Registry.ENCHANTMENT.get(enchantmentResource));
            }
        }

        return enchantments;
    }
}
