package dev.decobr.mcgeforce;

import dev.decobr.mcgeforce.bindings.MCGeForceHelper;
import dev.decobr.mcgeforce.handlers.TriggerHandler;
import dev.decobr.mcgeforce.handlers.impl.HypixelTriggerHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

@Mod(modid = MCGeForce.MODID, version = MCGeForce.VERSION)
public class MCGeForce {

    public static final String MODID = "mcgeforce";
    public static final String VERSION = "SLLCoding Rewrite";


    private final List<TriggerHandler> triggerHandlers = new ArrayList<>();
    private static MCGeForce instance;

    @EventHandler
    public void init(FMLInitializationEvent event) {
        instance = this;

        MinecraftForge.EVENT_BUS.register(this);
        //ClientCommandHandler.instance.registerCommand(new HighlightsCommand());


        triggerHandlers.add(new HypixelTriggerHandler());

        MCGeForceHelper.initialise();
    }

    @SubscribeEvent
    public void onChatMessage(ClientChatReceivedEvent event) {
        String message = EnumChatFormatting.getTextWithoutFormattingCodes(event.message.getUnformattedText());

        for (TriggerHandler triggerHandler : triggerHandlers) if (triggerHandler.onMessage(message)) break;
    }

    @SubscribeEvent
    public void onGuiInit(GuiScreenEvent.InitGuiEvent.Post event) {
        if(event.gui instanceof GuiIngameMenu) {
            GuiButton nvidiaButton = new GuiButton(999, 5, 5, "NVIDIA Highlights");
            nvidiaButton.width = 100;

            event.buttonList.add(nvidiaButton);
        }
    }

    @SubscribeEvent
    public void onActionPerformed(GuiScreenEvent.ActionPerformedEvent.Post event) {
        if(event.gui instanceof GuiIngameMenu && event.button.id == 999) {
            MCGeForceHelper.showHighlights();
        }
    }

    public List<TriggerHandler> getTriggerHandlers() {
        return triggerHandlers;
    }

    public static MCGeForce getInstance() {
        return instance;
    }

}
