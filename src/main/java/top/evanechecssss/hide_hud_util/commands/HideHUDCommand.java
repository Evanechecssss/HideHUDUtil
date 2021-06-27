package top.evanechecssss.hide_hud_util.commands;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class HideHUDCommand extends CommandBase {

    public HideHUDCommand() {
        super();
    }

    private final List<String> aliases = Lists.newArrayList("hud","-h");
    @Override
    public List<String> getAliases() {
        return aliases;
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, "h", "-h", "hide","s","-s", "show", "0","1","-","+");
        }else {
            return Collections.emptyList();
        }
    }

    @Override
    public String getName() {
        return "hud";
    }

    @Override
    public String getUsage(ICommandSender iCommandSender) {
        return "hide_hud_util.command.usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length<1){
            sender.sendMessage(new TextComponentTranslation("hide_hud_util.command.error"));
            return;
        }
        String function = args[0];
        if (function.equals("h") || function.equals("-h") || function.equals("0")|| function.equals("-")||function.equals("hide")){
            hideHUD(true);
        }
        else if (function.equals("s") || function.equals("-s") || function.equals("1")|| function.equals("+")||function.equals("show")){
            hideHUD(false);
        }else {
            sender.sendMessage(new TextComponentTranslation("hide_hud_util.command.error"));
        }
    }
    @SideOnly(Side.CLIENT)
    private void hideHUD(boolean hide){
        Minecraft.getMinecraft().gameSettings.hideGUI = hide;
    }

}
