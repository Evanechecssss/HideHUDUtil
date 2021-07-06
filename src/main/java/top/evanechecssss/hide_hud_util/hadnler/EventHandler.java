package top.evanechecssss.hide_hud_util.hadnler;

import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import top.evanechecssss.hide_hud_util.HideHUDUtil;
import top.evanechecssss.hide_hud_util.network.ClientProxy;

public class EventHandler {
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void gameRender(RenderGameOverlayEvent.Pre event) {

        switch (event.getType()) {
            case ALL:
                if (!ClientProxy.showAll) event.setCanceled(true);
                break;
            case AIR:
                if (!ClientProxy.showOxygen) event.setCanceled(true);
                break;
            case ARMOR:
                if (!ClientProxy.showArmor) event.setCanceled(true);
                break;
            case BOSSHEALTH:
            case BOSSINFO:
                if (!ClientProxy.showBossBar) event.setCanceled(true);
                break;
            case FOOD:
                if (!ClientProxy.showFood) event.setCanceled(true);
                break;
            case CROSSHAIRS:
                if (!ClientProxy.showCrosshair) event.setCanceled(true);
                break;
            case CHAT:
                if (!ClientProxy.showChat) event.setCanceled(true);
                break;
            case HEALTH:
            case HEALTHMOUNT:
                if (!ClientProxy.showHealth) event.setCanceled(true);
                break;
            case EXPERIENCE:
                if (!ClientProxy.showExp) event.setCanceled(true);
                break;
            case HELMET:
                if (!ClientProxy.showHelmet) event.setCanceled(true);
                break;
            case HOTBAR:
                if (!ClientProxy.showBAR) event.setCanceled(true);
                break;
            case PLAYER_LIST:
                if (!ClientProxy.showTab) event.setCanceled(true);
                break;
            case SUBTITLES:
                if (!ClientProxy.showSabTitle) event.setCanceled(true);
                break;
            case TEXT:
                if (!ClientProxy.showTitle) event.setCanceled(true);
                break;
            case POTION_ICONS:
                if (!ClientProxy.showPotion) event.setCanceled(true);
                break;
        }

    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void renderLines(DrawBlockHighlightEvent event) {
        if (!ClientProxy.showLines || !ClientProxy.showAll) event.setCanceled(true);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void renderHand(RenderHandEvent event) {
        if (!ClientProxy.showHand || !ClientProxy.showAll) event.setCanceled(true);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void clientDisconnection(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        if (!HideHUDUtil.ModConfig.saveFlowWorlds) {
            ClientProxy.showHand = true;
            ClientProxy.showBAR = true;
            ClientProxy.showHealth = true;
            ClientProxy.showHelmet = true;
            ClientProxy.showCrosshair = true;
            ClientProxy.showOxygen = true;
            ClientProxy.showFood = true;
            ClientProxy.showArmor = true;
            ClientProxy.showBossBar = true;
            ClientProxy.showExp = true;
            ClientProxy.showTab = true;
            ClientProxy.showPotion = true;
            ClientProxy.showSabTitle = true;
            ClientProxy.showTitle = true;
            ClientProxy.showAll = true;
            ClientProxy.showAllFULL = true;
            ClientProxy.showChat = true;
            ClientProxy.showInventory = true;
            ClientProxy.showLines = true;
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void renderGUI(GuiOpenEvent event) {
        if (event.getGui() instanceof GuiChat) {
            if (!ClientProxy.showAllFULL) event.setCanceled(true);
        }
        if (event.getGui() instanceof GuiInventory) {
            if (!ClientProxy.showInventory || !ClientProxy.showAllFULL) event.setCanceled(true);
        }
    }
}
