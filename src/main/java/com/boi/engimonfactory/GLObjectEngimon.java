package com.boi.engimonfactory;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.Vector;

public class GLObjectEngimon extends GLObject {

    private GLObject danger = GLObject.getMarker(Texture.getTexture("DANGER"));
    private GLObject love = GLObject.getMarker(Texture.getTexture("LOVE"));

    private static int timer = 0;
    private boolean isDanger = false;

    public void setDanger(boolean s)
    {
        isDanger = s;
    }

    public boolean isDanger()
    {
        return isDanger;
    }

    public GLObject getLove()
    {
        return love;
    }

    public GLObject getDanger()
    {
        return danger;
    }

    public Matrix4f getMarkerWorldMatrix()
    {
        timer+=1;
        Matrix4f res = new Matrix4f();
        Vector3f markerPos = new Vector3f(this.position.x, this.position.y + 0.7f, this.position.z);
        res.identity().translate(markerPos).
                rotateX((float)Math.toRadians(rotation.x+90)).
                rotateY((float)Math.toRadians(rotation.y+timer)).
                rotateZ((float)Math.toRadians(rotation.z)).
                scale(0.3f);

        return res;
    }

    public GLObjectEngimon(int code)
    {
        super(new Mesh(positions, textCoords, normals, indices, Texture.getTexture(String.valueOf(4002))));
        scale = new Vector3f(0.5f, 0.5f, 1f);
        rotation = new Vector3f(0, 0, 0);
    }

    private static float[] positions = new float[] {
            // V0
            -0.5f, 0.5f, 0.5f,
            // V1
            -0.5f, -0.5f, 0.5f,
            // V2
            0.5f, -0.5f, 0.5f,
            // V3
            0.5f, 0.5f, 0.5f,
            // V4
            -0.5f, 0.5f, -0.5f,
            // V5
            0.5f, 0.5f, -0.5f,
            // V6
            -0.5f, -0.5f, -0.5f,
            // V7
            0.5f, -0.5f, -0.5f,

            // For text coords in top face
            // V8: V4 repeated
            -0.5f, 0.5f, -0.5f,
            // V9: V5 repeated
            0.5f, 0.5f, -0.5f,
            // V10: V0 repeated
            -0.5f, 0.5f, 0.5f,
            // V11: V3 repeated
            0.5f, 0.5f, 0.5f,

            // For text coords in right face
            // V12: V3 repeated
            0.5f, 0.5f, 0.5f,
            // V13: V2 repeated
            0.5f, -0.5f, 0.5f,

            // For text coords in left face
            // V14: V0 repeated
            -0.5f, 0.5f, 0.5f,
            // V15: V1 repeated
            -0.5f, -0.5f, 0.5f,

            // For text coords in bottom face
            // V16: V6 repeated
            -0.5f, -0.5f, -0.5f,
            // V17: V7 repeated
            0.5f, -0.5f, -0.5f,
            // V18: V1 repeated
            -0.5f, -0.5f, 0.5f,
            // V19: V2 repeated
            0.5f, -0.5f, 0.5f,
    };

    private static float[] textCoords = new float[] {
            0.0f, 0.0f,
            0.0f, 0.5f,
            0.5f, 0.5f,
            0.5f, 0.0f,

            0.0f, 0.0f,
            0.5f, 0.0f,
            0.0f, 0.5f,
            0.5f, 0.5f,

            // For text coords in top face
            0.0f, 0.5f,
            0.5f, 0.5f,
            0.0f, 1.0f,
            0.5f, 1.0f,

            // For text coords in right face
            0.0f, 0.0f,
            0.0f, 0.5f,

            // For text coords in left face
            0.5f, 0.0f,
            0.5f, 0.5f,

            // For text coords in bottom face
            0.5f, 0.0f,
            1.0f, 0.0f,
            0.5f, 0.5f,
            1.0f, 0.5f,
    };

    private static int[] indices = new int[] {
            // Front face
            0, 1, 3, 3, 1, 2,
            // Top Face
            8, 10, 11, 9, 8, 11,
            // Right face
            12, 13, 7, 5, 12, 7,
            // Left face
            14, 15, 6, 4, 14, 6,
            // Bottom face
            16, 18, 19, 17, 16, 19,
            // Back face
            4, 6, 7, 5, 4, 7
    };

