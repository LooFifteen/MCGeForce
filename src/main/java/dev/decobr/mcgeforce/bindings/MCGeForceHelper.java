package dev.decobr.mcgeforce.bindings;

import dev.decobr.mcgeforce.MCGeForce;
import dev.decobr.mcgeforce.utils.NativeUtils;
import dev.sllcoding.mcgeforce.data.HighlightType;
import dev.sllcoding.mcgeforce.data.ReturnCode;
import gg.essential.api.EssentialAPI;

import java.io.IOException;

public class MCGeForceHelper {

    private static final MCGeForceHelper instance = new MCGeForceHelper();
    private static boolean initialised = false;

    static {
        try {
            NativeUtils.loadLibraryFromJar("/GfeSDK.dll");
            NativeUtils.loadLibraryFromJar("/MCGeForce.dll");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private native int init(String id);
    private native void setVideoHighlight(String id, int start, int end);
    private native void showHighlightsEditor();

    public static void initialise() {
        if (!initialised) {
            ReturnCode.from(instance.init("mcgeforcemod")).ifPresent(code -> {
                if (code.isSuccess()) initialised = true;
                else {
                    EssentialAPI.getNotifications().push("Error while starting MCGeForce.", code.description());
                }
            });
        }
    }

    public static void saveHighlight(HighlightType type) {
        saveHighlight(type, (int) (1000 - (MCGeForce.getInstance().getConfig().getClipLength() * 1000)), 1000);
    }

    public static void saveHighlight(HighlightType type, int start, int end) {
        if (MCGeForce.getInstance().getConfig().isEnabled()) instance.setVideoHighlight(type.getId(), start, end);
    }

    public static void showHighlights() {
        if (MCGeForce.getInstance().getConfig().isEnabled()) instance.showHighlightsEditor();
    }

}
