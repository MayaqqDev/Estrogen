#version 150

uniform sampler2D DiffuseSampler;
uniform vec2 InSize;
uniform vec4 Color;

in vec2 texCoord;
in vec2 oneTexel;

out vec4 fragColor;

void make_kernel(inout float n[9], sampler2D color, vec2 coord) {
    float w = 3.0 / InSize.x;
    float h = 3.0 / InSize.y;

    n[0] = texture(color, coord + vec2( -w, -h)).a;
    n[1] = texture(color, coord + vec2(0.0, -h)).a;
    n[2] = texture(color, coord + vec2(  w, -h)).a;
    n[3] = texture(color, coord + vec2( -w, 0.0)).a;
    n[4] = texture(color, coord).a;
    n[5] = texture(color, coord + vec2(  w, 0.0)).a;
    n[6] = texture(color, coord + vec2( -w, h)).a;
    n[7] = texture(color, coord + vec2(0.0, h)).a;
    n[8] = texture(color, coord + vec2(  w, h)).a;
}

void main() {
    float n[9];
    make_kernel(n, DiffuseSampler, texCoord);
    float sobel_edge_h = n[2] + (2.0*n[5]) + n[8] - (n[0] + (2.0*n[3]) + n[6]);
    float sobel_edge_v = n[0] + (2.0*n[1]) + n[2] - (n[6] + (2.0*n[7]) + n[8]);
    float sobel = sqrt((sobel_edge_h * sobel_edge_h) + (sobel_edge_v * sobel_edge_v));

    if (sobel <= 0.1) {
        discard;
    }

    fragColor = Color;
}