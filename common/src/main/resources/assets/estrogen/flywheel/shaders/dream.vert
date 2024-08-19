#use "estrogen:include/projection.glsl"

out vec4 texProj;
out vec3 vertNormal;

struct Dream {
    vec2 light;
    vec4 color;
    vec3 pos;
};

void vertex(inout Vertex v, Dream instance) {
    v.color = instance.color;
    v.light = instance.light;
    v.pos = v.pos + instance.pos;

    texProj = projection_from_position(FLWVertex(v));
    vertNormal = v.normal;
}
