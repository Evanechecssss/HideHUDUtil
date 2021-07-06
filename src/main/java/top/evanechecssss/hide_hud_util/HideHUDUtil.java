package top.evanechecssss.hide_hud_util;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import org.apache.logging.log4j.Logger;
import top.evanechecssss.hide_hud_util.network.CommonProxy;

@Mod(modid = HideHUDUtil.Info.MOD_ID, name = HideHUDUtil.Info.NAME, version = HideHUDUtil.Info.VERSION, updateJSON = "https://gist.github.com/Evanechecssss/34b942a0d903261005ddafc62c8238ba.js")
public class HideHUDUtil {
    @SidedProxy(clientSide = "top.evanechecssss.hide_hud_util.network.ClientProxy", serverSide = "top.evanechecssss.hide_hud_util.network.CommonProxy")
    private static CommonProxy proxy;
    public static Logger logger;
    public static SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(HideHUDUtil.Info.MOD_ID);
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);
        logger.info("!Mod pre initialization :" + HideHUDUtil.Info.NAME);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
        logger.info("!Mod initialization :" + HideHUDUtil.Info.NAME);

    }

    @EventHandler
    public void server(FMLServerStartingEvent event) {
        proxy.server(event);
        logger.info("!Mod server :" + HideHUDUtil.Info.NAME);
    }

    @Config(modid = HideHUDUtil.Info.MOD_ID, type = Config.Type.INSTANCE, name = HideHUDUtil.Info.MOD_ID + "_conf")

    public static class ModConfig {
        @Config.Comment("It save HUD on all worlds")
        @Config.RequiresWorldRestart
        @Config.LangKey("hide_hud_util.config")
        public static boolean saveFlowWorlds = false;
    }

    public static final class Info {
        public static final String MOD_ID = "hide_hud_util";
        public static final String NAME = "Hide HUD Util";
        public static final String VERSION = "1.1";
    }
}
