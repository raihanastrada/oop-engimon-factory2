package com.boi.engimonfactory;

import org.joml.Vector3f;

import java.util.HashMap;

public class GLObjectCell extends GLObject {

    private static HashMap<CellType, Mesh> mapMeshes = new HashMap<CellType, Mesh>();
    private static boolean initialized = false;

//    public static void init()
//    {
//        float[] positions = new float[]{
//                -0.5f,  0.0f, 0.5f,
//                -0.5f, 0.0f, -0.05f,
//                0.5f, 0.0f, -0.5f,
//                0.5f,  0.0f, 0.5f,
//        };
//
//        int[] indices = new int[]{
//                0, 1, 3, 3, 1, 2,
//        };
//
//        if (!initialized)
//        {
//            float[] white = new float[]{
//                    1.0f, 1.0f, 1.0f,
//                    1.0f, 1.0f, 1.0f,
//                    1.0f, 1.0f, 1.0f,
//                    1.0f, 1.0f, 1.0f,
//            };
//
//            float[] red = new float[]{
//                    1.0f, 0.0f, 0.0f,
//                    1.0f, 0.0f, 0.0f,
//                    1.0f, 0.0f, 0.0f,
//                    1.0f, 0.0f, 0.0f,
//            };
//
//            float[] green = new float[]{
//                    0.0f, 1.0f, 0.0f,
//                    0.0f, 1.0f, 0.0f,
//                    0.0f, 1.0f, 0.0f,
//                    0.0f, 1.0f, 0.0f,
//            };
//
//            float[] blue = new float[]{
//                    0.0f, 0.0f, 1.0f,
//                    0.0f, 0.0f, 1.0f,
//                    0.0f, 0.0f, 1.0f,
//                    0.0f, 0.0f, 1.0f,
//            };
//
//            mapMeshes.put(CellType.GRASSLAND, new Mesh(positions, green, indices));
//            mapMeshes.put(CellType.TUNDRA, new Mesh(positions, white, indices));
//            mapMeshes.put(CellType.MOUNTAINS, new Mesh(positions, red, indices));
//            mapMeshes.put(CellType.SEA, new Mesh(positions, blue, indices));
//        }
//    }


    public GLObjectCell(CellType t, float x, float y)
    {
        super(mapMeshes.get(t));
        position = new Vector3f(x, 0, y);
        scale = 1;
        rotation = new Vector3f(0, 0, 0);
    }
}
