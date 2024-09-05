package dev.mayaqq.estrogen.client.registry;

import com.simibubi.create.foundation.block.connected.AllCTTypes;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.block.connected.CTSpriteShifter;
import com.simibubi.create.foundation.block.connected.SimpleCTBehaviour;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.client.EstrogenClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class EstrogenSpriteShifts {

    public static final CTSpriteShiftEntry DORMANT_DREAM_BLOCK = omni("dormant_dream_block/dormant_dream_block");

    @Environment(EnvType.CLIENT)
    public static final EstrogenClient.CTModelProvider DORMANT_DREAM_BLOCK_PROVIDER = new EstrogenClient.CTModelProvider(new SimpleCTBehaviour(DORMANT_DREAM_BLOCK));

    private static CTSpriteShiftEntry omni(String name) {
        return CTSpriteShifter.getCT(AllCTTypes.OMNIDIRECTIONAL, Estrogen.id("block/" + name), Estrogen.id("block/" + name + "_connected"));
    }

}
