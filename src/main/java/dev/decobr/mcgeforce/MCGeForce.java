package dev.decobr.mcgeforce;

import dev.decobr.mcgeforce.bindings.MCGeForceHelper;
import dev.decobr.mcgeforce.config.MCGeForceConfig;
import dev.decobr.mcgeforce.handlers.TriggerHandler;
import dev.decobr.mcgeforce.handlers.impl.HypixelTriggerHandler;
import dev.decobr.mcgeforce.handlers.impl.VanillaTriggerHandler;
import gg.essential.api.utils.GuiUtil;
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
import java.util.Objects;

@Mod(modid = "mcgeforce", version = "SLLCoding Rewrite")
public class MCGeForce {

    @Mod.Instance private static MCGeForce instance;
    private final List<TriggerHandler> triggerHandlers = new ArrayList<>();
    private MCGeForceConfig config;

    @EventHandler
    public void init(FMLInitializationEvent event) {
        config = new MCGeForceConfig();
        config.preload();

        MinecraftForge.EVENT_BUS.register(this);

        triggerHandlers.add(new HypixelTriggerHandler());
        triggerHandlers.add(new VanillaTriggerHandler());

        MCGeForceHelper.initialise();
    }

    @SubscribeEvent
    public void onChatMessage(ClientChatReceivedEvent event) {
        if (!config.isEnabled()) return;
        String message = EnumChatFormatting.getTextWithoutFormattingCodes(event.message.getUnformattedText());
        for (TriggerHandler triggerHandler : triggerHandlers) if (triggerHandler.isEnabled()) if (triggerHandler.onMessage(message)) break;
    }

    @SubscribeEvent
    public void onGuiInit(GuiScreenEvent.InitGuiEvent.Post event) {
        if (event.gui instanceof GuiIngameMenu) {
            GuiButton mcGeForceButton = new GuiButton(998, 5, 5, 100, 20, "MCGeForce");
            event.buttonList.add(mcGeForceButton);

            if (config.isEnabled()) {
                GuiButton clips = new GuiButton(999, 5, 27, 100, 20, "Clips");
                event.buttonList.add(clips);
            }
        }
    }

    @SubscribeEvent
    public void onActionPerformed(GuiScreenEvent.ActionPerformedEvent.Post event) {
        if (event.gui instanceof GuiIngameMenu) {
            if (event.button.id == 998) {
                GuiUtil.open(Objects.requireNonNull(config.gui()));
            } else if (event.button.id == 999) {
                MCGeForceHelper.showHighlights();
            }
        }
    }

    public List<TriggerHandler> getTriggerHandlers() {
        return triggerHandlers;
    }

    public static MCGeForce getInstance() {
        return instance;
    }

    public MCGeForceConfig getConfig() {
        return config;
    }

}
