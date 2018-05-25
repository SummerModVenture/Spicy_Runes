package com.spicymemes.runes.coremod;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * Created by Spencer on 5/23/18.
 */
public class CoreMod implements IFMLLoadingPlugin {
    @Override
    public String[] getASMTransformerClass() {
        System.out.println("test");
        return new String[]{MethodPatcher.class.getName()};
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Nullable
    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {

    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
