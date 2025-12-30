package io.github.equinoxelectronic.controlling;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import java.util.HashMap;

/**
 * Singleton class for managing key bindings.
 */
public class KeyBindManager {
    private static KeyBindManager instance;
    /**
     * Key bindings for each action.
     */
    private HashMap<String, Integer> keyBindings;

    private KeyBindManager() {
        keyBindings = new HashMap<>();
    }

    public static KeyBindManager getInstance() {
        if (instance == null) {
            instance = new KeyBindManager();
        }
        return instance;
    }

    /**
     * Checks if the specified key is pressed.
     * @param action The action to check.
     * @return True if the key is pressed, false otherwise.
     */
    public boolean isKeyPressed(String action) {
        Integer keyCode = keyBindings.get(action);
        return keyCode != null && Gdx.input.isKeyPressed(keyCode);
    }

    /**
     * Checks if the specified key was just pressed.
     * @param action The action to check.
     * @return True if the key was just pressed, false otherwise.
     */
    public boolean isKeyJustPressed(String action) {
        Integer keyCode = keyBindings.get(action);
        return keyCode != null && Gdx.input.isKeyJustPressed(keyCode);
    }

    /**
     * Sets the key binding for the specified action.
     * @param action
     * @param keyCode
     */
    public void setKeyBinding(String action, int keyCode) {
        keyBindings.put(action, keyCode);
    }

    /**
     * Checks if the specified key combination is pressed.
     * @param keyCodes
     * @return
     */
    public boolean multipleKeyBinding(int... keyCodes) {
        boolean isPressed = true;
        for (int keyCode : keyCodes) {
            if(!Gdx.input.isKeyPressed(keyCode) || !isPressed) {
                isPressed = false;
                break;
            }
        }
        return isPressed;
    }

    /**
     * Checks if the specified key is pressed, not using the key manager
     * @param keyCode the code for the key
     * @return is the key pressed?
     */
    public boolean isKeyPressed(int keyCode) {
        return Gdx.input.isKeyPressed(keyCode);
    }
}
