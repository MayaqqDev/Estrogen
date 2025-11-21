#version 150

uniform sampler2D DiffuseSampler;
uniform sampler2D DiffuseDepthSampler;
uniform vec2 InSize;
uniform vec4 Color;
uniform float EstrogenFarPlane;

in vec2 texCoord;
in vec2 oneTexel;

out vec4 fragColor;

float linearizeDepth(float d) {
    float zNear = 0.05;
    float zFar = EstrogenFarPlane;
    float z_n = 2.0 * d - 1.0;
    return 2.0 * zNear * zFar / (zFar + zNear - z_n * (zFar - zNear));
}

vec4 testColor(sampler2D colorSampler, sampler2D depthSampler, vec2 coord) {
    if (texture(colorSampler, coord).a <= 0.1) {
        return vec4(0.0, 0.0, 0.0, 0.0);
    } else {
        return texture(depthSampler, coord);
    }
}

void make_kernel(inout float n[9], sampler2D color, sampler2D depth, vec2 coord) {
    float w = 3.0 / InSize.x;
    float h = 3.0 / InSize.y;

    n[0] = linearizeDepth(testColor(color, depth, coord + vec2( -w, -h)).r);
    n[1] = linearizeDepth(testColor(color, depth, coord + vec2(0.0, -h)).r);
    n[2] = linearizeDepth(testColor(color, depth, coord + vec2(  w, -h)).r);
    n[3] = linearizeDepth(testColor(color, depth, coord + vec2( -w, 0.0)).r);
    n[4] = linearizeDepth(testColor(color, depth, coord).r);
    n[5] = linearizeDepth(testColor(color, depth, coord + vec2(  w, 0.0)).r);
    n[6] = linearizeDepth(testColor(color, depth, coord + vec2( -w, h)).r);
    n[7] = linearizeDepth(testColor(color, depth, coord + vec2(0.0, h)).r);
    n[8] = linearizeDepth(testColor(color, depth, coord + vec2(  w, h)).r);
}

void main() {
    float n[9];
    make_kernel(n, DiffuseSampler, DiffuseDepthSampler, texCoord);
    float sobel_edge_h = n[2] + (2.0*n[5]) + n[8] - (n[0] + (2.0*n[3]) + n[6]);
    float sobel_edge_v = n[0] + (2.0*n[1]) + n[2] - (n[6] + (2.0*n[7]) + n[8]);
    float sobel = sqrt((sobel_edge_h * sobel_edge_h) + (sobel_edge_v * sobel_edge_v));

    if (sobel <= 0.3) {
        discard;
    }

    fragColor = Color;
}