package dev.mayaqq.estrogen.registry;

import dev.mayaqq.estrogen.registry.advancements.triggers.InsertJarTrigger;
import dev.mayaqq.estrogen.registry.advancements.triggers.KilledWithEffectTrigger;
import net.minecraft.advancements.CriteriaTriggers;

public class EstrogenAdvancementCriteria {

    public static final EstrogenAdvancementCriteria CRITERIAS = new EstrogenAdvancementCriteria();

    public static InsertJarTrigger INSERT_JAR = CriteriaTriggers.register(new InsertJarTrigger());
    public static KilledWithEffectTrigger KILLED_WITH_EFFECT = CriteriaTriggers.register(new KilledWithEffectTrigger());

    public void init() {}
}
