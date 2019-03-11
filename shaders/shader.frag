#version 330 core
#extension GL_ARB_separate_shader_objects : enable
#extension GL_ARB_explicit_uniform_location : enable

layout (location = 0) in vec4 inColor;
layout (location = 1) in vec2 inUV;

layout (location = 0) out vec4 outColor;

layout (location = 0) uniform sampler2D uTexture;

void main()
{
	outColor = vec4(inColor) * texture(uTexture, inUV);
}