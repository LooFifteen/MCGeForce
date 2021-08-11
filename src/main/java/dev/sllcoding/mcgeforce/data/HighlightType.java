package dev.sllcoding.mcgeforce.data;

public enum HighlightType {

    KILL("mcg_kill", "Kill"),
    GAME("mcg_game", "Game"),
    WIN("mcg_win", "Win"),
    DEATH("mcg_death", "Death");

    private final String id;
    private final String name;

    HighlightType(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

}
