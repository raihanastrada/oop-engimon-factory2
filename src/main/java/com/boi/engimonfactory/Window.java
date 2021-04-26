package com.boi.engimonfactory;

import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.flag.ImGuiConfigFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import org.joml.Vector3f;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.joml.Matrix4f;

import java.util.ArrayList;

public class Window {
    private final ImGuiImplGlfw imGuiGlfw = new ImGuiImplGlfw();
    private final ImGuiImplGl3 imGuiGl3 = new ImGuiImplGl3();

    private String glslVersion = null;
    private long windowPtr;
    private UI imguiLayer;
    private ShaderProgram shaderProgram;
//    private Mesh test;
//    private GLObject testO;

    private ArrayList<GLObjectCell> mapCells = new ArrayList<>();
    private ArrayList<Engimon> wildEngimon = new ArrayList<>();
    private GLObjectPlayer player;
    private GLObjectEngimon activeEngimon;

    private Peta gameMap;

    private final int windowWidth = 1600;
    private final int windowHeight = 900;
    /*
    * PROJECTION VARIABLES
    * */
    private static final float FOV = (float) Math.toRadians(60.0f);
    private static final float Z_NEAR = 0.01f;
    private static final float Z_FAR = 1000.0f;
    private Matrix4f projectionMatrix;
    private Matrix4f viewMatrix;
    /*
    * VIEW VARIABLES
    * */
    private final Vector3f cameraPos  = new Vector3f(5.5f, 5, -4.f);
    private final Vector3f cameraTarget = new Vector3f(5.5f, 0, 5);
    private final Vector3f cameraUp = new Vector3f(0, 1, 0);

    public Window(UI layer) {
        imguiLayer = layer;
    }

    public void init() throws Exception {

        initWindow();
        initImGui();
        Texture.initTextureLibrary();
        initMap();


        imGuiGlfw.init(windowPtr, true);
        imGuiGl3.init(glslVersion);
        viewMatrix = new Matrix4f().identity().lookAt(cameraPos, cameraTarget, cameraUp);

        shaderProgram = new ShaderProgram();
        shaderProgram.createVertexShader(Utils.loadResource("vertexShader.vs"));
        shaderProgram.createFragmentShader(Utils.loadResource("fragmentShader.fs"));
        shaderProgram.link();

        shaderProgram.createUniform("projectionMatrix");
        shaderProgram.createUniform("viewMatrix");
        shaderProgram.createUniform("worldMatrix");
        shaderProgram.createUniform("sampler");

        float aspectRatio = (float) windowWidth / windowHeight;
        projectionMatrix = new Matrix4f().perspective(FOV, aspectRatio, Z_NEAR, Z_FAR);
    }

    public void destroy() {
        imGuiGl3.dispose();
        imGuiGlfw.dispose();
        ImGui.destroyContext();

        if (shaderProgram != null) {
            shaderProgram.cleanup();
        }

        Callbacks.glfwFreeCallbacks(windowPtr);
        glfwDestroyWindow(windowPtr);
        glfwTerminate();
    }

    private void initWindow() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( !glfwInit() ) {
            System.out.println("Unable to initialize GLFW");
            System.exit(-1);
        }

        glslVersion = "#version 130";
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 0);

        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        windowPtr = glfwCreateWindow(windowWidth, windowHeight, "Engimon Factory 2: Electric Boogaloo", NULL, NULL);

        if (windowPtr == NULL) {
            System.out.println("Unable to create window");
            System.exit(-1);
        }

        glfwMakeContextCurrent(windowPtr);
        glfwSwapInterval(1);
        glfwShowWindow(windowPtr);

        GL.createCapabilities();
        glEnable(GL_DEPTH_TEST);
    }

    private void initImGui() {
        ImGui.createContext();
        ImGuiIO io = ImGui.getIO();
        io.addConfigFlags(ImGuiConfigFlags.ViewportsEnable);
    }

    public void initMap() {
        gameMap = imguiLayer.getMap();
        GLObjectCell.init();
        GLObjectCell temp;

        for(int x = 0; x < 12; x++)
        {
            for(int y = 0; y < 10; y++)
            {
                temp = new GLObjectCell(gameMap.getCell(x, y).getType(), x, y);
                mapCells.add(temp);
            }
        }
    }

    public void update() {

    }

    public void run() {
        while (!glfwWindowShouldClose(windowPtr)) {
//            glClearColor(0.1f, 0.09f, 0.1f, 1.0f);
            glClearColor(0.52f, 0.81f, 0.921f, 1.0f);

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            shaderProgram.bind();
            shaderProgram.setUniform("projectionMatrix", projectionMatrix);
            shaderProgram.setUniform("viewMatrix", viewMatrix);
            shaderProgram.setUniform("sampler", 0);
            glBindVertexArray(0);

            for(GLObjectCell c: mapCells)
            {
                shaderProgram.setUniform("worldMatrix", c.getWorldMatrix());
                c.getMesh().render();
            }

            shaderProgram.unbind();


            // Draw the mesh
//            testO.getMesh().render();
//            glActiveTexture(GL_TEXTURE0);
//            glBindTexture(GL_TEXTURE_2D, texture.getId());
//
//            glBindVertexArray(testO.getMesh().getVaoId());
//            glEnableVertexAttribArray(0);
//            glEnableVertexAttribArray(1);
//            glDrawElements(GL_TRIANGLES, testO.getMesh().getVertexCount(), GL_UNSIGNED_INT, 0);
            // Restore state


            imGuiGlfw.newFrame();
            ImGui.newFrame();

            imguiLayer.ui();

            ImGui.render();
            imGuiGl3.renderDrawData(ImGui.getDrawData());

            if (ImGui.getIO().hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
                final long backupWindowPtr = org.lwjgl.glfw.GLFW.glfwGetCurrentContext();
                ImGui.updatePlatformWindows();
                ImGui.renderPlatformWindowsDefault();
                GLFW.glfwMakeContextCurrent(backupWindowPtr);
            }

            GLFW.glfwSwapBuffers(windowPtr);
            GLFW.glfwPollEvents();
        }
    }
}
