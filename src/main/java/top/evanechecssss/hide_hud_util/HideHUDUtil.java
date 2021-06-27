package top.evanechecssss.hide_hud_util;

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

@Mod(modid = HideHUDUtil.Info.MOD_ID, name = HideHUDUtil.Info.NAME, version = HideHUDUtil.Info.VERSION)
public class HideHUDUtil {
    @SidedProxy(clientSide = "top.evanechecssss.hide_hud_util.network.ClientProxy", serverSide = "top.evanechecssss.hide_hud_util.network.CommonProxy")
    private static CommonProxy proxy;
    public static Logger logger;

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

    public static final class Info {
        public static final String MOD_ID = "hide_hud_util";
        public static final String NAME = "Hide HUD Util";
        public static final String VERSION = "1.0";
    }
}
