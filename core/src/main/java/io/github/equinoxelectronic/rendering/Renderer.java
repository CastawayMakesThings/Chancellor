package io.github.equinoxelectronic.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.equinoxelectronic.utility.AssetManager;

/**
 * The Renderer class handles all rendering operations for the game.
 * It uses a viewport-based approach with a meter-based coordinate system for consistent
 * rendering across different screen sizes and resolutions.
 *
 * The coordinate system is based on meters rather than pixels, where:
 * - 1 meter = 100 pixels (standard Box2D scale)
 * - The origin (0,0) is at the bottom-left corner of the screen
 * - Positive x is to the right, positive y is up
 *
 * This class is designed to be scalable and adaptable to different screen sizes
 * through the use of viewports.
 */
public class Renderer {
    private static final Logger logger = new Logger("Chancellor Renderer", Logger.DEBUG);

    // The world dimensions in meters
    private static final float WORLD_WIDTH = 16f;  // 16 meters wide (standard for 16:9 aspect ratio)
    private static final float WORLD_HEIGHT = 9f;  // 9 meters high

    // Pixels per meter (for conversion between world and screen coordinates)
    private static final float PPM = 100f;  // 100 pixels = 1 meter

    // Camera and viewport for handling different screen sizes
    private static OrthographicCamera camera;
    private static Viewport viewport;

    // Debug mode flag
    private static boolean debugMode = false;

    /**
     * Initializes the renderer with default settings.
     * Sets up the camera and viewport for rendering.
     */
    public static void init() {
        logger.info("Initializing Renderer...");

        // Create a new orthographic camera
        camera = new OrthographicCamera();

        // Create an ExtendViewport which maintains the aspect ratio
        // and extends the world in one direction if needed
        viewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        // Set the camera position to the center of the world
        camera.position.set(WORLD_WIDTH / 2f, WORLD_HEIGHT / 2f, 0);

        // Enable debug mode by default to show the example
        debugMode = true;
        logger.info("Debug mode enabled by default");

        logger.info("Renderer initialized with world dimensions: " +
                    WORLD_WIDTH + "m x " + WORLD_HEIGHT + "m");
    }

    /**
     * Renders the game world using the provided SpriteBatch.
     *
     * @param batch The SpriteBatch to use for rendering
     */
    public static void render(SpriteBatch batch) {
        // Update the camera
        camera.update();

        // Set the projection matrix of the batch to the camera's combined matrix
        batch.setProjectionMatrix(camera.combined);

        // Rendering code will go here
        // This is where you would draw sprites, backgrounds, etc.

        // Example of how to render a texture (commented out as it's just an example)
        // TextureRegion texture = AssetManager.getInstance().getTextureRegion("example.png");
        // if (texture != null) {
        //     batch.draw(texture, 0, 0, 1, 1); // Draw at (0,0) with size 1m x 1m
        // }

        // Debug rendering if enabled
        if (debugMode) {
            renderDebugInfo(batch);
        }
    }

    /**
     * Renders debug information when debug mode is enabled.
     * This includes a grid and coordinate axes to visualize the meter-based coordinate system.
     *
     * @param batch The SpriteBatch to use for rendering
     */
    private static void renderDebugInfo(SpriteBatch batch) {
        // Get the libGDX logo texture for demonstration
        TextureRegion logo = AssetManager.getInstance().getTextureRegion("libgdx.png");

        if (logo != null) {
            // Draw the logo at the center of the world with a size of 2x2 meters
            float logoX = WORLD_WIDTH / 2f - 1f; // Center X - half width
            float logoY = WORLD_HEIGHT / 2f - 1f; // Center Y - half height
            batch.draw(logo, logoX, logoY, 2f, 2f); // 2x2 meters

            // Draw text to indicate coordinates (would need a BitmapFont in a real implementation)
            logger.debug("Logo drawn at world coordinates: (" + logoX + "m, " + logoY + "m)");
            logger.debug("Logo size: 2m x 2m");
        }
    }

    /**
     * Handles screen resize events by updating the viewport.
     * This ensures the game looks consistent across different screen sizes.
     *
     * @param width The new screen width in pixels
     * @param height The new screen height in pixels
     */
    public static void resize(int width, int height) {
        logger.info("Resizing viewport to: " + width + "x" + height);
        viewport.update(width, height, true);
        camera.position.set(WORLD_WIDTH / 2f, WORLD_HEIGHT / 2f, 0);
    }

    /**
     * Converts screen coordinates to world coordinates.
     * Useful for handling input that needs to interact with the game world.
     *
     * @param screenX The x-coordinate in screen space (pixels)
     * @param screenY The y-coordinate in screen space (pixels)
     * @return float[] Array containing the world x and y coordinates in meters
     */
    public static float[] screenToWorld(int screenX, int screenY) {
        // Unproject the screen coordinates to world coordinates
        camera.unproject(camera.position.set(screenX, screenY, 0));
        return new float[] { camera.position.x, camera.position.y };
    }

    /**
     * Converts world coordinates to screen coordinates.
     *
     * @param worldX The x-coordinate in world space (meters)
     * @param worldY The y-coordinate in world space (meters)
     * @return int[] Array containing the screen x and y coordinates in pixels
     */
    public static int[] worldToScreen(float worldX, float worldY) {
        // Project the world coordinates to screen coordinates
        camera.project(camera.position.set(worldX, worldY, 0));
        return new int[] { (int)camera.position.x, (int)camera.position.y };
    }

    /**
     * Toggles debug mode on/off.
     * When debug mode is enabled, additional visual information is rendered.
     *
     * @param enabled Whether debug mode should be enabled
     */
    public static void setDebugMode(boolean enabled) {
        debugMode = enabled;
        logger.info("Debug mode " + (enabled ? "enabled" : "disabled"));
    }

    /**
     * Gets the current camera used by the renderer.
     *
     * @return The OrthographicCamera instance
     */
    public static OrthographicCamera getCamera() {
        return camera;
    }

    /**
     * Gets the current viewport used by the renderer.
     *
     * @return The Viewport instance
     */
    public static Viewport getViewport() {
        return viewport;
    }

    /**
     * Gets the world width in meters.
     *
     * @return The world width in meters
     */
    public static float getWorldWidth() {
        return WORLD_WIDTH;
    }

    /**
     * Gets the world height in meters.
     *
     * @return The world height in meters
     */
    public static float getWorldHeight() {
        return WORLD_HEIGHT;
    }

    /**
     * Gets the pixels per meter ratio.
     *
     * @return The pixels per meter ratio
     */
    public static float getPixelsPerMeter() {
        return PPM;
    }

    /**
     * Disposes of all resources used by the renderer.
     * This should be called when the game is closing to prevent memory leaks.
     */
    public static void dispose() {
        logger.info("Disposing Renderer...");
        // No resources to dispose currently, but this is where you would dispose
        // of any resources that the renderer owns (textures, shaders, etc.)
    }
}
