
vec4 projection_from_position(vec4 position) {
    vec4 projection = position * 0.5;
    projection.xy = vec2(projection.x + projection.w, projection.y + projection.w);
    projection.zw = position.zw;
    return projection;
}

mat2 mat2_rotate_z(float radians) {
    return mat2(
    cos(radians), -sin(radians),
    sin(radians), cos(radians)
    );
}
