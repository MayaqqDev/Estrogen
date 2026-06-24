#use "estrogen:include/projection.glsl"

out vec4 texProj;
out vec4 vertColor;

struct Dream {
    vec2 light;
    vec4 color;
    vec3 pos;
};


void vertex(inout Vertex v, Dream instance) {
    vertColor = v.color;
    v.color = instance.color;
    v.light = instance.light;
    v.pos = v.pos + instance.pos;

    texProj = projection_from_position(uViewProjection * vec4(v.pos, 1.));
}


