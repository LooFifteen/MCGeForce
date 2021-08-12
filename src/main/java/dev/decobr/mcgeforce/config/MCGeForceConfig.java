package dev.decobr.mcgeforce.config;

import dev.decobr.mcgeforce.bindings.MCGeForceHelper;
import gg.essential.vigilance.Vigilant;
import gg.essential.vigilance.data.Property;
import gg.essential.vigilance.data.PropertyType;

import java.io.File;

public class MCGeForceConfig extends Vigilant {

    /*
        GENERAL
     */
    @Property(
            type = PropertyType.SWITCH, name = "MCGeForce",
            description = "Turn on and off MCGeForce",
            category = "General", subcategory = "General"
    )
    private boolean enabled = true;

    @Property(
            type = PropertyType.BUTTON, name = "View Clips",
            description = "View recorded clips",
            category = "General", subcategory = "General"
    )
    private void viewClips() {
        MCGeForceHelper.showHighlights();
    }

    @Property(
            type = PropertyType.DECIMAL_SLIDER, name = "Clip Length",
            description = "Length of clips (s)",
            category = "General", subcategory = "Timings",
            minF = 1, maxF = 60
    )
    private float clipLength = 10;
    /*
        GENERAL
     */


    /*
        HYPIXEL
     */
    @Property(
            type = PropertyType.SWITCH, name = "Hypixel Clips",
            description = "Turn on and off clips for Hypixel",
            category = "Hypixel", subcategory = "General"
    )
    private boolean hypixelEnabled = true;

    @Property(
            type = PropertyType.SWITCH, name = "Kills",
            description = "Record your kills",
            category = "Hypixel", subcategory = "General"
    )
    private boolean hypixelKillsEnabled = true;

    @Property(
            type = PropertyType.SWITCH, name = "Deaths",
            description = "Record your deaths",
            category = "Hypixel", subcategory = "General"
    )
    private boolean hypixelDeathsEnabled = true;

    @Property(
            type = PropertyType.SWITCH, name = "Wins",
            description = "Record your wins",
            category = "Hypixel", subcategory = "General"
    )
    private boolean hypixelWinsEnabled = true;
    /*
        HYPIXEL
     */

    public MCGeForceConfig() {
        super(new File("./config/mcgeforce.toml"));
        initialize();
    }

    public boolean isEnabled() {
        return enabled;
    }

    public float getClipLength() {
        return clipLength;
    }

    public boolean isHypixelEnabled() {
        return hypixelEnabled;
    }

    public boolean isHypixelDeathsEnabled() {
        return hypixelDeathsEnabled;
    }

    public boolean isHypixelKillsEnabled() {
        return hypixelKillsEnabled;
    }

    public boolean isHypixelWinsEnabled() {
        return hypixelWinsEnabled;
    }

}
