package dev.mayaqq.estrogen.utils;

import com.google.common.base.Suppliers;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Keyable;
import com.teamresourceful.resourcefullib.common.exceptions.UtilityClassException;
import net.minecraft.util.StringRepresentable;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public final class EstrogenCodecs {

    private EstrogenCodecs() throws UtilityClassException {
        throw new UtilityClassException();
    }

    public static <S extends StringRepresentable> Keyable arrayKeyable(Supplier<S[]> supplier) {
        return Keyable.forStrings(() -> Arrays.stream(supplier.get()).map(StringRepresentable::getSerializedName));
    }

    public static <T> Codec<T> recursive(String name, UnaryOperator<Codec<T>> codec) {
        return new RecursiveCodec<>(name, codec);
    }

    public static <T> Codec<T> alternatives(Codec<T> codec1, Codec<T> codec2) {
        return Codec.either(codec1, codec2).xmap(
            either -> either.map(Function.identity(), Function.identity()),
            Either::left
        );
    }

    public static class RecursiveCodec<T> implements Codec<T> {
        private final String name;
        private final Supplier<Codec<T>> wrapped;

        private RecursiveCodec(final String name, final UnaryOperator<Codec<T>> wrapped) {
            this.name = name;
            this.wrapped = Suppliers.memoize(() -> wrapped.apply(this));
        }

        @Override
        public <S> DataResult<Pair<T, S>> decode(final DynamicOps<S> ops, final S input) {
            return wrapped.get().decode(ops, input);
        }

        @Override
        public <S> DataResult<S> encode(final T input, final DynamicOps<S> ops, final S prefix) {
            return wrapped.get().encode(input, ops, prefix);
        }

        @Override
        public String toString() {
            return "RecursiveCodec[" + name + ']';
        }
    }
}
