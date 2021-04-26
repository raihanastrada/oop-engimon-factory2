package com.boi.engimonfactory;

import org.joml.Vector3f;
import org.joml.Matrix4f;

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
}
