package com.spicymemes.runes.coremod;

import com.google.common.eventbus.EventBus;
import com.spicymemes.core.MainMod;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.versioning.ArtifactVersion;
import net.minecraftforge.fml.common.versioning.VersionRange;

import javax.annotation.Nullable;
import java.io.File;
import java.net.URL;
import java.security.cert.Certificate;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Spencer on 5/23/18.
 */
public class Container extends DummyModContainer {
    @Override
    public String getModId() {
        return MainMod.MODID;
    }

    @Override
    public String getName() {
        return MainMod.NAME;
    }

    @Override
    public String getVersion() {
        return MainMod.VERSION;
    }
}
