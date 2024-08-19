#use "estrogen:include/projection.glsl"

out vec4 texProj;

struct Dream {
    vec2 light;
    vec4 color;
    vec3 pos;
};

void vertex(inout Vertex v, Dream instance) {
    v.pos = v.pos + instance.pos;
    v.color = instance.color;
    v.light = instance.light;

    texProj = projection_from_position(vec4(v.pos * uCameraPos, 1) * uViewProjection);
}
