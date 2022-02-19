package com.willr27.blocklings.goal.goals;

import com.willr27.blocklings.entity.entities.blockling.BlocklingEntity;
import com.willr27.blocklings.task.BlocklingTasks;
import com.willr27.blocklings.goal.goals.target.BlocklingAtackHurtByTargetGoal;

import javax.annotation.Nonnull;
import java.util.UUID;

/**
 * Attacks the last entity to attack the blockling using melee.
 */
public class BlocklingMeleeAttackHurtByGoal extends BlocklingMeleeAttackGoal<BlocklingAtackHurtByTargetGoal>
{
    /**
     * The associated target goal.
     */
    @Nonnull
    private final BlocklingAtackHurtByTargetGoal targetGoal;

    /**
     * @param id the id associated with the goal's task.
     * @param blockling the blockling.
     * @param tasks the blockling tasks.
     */
    public BlocklingMeleeAttackHurtByGoal(@Nonnull UUID id, @Nonnull BlocklingEntity blockling, @Nonnull BlocklingTasks tasks)
    {
        super(id, blockling, tasks);

        targetGoal = new BlocklingAtackHurtByTargetGoal(this);
    }

    @Override
    @Nonnull
    public BlocklingAtackHurtByTargetGoal getTargetGoal()
    {
        return targetGoal;
    }
}
