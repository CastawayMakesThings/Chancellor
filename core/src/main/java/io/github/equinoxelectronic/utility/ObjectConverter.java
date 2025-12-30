package io.github.equinoxelectronic.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.google.gson.Gson;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.FileReader;
import java.io.File;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;

/**
 * This class converts a file into an object that can be used by the game.
 */
public class ObjectConverter {
    /**
     * Converts a file into an object that can be used by the game.
     * @param file The file to convert.
     * @return The converted object.
     */
    public static Object convert(File file) {
        switch (file.getName().substring(file.getName().lastIndexOf(".") + 1)) {
            case "png":
            case "jpg":
            case "jpeg":
            case "bmp":
            case "tga":
            case "gif":
            case "dds":
            case "hdr":
                try {
                    Texture texture = new Texture(new FileHandle(file.getAbsolutePath()));
                    return new TextureRegion(texture);
                } catch (Exception e) {
                    System.err.println("COULD NOT CONVERT IMAGE! "+file.getName() + "\n" + e.getMessage());
                    return file;
                }

            case "json":
                try {
                    return new Gson().fromJson(new FileReader(file), Object.class);
                } catch (Exception e) {
                    return file;
                }
            case "wav":
            case "mp3":
            case "ogg":
                try {
                    return Gdx.audio.newSound(Gdx.files.absolute(file.getPath()));
                } catch (Exception e) {
                    return file;
                }
            case "glsl":
            case "vert":
            case "frag":
                try {
                    String shaderCode = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
                    return new ShaderProgram(shaderCode, shaderCode);
                } catch (Exception e) {
                    return file;
                }
            case "txt":
                try {
                    return new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
                } catch (Exception e) {
                    return file;
                }
            case "xml":
                try {
                    JAXBContext context = JAXBContext.newInstance(Object.class);
                    Unmarshaller unmarshaller = context.createUnmarshaller();
                    return unmarshaller.unmarshal(file);
                } catch (Exception e) {
                    return file;
                }

            default: return file;
        }
    }
}
