#use "estrogen:include/projection.glsl"

out vec4 texProj;
out vec3 vertNormal;

struct Dream {
    vec2 light;
    vec4 color;
    vec3 pos;
};

vec4 makeProjection(Vertex v) {
    // FragDistance = cylindrical_distance(v.pos, uCameraPos / 2);

    return uViewProjection * vec4(v.pos, 1.);
}

void vertex(inout Vertex v, Dream instance) {
    v.color = instance.color;
    v.light = instance.light;
    v.pos = v.pos + instance.pos;

    texProj = projection_from_position(uViewProjection * vec4(v.pos, 1.));
    vertNormal = v.normal;
}


