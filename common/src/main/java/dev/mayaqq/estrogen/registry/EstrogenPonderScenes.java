package dev.mayaqq.estrogen.registry;

import com.simibubi.create.foundation.ponder.PonderRegistrationHelper;
import dev.mayaqq.estrogen.registry.ponders.CentrifugeStoryboard;

public class EstrogenPonderScenes {

    static final PonderRegistrationHelper HELPER = new PonderRegistrationHelper("estrogen");

    public static void register() {
        //TODO: BROKEN
        HELPER.forComponents()
                .addStoryBoard("centrifuge/intro", CentrifugeStoryboard::centrifugeStoryboardIntro)
                .addStoryBoard("centrifuge/basic", CentrifugeStoryboard::centrifugeStoryboardBasic);
    }
}