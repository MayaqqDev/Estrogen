#use "estrogen:include/projection.glsl"

const mat4 SCALE_TRANSLATE = mat4(
0.5, 0.0, 0.0, 0.25,
0.0, 0.5, 0.0, 0.25,
0.0, 0.0, 1.0, 0.0,
0.0, 0.0, 0.0, 1.0
);

in vec4 texProj;
in vec4 vertColor;

struct Fragment {
    vec2 texCoords;
    vec4 color;
    float diffuse;
    vec2 light;
};

mat4 dreamBlockLayer(float layer) {
    mat4 translate = mat4(
    1.0, 0.0, 0.0, 17.0 / layer,
    0.0, 1.0, 0.0, (2.0 + layer / 1.5) * (uTime / 5000.0),
    0.0, 0.0, 1.0, 0.0,
    0.0, 0.0, 0.0, 1.0
    );

    mat2 scale = mat2((4.5 - layer / 4.0) * 2.0);

    return mat4(scale) * translate * SCALE_TRANSLATE;
}


vec4 fragment(Fragment r) {
    if(vertColor.w > 0.1) {
        return vertColor;
    } else {
        vec4 color = textureProj(uBlockAtlas, texProj).rgba;
        for (int i = 0; i < 8; i++) {
            color += textureProj(uBlockAtlas, texProj * dreamBlockLayer(float(i + 1) * 2)).rgba;
        }
        return color;
    }
}