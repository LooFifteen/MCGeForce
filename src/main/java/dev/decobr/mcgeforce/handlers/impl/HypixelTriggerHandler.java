package dev.decobr.mcgeforce.handlers.impl;

import dev.decobr.mcgeforce.handlers.TriggerHandler;
import dev.sllcoding.mcgeforce.data.HighlightType;
import net.minecraft.client.Minecraft;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HypixelTriggerHandler implements TriggerHandler {
    public static final HypixelTriggerHandler INSTANCE = new HypixelTriggerHandler();

    private String message = "";
    private String username = "";

    /**
     * The time (in milliseconds) that the game started at
     *
     * @see System#currentTimeMillis()
     */
    public long startGameTime = 0;

    /**
     * Check if the message indicates that the game has begun
     */
    public boolean checkStartGame() {
        boolean gameStarted = message.equals("The game starts in 1 second!");

        if (gameStarted) {
            startGameTime = System.currentTimeMillis();
            System.out.println("Game has started!");
        }

        return gameStarted;
    }

    /**
     * Check if the message indicates that the player has won a game
     */
    public boolean checkWin() {
        return false;
    }

    /**
     * Check if the message indicates that our player has eliminated another player or has died
     */
    public boolean checkKill() {
        try {
            String killMessageRegex = "(\\w{1,16}).+ (by|of|to|for|with) (" + username + ")";
            String usernamePatternRegex = "^[a-zA-Z0-9_-]{3,16}$";

            Pattern killMessagePattern = Pattern.compile(killMessageRegex);
            Pattern usernamePattern = Pattern.compile(usernamePatternRegex);

            Matcher killMessageMatcher = killMessagePattern.matcher(message);
            Matcher usernameMatcher = usernamePattern.matcher(message.split(" ")[0]);

            if (killMessageMatcher.find() && usernameMatcher.find()) {
                String killed = killMessageMatcher.group(1);
                System.out.println("Killed: " + killed);
                System.out.println("Username: " + username);

                if (!killed.equals(username)) {
                    createHighlight(HighlightType.KILL, -10000, 1000);

                    return true;
                }
            }
        } catch (Exception ignored) {
        }

        return false;
    }

    /**
     * Check if the message indicates that our player has died
     */
    public boolean checkDeath() {
        try {
            String toWorldRegex = "(" + username + ")+ (fell|died|burned)"; // https://regexr.com/594li
            String toPlayerRegex = "(" + username + ")+ (\\w{1,16}).+ (by|of|to|for|with)"; // https://regexr.com/594li

            Pattern toWorldPattern = Pattern.compile(toWorldRegex);
            Pattern toPlayerPattern = Pattern.compile(toPlayerRegex);

            Matcher toWorldMatcher = toWorldPattern.matcher(message);
            Matcher toPlayerMatcher = toPlayerPattern.matcher(message);

            if (toWorldMatcher.find() || toPlayerMatcher.find()) {
                createHighlight(HighlightType.DEATH, -10000, 1000);

                return true;
            }
        } catch (Exception ignored) {
        }

        return false;
    }

    /**
     * Fired when a chat message is recieved and we should check all available message types
     *
     * @param message the chat message
     */
    public boolean checkAll(String message) {
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
