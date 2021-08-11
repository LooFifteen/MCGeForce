package dev.decobr.mcgeforce.bindings;

import dev.decobr.mcgeforce.utils.NativeUtils;
import dev.sllcoding.mcgeforce.data.HighlightType;

import java.io.IOException;

/**
 * The class which uses JNI to communicate with the MCGeForce Helper (MCGeForce.dll)
 */
public class MCGeForceHelper {
    public static final MCGeForceHelper INSTANCE = new MCGeForceHelper();

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

    public void initialise() {
        INSTANCE.init("mcgeforcemod");
    }

    public void saveHighlight(HighlightType type, int start, int end) {
        INSTANCE.setVideoHighlight(type.getId(), start, end);
    }

    public void showHighlights() {
        INSTANCE.showHighlightsEditor();
    }

}
