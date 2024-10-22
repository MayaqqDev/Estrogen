package dev.mayaqq.estrogen.client.cosmetics.model.animation;

import org.joml.Vector3fc;

import java.util.Optional;

public interface Animatable {

    void offsetPosition(Vector3fc offset);

    void offsetRotation(Vector3fc offset);

    void offsetScale(Vector3fc offset);

    void reset();

    interface Provider {
        Optional<Animatable> getAny(String key);
    }
}
