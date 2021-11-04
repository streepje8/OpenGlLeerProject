#version 330

in  vec2 outTexCoord;
out vec4 fragColor;

uniform sampler2D texture_sampler;

void main()
{
	vec4 kleur = texture(texture_sampler, outTexCoord);
    fragColor = kleur;
}