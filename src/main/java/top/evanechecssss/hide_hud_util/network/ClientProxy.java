package top.evanechecssss.hide_hud_util.network;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {
    public static boolean showHand = true;
    public static boolean showLines = false;
    public static boolean showBAR = true;
    public static boolean showHealth = true;
    public static boolean showHelmet = true;
    public static boolean showCrosshair = true;
    public static boolean showOxygen = true;
    public static boolean showFood = true;
    public static boolean showArmor = true;
    public static boolean showBossBar = true;
    public static boolean showExp = true;
    public static boolean showTab = true;
    public static boolean showPotion = true;
    public static boolean showSabTitle = true;
    public static boolean showTitle = true;
    public static boolean showAll = true;
    public static boolean showAllFULL = true;
    public static boolean showChat = true;
    public static boolean showInventory = true;

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }
}
