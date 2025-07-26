#version 150

#moj_import <projection.glsl>

in vec3 Position;
in vec4 Color;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;
uniform vec2 ScreenSize;

out vec2 texProj0;
out vec4 vertexColor;

void main() {
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);
    texProj0 = (projection_from_position(gl_Position).xy - vec2(0.5, 0.5)) * ScreenSize / ScreenSize.y;
    vertexColor = Color;
}