package dev.mayaqq.estrogen.registry;

import dev.mayaqq.estrogen.registry.advancements.triggers.InsertJarTrigger;
import net.minecraft.advancements.CriteriaTriggers;

public class EstrogenAdvancementCriteria {

    public static final EstrogenAdvancementCriteria CRITERIAS = new EstrogenAdvancementCriteria();

    public static InsertJarTrigger INSERT_JAR = CriteriaTriggers.register(new InsertJarTrigger());

    public void init() {}
}
