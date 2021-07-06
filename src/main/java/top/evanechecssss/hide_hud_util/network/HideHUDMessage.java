package top.evanechecssss.hide_hud_util.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class HideHUDMessage implements IMessage {
    public HideHUDMessage() {
    }

    public boolean hide, showHand, showBAR, showHealth, showHelmet, showCrosshair, showOxygen, showFood, showArmor, showBossBar, showExp, showTab, showPotion, showSabTitle, showTitle, showAllFULL, showChat, showInventory, lines;


    public HideHUDMessage(boolean hide, boolean showHand, boolean showBAR, boolean showHealth, boolean showHelmet, boolean showCrosshair, boolean showOxygen, boolean showFood, boolean showArmor, boolean showBossBar, boolean showExp, boolean showTab, boolean showPotion, boolean showSabTitle, boolean showTitle, boolean showAllFULL, boolean showChat, boolean showInventory, boolean lines) {
        this.hide = hide;
        this.showHand = showHand;
        this.showBAR = showBAR;
        this.showHealth = showHealth;
        this.showHelmet = showHelmet;
        this.showCrosshair = showCrosshair;
        this.showOxygen = showOxygen;
        this.showFood = showFood;
        this.showArmor = showArmor;
        this.showBossBar = showBossBar;
        this.showExp = showExp;
        this.showTab = showTab;
        this.showPotion = showPotion;
        this.showSabTitle = showSabTitle;
        this.showTitle = showTitle;
        this.showAllFULL = showAllFULL;
        this.showChat = showChat;
        this.showInventory = showInventory;
        this.lines = lines;
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        this.hide = byteBuf.readBoolean();
        this.showHand = byteBuf.readBoolean();
        this.showBAR = byteBuf.readBoolean();
        this.showHealth = byteBuf.readBoolean();
        this.showHelmet = byteBuf.readBoolean();
        this.showCrosshair = byteBuf.readBoolean();
        this.showOxygen = byteBuf.readBoolean();
        this.showFood = byteBuf.readBoolean();
        this.showArmor = byteBuf.readBoolean();
        this.showBossBar = byteBuf.readBoolean();
        this.showExp = byteBuf.readBoolean();
        this.showTab = byteBuf.readBoolean();
        this.showPotion = byteBuf.readBoolean();
        this.showSabTitle = byteBuf.readBoolean();
        this.showTitle = byteBuf.readBoolean();
        this.showAllFULL = byteBuf.readBoolean();
        this.showChat = byteBuf.readBoolean();
        this.showInventory = byteBuf.readBoolean();
        this.lines = byteBuf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeBoolean(this.hide);
        byteBuf.writeBoolean(this.showHand);
        byteBuf.writeBoolean(this.showBAR);
        byteBuf.writeBoolean(this.showHealth);
        byteBuf.writeBoolean(this.showHelmet);
        byteBuf.writeBoolean(this.showCrosshair);
        byteBuf.writeBoolean(this.showOxygen);
        byteBuf.writeBoolean(this.showFood);
        byteBuf.writeBoolean(this.showArmor);
        byteBuf.writeBoolean(this.showBossBar);
        byteBuf.writeBoolean(this.showExp);
        byteBuf.writeBoolean(this.showTab);
        byteBuf.writeBoolean(this.showPotion);
        byteBuf.writeBoolean(this.showSabTitle);
        byteBuf.writeBoolean(this.showTitle);
        byteBuf.writeBoolean(this.showAllFULL);
        byteBuf.writeBoolean(this.showChat);
        byteBuf.writeBoolean(this.showInventory);
        byteBuf.writeBoolean(this.lines);
    }

    public static class HideHUDMessageHandler implements IMessageHandler<HideHUDMessage, IMessage> {

        @SideOnly(Side.CLIENT)
        private static void changeHUDSpecial(boolean hide, boolean showHand, boolean showBAR, boolean showHealth, boolean showHelmet, boolean showCrosshair, boolean showOxygen, boolean showFood, boolean showArmor, boolean showBossBar, boolean showExp, boolean showTab, boolean showPotion, boolean showSabTitle, boolean showTitle, boolean showAllFULL, boolean showChat, boolean showInventory, boolean showLines) {
            ClientProxy.showAll = !hide;
            ClientProxy.showHand = showHand;
            ClientProxy.showAllFULL = showAllFULL;
            ClientProxy.showInventory = showInventory;
            ClientProxy.showPotion = showPotion;
            ClientProxy.showBAR = showBAR;
            ClientProxy.showHelmet = showHelmet;
            ClientProxy.showHealth = showHealth;
            ClientProxy.showCrosshair = showCrosshair;
            ClientProxy.showChat = showChat;
            ClientProxy.showOxygen = showOxygen;
            ClientProxy.showFood = showFood;
            ClientProxy.showArmor = showArmor;
            ClientProxy.showBossBar = showBossBar;
            ClientProxy.showExp = showExp;
            ClientProxy.showTab = showTab;
            ClientProxy.showTitle = showTitle;
            ClientProxy.showSabTitle = showSabTitle;
            ClientProxy.showLines = showLines;

        }

        @Override
        public IMessage onMessage(HideHUDMessage message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                changeHUDSpecial(message.hide, message.showHand, message.showBAR, message.showHealth, message.showHelmet, message.showCrosshair, message.showOxygen, message.showFood, message.showArmor, message.showBossBar, message.showExp, message.showTab, message.showPotion, message.showSabTitle, message.showTitle, message.showAllFULL, message.showChat, message.showInventory, message.lines);
            });
            return null;
        }
    }
}
