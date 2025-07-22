#version 150

#moj_import <matrix.glsl>

uniform sampler2D Sampler0;
uniform float GameTime;

in vec2 texProj0;

out vec4 fragColor;

// https://www.shadertoy.com/view/lssGDr really helpful

void main() {
    float f = 0.2;
    float angle = atan(texProj0.y, texProj0.x);
    float see_through_dist = 0.02 * (sin(7 * angle + 15708 * GameTime) + sin(9 * angle - 14137 * GameTime));
    if (length(texProj0) < 0.3 + see_through_dist) discard;
    vec3 color;
    if (length(texProj0) < 0.32 + see_through_dist) {
        color = vec3(1.0);
    } else {
        color = vec3(0.0);
        for (int i = 0; i < 8; i++) {
            float h = -(1.0 + i * 2.0);
            float a = h / texProj0.y;
            int j = i;
            if (a < 0) {
                a *= -1.0;
                j += 8;
            }

            vec3 plane_point = vec3(texProj0.x * a, texProj0.y * a, f * a);
            float fade = max(1.0 - length(plane_point) / 30.0, 0.0);
            plane_point.xz += vec2(j * 1.618);
            plane_point.z += GameTime * 8000.0;
            vec2 tex_index = plane_point.xz;
            color += texture(Sampler0, tex_index / 10.0).rgb * fade;
        }
    }

    fragColor = vec4(color, 1.0);
}