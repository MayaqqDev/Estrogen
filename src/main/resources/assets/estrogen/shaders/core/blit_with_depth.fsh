#version 150

uniform sampler2D DiffuseSampler;
uniform sampler2D DiffuseDepthSampler;
uniform sampler2D MainDepthSampler;

uniform vec4 ColorModulator;

in vec2 texCoord;
in vec4 vertexColor;

out vec4 fragColor;

void main() {

    if (texture(DiffuseDepthSampler, texCoord).r > texture(MainDepthSampler, texCoord).r) {
        discard;
    }

    vec4 color = texture(DiffuseSampler, texCoord) * vertexColor;
    fragColor = color * ColorModulator;
}
