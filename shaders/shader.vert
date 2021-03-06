#version 330 core
#extension GL_ARB_separate_shader_objects : enable
#extension GL_ARB_explicit_uniform_location : enable

layout (location = 0) in vec3 inPos;
layout (location = 1) in vec4 inColor;
layout (location = 2) in vec2 inUV;

layout (location = 0) out vec4 outColor;
layout (location = 1) out vec2 outUV;

layout (location = 1) uniform mat4 viewMatrix;
layout (location = 2) uniform mat4 texMatrix;

void main()
{
	gl_Position = viewMatrix * vec4(inPos, 1.0);
	outColor = inColor;
	outUV = (texMatrix * vec4(inUV, 0.0, 1.0)).xy;
}