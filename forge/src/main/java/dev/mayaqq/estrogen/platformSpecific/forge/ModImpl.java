package dev.mayaqq.estrogen.platformSpecific.forge;

import dev.mayaqq.estrogen.platformSpecific.Mod;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.IModInfo;

import java.util.Optional;

public class ModImpl {
    @org.jetbrains.annotations.Contract
    public static Optional<Mod> getOptionalMod(String modid) {
        if (ModList.get().getModContainerById(modid).orElse(null) != null) {
            IModInfo metadata = ModList.get().getModContainerById(modid).get().getModInfo();
            return Optional.of(new Mod(modid, metadata.getVersion().getQualifier(), metadata.getDisplayName(), metadata.getDescription()));
        }
        return Optional.empty();
    }
}
