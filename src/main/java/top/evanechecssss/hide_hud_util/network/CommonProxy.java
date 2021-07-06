package top.evanechecssss.hide_hud_util.network;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import top.evanechecssss.hide_hud_util.HideHUDUtil;
import top.evanechecssss.hide_hud_util.commands.HideHUDCommand;
import top.evanechecssss.hide_hud_util.hadnler.EventHandler;


@Mod.EventBusSubscriber
public class CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new EventHandler());
        MinecraftForge.EVENT_BUS.register(this);
        HideHUDUtil.NETWORK.registerMessage(HideHUDMessage.HideHUDMessageHandler.class, HideHUDMessage.class, 0, Side.CLIENT);
    }

    public void init(FMLInitializationEvent event) {
        ConfigManager.sync(HideHUDUtil.Info.MOD_ID, Config.Type.INSTANCE);
    }

    public void server(FMLServerStartingEvent event) {
        event.registerServerCommand(new HideHUDCommand());
    }

    @SubscribeEvent
    public void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(HideHUDUtil.Info.MOD_ID)) {
            ConfigManager.sync(HideHUDUtil.Info.MOD_ID, Config.Type.INSTANCE);
        }
    }
}
