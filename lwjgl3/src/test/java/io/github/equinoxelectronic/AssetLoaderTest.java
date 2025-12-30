package io.github.equinoxelectronic;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import io.github.equinoxelectronic.utility.AssetManager;

public class AssetLoaderTest extends ApplicationAdapter {
    @Override
    public void create() {
        System.out.println("Starting AssetLoaderTest...");

        // Get the singleton instance
        AssetManager assetManager = AssetManager.getInstance();

        // Load assets
        assetManager.loadAssets("assets");

        // Print all loaded assets
        System.out.println("Loaded assets:");
        assetManager.getAllAssets().forEach((k, v) -> System.out.println(k + " -> " + v.getClass().getSimpleName()));

        // Exit after testing
        Gdx.app.exit();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowedMode(800, 600);
        new Lwjgl3Application(new AssetLoaderTest(), config);
    }
}
