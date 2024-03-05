package dev.mayaqq.estrogen.utils;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public sealed interface Either<L, R> permits Either.Left, Either.Right {

    static <L, R> Either<L, R> ofLeft(final L left) {
        return new Left<>(left);
    }

    static <L, R> Either<L, R> ofRight(final R right) {
        return new Right<>(right);
    }

    static <T> T value(Either<T, T> either) {
        return either.map(Function.identity(), Function.identity());
    }

    <T> T map(Function<L, T> mapLeft, Function<R, T> mapRight);

    default <F, S> Either<F, S> mapEither(Function<L, F> mapLeft, Function<R, S> mapRight) {
        return map(
                value -> Either.ofLeft(mapLeft.apply(value)),
                value -> Either.ofRight(mapRight.apply(value))
        );
    }

    default <T> Either<T, R> mapLeft(Function<L, T> mapLeft) {
        return map(value -> Either.ofLeft(mapLeft.apply(value)), Either::ofRight);
    }

    default <T> Either<L, T> mapRight(Function<R, T> mapRight) {
        return map(Either::ofLeft, value -> Either.ofRight(mapRight.apply(value)));
    }

    default Either<L, R> ifLeft(Consumer<L> consumer) {
        left().ifPresent(consumer);
        return this;
    }

    default Either<L, R> ifRight(Consumer<R> consumer) {
        right().ifPresent(consumer);
        return this;
    }

    default Either<L, R> ifLeftOrElse(Consumer<L> consumer, Runnable orElse){
        left().ifPresentOrElse(consumer, orElse);
        return this;
    }

    default Either<L, R> ifRightOrElse(Consumer<R> consumer, Runnable orElse) {
        right().ifPresentOrElse(consumer, orElse);
        return this;
    }

    default Optional<L> left() {
        return Optional.empty();
    }

    default L leftOr(L value) {
        return left().orElse(value);
    }

    default L leftOrThrow() {
        return left().orElseThrow();
    }

    default Optional<R> right() {
        return Optional.empty();
    }

    default R rightOr(R value) {
        return right().orElse(value);
    }

    default R rightOrThrow() {
        return right().orElseThrow();
    }

    default boolean isLeft() {
        return left().isPresent();
    }

    default boolean isRight() {
        return right().isPresent();
    }

    record Left<L, R>(L value) implements Either<L, R> {

        @Override
        public <T> T map(Function<L, T> mapLeft, Function<R, T> mapRight) {
            return mapLeft.apply(value);
        }

        @Override
        public Optional<L> left() {
            return Optional.of(value);
        }
    }

    record Right<L, R>(R value) implements Either<L, R> {

        @Override
        public <T> T map(Function<L, T> mapLeft, Function<R, T> mapRight) {
            return mapRight.apply(value);
        }

        @Override
        public Optional<R> right() {
            return Optional.of(value);
        }
    }

}

