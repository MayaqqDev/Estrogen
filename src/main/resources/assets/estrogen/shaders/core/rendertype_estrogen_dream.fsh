#version 150

#moj_import <matrix.glsl>

uniform sampler2D Sampler0;
uniform float CeaselessGameTime;

in vec4 vertexColor;
in vec4 texProj0;
in vec2 uv;

const mat4 SCALE_TRANSLATE = mat4(
    0.5, 0.0, 0.0, 0.25,
    0.0, 0.5, 0.0, 0.25,
    0.0, 0.0, 1.0, 0.0,
    0.0, 0.0, 0.0, 1.0
);

mat4 dreamBlockLayer(float layer) {
    mat4 translate = mat4(
        1.0, 0.0, 0.0, 17.0 / layer,
        0.0, 1.0, 0.0, (2.0 + layer / 1.5) * (CeaselessGameTime * 3.5),
        0.0, 0.0, 1.0, 0.0,
        0.0, 0.0, 0.0, 1.0
    );

    mat2 scale = mat2((4.5 - layer / 4.0) * 2.0);

    return mat4(scale) * translate * SCALE_TRANSLATE;
}

out vec4 fragColor;

void main() {
    vec3 color;
    float alpha = 1.0;
    if (vertexColor.x > 0.5) {
        if (vertexColor.y > 0.5) {
            alpha = 0.2;
            if (
                abs(uv.x - 0.5) < 7.0/16.0 &&
                abs(uv.y - 0.5) < 7.0/16.0
            ) discard;
        }
        color = vec3(1.0, 1.0, 1.0);
    } else {
        color = textureProj(Sampler0, texProj0).rgb;
        for (int i = 0; i < 8; i++) {
            color += textureProj(Sampler0, texProj0 * dreamBlockLayer(float(i + 1) * 2)).rgb;
        }
    }
    fragColor = vec4(color, alpha);
}
