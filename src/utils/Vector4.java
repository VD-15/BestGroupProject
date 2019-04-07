package utils;

public class Vector4
{
	public float x;
	public float y;
	public float z;
	public float w;
	
	public Vector4()
	{
		this.x = 0f;
		this.y = 0f;
		this.z = 0f;
		this.w = 1f;
	}
	
	public Vector4(Vector2 v)
	{
		this.x = v.x;
		this.y = v.y;
		this.z = 0f;
		this.w = 1f;
	}
	
	public Vector4(float x, float y, float z, float w)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public Vector4(float[] arr)
	{
		this.x = arr[0];
		this.y = arr[1];
		this.z = arr[2];
		this.w = arr[3];
	}
	
	public float[] getArray()
	{
		return new float[]
		{
			this.x,
			this.y,
			this.z,
			this.w
		};
	}
}
