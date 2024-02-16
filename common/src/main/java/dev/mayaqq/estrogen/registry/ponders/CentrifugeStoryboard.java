package dev.mayaqq.estrogen.registry.ponders;

import com.simibubi.create.foundation.ponder.SceneBuilder;
import com.simibubi.create.foundation.ponder.SceneBuildingUtil;
import com.simibubi.create.foundation.ponder.Selection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public class CentrifugeStoryboard {
    public static void centrifugeStoryboardIntro(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("intro", "The Centrifuge Requirements");
        scene.configureBasePlate(0, 0, 5);
        scene.world.showSection(util.select.layer(0), Direction.UP);

        BlockPos centrifugePos = util.grid.at(2, 1, 2);
        Selection centrifuge = util.select.position(centrifugePos);
        scene.world.showSection(centrifuge, Direction.UP);
        scene.world.setKineticSpeed(centrifuge, 0);
        scene.idle(5);
        scene.overlay.showText(300)
                .placeNearTarget()
                .text("The centrifuge needs the maximum speed (256 RPM) to work!")
                .pointAt(util.vector.of(2, 1, 2));
        scene.idle(100);

        Selection speedStuff = util.select.fromTo(1, 1, 2, 0, 2, 2);
        scene.world.showSection(speedStuff, Direction.DOWN);
        Selection controller = util.select.position(0, 1, 2);
        scene.world.setKineticSpeed(controller, 16);
        scene.world.setKineticSpeed(centrifuge, 256);

        scene.idle(100);

        scene.markAsFinished();
    }

    public static void centrifugeStoryboardBasic(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("basic", "How to use the Centrifuge");
        scene.configureBasePlate(0, 0, 5);
        scene.world.showSection(util.select.layer(0), Direction.UP);

        BlockPos centrifugePos = util.grid.at(2, 3, 2);
        Selection centrifuge = util.select.position(centrifugePos);
        scene.world.setKineticSpeed(centrifuge, 0);
        scene.world.showSection(centrifuge, Direction.UP);
        scene.addKeyframe();
        scene.overlay.showText(100)
                .placeNearTarget()
                .text("The Centrifuge doesn't have any inventory, you will need to place fluid containers around it to make it work!")
                .pointAt(util.vector.of(2, 3, 2));
        scene.idle(120);
        Selection fluidInput = util.select.fromTo(2, 1, 2, 2, 2, 2);
        scene.addKeyframe();
        scene.overlay.showText(100)
                .placeNearTarget()
                .text("You can input fluids from the bottom")
                .pointAt(util.vector.of(2, 1, 2));
        scene.world.showSection(fluidInput, Direction.DOWN);
        scene.idle(100);
        Selection fluidOutput = util.select.fromTo(2, 4, 2, 2, 5, 2);
        scene.addKeyframe();
        scene.overlay.showText(100)
                .placeNearTarget()
                .text("And output fluids from the top")
                .pointAt(util.vector.of(2, 5, 2));
        scene.world.showSection(fluidOutput, Direction.UP);
        scene.idle(100);
        scene.world.setKineticSpeed(centrifuge, 256);
        scene.idle(50);

        scene.markAsFinished();
    }
}
