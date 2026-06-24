#version 150

uniform sampler2D DiffuseSampler;

uniform float Intensity;
uniform float Opacity;
uniform vec2 InSize;
uniform vec4 ColorModulate;

in vec2 texCoord;

out vec4 fragColor;

void main() {
    vec2 offset = (texCoord - vec2(0.5, 0.5)) * (Intensity / 1000.0);
    float r = texture(DiffuseSampler, texCoord - offset).r;
    float g = texture(DiffuseSampler, texCoord).g;
    float b = texture(DiffuseSampler, texCoord + offset).b;

    fragColor = vec4(r, g, b, Opacity) * ColorModulate;
}