package io.github.equinoxelectronic;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.equinoxelectronic.controlling.KeyBindManager;
import io.github.equinoxelectronic.rendering.Renderer;
import io.github.equinoxelectronic.utility.AssetManager;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Chancellor extends ApplicationAdapter {
    private SpriteBatch batch;
    private static final Logger logger = new Logger("Chancellor", Logger.DEBUG);

    //========================================================
    //========================================================
    //========================================================
    //========================================================

    public static final String APPLICATION_TITLE = "Chancellor";
    public static final String VERSION = "1.0.0";

    //========================================================
    //========================================================
    //========================================================
    //========================================================

    @Override
    public void create() {
        logger.info("Starting Chancellor...");
        batch = new SpriteBatch();
        AssetManager.getInstance().loadAssets("assets");
        Renderer.init();
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        Renderer.render(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // Update the renderer's viewport when the window is resized
        Renderer.resize(width, height);
    }

    @Override
    public void dispose() {
        logger.info("Disposing Chancellor...");
        Renderer.dispose();
        if (batch != null) {
            batch.dispose();
        }
        AssetManager.getInstance().dispose();
    }
}
