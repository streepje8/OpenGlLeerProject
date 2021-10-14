#version 330

in  vec2 outTexCoord;
in  vec3 exColour;

out vec4 fragColor;

uniform sampler2D texture_sampler;
void main()
{
    fragColor = vec4(exColour, 1.0) * texture(texture_sampler, outTexCoord);
}