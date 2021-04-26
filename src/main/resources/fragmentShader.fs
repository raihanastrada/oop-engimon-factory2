#version 330

in  vec2 fTexCoord;
in  vec3 fNormal;
in  vec3 lightDirection;
out vec4 fragColor;

uniform sampler2D sampler;

void main()
{
    vec3 nNormal = normalize(fNormal);
    vec3 nlightDirection = normalize(lightDirection);

    float lightInt = dot(nNormal, nlightDirection);
    float brightness = max(lightInt, 0.0);
    vec3 lightcolor = vec3(1.0, 1.0, 1.0);

    vec3 diffuseLight = brightness * lightcolor;

    fragColor = texture(sampler, fTexCoord);
}