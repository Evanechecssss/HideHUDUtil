package top.evanechecssss.hide_hud_util.network;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import top.evanechecssss.hide_hud_util.commands.HideHUDCommand;


@Mod.EventBusSubscriber
public class CommonProxy {
    public void preInit(FMLPreInitializationEvent event)
    {
    }
    public void init(FMLInitializationEvent event)
    {
    }
    public void server(FMLServerStartingEvent event){
        event.registerServerCommand(new HideHUDCommand());
    }
}
