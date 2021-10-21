#version 330

in  vec2 outTexCoord;
out vec4 fragColor;

uniform sampler2D texture_sampler;

void main()
{
	vec4 kleur = texture(texture_sampler, outTexCoord);
	if(kleur.r + kleur.g + kleur.b == 0) {
		kleur = vec4(1);
	}
    fragColor = kleur;
}