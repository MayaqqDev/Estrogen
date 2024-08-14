package dev.mayaqq.estrogen.fabric.datagen.tags;

public class EstrogenTagsForgeImpl implements EstrogenTagsInterface {
    @Override
    public String getName(String name) {
        return name + " (Forge)";
    }
}
