package dev.decobr.mcgeforce.bindings;

import dev.decobr.mcgeforce.utils.NativeUtils;
import dev.sllcoding.mcgeforce.data.HighlightType;

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

    private native void init(String id);
    private native void setVideoHighlight(String id, int start, int end);
    private native void showHighlightsEditor();

    public static void initialise() {
        if (!initialised) {
            instance.init("mcgeforcemod");
            initialised = true;
        }
    }

    public static void saveHighlight(HighlightType type, int start, int end) {
        instance.setVideoHighlight(type.getId(), start, end);
    }

    public static void showHighlights() {
        instance.showHighlightsEditor();
    }

}
