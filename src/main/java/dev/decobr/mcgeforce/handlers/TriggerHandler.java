package dev.decobr.mcgeforce.handlers;

public interface TriggerHandler {

    boolean isEnabled();
    boolean onMessage(String message);
    boolean onTitle(String title);

}
