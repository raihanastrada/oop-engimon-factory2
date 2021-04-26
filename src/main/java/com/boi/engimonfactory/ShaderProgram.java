package com.boi.engimonfactory;

import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.*;

public class ShaderProgram {
    private final int programId;
    private int vertexShaderId;
    private int fragmentShaderId;

    private final Map<String, Integer> uniforms;

    public ShaderProgram() {
        programId = glCreateProgram();
        if (programId == 0)
        {
            System.out.println("Error creating shader program");
            System.exit(-1);
        }

        uniforms = new HashMap<>();
    }

    public void createVertexShader(String shaderCode)
    {
        vertexShaderId = createShader(shaderCode, GL_VERTEX_SHADER);
    }

    public void createFragmentShader(String shaderCode)
    {
        fragmentShaderId = createShader(shaderCode, GL_FRAGMENT_SHADER);
    }

    public void createUniform(String uName) {
        int uniformLocation = glGetUniformLocation(programId, uName);
        if (uniformLocation < 0)
        {
            System.out.println("Uniform not found: " + uName);
            System.exit(-1);
        }

        uniforms.put(uName, uniformLocation);
    }

    protected  int createShader(String shaderCode, int shaderType)
    {
        int shaderId = glCreateShader(shaderType);
        if (shaderId == 0)
        {
            System.out.println("Error creating shader program");
            System.exit(-1);
        }

        glShaderSource(shaderId, shaderCode);
        glCompileShader(shaderId);

        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0)
        {
            System.out.println("Error compiling shader program");
            System.out.println(glGetShaderInfoLog(shaderId, 1024));
            System.exit(-1);
        }

        glAttachShader(programId, shaderId);
        return shaderId;
    }

    public void setUniform(String uName, Matrix4f val)
    {
        try (MemoryStack stack = MemoryStack.stackPush())
        {
            FloatBuffer b = stack.mallocFloat(16);
            val.get(b);
            glUniformMatrix4fv(uniforms.get(uName), false, b); // Don't transpose matrices
        }
    }

    public void setUniform(String uName, int val)
    {
        glUniform1i(uniforms.get(uName), val);
    }

    public void link()
    {
        glLinkProgram(programId);
        if(glGetProgrami(programId, GL_LINK_STATUS) == 0)
        {
            System.out.println("Error linking shader program");
            System.out.println(glGetProgramInfoLog(programId, 1024));
            System.exit(-1);
        }

        if (vertexShaderId != 0) {
            glDetachShader(programId, vertexShaderId);
        }
        if (fragmentShaderId != 0) {
            glDetachShader(programId, fragmentShaderId);
        }

        glValidateProgram(programId);
        if (glGetProgrami(programId, GL_VALIDATE_STATUS) == 0) {
            System.err.println("Warning validating Shader code: " + glGetProgramInfoLog(programId, 1024));
        }
    }

    public void bind()
    {
        glUseProgram(programId);
    }

    public void unbind() {
        glUseProgram(0);
    }

    public void cleanup()
    {
        unbind();
        if (programId!=0)
        {
            glDeleteProgram(programId);
        }
    }



}
