package dev.mayaqq.estrogen.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "estrogen")
public class EstrogenConfig implements ConfigData {
    public boolean chestFeature = true;
}
