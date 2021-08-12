package dev.sllcoding.mcgeforce.data;

import java.util.Arrays;
import java.util.Optional;

public enum ReturnCode {

    SUCCESS(0, "Success"),
    VERSION_OLD_SDK(1001, "Update MCGeForce"),
    VERSION_OLD_GFE(1002, "Update GeForce Experience"),
    SUCCESS_PENDING(1003, "Pending"),
    USER_NOT_INTERESTED(1004, "Permission Denied"),
    PERMISSION_GRANTED(1005, "Permission Granted"),
    LINKED(1006, "Successfully Linked"),

    ERR_GENERIC(-1001, "Internal Error"),
    ERR_GFE_VERSION(-1002, "Update GeForce Experience"),
    ERR_SDK_VERSION(-1003, "Update MCGeForce"),
    ERR_NOT_IMPLEMENTED(-1004, "Internal Error"),
    ERR_INVALID_PARAMETER(-1005, "Internal Error"),
    ERR_NOT_SET(-1006, "Internal Error"),
    ERR_SHADOWPLAY_IR_DISABLED(-1007, "Enable Nvidia Highlights"),
    ERR_SDK_IN_USE(-1008, "Internal Error"),
    ERR_GROUP_NOT_FOUND(-1009, "Internal Error"),
    ERR_FILE_NOT_FOUND(-1010, "File Not Found"),
    ERR_HIGHLIGHTS_SETUP_FAILED(-1011, "Internal Error"),
    ERR_HIGHLIGHTS_NOT_CONFIGURED(-1012, "Enable Nvidia Highlights"),
    ERR_HIGHLIGHTS_SAVE_FAILED(-1013, "Internal Error"),
    ERR_UNEXPECTED_EXCEPTION(-1014, "Internal Error"),
    ERR_NO_HIGHLIGHTS(-1015, "No Clips Saved"),
    ERR_NO_CONNECTION(-1016, "Connect to the Internet"),
    ERR_PERMISSION_NOT_GRANTED(-1017, "Allow MCGeForce access to Highlights"),
    ERR_PERMISSION_DENIED(-1018, "Allow MCGeForce access to Highlights"),
    ERR_INVALID_HANDLE(-1019, "Internal Error"),
    ERR_UNHANDLED_EXCEPTION(-1020, "Internal Error"),
    ERR_OUT_OF_MEMORY(-1021, "Out of Memory"),
    ERR_LOAD_LIBRARY(-1022, "Internal Error"),
    ERR_LIB_CALL_FAILED(-1023, "Internal Error"),
    ERR_IPC_FAILED(-1024, "Internal Error"),
    ERR_CONNECTION(-1025, "Internal Error"),
    ERR_MODULE_NOT_LOADED(-1026, "Internal Error"),
    ERR_LIB_CALL_TIMEOUT(-1027, "Internal Error"),
    ERR_APPLICATION_LOOKUP_FAILED(-1028, "Internal Error"),
    ERR_APPLICATION_NOT_KNOWN(-1029, "Internal Error"),
    ERR_FEATURE_DISABLED(-1030, "Enable Nvidia Highlights"),
    ERR_APP_NO_OPTIMIZATION(-1031, "Internal Error"),
    ERR_APP_SETTINGS_READ(-1032, "Allow MCGeForce access to Highlights"),
    ERR_APP_SETTINGS_WRITE(-1033, "Allow MCGeForce access to Highlights"),
    ERR_HIGHLIGHT_NOT_DEFINED(-1034, "Internal Error"),
    ERR_CAP_LIMIT_REACHED(-1035, "Delete some Highlights before continuing"),
    ERR_DISK_LIMIT_REACHED(-1036, "No Disk Space");

    private final int code;
    private final String description;

    ReturnCode(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int code() {
        return code;
    }

    public String description() {
        return description;
    }

    public boolean isSuccess() {
        return code >= 0;
    }

    public static Optional<ReturnCode> from(int id) {
        return Arrays.stream(values()).filter(code -> code.code == id).findFirst();
    }

    public static Optional<ReturnCode> from(String name) {
        String newName = name.replaceFirst("NVGSDK_", "");
        return Arrays.stream(values()).filter(code -> code.name().equalsIgnoreCase(newName)).findFirst();
    }

}
