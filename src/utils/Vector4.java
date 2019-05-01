package utils;

/**
 * Stores two floating-point values ( x , y , z , w )
 * Used for matrix multiplications and basically nothing else
 * @author Vee
 */
public class Vector4
{
	/**
	 * X-component of this point
	 */
	public float x;

	/**
	 * Y-component of this point
	 */
	public float y;

	/**
	 * Z-component of this point
	 */
	public float z;

	/**
	 * W-component of this point
	 */
	public float w;

	/**
	 * Constructs an identity Vector4 (0, 0, 0, 1)
	 */
	public Vector4()
	{
		this.x = 0f;
		this.y = 0f;
		this.z = 0f;
		this.w = 1f;
	}

	/**
	 * Constructs a Vector4 that represents the same position in space as the given Vector2
	 */
	public Vector4(Vector2 v)
	{
		this.x = v.x;
		this.y = v.y;
		this.z = 0f;
		this.w = 1f;
	}
	
	/**
	 * Creates a Vector4 with the given arguments.
	 * Maybe don't use this.
	 */
	public Vector4(float x, float y, float z, float w)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	/**
	 * Constructs a Vector4 from an array of given values.
	 * Identical in function to {@link #Vector4(float, float, float, float)}
	 * @param arr The array of values to use. Must have at least four elements.
	 */
	public Vector4(float[] arr)
	{
		this.x = arr[0];
		this.y = arr[1];
		this.z = arr[2];
		this.w = arr[3];
	}

	/**
	 * Gets all the components of this vector
	 * Useful for interfacing with OpenGL
	 * @return a four-element float array containing the X, Y, Z and W-components of the vector
	 */
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
