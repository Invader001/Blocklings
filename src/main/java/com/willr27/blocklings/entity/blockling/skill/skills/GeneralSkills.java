package com.willr27.blocklings.entity.blockling.skill.skills;

import com.willr27.blocklings.client.gui.GuiTextures;
import com.willr27.blocklings.client.gui.controls.skills.SkillControl;
import com.willr27.blocklings.entity.blockling.attribute.BlocklingAttributes;
import com.willr27.blocklings.entity.blockling.skill.Skill;
import com.willr27.blocklings.entity.blockling.skill.SkillGroup;
import com.willr27.blocklings.entity.blockling.skill.info.*;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

/**
 * The general skills.
 */
public class GeneralSkills
{
    public static final SkillInfo HEAL = new SkillInfo("e6361ca8-a0c5-4a64-8be9-6928a98a4594",
            new SkillGeneralInfo(Skill.Type.OTHER, "general.heal"),
            new SkillDefaultsInfo(Skill.State.UNLOCKED),
            new SkillRequirementsInfo(new HashMap<BlocklingAttributes.Level, Integer>() {{ put(BlocklingAttributes.Level.TOTAL, 10); }}),
            new SkillGuiInfo(0, 50, SkillControl.ConnectionType.SINGLE_LONGEST_FIRST, 0xa8f4a1, new SkillGuiInfo.SkillIconTexture(GuiTextures.GENERAL_ICONS, 0, 0)));

    public static final SkillInfo PACKLING = new SkillInfo("5cd54257-954f-4962-b248-99f58fb11d5d",
            new SkillGeneralInfo(Skill.Type.OTHER, "general.packling"),
            new SkillDefaultsInfo(Skill.State.UNLOCKED),
            new SkillRequirementsInfo(new HashMap<BlocklingAttributes.Level, Integer>() {{ put(BlocklingAttributes.Level.TOTAL, 25); }}),
            new SkillGuiInfo(-50, 0, SkillControl.ConnectionType.SINGLE_LONGEST_FIRST, 0xcca58a, new SkillGuiInfo.SkillIconTexture(GuiTextures.GENERAL_ICONS, 1, 0)));

    public static final SkillInfo ARMADILLO = new SkillInfo("28ae60b1-1e8a-4c73-b1a1-5519be35d0ea",
            new SkillGeneralInfo(Skill.Type.OTHER, "general.armadillo"),
            new SkillDefaultsInfo(Skill.State.UNLOCKED),
            new SkillRequirementsInfo(new HashMap<BlocklingAttributes.Level, Integer>() {{ put(BlocklingAttributes.Level.TOTAL, 50); }}),
            new SkillGuiInfo(50, 0, SkillControl.ConnectionType.SINGLE_LONGEST_FIRST, 0xa8924f, new SkillGuiInfo.SkillIconTexture(GuiTextures.GENERAL_ICONS, 2, 0)))
    {
        @Override
        @Nonnull
        public List<SkillInfo> parents()
        {
            return Collections.singletonList(PACKLING);
        }
    };

    public static final SkillInfo SPEED_1 = new SkillInfo("157b1dae-e1e5-4ba7-9cb2-32e417b311ae",
            new SkillGeneralInfo(Skill.Type.STAT, "general.speed_1"),
            new SkillDefaultsInfo(Skill.State.UNLOCKED),
            new SkillRequirementsInfo(new HashMap<BlocklingAttributes.Level, Integer>() {{ put(BlocklingAttributes.Level.TOTAL, 50); }}),
            new SkillGuiInfo(-100, -50, SkillControl.ConnectionType.SINGLE_LONGEST_FIRST, 0x662522, new SkillGuiInfo.SkillIconTexture(GuiTextures.GENERAL_ICONS, 3, 0)))
    {
        @Override
        public boolean onTryBuy(@Nonnull Skill skill)
        {
            skill.blockling.getStats().moveSpeedSkillSpeedModifier.setValue(1.05f, false);

            return true;
        }
    };

    public static final SkillInfo SPEED_2 = new SkillInfo("77bedcd6-596c-4ebd-bb90-b5da1c5a5559",
            new SkillGeneralInfo(Skill.Type.STAT, "general.speed_2"),
            new SkillDefaultsInfo(Skill.State.LOCKED),
            new SkillRequirementsInfo(new HashMap<BlocklingAttributes.Level, Integer>() {{ put(BlocklingAttributes.Level.TOTAL, 100); }}),
            new SkillGuiInfo(0, -50, SkillControl.ConnectionType.SINGLE_LONGEST_FIRST, 0xb06d1f, new SkillGuiInfo.SkillIconTexture(GuiTextures.GENERAL_ICONS, 4, 0)))
    {
        @Override
        public boolean onTryBuy(@Nonnull Skill skill)
        {
            skill.blockling.getStats().moveSpeedSkillSpeedModifier.setValue(1.1f, false);

            return true;
        }

        @Override
        @Nonnull
        public List<SkillInfo> parents()
        {
            return Collections.singletonList(SPEED_1);
        }
    };

    public static final SkillInfo SPEED_3 = new SkillInfo("57b70a86-e2e0-4b0e-b7a1-f299915f03dd",
            new SkillGeneralInfo(Skill.Type.STAT, "general.speed_3"),
            new SkillDefaultsInfo(Skill.State.LOCKED),
            new SkillRequirementsInfo(new HashMap<BlocklingAttributes.Level, Integer>() {{ put(BlocklingAttributes.Level.TOTAL, 150); }}),
            new SkillGuiInfo(100, -50, SkillControl.ConnectionType.SINGLE_LONGEST_FIRST, 0x03595e, new SkillGuiInfo.SkillIconTexture(GuiTextures.GENERAL_ICONS, 5, 0)))
    {
        @Override
        public boolean onTryBuy(@Nonnull Skill skill)
        {
            skill.blockling.getStats().moveSpeedSkillSpeedModifier.setValue(1.2f, false);

            return true;
        }

        @Override
        @Nonnull
        public List<SkillInfo> parents()
        {
            return Collections.singletonList(SPEED_2);
        }
    };

    public static final SkillInfo AUTOSWITCH = new SkillInfo("b431f534-40eb-47c9-9cfe-5192b0492704",
            new SkillGeneralInfo(Skill.Type.OTHER, "general.autoswitch"),
            new SkillDefaultsInfo(Skill.State.UNLOCKED),
            new SkillRequirementsInfo(new HashMap<BlocklingAttributes.Level, Integer>() {{ put(BlocklingAttributes.Level.TOTAL, 100); }}),
            new SkillGuiInfo(0, -100, SkillControl.ConnectionType.SINGLE_LONGEST_FIRST, 0xd8d8d8, new SkillGuiInfo.SkillIconTexture(GuiTextures.GENERAL_ICONS, 6, 0)));

    public static final List<Function<SkillGroup, Skill>> SKILLS = new ArrayList<Function<SkillGroup, Skill>>()
    {{
        add(group -> new Skill(HEAL, group));
        add(group -> new Skill(PACKLING, group));
        add(group -> new Skill(ARMADILLO, group));
        add(group -> new Skill(SPEED_1, group));
        add(group -> new Skill(SPEED_2, group));
        add(group -> new Skill(SPEED_3, group));
        add(group -> new Skill(AUTOSWITCH, group));
    }};
}
