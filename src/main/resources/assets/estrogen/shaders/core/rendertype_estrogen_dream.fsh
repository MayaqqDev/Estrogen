#version 150

#moj_import <matrix.glsl>

uniform sampler2D Sampler0;
uniform float GameTime;

in vec4 vertexColor;
in vec4 texProj0;
in vec2 screenSize;

const mat4 SCALE_TRANSLATE = mat4(
    0.5, 0.0, 0.0, 0.25,
    0.0, 0.5, 0.0, 0.25,
    0.0, 0.0, 1.0, 0.0,
    0.0, 0.0, 0.0, 1.0
);

mat4 dreamBlockLayer(float layer) {
    mat4 translate = mat4(
        1.0, 0.0, 0.0, 17.0 / layer,
        0.0, 1.0, 0.0, (2.0 + layer / 1.5) * (GameTime * 3.5),
        0.0, 0.0, 1.0, 0.0,
        0.0, 0.0, 0.0, 1.0
    );

    mat2 scale = mat2((4.5 - layer / 4.0) * 2.0);

    return mat4(scale) * translate * SCALE_TRANSLATE;
}

out vec4 fragColor;

void main() {
    // vertexColor.x is 1.0 for the borders of a dream block and 0.0 otherwise
    // vertexColor.y is 1.0 when the player is inside a dream block and 0.0 otherwise
    vec4 rescaledTexProj = texProj0 * vec4(screenSize / screenSize.y, 1.0, 1.0);
    vec3 color;
    float alpha;
    if (vertexColor.y > 0.5) {
        vec2 pos = rescaledTexProj.xy / rescaledTexProj.w - vec2(0.5, 0.5) * screenSize / screenSize.y;
        alpha = min(1.0, 10.0 * dot(pos, pos));
    } else {
        alpha = 1.0;
    }
    if (vertexColor.x > 0.5) {
        color = vec3(1.0, 1.0, 1.0);
    } else {
        color = textureProj(Sampler0, rescaledTexProj).rgb;
        for (int i = 0; i < 8; i++) {
            color += textureProj(Sampler0, rescaledTexProj * dreamBlockLayer(float(i + 1) * 2)).rgb;
        }
    }
    fragColor = vec4(color, alpha);
}
