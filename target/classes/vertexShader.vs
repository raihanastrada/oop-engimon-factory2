#version 330

layout (location=0) in vec3 position;
layout (location=1) in vec2 texCoord;
layout (location=2) in vec3 normal;

out vec2 fTexCoord;
out vec3 fNormal;
out vec3 lightDirection;

uniform mat4 worldMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

void main()
{
    vec3 lightPosition = vec3(0.0, 0.0, 0.0);

    vec4 worldPos = worldMatrix * vec4(position, 1.0);

    gl_Position = projectionMatrix * viewMatrix * worldMatrix * vec4(position, 1.0);
    fTexCoord = texCoord;
    fNormal = (worldMatrix * vec4(normal, 0.0)).xyz;
    lightDirection = lightPosition - worldPos.xyz;
}