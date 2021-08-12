package dev.decobr.mcgeforce.handlers.impl;

import dev.decobr.mcgeforce.MCGeForce;
import dev.decobr.mcgeforce.bindings.MCGeForceHelper;
import dev.decobr.mcgeforce.handlers.TriggerHandler;
import dev.sllcoding.mcgeforce.data.HighlightType;
import gg.essential.api.EssentialAPI;
import net.minecraft.client.Minecraft;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HypixelTriggerHandler implements TriggerHandler {

    private String message = "";
    private String username = "";

    public long startGameTime = 0;

    @Override
    public boolean isEnabled() {
        return MCGeForce.getInstance().getConfig().isHypixelEnabled() && EssentialAPI.getMinecraftUtil().isHypixel();
    }

    @Override
    public boolean onMessage(String message) {
        if(Minecraft.getMinecraft().thePlayer == null) return false;

        this.message = message;
        this.username = Minecraft.getMinecraft().thePlayer.getName();

        if (!isMessageFromPlayer()) {
            if (!checkStartGame()) {
                if (!checkKill()) {
                    return checkDeath();
                } else {
                    return true;
                }
            }
        }

        this.message = "";

        return false;
    }

    @Override
    public boolean onTitle(String title) {
        if (!MCGeForce.getInstance().getConfig().isHypixelWinsEnabled()) return false;

        title = title.toLowerCase();
        long startTime = startGameTime;
        int endTime = (int) (startGameTime - System.currentTimeMillis());

        if (startTime != 0) {
            if (title.contains("game over")) {
                MCGeForceHelper.saveHighlight(HighlightType.GAME, endTime, 1000);
                startGameTime = 0;
                return true;
            } else if ((title.contains("you won") || title.startsWith("victory"))) {
                MCGeForceHelper.saveHighlight(HighlightType.WIN, endTime, 1000);
                startGameTime = 0;
                return true;
            }
        }

        return false;
    }

    public boolean checkStartGame() {
        boolean gameStarted = message.equals("The game starts in 1 second!");

        if (gameStarted) {
            startGameTime = System.currentTimeMillis();
        }

        return gameStarted;
    }

    public boolean checkKill() {
        if (!MCGeForce.getInstance().getConfig().isHypixelKillsEnabled()) return false;
        try {
            String killMessageRegex = "(\\w{1,16}).+ (by|of|to|for|with) (" + username + ")";
            String usernamePatternRegex = "^[a-zA-Z0-9_-]{3,16}$";

            Pattern killMessagePattern = Pattern.compile(killMessageRegex);
            Pattern usernamePattern = Pattern.compile(usernamePatternRegex);

            Matcher killMessageMatcher = killMessagePattern.matcher(message);
            Matcher usernameMatcher = usernamePattern.matcher(message.split(" ")[0]);

            if (killMessageMatcher.find() && usernameMatcher.find()) {
                String killed = killMessageMatcher.group(1);

                if (!killed.equals(username)) {
                    MCGeForceHelper.saveHighlight(HighlightType.KILL);
                    return true;
                }
            }
        } catch (Exception ignored) {}

        return false;
    }

    public boolean checkDeath() {
        if (!MCGeForce.getInstance().getConfig().isHypixelDeathsEnabled()) return false;
        try {
            String toWorldRegex = "(" + username + ")+ (fell|died|burned)"; // https://regexr.com/594li
            String toPlayerRegex = "(" + username + ")+ (\\w{1,16}).+ (by|of|to|for|with)"; // https://regexr.com/594li

            Pattern toWorldPattern = Pattern.compile(toWorldRegex);
            Pattern toPlayerPattern = Pattern.compile(toPlayerRegex);

            Matcher toWorldMatcher = toWorldPattern.matcher(message);
            Matcher toPlayerMatcher = toPlayerPattern.matcher(message);

            if (toWorldMatcher.find() || toPlayerMatcher.find()) {
                MCGeForceHelper.saveHighlight(HighlightType.DEATH);
                return true;
            }
        } catch (Exception ignored) {
        }

        return false;
    }

    /**
     * Check if message is from a player, if it is then return true
     * <p>
     * Valid match examples:
     * [MVP+] Decobr: Hello!
     * [SHOUT] [MVP+] Decobr: Hello!
     * [SHOUT] Decobr: Hello!
     * Decobr: Hello!
     */
    public boolean isMessageFromPlayer() {
        Pattern userMessage = Pattern.compile("(\\[.*])?.* ?(\\w{1,16}): .*");
        return userMessage.matcher(message).matches();
    }

}
