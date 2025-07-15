#version 150

uniform sampler2D DiffuseSampler;
uniform sampler2D DiffuseDepthSampler;

uniform vec3 Color;
uniform float Start;
uniform float End;

in vec2 texCoord;

out vec4 fragColor;

float linearizeDepth(float z, float n, float f) {
    return (2.0 * n) / (f + n - z * (f - n));
}

vec3 blend( vec3 dst, vec3 src, float bias) {
    return ( dst * bias ) + src;
}

void main() {
    float modulate = linearizeDepth(texture(DiffuseDepthSampler, texCoord).r, Start == 0 ? 0 : 1.0 / Start, End);
    vec4 color = texture(DiffuseSampler, texCoord);
    fragColor = vec4(blend(Color, color.rgb, modulate), color.a);
}