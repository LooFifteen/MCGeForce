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
    /**
     * The mod id of the mod
     */
    public static final String MODID = "mcgeforce";

    /**
     * The current version of the mod
     */
    public static final String VERSION = "1.0.2";

    /**
     * An array list of TriggerHandlers to check messages against
     */
    private final List<TriggerHandler> triggerHandlers = new ArrayList<>();

    /**
     * Fired when FML is ready for our mod to start loading
     * @see FMLInitializationEvent
     */
    @EventHandler
    public void init(FMLInitializationEvent event) {
        // Register stuff
        MinecraftForge.EVENT_BUS.register(this);
        //ClientCommandHandler.instance.registerCommand(new HighlightsCommand());

        // Populate triggerHandlers arraylist with the available trigger handlers
        initTriggerHandlers();

        // Initialise connection with GeForce Experience
        MCGeForceHelper.INSTANCE.initialise();
    }

    /**
     * Fired when a chat message is received by the client
     * @see ClientChatReceivedEvent
     */
    @SubscribeEvent
    public void onChatMessage(ClientChatReceivedEvent event) {
        String message = EnumChatFormatting.getTextWithoutFormattingCodes(event.message.getUnformattedText());

        for (TriggerHandler triggerHandler : triggerHandlers) {
            if(triggerHandler.checkAll(message)) break;
        }
    }

    /**
     * Fired when a GUI is initialising
     * @see GuiScreenEvent
     */
    @SubscribeEvent
    public void onGuiInit(GuiScreenEvent.InitGuiEvent.Post event) {
        if(event.gui instanceof GuiIngameMenu) {
            GuiButton nvidiaButton = new GuiButton(999, 5, 5, "NVIDIA Highlights");
            nvidiaButton.width = 100;

            event.buttonList.add(nvidiaButton);
        }
    }

    /**
     * Fired when a button is pressed on any GuiScreen instance
     * @see GuiScreenEvent
     */
    @SubscribeEvent
    public void onActionPerformed(GuiScreenEvent.ActionPerformedEvent.Post event) {
        if(event.gui instanceof GuiIngameMenu && event.button.id == 999) {
            MCGeForceHelper.INSTANCE.showHighlights();
        }
    }

    /**
     * Adds all trigger handlers into an array list
     */
    private void initTriggerHandlers() {
        triggerHandlers.add(HypixelTriggerHandler.INSTANCE);
    }
}
