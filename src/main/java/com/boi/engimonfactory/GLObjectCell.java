package com.boi.engimonfactory;

import org.joml.Vector3f;

import java.util.HashMap;

public class GLObjectCell extends GLObject {

    private static HashMap<CellType, Mesh> mapMeshes = new HashMap<CellType, Mesh>();
    private static boolean initialized = false;

    public static void init()
    {
        float[] positions = new float[] {
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

        float[] textCoords = new float[]{
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

        int[] indices = new int[] {
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

        try {
            Texture green = new Texture("textures/grassblock.png");

            mapMeshes.put(CellType.GRASSLAND, new Mesh(positions, textCoords, indices, green));
            mapMeshes.put(CellType.TUNDRA, new Mesh(positions, textCoords, indices, green));
            mapMeshes.put(CellType.MOUNTAINS, new Mesh(positions, textCoords, indices, green));
            mapMeshes.put(CellType.SEA, new Mesh(positions, textCoords, indices, green));
        } catch (Exception e)
        {

        }

    }


    public GLObjectCell(CellType t, float x, float y)
    {
        super(mapMeshes.get(t));
        this.mesh = mapMeshes.get(CellType.GRASSLAND);
        switch(t){
            case SEA:
                position = new Vector3f(x, -0.3f, y);
                break;
            case GRASSLAND:
                position = new Vector3f(x, -0.2f, y);
                break;
            case TUNDRA:
                position = new Vector3f(x, -0.1f, y);
                break;
            case MOUNTAINS:
                position = new Vector3f(x, 0.0f, y);
                break;
            default:
                position = new Vector3f(x, -0.4f, y);
        }
        scale = 1;
        rotation = new Vector3f(0, 0, 0);
    }
}
