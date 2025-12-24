package dev.mayaqq.estrogen.fabric.mixins;

import net.minecraft.world.entity.MobCategory;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.ArrayList;
import java.util.Arrays;

@Mixin(MobCategory.class)
public class MobCategoryMixin {
    @Shadow
    @Final
    @Mutable
    private static MobCategory[] $VALUES;


    @Unique
    private static final MobCategory MOTH = addCategory("MOTH", "moth", 50, true, true, 128);

    @Invoker("<init>")
    public static MobCategory invokeInit(String internalName, String name, int max, boolean isFriendly, boolean isPersistent, int despawnDistance) {
        throw new AssertionError();
    }

    private static MobCategory addCategory(String internalName, String name, int max, boolean isFriendly, boolean isPersistent, int despawnDistance) {
        ArrayList<MobCategory> categories = new ArrayList(Arrays.asList($VALUES));
        MobCategory instrument = invokeInit(internalName, name, max, isFriendly, isPersistent, despawnDistance);
        categories.add(instrument);
        $VALUES = categories.toArray(new MobCategory[0]);
        return instrument;
    }
}
