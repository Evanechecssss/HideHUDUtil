package top.evanechecssss.hide_hud_util.commands;

import com.google.common.collect.Lists;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import top.evanechecssss.hide_hud_util.HideHUDUtil;
import top.evanechecssss.hide_hud_util.network.ClientProxy;
import top.evanechecssss.hide_hud_util.network.HideHUDMessage;

import javax.annotation.Nullable;
import java.util.*;

public class HideHUDCommand extends CommandBase {
    private List<String> acceptElements = Lists.newArrayList("hand", "bar", "health", "helmet", "crosshair", "oxygen", "food", "armor", "bossbar", "exp", "tab", "potion", "sabtitle", "title", "all", "chat", "inventory", "lines");


    public HideHUDCommand() {
        super();
    }

    private final List<String> aliases = Lists.newArrayList("hud", "h");

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
            return getListOfStringsMatchingLastWord(args, "h", "-h", "hide", "s", "-s", "show", "0", "1", "-", "+", "l", "-l", "list", "?");
        } else if (args.length == 2) {
            Collection<String> collection = new ArrayList<>();
            collection.add("o");
            collection.add("only");
            collection.add("=");
            collection.add("but");
            collection.add("b");
            collection.add("!");
            collection.addAll(Arrays.asList(server.getOnlinePlayerNames()));
            return getListOfStringsMatchingLastWord(args, collection);
        } else if (args.length == 3) {

            List<String> adaptive = Lists.newArrayList("o", "only", "=", "but", "b", "!");
            if (adaptive.contains(args[1])) {
                return getListOfStringsMatchingLastWord(args, acceptElements);
            }
            try {
                Entity player = getEntity(server, sender, args[1]);
                return getListOfStringsMatchingLastWord(args, "o", "only", "=", "but", "b", "!");
            } catch (CommandException e) {
                e.printStackTrace();
            }

            return Collections.emptyList();
        } else if (args.length == 4) {

            List<String> adaptive = Lists.newArrayList("o", "only", "=", "but", "b", "!");
            if (adaptive.contains(args[1])) {
                try {
                    Entity player = getEntity(server, sender, args[2]);
                    return getListOfStringsMatchingLastWord(args, acceptElements);
                } catch (CommandException e) {
                    e.printStackTrace();
                }
            }
            try {
                Entity player = getEntity(server, sender, args[1]);
                if (adaptive.contains(args[2])) {
                    return getListOfStringsMatchingLastWord(args, acceptElements);
                }
            } catch (CommandException e) {
                e.printStackTrace();
            }
            return Collections.emptyList();
        } else {
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
        if (args.length < 1) {
            sender.sendMessage(new TextComponentTranslation("hide_hud_util.command.error"));
            return;
        }
        String function = args[0];
        switch (function) {
            case "h":
            case "-h":
            case "0":
            case "-":
            case "hide":
                hideHUDCommand(server, sender, args);
                break;
            case "s":
            case "-s":
            case "1":
            case "+":
            case "show":
                showHUDCommand(server, sender, args);
                break;
            case "l":
            case "-l":
            case "2":
            case "?":
            case "list":
                listCommand(server, sender, args);
                break;
            default:
                sender.sendMessage(new TextComponentTranslation("hide_hud_util.command.error"));
                break;
        }
    }

    private void hideHUDCommand(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

        if (args.length == 1) {
            HideHUDUtil.NETWORK.sendToAll(getDefaultMessage(true));
        } else if (args.length == 2) {
            Entity player = getEntity(server, sender, args[1]);
            if (player instanceof EntityPlayerMP) {
                HideHUDUtil.NETWORK.sendTo(getDefaultMessage(true), (EntityPlayerMP) player);
            }
        } else if (args.length == 3) {
            if (args[1].equals("only") || args[1].equals("o") || args[1].equals("=")) {
                List<String> elementList = Lists.newArrayList(args[2].split(","));
                if (testElementList(elementList, sender)) {
                    HideHUDUtil.NETWORK.sendToAll(HideHUDOnly(elementList));
                }
            } else if (args[1].equals("but") || args[1].equals("b") || args[1].equals("!")) {
                List<String> elementList = Lists.newArrayList(args[2].split(","));
                if (testElementList(elementList, sender)) {
                    HideHUDUtil.NETWORK.sendToAll(HideHUDBut(elementList));
                }
            } else {
                sender.sendMessage(new TextComponentTranslation("hide_hud_util.command.error_element", args[1]));
            }
        } else if (args.length == 4) {
            Entity player = getEntity(server, sender, args[1]);
            if (args[2].equals("only") || args[2].equals("o") || args[2].equals("=")) {
                List<String> elementList = Lists.newArrayList(args[3].split(","));
                if (testElementList(elementList, sender)) {
                    if (player instanceof EntityPlayerMP) {
                        HideHUDUtil.NETWORK.sendTo(HideHUDOnly(elementList), (EntityPlayerMP) player);
                    }
                }
            } else if (args[2].equals("but") || args[2].equals("b") || args[2].equals("!")) {
                List<String> elementList = Lists.newArrayList(args[3].split(","));
                if (testElementList(elementList, sender)) {
                    if (player instanceof EntityPlayerMP) {
                        HideHUDUtil.NETWORK.sendTo(HideHUDBut(elementList), (EntityPlayerMP) player);
                    }
                }
            } else {
                sender.sendMessage(new TextComponentTranslation("hide_hud_util.command.error_element", args[2]));
            }
        } else {
            sender.sendMessage(new TextComponentTranslation("hide_hud_util.command.error"));
        }
    }

    private void showHUDCommand(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

        if (args.length == 1) {
            HideHUDUtil.NETWORK.sendToAll(getDefaultMessage(false));
        } else if (args.length == 2) {
            Entity player = getEntity(server, sender, args[1]);
            if (player instanceof EntityPlayerMP) {
                HideHUDUtil.NETWORK.sendTo(getDefaultMessage(false), (EntityPlayerMP) player);
            }
        } else if (args.length == 3) {
            if (args[1].equals("only") || args[1].equals("o") || args[1].equals("=")) {
                List<String> elementList = Lists.newArrayList(args[2].split(","));
                if (testElementList(elementList, sender)) {
                    HideHUDUtil.NETWORK.sendToAll(ShowHUDOnly(elementList));
                }
            } else if (args[1].equals("but") || args[1].equals("b") || args[1].equals("!")) {
                List<String> elementList = Lists.newArrayList(args[2].split(","));
                if (testElementList(elementList, sender)) {
                    HideHUDUtil.NETWORK.sendToAll(ShowHUDBut(elementList));
                }
            } else {
                sender.sendMessage(new TextComponentTranslation("hide_hud_util.command.error_element", args[1]));
            }
        } else if (args.length == 4) {
            Entity player = getEntity(server, sender, args[1]);
            if (args[2].equals("only") || args[2].equals("o") || args[2].equals("=")) {
                List<String> elementList = Lists.newArrayList(args[3].split(","));
                if (testElementList(elementList, sender)) {
                    if (player instanceof EntityPlayerMP) {
                        HideHUDUtil.NETWORK.sendTo(ShowHUDOnly(elementList), (EntityPlayerMP) player);
                    }
                }
            } else if (args[2].equals("but") || args[2].equals("b") || args[2].equals("!")) {
                List<String> elementList = Lists.newArrayList(args[3].split(","));
                if (testElementList(elementList, sender)) {
                    if (player instanceof EntityPlayerMP) {
                        HideHUDUtil.NETWORK.sendTo(ShowHUDBut(elementList), (EntityPlayerMP) player);
                    }
                }
            } else {
                sender.sendMessage(new TextComponentTranslation("hide_hud_util.command.error_element", args[2]));
            }
        } else {
            sender.sendMessage(new TextComponentTranslation("hide_hud_util.command.error"));
        }
    }

    private void listCommand(MinecraftServer server, ICommandSender sender, String[] args) {
        if (args.length == 1) {
            if (sender instanceof EntityPlayer) {
                sendPlayerListElements(sender);
            }
        } else {
            sender.sendMessage(new TextComponentTranslation("hide_hud_util.command.usage"));
        }
    }

    private HideHUDMessage HideHUDOnly(List<String> elementList) {
        return new HideHUDMessage(false, getHandState(elementList, true, false), getBarState(elementList, true, false), getHealthState(elementList, true, false), getHelmetState(elementList, true, false), getCrosshairState(elementList, true, false), getOxygenState(elementList, true, false), getFoodState(elementList, true, false), getArmorState(elementList, true, false), getBossbarState(elementList, true, false), getExpState(elementList, true, false), getTabState(elementList, true, false), getPotionState(elementList, true, false), getSabtitleState(elementList, true, false), getTitleState(elementList, true, false), getAllState(elementList, true, false), getChatState(elementList, true, false), getInventoryState(elementList, true, false), getLinesState(elementList, true, false));
    }

    private HideHUDMessage HideHUDBut(List<String> elementList) {
        return new HideHUDMessage(false, getHandState(elementList, true, true), getBarState(elementList, true, true), getHealthState(elementList, true, true), getHelmetState(elementList, true, true), getCrosshairState(elementList, true, true), getOxygenState(elementList, true, true), getFoodState(elementList, true, true), getArmorState(elementList, true, true), getBossbarState(elementList, true, true), getExpState(elementList, true, true), getTabState(elementList, true, true), getPotionState(elementList, true, true), getSabtitleState(elementList, true, true), getTitleState(elementList, true, true), true, getChatState(elementList, true, true), true, getLinesState(elementList, true, true));

    }

    private HideHUDMessage ShowHUDOnly(List<String> elementList) {
        return new HideHUDMessage(false, getHandState(elementList, false, false), getBarState(elementList, false, false), getHealthState(elementList, false, false), getHelmetState(elementList, false, false), getCrosshairState(elementList, false, false), getOxygenState(elementList, false, false), getFoodState(elementList, false, false), getArmorState(elementList, false, false), getBossbarState(elementList, false, false), getExpState(elementList, false, false), getTabState(elementList, false, false), getPotionState(elementList, false, false), getSabtitleState(elementList, false, false), getTitleState(elementList, false, false), getAllState(elementList, false, false), getChatState(elementList, false, false), getInventoryState(elementList, false, false), getLinesState(elementList, false, false));
    }

    private HideHUDMessage ShowHUDBut(List<String> elementList) {
        return new HideHUDMessage(false, getHandState(elementList, false, true), getBarState(elementList, false, true), getHealthState(elementList, false, true), getHelmetState(elementList, false, true), getCrosshairState(elementList, false, true), getOxygenState(elementList, false, true), getFoodState(elementList, false, true), getArmorState(elementList, false, true), getBossbarState(elementList, false, true), getExpState(elementList, false, true), getTabState(elementList, false, true), getPotionState(elementList, false, true), getSabtitleState(elementList, false, true), getTitleState(elementList, false, true), true, getChatState(elementList, false, true), true, getLinesState(elementList, false, true));

    }

    private boolean testElementList(List<String> list, ICommandSender sender) {
        for (String element : list) {
            if (!acceptElements.contains(element)) {
                sender.sendMessage(new TextComponentTranslation("hide_hud_util.command.error_element", element));
                return false;
            }
        }
        return true;
    }

    private HideHUDMessage getDefaultMessage(boolean hide) {
        return new HideHUDMessage(hide, !hide, !hide, !hide, !hide, !hide, !hide, !hide, !hide, !hide, !hide, !hide, !hide, !hide, !hide, true, !hide, true, !hide);
    }

    private static void sendPlayerListElements(ICommandSender sender) {
        TextComponentTranslation textHead = new TextComponentTranslation("hide_hud_util.command.list.head");
        TextComponentTranslation textBar = new TextComponentTranslation("hide_hud_util.command.list_bar");
        TextComponentTranslation textHealth = new TextComponentTranslation("hide_hud_util.command.list_health");
        TextComponentTranslation textHelmet = new TextComponentTranslation("hide_hud_util.command.list_helmet");
        TextComponentTranslation textCrosshair = new TextComponentTranslation("hide_hud_util.command.list_crosshair");
        TextComponentTranslation textOxygen = new TextComponentTranslation("hide_hud_util.command.list_oxygen");
        TextComponentTranslation textFood = new TextComponentTranslation("hide_hud_util.command.list_food");
        TextComponentTranslation textArmor = new TextComponentTranslation("hide_hud_util.command.list_armor");
        TextComponentTranslation textBossBar = new TextComponentTranslation("hide_hud_util.command.list_bossbar");
        TextComponentTranslation textExp = new TextComponentTranslation("hide_hud_util.command.list_exp");
        TextComponentTranslation textTab = new TextComponentTranslation("hide_hud_util.command.list_tab");
        TextComponentTranslation textPotion = new TextComponentTranslation("hide_hud_util.command.list_potion");
        TextComponentTranslation textTitle = new TextComponentTranslation("hide_hud_util.command.list_title");
        TextComponentTranslation lines = new TextComponentTranslation("hide_hud_util.command.list_lines");
        TextComponentTranslation textSubTitle = new TextComponentTranslation("hide_hud_util.command.list_sabtitle");
        TextComponentTranslation textAll = new TextComponentTranslation("hide_hud_util.command.list_all");
        TextComponentTranslation textChat = new TextComponentTranslation("hide_hud_util.command.list_chat");
        TextComponentTranslation textInventory = new TextComponentTranslation("hide_hud_util.command.list_inventory");
        sender.sendMessage(textHead);
        sender.sendMessage(textBar);
        sender.sendMessage(textHealth);
        sender.sendMessage(textHelmet);
        sender.sendMessage(textCrosshair);
        sender.sendMessage(textOxygen);
        sender.sendMessage(textFood);
        sender.sendMessage(textArmor);
        sender.sendMessage(textBossBar);
        sender.sendMessage(textExp);
        sender.sendMessage(textTab);
        sender.sendMessage(textPotion);
        sender.sendMessage(textTitle);
        sender.sendMessage(lines);
        sender.sendMessage(textSubTitle);
        sender.sendMessage(textAll);
        sender.sendMessage(textChat);
        sender.sendMessage(textInventory);
    }

    public boolean getHandState(List<String> elementList, boolean hide, boolean invent) {
        if (hide) {
            if (invent) {
                return elementList.contains(acceptElements.get(0)) && ClientProxy.showHand;
            } else {
                return !elementList.contains(acceptElements.get(0)) && ClientProxy.showHand;
            }
        } else {
            if (invent) {
                return !elementList.contains(acceptElements.get(0)) || ClientProxy.showHand;
            } else {
                return elementList.contains(acceptElements.get(0)) || ClientProxy.showHand;
            }
        }
    }

    public boolean getBarState(List<String> elementList, boolean hide, boolean invent) {
        if (hide) {
            if (invent) {
                return elementList.contains(acceptElements.get(1)) && ClientProxy.showBAR;
            } else {
                return !elementList.contains(acceptElements.get(1)) && ClientProxy.showBAR;
            }
        } else {
            if (invent) {
                return !elementList.contains(acceptElements.get(1)) || ClientProxy.showBAR;
            } else {
                return elementList.contains(acceptElements.get(1)) || ClientProxy.showBAR;
            }
        }
    }

    public boolean getHealthState(List<String> elementList, boolean hide, boolean invent) {
        if (hide) {
            if (invent) {
                return elementList.contains(acceptElements.get(2)) && ClientProxy.showHealth;
            } else {
                return !elementList.contains(acceptElements.get(2)) && ClientProxy.showHealth;
            }
        } else {
            if (invent) {
                return !elementList.contains(acceptElements.get(2)) || ClientProxy.showHealth;
            } else {
                return elementList.contains(acceptElements.get(2)) || ClientProxy.showHealth;
            }
        }
    }

    public boolean getHelmetState(List<String> elementList, boolean hide, boolean invent) {
        if (hide) {
            if (invent) {
                return elementList.contains(acceptElements.get(3)) && ClientProxy.showHealth;
            } else {
                return !elementList.contains(acceptElements.get(3)) && ClientProxy.showHelmet;
            }
        } else {
            if (invent) {
                return !elementList.contains(acceptElements.get(3)) || ClientProxy.showHelmet;
            } else {
                return elementList.contains(acceptElements.get(3)) || ClientProxy.showHelmet;
            }
        }
    }

    public boolean getCrosshairState(List<String> elementList, boolean hide, boolean invent) {
        if (hide) {
            if (invent) {
                return elementList.contains(acceptElements.get(4)) && ClientProxy.showCrosshair;
            } else {
                return !elementList.contains(acceptElements.get(4)) && ClientProxy.showCrosshair;
            }
        } else {
            if (invent) {
                return !elementList.contains(acceptElements.get(4)) || ClientProxy.showCrosshair;
            } else {
                return elementList.contains(acceptElements.get(4)) || ClientProxy.showCrosshair;
            }
        }
    }

    public boolean getOxygenState(List<String> elementList, boolean hide, boolean invent) {
        if (hide) {
            if (invent) {
                return elementList.contains(acceptElements.get(5)) && ClientProxy.showOxygen;
            } else {
                return !elementList.contains(acceptElements.get(5)) && ClientProxy.showOxygen;
            }
        } else {
            if (invent) {
                return !elementList.contains(acceptElements.get(5)) || ClientProxy.showOxygen;
            } else {
                return elementList.contains(acceptElements.get(5)) || ClientProxy.showOxygen;
            }
        }
    }

    public boolean getFoodState(List<String> elementList, boolean hide, boolean invent) {
        if (hide) {
            if (invent) {
                return elementList.contains(acceptElements.get(6)) && ClientProxy.showFood;
            } else {
                return !elementList.contains(acceptElements.get(6)) && ClientProxy.showFood;
            }
        } else {
            if (invent) {
                return !elementList.contains(acceptElements.get(6)) || ClientProxy.showFood;
            } else {
                return elementList.contains(acceptElements.get(6)) || ClientProxy.showFood;
            }
        }
    }

    public boolean getArmorState(List<String> elementList, boolean hide, boolean invent) {
        if (hide) {
            if (invent) {
                return elementList.contains(acceptElements.get(7)) && ClientProxy.showArmor;
            } else {
                return !elementList.contains(acceptElements.get(7)) && ClientProxy.showArmor;
            }
        } else {
            if (invent) {
                return !elementList.contains(acceptElements.get(7)) || ClientProxy.showArmor;
            } else {
                return elementList.contains(acceptElements.get(7)) || ClientProxy.showArmor;
            }
        }
    }

    public boolean getBossbarState(List<String> elementList, boolean hide, boolean invent) {
        if (hide) {
            if (invent) {
                return elementList.contains(acceptElements.get(8)) && ClientProxy.showBossBar;
            } else {
                return !elementList.contains(acceptElements.get(8)) && ClientProxy.showBossBar;
            }
        } else {
            if (invent) {
                return !elementList.contains(acceptElements.get(8)) || ClientProxy.showBossBar;
            } else {
                return elementList.contains(acceptElements.get(8)) || ClientProxy.showBossBar;
            }
        }
    }

    public boolean getExpState(List<String> elementList, boolean hide, boolean invent) {
        if (hide) {
            if (invent) {
                return elementList.contains(acceptElements.get(9)) && ClientProxy.showExp;
            } else {
                return !elementList.contains(acceptElements.get(9)) && ClientProxy.showExp;
            }
        } else {
            if (invent) {
                return !elementList.contains(acceptElements.get(9)) || ClientProxy.showExp;
            } else {
                return elementList.contains(acceptElements.get(9)) || ClientProxy.showExp;
            }
        }
    }

    public boolean getTabState(List<String> elementList, boolean hide, boolean invent) {
        if (hide) {
            if (invent) {
                return elementList.contains(acceptElements.get(10)) && ClientProxy.showTab;
            } else {
                return !elementList.contains(acceptElements.get(10)) && ClientProxy.showTab;
            }
        } else {
            if (invent) {
                return !elementList.contains(acceptElements.get(10)) || ClientProxy.showTab;
            } else {
                return elementList.contains(acceptElements.get(10)) || ClientProxy.showTab;
            }
        }
    }

    public boolean getPotionState(List<String> elementList, boolean hide, boolean invent) {
        if (hide) {
            if (invent) {
                return elementList.contains(acceptElements.get(11)) && ClientProxy.showPotion;
            } else {
                return !elementList.contains(acceptElements.get(11)) && ClientProxy.showPotion;
            }
        } else {
            if (invent) {
                return !elementList.contains(acceptElements.get(11)) || ClientProxy.showPotion;
            } else {
                return elementList.contains(acceptElements.get(11)) || ClientProxy.showPotion;
            }
        }
    }

    public boolean getSabtitleState(List<String> elementList, boolean hide, boolean invent) {
        if (hide) {
            if (invent) {
                return elementList.contains(acceptElements.get(12)) && ClientProxy.showSabTitle;
            } else {
                return !elementList.contains(acceptElements.get(12)) && ClientProxy.showSabTitle;
            }
        } else {
            if (invent) {
                return !elementList.contains(acceptElements.get(12)) || ClientProxy.showSabTitle;
            } else {
                return elementList.contains(acceptElements.get(12)) || ClientProxy.showSabTitle;
            }
        }
    }

    public boolean getTitleState(List<String> elementList, boolean hide, boolean invent) {
        if (hide) {
            if (invent) {
                return elementList.contains(acceptElements.get(13)) && ClientProxy.showTitle;
            } else {
                return !elementList.contains(acceptElements.get(13)) && ClientProxy.showTitle;
            }
        } else {
            if (invent) {
                return !elementList.contains(acceptElements.get(13)) || ClientProxy.showTitle;
            } else {
                return elementList.contains(acceptElements.get(13)) || ClientProxy.showTitle;
            }
        }
    }

    public boolean getAllState(List<String> elementList, boolean hide, boolean invent) {
        if (hide) {
            if (invent) {
                return elementList.contains(acceptElements.get(14)) && ClientProxy.showAllFULL;
            } else {
                return !elementList.contains(acceptElements.get(14)) && ClientProxy.showAllFULL;
            }
        } else {
            if (invent) {
                return !elementList.contains(acceptElements.get(14)) || ClientProxy.showAllFULL;
            } else {
                return elementList.contains(acceptElements.get(14)) || ClientProxy.showAllFULL;
            }
        }
    }

    public boolean getChatState(List<String> elementList, boolean hide, boolean invent) {
        if (hide) {
            if (invent) {
                return elementList.contains(acceptElements.get(15)) && ClientProxy.showChat;
            } else {
                return !elementList.contains(acceptElements.get(15)) && ClientProxy.showChat;
            }
        } else {
            if (invent) {
                return !elementList.contains(acceptElements.get(15)) || ClientProxy.showChat;
            } else {
                return elementList.contains(acceptElements.get(15)) || ClientProxy.showChat;
            }
        }
    }

    public boolean getInventoryState(List<String> elementList, boolean hide, boolean invent) {
        if (hide) {
            if (invent) {
                return elementList.contains(acceptElements.get(16)) && ClientProxy.showInventory;
            } else {
                return !elementList.contains(acceptElements.get(16)) && ClientProxy.showInventory;
            }
        } else {
            if (invent) {
                return !elementList.contains(acceptElements.get(16)) || ClientProxy.showInventory;
            } else {
                return elementList.contains(acceptElements.get(16)) || ClientProxy.showInventory;
            }
        }
    }

    public boolean getLinesState(List<String> elementList, boolean hide, boolean invent) {
        if (hide) {
            if (invent) {
                return elementList.contains(acceptElements.get(17)) && ClientProxy.showLines;
            } else {
                return !elementList.contains(acceptElements.get(17)) && ClientProxy.showLines;
            }
        } else {
            if (invent) {
                return !elementList.contains(acceptElements.get(17)) || ClientProxy.showLines;
            } else {
                return elementList.contains(acceptElements.get(17)) || ClientProxy.showLines;
            }
        }
    }

}
