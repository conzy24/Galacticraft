package micdoodle8.mods.galacticraft.moon;

import micdoodle8.mods.galacticraft.api.world.IMapObject;
import micdoodle8.mods.galacticraft.api.world.IMoon;
import micdoodle8.mods.galacticraft.api.world.IPlanet;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.moon.client.GCMoonMapPlanet;
import micdoodle8.mods.galacticraft.moon.dimension.GCMoonWorldProvider;
import net.minecraft.world.WorldProvider;

public class marsy implements IPlanet
{
    private final IMapObject moon = new GCMoonMapPlanet();

    @Override
    public String getName()
    {
        return "Mars";
    }

    @Override
    public boolean isReachable()
    {
        return true;
    }

    
   
    @Override
    public IMapObject getMapObject()
    {
        return this.mars;
    }

    @Override
    public boolean autoRegister()
    {
        return true;
    }

    @Override
    public boolean addToList()
    {
        return true;
    }

    @Override
    public Class<? extends WorldProvider> getWorldProvider()
    {
        return GCMoonWorldProvider.class;
    }

    @Override
    public int getDimensionID()
    {
        return 4;
        
        // work in progress.
    }
}
