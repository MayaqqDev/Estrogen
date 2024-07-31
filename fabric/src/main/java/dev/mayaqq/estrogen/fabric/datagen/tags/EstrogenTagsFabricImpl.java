package dev.mayaqq.estrogen.fabric.datagen.tags;

public class EstrogenTagsFabricImpl implements EstrogenTagsInterface {
    @Override
    public String getName(String name) {
        return name + " (Fabric)";
    }
}
