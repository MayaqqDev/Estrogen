#version 150

#moj_import <light.glsl>
#moj_import <fog.glsl>

in vec3 Position;
in vec4 Color;
in vec2 UV0;
in ivec2 UV1;
in ivec2 UV2;
in vec3 Normal;

uniform sampler2D Sampler1;
uniform sampler2D Sampler2;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;
uniform int FogShape;
uniform vec2 ScreenSize;

out float vertexDistance;
out vec4 vertexColor;
out vec4 lightMapColor;
out vec4 overlayColor;
out vec2 texCoord0;
out vec4 normal;

void main() {
    mat4 mvp = ProjMat * ModelViewMat;
    vec4 clipPos = mvp * vec4(Position, 1.0);
    vec4 clipNormal = mvp * vec4(Normal, 1.0);

    //vec2 offset = normalize(clipNormal.xy / ScreenSize) * 16.0;

    //clipPos.xy += offset * (clipPos.w * 2.0 / ScreenSize);
    clipPos.z += 0.005;

    gl_Position = clipPos;

    vertexDistance = fog_distance(Position, FogShape);
    vertexColor = Color * texelFetch(Sampler2, UV2 / 16, 0) * texelFetch(Sampler1, UV1, 0);
    texCoord0 = UV0;
    normal = clipNormal;
}
