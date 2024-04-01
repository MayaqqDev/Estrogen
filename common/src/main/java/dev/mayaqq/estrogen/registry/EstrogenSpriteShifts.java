package dev.mayaqq.estrogen.registry;

import com.simibubi.create.foundation.block.connected.AllCTTypes;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.block.connected.CTSpriteShifter;
import dev.mayaqq.estrogen.Estrogen;

public class EstrogenSpriteShifts {
    public static CTSpriteShiftEntry DORMANT_DREAM_BLOCK = omni("dormant_dream_block/dormant_dream_block");

    private static CTSpriteShiftEntry omni(String name) {
        return CTSpriteShifter.getCT(AllCTTypes.OMNIDIRECTIONAL, Estrogen.id("block/" + name), Estrogen.id("block/" + name + "_connected"));
    }
}