    private static float[] normals = new float[] {
            0
            ,-1
            ,0
            ,0
            ,-1
            ,0
            ,0
            ,-1
            ,0
            ,0
            ,1
            ,0
            ,0
            ,1
            ,0
            ,0
            ,1
            ,0
            ,1
            ,0
            ,0
            ,1
            ,0
            ,0
            ,1
            ,0
            ,0
            ,0
            ,0
            ,1
            ,0
            ,0
            ,1
            ,0
            ,0
            ,1
            ,-1
            ,0
            ,0
            ,-1
            ,0
            ,0
            ,-1
            ,0
            ,0
            ,0
            ,0
            ,-1
            ,0
            ,0
            ,-1
            ,0
            ,0
            ,-1
            ,0
            ,-1
            ,0
            ,0
            ,1
            ,0
            ,1
            ,0
            ,0
            ,0
            ,0
            ,1
            ,-1
            ,0
            ,0
    };

//    private static final float[] vertices = new float[] {
//            1
//            ,1
//            ,4
//            ,-1
//            ,1
//            ,4
//            ,-1
//            ,-1
//            ,4
//            ,1
//            ,-1
//            ,4
//            ,1
//            ,-1
//            ,0
//            ,1
//            ,-1
//            ,4
//            ,-1
//            ,-1
//            ,4
//            ,-1
//            ,-1
//            ,0
//            ,-1
//            ,-1
//            ,0
//            ,-1
//            ,-1
//            ,4
//            ,-1
//            ,1
//            ,4
//            ,-1
//            ,1
//            ,0
//            ,-1
//            ,1
//            ,0
//            ,1
//            ,1
//            ,0
//            ,1
//            ,-1
//            ,0
//            ,-1
//            ,-1
//            ,0
//            ,1
//            ,1
//            ,0
//            ,1
//            ,1
//            ,4
//            ,1
//            ,-1
//            ,4
//            ,1
//            ,-1
//            ,0
//            ,-1
//            ,1
//            ,0
//            ,-1
//            ,1
//            ,4
//            ,1
//            ,1
//            ,4
//            ,1
//            ,1
//            ,0
//            ,-1.2f
//            ,-1.2f
//            ,4
//            ,-1.2f
//            ,-1.2f
//            ,6.4f
//            ,-1.2f
//            ,1.2f
//            ,6.4f
//            ,-1.2f
//            ,1.2f
//            ,4
//            ,-1.2f
//            ,1.2f
//            ,4
//            ,-1.2f
//            ,1.2f
//            ,6.4f
//            ,1.2f
//            ,1.2f
//            ,6.4f
//            ,1.2f
//            ,1.2f
//            ,4
//            ,1.2f
//            ,1.2f
//            ,4
//            ,1.2f
//            ,1.2f
//            ,6.4f
//            ,1.2f
//            ,-1.2f
//            ,6.4f
//            ,1.2f
//            ,-1.2f
//            ,4
//            ,1.2f
//            ,-1.2f
//            ,4
//            ,1.2f
//            ,-1.2f
//            ,6.4f
//            ,-1.2f
//            ,-1.2f
//            ,6.4f
//            ,-1.2f
//            ,-1.2f
//            ,4
//            ,-1.2f
//            ,1.2f
//            ,4
//            ,1.2f
//            ,1.2f
//            ,4
//            ,1.2f
//            ,-1.2f
//            ,4
//            ,-1.2f
//            ,-1.2f
//            ,4
//            ,1.2f
//            ,1.2f
//            ,6.4f
//            ,-1.2f
//            ,1.2f
//            ,6.4f
//            ,-1.2f
//            ,-1.2f
//            ,6.4f
//            ,1.2f
//            ,-1.2f
//            ,6.4f
//    };
//
//    private static final float[] texCoords= new float[] {
//            0.435781f
//            ,0.686253f
//            ,0.43625f
//            ,0.874942f
//            ,0.31033f
//            ,0.875627f
//            ,0.309861f
//            ,0.686937f
//            ,0.309392f
//            ,0.498248f
//            ,0.309861f
//            ,0.686937f
//            ,0.250347f
//            ,0.687621f
//            ,0.249878f
//            ,0.498932f
//            ,0.626606f
//            ,0.496195f
//            ,0.627075f
//            ,0.684884f
//            ,0.501155f
//            ,0.685569f
//            ,0.500686f
//            ,0.496879f
//            ,0.434843f
//            ,0.636999f
//            ,0.435312f
//            ,0.497563f
//            ,0.309392f
//            ,0.498248f
//            ,0.308923f
//            ,0.637683f
//            ,0.435312f
//            ,0.497563f
//            ,0.435781f
//            ,0.686253f
//            ,0.309861f
//            ,0.686937f
//            ,0.309392f
//            ,0.498248f
//            ,0.500686f
//            ,0.496879f
//            ,0.501155f
//            ,0.685569f
//            ,0.435781f
//            ,0.686253f
//            ,0.435312f
//            ,0.497563f
//            ,0.490037f
//            ,0.750212f
//            ,0.490861f
//            ,0.871984f
//            ,0.368685f
//            ,0.872685f
//            ,0.367861f
//            ,0.750913f
//            ,0.367861f
//            ,0.750913f
//            ,0.368685f
//            ,0.872685f
//            ,0.246509f
//            ,0.873386f
//            ,0.245685f
//            ,0.751614f
//            ,0.245685f
//            ,0.751614f
//            ,0.246509f
//            ,0.873386f
//            ,0.124333f
//            ,0.874087f
//            ,0.123509f
//            ,0.752315f
//            ,0.123509f
//            ,0.752315f
//            ,0.124333f
//            ,0.874087f
//            ,0.00215726f
//            ,0.874788f
//            ,0.0013333f
//            ,0.753016f
//            ,0.244861f
//            ,0.629843f
//            ,0.245685f
//            ,0.751614f
//            ,0.123509f
//            ,0.752315f
//            ,0.122685f
//            ,0.630544f
//            ,0.246509f
//            ,0.873386f
//            ,0.247333f
//            ,0.995157f
//            ,0.125157f
//            ,0.995858f
//            ,0.124333f
//            ,0.874087f
//    };
//
//    private static final int[] faces = new int[]{
//            0, 1, 2,
//            0, 2, 3,
//            4, 5, 6,
//            4, 6, 7,
//            8, 9, 10,
//            8, 10, 11,
//            12, 13, 14,
//            12, 14, 15,
//            16, 17, 18,
//            16, 18, 19,
//            20, 21, 22,
//            20, 22, 23,
//            24, 25, 26,
//            24, 26, 27,
//            28, 29, 30,
//            28, 30, 31,
//            32, 33, 34,
//            32, 34, 35,
//            36, 38, 39,
//            40, 41, 42,
//            40, 42, 43,
//            44, 45, 46,
//            44, 46, 47
//    };
}
