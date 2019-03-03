#version 330 core
#extension GL_ARB_separate_shader_objects : enable
#extension GL_ARB_explicit_uniform_location : enable

layout (location = 0) in vec2 inPos;
layout (location = 1) in vec3 inColor;
layout (location = 2) in vec2 inUV;

layout (location = 0) out vec3 outColor;
layout (location = 1) out vec2 outUV;

layout (location = 1) uniform mat4 viewMatrix;

void main()
{
	gl_Position = vec4(inPos, 0.0, 1.0) * viewMatrix;
	outColor = inColor;
	outUV = inUV;
}