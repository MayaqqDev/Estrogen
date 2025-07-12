#version 150

#moj_import <projection.glsl>

in vec3 Position;
in vec4 Color;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;
uniform vec2 ScreenSize;

out vec4 vertexColor;
out vec4 texProj0;
out vec2 screenSize;

void main() {
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);
    vertexColor = Color;
    screenSize = ScreenSize;
    texProj0 = projection_from_position(gl_Position);
}