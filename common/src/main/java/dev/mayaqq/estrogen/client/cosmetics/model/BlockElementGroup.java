package dev.mayaqq.estrogen.client.cosmetics.model;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public record BlockElementGroup(String name, Vector3f origin, IntList elementIndices, List<BlockElementGroup> subGroups) {

    @SuppressWarnings("deprecation")
    public static BlockElementGroup fromCombinedList(String name, Vector3f origin, List<Either<Integer, BlockElementGroup>> data) {
        IntList indices = new IntArrayList();
        List<BlockElementGroup> groups = new ArrayList<>();

        data.forEach(either -> either.ifLeft(indices::add).ifRight(groups::add));
        return new BlockElementGroup(name, origin, indices, groups);
    }

    public List<Either<Integer, BlockElementGroup>> toCombinedList() {
        ImmutableList.Builder<Either<Integer, BlockElementGroup>> builder = ImmutableList.builder();
        subGroups.forEach(group -> builder.add(Either.right(group)));
        elementIndices.forEach(index -> builder.add(Either.left(index)));
        return builder.build();
    }

    public void traverse(Visitor visitor) {
        visitor.visit(this, name);

        for(BlockElementGroup group : subGroups) {
            group.traverse((grp, name) -> visitor.visit(grp, this.name + "/" + name));
        }
    }

    public boolean isTerminal() {
        return subGroups.isEmpty();
    }

    public interface Visitor {
        void visit(BlockElementGroup group, String name);
    }
}
