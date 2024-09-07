package dev.mayaqq.estrogen.integrations.newage;

import com.simibubi.create.foundation.ponder.PonderRegistrationHelper;
import dev.mayaqq.estrogen.registry.EstrogenBlocks;
import dev.mayaqq.estrogen.registry.EstrogenItems;
import org.antarcticgardens.newage.content.generation.GenerationPonder;

public class CreateNewAgeCompat {
    public static void registerPonderScenes(PonderRegistrationHelper helper) {
        helper.addStoryBoard(EstrogenBlocks.DORMANT_DREAM_BLOCK.getId(), "generation", GenerationPonder::ponder);
    }
}