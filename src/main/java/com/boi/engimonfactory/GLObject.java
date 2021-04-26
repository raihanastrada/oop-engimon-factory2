package com.boi.engimonfactory;

import org.joml.Vector3f;
import org.joml.Matrix4f;

import java.util.Vector;

public class GLObject {
    protected Mesh mesh;
    protected Vector3f position;
    protected Vector3f scale;
    protected Vector3f rotation;

    public GLObject(Mesh mesh)
    {
        this.mesh = mesh;
        position = new Vector3f(0, 0, 0);
        scale = new Vector3f(1, 1, 1);
        rotation = new Vector3f(0, 0, 0);
    }

    public void setPosition(float x, float y, float z) {
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
    }

    public void setPosition(Vector3f p)
    {
        this.position = p;
    }

    public Vector3f getPosition()
    {
        return this.position;
    };

    public Vector3f getScale() {
        return scale;
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(float x, float y, float z) {
        this.rotation.x = x;
        this.rotation.y = y;
        this.rotation.z = z;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public Matrix4f getWorldMatrix() {
        Matrix4f res = new Matrix4f();
        res.identity().translate(position).
                rotateX((float)Math.toRadians(rotation.x)).
                rotateY((float)Math.toRadians(rotation.y)).
                rotateZ((float)Math.toRadians(rotation.z)).
                scale(scale);

        return res;
    }

    protected static GLObject getMarker(Texture t)
    {
        float[] vertices = new float[] {
            -0.5f,  0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f,
            0.5f,  0.5f, 0.0f,
        };

        int[] indices = new int[] {
                0, 1, 3,
                3, 1, 2
        };

        float[] texCoords = new float[] {
                0.0f, 0.0f,
                0.0f, 0.5f,
                0.5f, 0.5f,
                0.5f, 0.0f
        };

        float[] normals = new float[] {
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
        };

        return new GLObject(new Mesh(vertices, texCoords, normals, indices, t));
    }


}
