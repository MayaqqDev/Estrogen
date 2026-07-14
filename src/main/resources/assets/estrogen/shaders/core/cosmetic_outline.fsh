#version 150

#moj_import <fog.glsl>

uniform sampler2D Sampler0;

uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;

in float vertexDistance;
in vec2 texCoord0;
in vec4 vertexColor;

out vec4 fragColor;

void main() {
    vec4 color = texture(Sampler0, texCoord0) * vertexColor * ColorModulator;
//    if (color.a > 0.1) {
//        discard;
//    }

    // 4-way Edge Detection
    vec2 texelSize = 1.0 / textureSize(Sampler0, 0);
    float alphaN = texture(Sampler0, texCoord0 + vec2(0.0,  texelSize.y)).a;
    float alphaS = texture(Sampler0, texCoord0 + vec2(0.0, -texelSize.y)).a;
    float alphaE = texture(Sampler0, texCoord0 + vec2( texelSize.x, 0.0)).a;
    float alphaW = texture(Sampler0, texCoord0 + vec2(-texelSize.x, 0.0)).a;

    if (alphaN > 0.1 || alphaS > 0.1 || alphaE > 0.1 || alphaW > 0.1) {
        fragColor = linear_fog(vertexColor * ColorModulator, vertexDistance, FogStart, FogEnd, FogColor);
    } else {
        discard;
    }
}
