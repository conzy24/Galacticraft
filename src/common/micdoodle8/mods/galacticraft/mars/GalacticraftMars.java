package micdoodle8.mods.galacticraft.mars;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import micdoodle8.mods.galacticraft.core.GCCoreLocalization;
import micdoodle8.mods.galacticraft.core.GCCoreUtil;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.INetworkManager;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraftforge.common.DimensionManager;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.ServerStarted;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

/**
 * Copyright 2012, micdoodle8
 * 
 *  All rights reserved.
 *
 */
@Mod(name="Galacticraft Mars", version="v1", useMetadata = false, modid = "GalacticraftMars")
@NetworkMod(channels = {"GalacticraftMars"}, clientSideRequired = true, serverSideRequired = false)
public class GalacticraftMars 
{
	@SidedProxy(clientSide = "micdoodle8.mods.galacticraft.mars.client.ClientProxyMars", serverSide = "micdoodle8.mods.galacticraft.mars.CommonProxyMars")
	public static CommonProxyMars proxy;
	
	@Instance("GalacticraftMars")
	public static GalacticraftMars instance;
	
	public static List serverPlayerBaseList = new ArrayList();
	public static List serverPlayerAPIs = new ArrayList();
	
	public static GCCoreLocalization lang;
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event)
	{
		new GCMarsConfigManager(new File(event.getModConfigurationDirectory(), "Galacticraft/mars.conf"));
		
		lang = new GCCoreLocalization("micdoodle8/mods/galacticraft/mars/client");
		
		GCMarsBlocks.initBlocks();
		GCMarsBlocks.registerBlocks();
		GCMarsBlocks.setHarvestLevels();
		GCMarsBlocks.addNames();
		
		GCMarsItems.initItems();
		GCMarsItems.addNames();
		
		proxy.preInit(event);
	}
	
	@Init
	public void init(FMLInitializationEvent event)
	{
		DimensionManager.registerProviderType(GCMarsConfigManager.dimensionIDMars, GCMarsWorldProvider.class, true);
		DimensionManager.registerDimension(GCMarsConfigManager.dimensionIDMars, GCMarsConfigManager.dimensionIDMars);
		GCMarsUtil.addSmeltingRecipes();
		this.registerTileEntities();
		this.registerCreatures();
		this.registerOtherEntities();
		proxy.init(event);
	}
	
	public void registerTileEntities()
	{
		
	}
	
	public void registerCreatures()
	{
		registerGalacticraftCreature(GCMarsEntityCreeperBoss.class, "Creeper Boss", GCMarsConfigManager.idEntityCreeperBoss, 894731, 0);
		registerGalacticraftCreature(GCMarsEntitySludgeling.class, "Sludgeling", GCMarsConfigManager.idEntitySludgeling, 25600, 0);
	}
	
	public void registerOtherEntities()
	{
		registerGalacticraftNonMobEntity(GCMarsEntityProjectileTNT.class, "Projectile TNT", GCMarsConfigManager.idEntityProjectileTNT, 150, 5, true);
	}
	
	@PostInit
	public void postInit(FMLPostInitializationEvent event)
	{
//		GCMarsAchievementList.initAchievs();
		proxy.postInit(event);
		proxy.registerRenderInformation();
	}
	
	@ServerStarted
	public void serverInit(FMLServerStartedEvent event)
	{
        NetworkRegistry.instance().registerChannel(new ServerPacketHandler(), "GalacticraftMars", Side.SERVER);
	}

    public void registerGalacticraftCreature(Class var0, String var1, int id, int back, int fore)
    {
    	EntityRegistry.registerGlobalEntityID(var0, var1, id, back, fore);
        EntityRegistry.registerModEntity(var0, var1, id, instance, 80, 3, true);
		LanguageRegistry.instance().addStringLocalization("entity." + var1 + ".name", "en_US", var1);
    }
    
    public void registerGalacticraftNonMobEntity(Class var0, String var1, int id, int trackingDistance, int updateFreq, boolean sendVel)
    {
        EntityRegistry.registerModEntity(var0, var1, id, this, trackingDistance, updateFreq, sendVel);
    }
	
    public class ServerPacketHandler implements IPacketHandler
    {
        @Override
        public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player p)
        {
            DataInputStream data = new DataInputStream(new ByteArrayInputStream(packet.data));
            int packetType = GCCoreUtil.readPacketID(data);
            EntityPlayerMP player = (EntityPlayerMP)p;
            
            if (packetType == 0)
            {
            	
            }
        }
    }
}