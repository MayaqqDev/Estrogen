#version 150

#moj_import <fog.glsl>

const vec4 colors[] = vec4[5](
        vec4(0.357, 0.808, 0.980, 1.0),
        vec4(0.961, 0.663, 0.722, 1.0),
        vec4(1.0, 1.0, 1.0, 1.0),
        vec4(0.961, 0.663, 0.722, 1.0),
        vec4(0.357, 0.808, 0.980, 1.0)
);
const vec2 direction = vec2(1.0, 1.0);
const float speed = 1.0;

uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;
uniform float GameTime;

in float vertexDistance;
in vec4 vertexColor;

out vec4 fragColor;

vec4 SMOOTHY(float x) {
    x *= (colors.length() - 1);
    return mix(colors[int(x)], colors[int(x) + 1], smoothstep(0.0, 1.0, fract(x)));
}

void main() {
    vec4 color = vertexColor * ColorModulator;

    if (length(color.rgb) != 0.0) {
        vec2 coords = gl_FragCoord.xy;
        color *= vec4(SMOOTHY(float(int(length(coords + (direction * GameTime * 24000 * speed) * 2)) % 500) / 500.0).rgb, 1) * vertexColor;
    }
    fragColor = linear_fog(color, vertexDistance, FogStart, FogEnd, FogColor);
}
