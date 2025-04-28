#version 150

uniform sampler2D DiffuseSampler;
uniform sampler2D DiffuseDepthSampler;

uniform float FogStart;
uniform float FogEnd;
uniform vec4 Color;

in vec2 texCoord;

out vec4 fragColor;

float linearizeDepth(float z) {
    float n = FogStart;
    float f = FogEnd;
    return (2.0 * n) / (f + n - z * (f - n));
}

void main() {
    float modulate = linearizeDepth(texture(DiffuseDepthSampler, texCoord).r);
    fragColor = vec4(Color.rgb, modulate);
}