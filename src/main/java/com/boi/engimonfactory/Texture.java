package com.boi.engimonfactory;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Map;
import java.util.HashMap;

import org.lwjgl.system.MemoryStack;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.stb.STBImage.*;

public class Texture {

    private final int id;

    private static Map<String, Texture> textureLibrary = new HashMap<>();

    public Texture(String fileName) throws Exception {
        this(loadTexture(fileName));
    }

    public Texture(int id) {
        this.id = id;
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public int getId() {
        return id;
    }

    private static int loadTexture(String fileName) throws Exception {
        int width;
        int height;
        ByteBuffer buf;
        // Load Texture file
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);

            buf = stbi_load(fileName, w, h, channels, 4);
            if (buf == null) {
                throw new Exception("Image file [" + fileName  + "] not loaded: " + stbi_failure_reason());
            }

            /* Get width and height of image */
            width = w.get();
            height = h.get();
        }

        // Create a new OpenGL texture
        int textureId = glGenTextures();
        // Bind the texture
        glBindTexture(GL_TEXTURE_2D, textureId);

        // Tell OpenGL how to unpack the RGBA bytes. Each component is 1 byte size
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        // Upload the texture data
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0,
                GL_RGBA, GL_UNSIGNED_BYTE, buf);
        // Generate Mip Map
        glGenerateMipmap(GL_TEXTURE_2D);

        stbi_image_free(buf);

        return textureId;
    }

    public void cleanup() {
        glDeleteTextures(id);
    }

    public static Texture getTexture(String textureName)
    {
        return textureLibrary.get(textureName);
    }

    public static void initTextureLibrary()
    {
        addToLibrary("GRASS", "textures/grassblock.png");
        addToLibrary("TUNDRA", "textures/tundrablock.png");
        addToLibrary("MOUNTAIN", "textures/mountainblock.png");
        addToLibrary("WATER", "textures/waterblock.png");
        addToLibrary("PLAYER", "textures/Player.png");

        addToLibrary("4002", "textures/4002.png");

        addToLibrary("LOVE", "textures/love.png");
        addToLibrary("DANGER", "textures/danger.png");

        addToLibrary("1", "textures/1.png");
        addToLibrary("1_1", "textures/1_1.png");
        addToLibrary("1_2", "textures/1_2.png");
        addToLibrary("1_3", "textures/1_3.png");

        addToLibrary("2", "textures/2.png");
        addToLibrary("2_1", "textures/2_1.png");
        addToLibrary("2_2", "textures/2_2.png");
        addToLibrary("2_3", "textures/2_3.png");

        addToLibrary("3", "textures/3.png");
        addToLibrary("3_1", "textures/3_1.png");
        addToLibrary("3_2", "textures/3_2.png");
        addToLibrary("3_3", "textures/3_3.png");

        addToLibrary("4", "textures/4.png");
        addToLibrary("4_1", "textures/4_1.png");
        addToLibrary("4_2", "textures/4_2.png");
        addToLibrary("4_3", "textures/4_3.png");

        addToLibrary("5", "textures/5.png");
        addToLibrary("5_1", "textures/5_1.png");
        addToLibrary("5_2", "textures/5_2.png");
        addToLibrary("5_3", "textures/5_3.png");

        addToLibrary("6", "textures/6.png");
        addToLibrary("6_1", "textures/6_1.png");
        addToLibrary("6_2", "textures/6_2.png");
        addToLibrary("6_3", "textures/6_3.png");

        addToLibrary("7", "textures/7.png");
        addToLibrary("7_1", "textures/7_1.png");
        addToLibrary("7_2", "textures/7_2.png");
        addToLibrary("7_3", "textures/7_3.png");

        addToLibrary("8", "textures/8.png");
        addToLibrary("8_1", "textures/8_1.png");
        addToLibrary("8_2", "textures/8_2.png");
        addToLibrary("8_3", "textures/8_3.png");

        addToLibrary("9", "textures/9.png");
        addToLibrary("9_1", "textures/9_1.png");
        addToLibrary("9_2", "textures/9_2.png");
        addToLibrary("9_3", "textures/9_3.png");

        addToLibrary("10", "textures/10.png");
        addToLibrary("10_1", "textures/10_1.png");
        addToLibrary("10_2", "textures/10_2.png");
        addToLibrary("10_3", "textures/10_3.png");

        addToLibrary("11", "textures/11.png");
        addToLibrary("11_1", "textures/11_1.png");
        addToLibrary("11_2", "textures/11_2.png");
        addToLibrary("11_3", "textures/11_3.png");

        addToLibrary("12", "textures/12.png");
        addToLibrary("12_1", "textures/12_1.png");
        addToLibrary("12_2", "textures/12_2.png");
        addToLibrary("12_3", "textures/12_3.png");

        addToLibrary("13", "textures/13.png");
        addToLibrary("13_1", "textures/13_1.png");
        addToLibrary("13_2", "textures/13_2.png");
        addToLibrary("13_3", "textures/13_3.png");

        addToLibrary("14", "textures/14.png");
        addToLibrary("14_1", "textures/14_1.png");
        addToLibrary("14_2", "textures/14_2.png");
        addToLibrary("14_3", "textures/14_3.png");

        addToLibrary("15", "textures/15.png");
        addToLibrary("15_1", "textures/15_1.png");
        addToLibrary("15_2", "textures/15_2.png");
        addToLibrary("15_3", "textures/15_3.png");

        addToLibrary("16", "textures/16.png");
        addToLibrary("16_1", "textures/16_1.png");
        addToLibrary("16_2", "textures/16_2.png");
        addToLibrary("16_3", "textures/16_3.png");

    }

    private static void addToLibrary(String textureName, String texturePath)
    {
        try {
            Texture newTex = new Texture(texturePath);
            textureLibrary.put(textureName, newTex);
        } catch (Exception e)
        {
            System.out.println("ERROR ADDING TO LIBRARY" + textureName + texturePath);
        }
    }



}