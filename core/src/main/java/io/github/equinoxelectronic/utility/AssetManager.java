package io.github.equinoxelectronic.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Logger;

import java.io.File;
import java.util.HashMap;

/**
 * A simplified asset manager that loads all assets at startup.
 */
public class AssetManager {
    // Singleton instance
    private static AssetManager instance;

    // Logger for debugging
    private static final Logger logger = new Logger("AssetManager", Logger.DEBUG);

    // Map to store all loaded assets
    private HashMap<String, Object> assets;

    /**
     * Private constructor to prevent instantiation
     */
    private AssetManager() {
        assets = new HashMap<>();
    }

    /**
     * Get the singleton instance of AssetManager
     * @return the singleton instance
     */
    public static AssetManager getInstance() {
        if (instance == null) {
            instance = new AssetManager();
        }
        return instance;
    }

    /**
     * Loads all assets from the specified directory
     * @param directoryPath the directory to load assets from
     */
    public void loadAssets(String directoryPath) {
        logger.info("Loading assets from: " + directoryPath);

        if (directoryPath == null || directoryPath.isEmpty()) {
            logger.error("Directory path is null or empty");
            return;
        }

        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            logger.error("Directory does not exist or is not a directory: " + directoryPath);
            return;
        }

        // Clear existing assets
        assets.clear();

        // Load all assets recursively
        loadAssetsRecursively(directory, "");

        logger.info("Loaded " + assets.size() + " assets");

        // Debug: print all loaded assets
        for (String key : assets.keySet()) {
            logger.debug("Loaded asset: " + key + " (" + assets.get(key).getClass().getSimpleName() + ")");
        }
    }

    /**
     * Recursively loads assets from a directory
     * @param directory the directory to load from
     * @param path the current path prefix
     */
    private void loadAssetsRecursively(File directory, String path) {
        File[] files = directory.listFiles();
        if (files == null) {
            return;
        }

        for (File file : files) {
            String filePath = path.isEmpty() ? file.getName() : path + "/" + file.getName();

            if (file.isDirectory()) {
                loadAssetsRecursively(file, filePath);
            } else {
                Object asset = ObjectConverter.convert(file);
                assets.put(filePath, asset);
            }
        }
    }

    /**
     * Gets an asset by its path
     * @param path the path to the asset
     * @return the asset, or null if not found
     */
    public Object getAsset(String path) {
        return assets.get(path);
    }

    /**
     * Gets a texture region by its path
     * @param path the path to the texture region
     * @return the texture region, or null if not found
     */
    public TextureRegion getTextureRegion(String path) {
        Object asset = assets.get(path);
        if (asset instanceof TextureRegion) {
            return (TextureRegion) asset;
        }
        logger.error("Asset is not a TextureRegion: " + path);
        return null;
    }

    /**
     * Gets a sound by its path
     * @param path the path to the sound
     * @return the sound, or null if not found
     */
    public Sound getSound(String path) {
        Object asset = assets.get(path);
        if (asset instanceof Sound) {
            return (Sound) asset;
        }
        return null;
    }

    /**
     * Gets a shader by its path
     * @param path the path to the shader
     * @return the shader, or null if not found
     */
    public ShaderProgram getShader(String path) {
        Object asset = assets.get(path);
        if (asset instanceof ShaderProgram) {
            return (ShaderProgram) asset;
        }
        return null;
    }

    /**
     * Gets a text file by its path
     * @param path the path to the text file
     * @return the text content, or null if not found
     */
    public String getText(String path) {
        Object asset = assets.get(path);
        if (asset instanceof String) {
            return (String) asset;
        }
        return null;
    }

    /**
     * Gets all loaded assets
     * @return a map of all loaded assets
     */
    public HashMap<String, Object> getAllAssets() {
        return assets;
    }

    /**
     * Disposes all disposable assets
     */
    public void dispose() {
        for (Object asset : assets.values()) {
            if (asset instanceof Texture) {
                ((Texture) asset).dispose();
            } else if (asset instanceof Sound) {
                ((Sound) asset).dispose();
            } else if (asset instanceof ShaderProgram) {
                ((ShaderProgram) asset).dispose();
            }
        }
        assets.clear();
    }
}
