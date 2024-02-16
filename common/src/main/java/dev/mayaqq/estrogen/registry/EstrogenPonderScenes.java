package dev.mayaqq.estrogen.registry;

import com.simibubi.create.foundation.ponder.PonderRegistrationHelper;
import dev.mayaqq.estrogen.registry.ponders.CentrifugeStoryboard;

public class EstrogenPonderScenes {

    static final PonderRegistrationHelper HELPER = new PonderRegistrationHelper("estrogen");

    public static void register() {
        HELPER.forComponents(EstrogenBlocks.CENTRIFUGE)
                .addStoryBoard("centrifuge/intro", CentrifugeStoryboard::centrifugeStoryboardIntro)
                .addStoryBoard("centrifuge/basic", CentrifugeStoryboard::centrifugeStoryboardBasic);
    }
}
