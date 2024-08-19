#use "estrogen:include/projection.glsl"

const mat4 SCALE_TRANSLATE = mat4(
0.5, 0.0, 0.0, 0.25,
0.0, 0.5, 0.0, 0.25,
0.0, 0.0, 1.0, 0.0,
0.0, 0.0, 0.0, 1.0
);

in vec4 texProj;

struct Fragment {
    vec2 texCoords;
    vec4 color;
    float diffuse;
    vec2 light;
};

mat4 end_portal_layer(float layer) {
    mat4 translate = mat4(
    1.0, 0.0, 0.0, 17.0 / layer,
    0.0, 1.0, 0.0, (2.0 + layer / 1.5) * (uTime * 1.5),
    0.0, 0.0, 1.0, 0.0,
    0.0, 0.0, 0.0, 1.0
    );

    mat2 rotate = mat2_rotate_z(radians((layer * layer * 4321.0 + layer * 9.0) * 2.0));

    mat2 scale = mat2((4.5 - layer / 4.0) * 2.0);

    return mat4(scale * rotate) * translate * SCALE_TRANSLATE;
}


vec4 fragment(Fragment r) {
    //vec4 tex = FLWBlockTexture(r.texCoords);

    //if(tex.x > 0.5) {
     //   return tex;
    //} else {
    vec3 color = textureProj(uBlockAtlas, texProj).rgb;
    for (int i = 0; i < 8; i++) {
        color += textureProj(uBlockAtlas, texProj * end_portal_layer(float(i + 1) * 2)).rgb;
    }
    return vec4(color, 1.0);
    //}
}