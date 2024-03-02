package dev.mayaqq.estrogen.registry;

import com.simibubi.create.foundation.ponder.PonderRegistrationHelper;
import com.simibubi.create.infrastructure.ponder.AllPonderTags;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.registry.ponders.CentrifugeStoryboard;

public class EstrogenPonderScenes {

    static final PonderRegistrationHelper HELPER = new PonderRegistrationHelper(Estrogen.MOD_ID);

    public static void register() {
        HELPER.addStoryBoard(EstrogenItems.CENTRIFUGE.getId(), Estrogen.id("centrifuge/intro"), new CentrifugeStoryboard.Intro(), AllPonderTags.KINETIC_APPLIANCES);
        HELPER.addStoryBoard(EstrogenItems.CENTRIFUGE.getId(), Estrogen.id("centrifuge/basic"), new CentrifugeStoryboard.Basic(), AllPonderTags.KINETIC_APPLIANCES);
    }
}
