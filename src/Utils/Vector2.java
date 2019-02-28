package Utils;

/**
 * <h1>Vector2</h1>
 * A two-dimensional vector that stores two floating 
 * point values to represent a position in 2D space
 * and is capable of basic transformations.
 * <p>
 * 
 * @author Team 26
 * @version 1.0
 * @since 28-02-2019
 */
public class Vector2
{
	/**
	 * The X component of the vector.
	 */
	public float x;
	
	/**
	 * The Y component of the vector.
	 */
	public float y;
	
	/**
	 * Initialize a Vector2 object at the origin (0, 0).
	 */
	public Vector2()
	{
		x = 0f;
		y = 0f;
	}
	
	/**
	 * Initialize a Vector2 object with the given x and
	 * y coordinates.
	 * 
	 * @param _x The X component of the vector.
	 * @param _y The Y component of the vector.
	 */
	public Vector2(float _x, float _y)
	{
		x = _x;
		y = _y;
	}
	
	/**
	 * Adds the vector provided to this vector.
	 * @param v The vector to add.
	 */
	public void add(final Vector2 v)
	{
		x += v.x;
		y += v.y;
	}
	
	/**
	 * Subtract the vector provided from this vector.
	 * @param v The vector to subtract.
	 */
	public void subtract(final Vector2 v)
	{
		x -= v.x;
		y -= v.y;
	}
	
	/**
	 * Negates this vector. Has the same effect as 
	 * multiplying by -1.
	 */
	public final void negate()
	{
		x = -x;
		y = -y;
	}
	
	/**
	 * Scales this vector by the scale factor provided.
	 * @param factor The factor to scale this vector by.
	 */
	public void scale(float factor)
	{
		x *= factor;
		y *= factor;
	}
	
	/**
	 * Adds two vectors together.
	 * @param v1 The first vector to add.
	 * @param v2 The second vector to add.
	 * @return A {@link Vector2} representing the sum of the two vectors.
	 */
	public static Vector2 add(final Vector2 v1, final Vector2 v2)
	{
		return new Vector2(v1.x + v2.x, v1.y + v2.y);
	}

	/**
	 * Adds two vectors together.
	 * @param v1 The vector to be subtracted from.
	 * @param v2 The vector to subtract.
	 * @return A {@link Vector2} representing the difference of the two vectors.
	 */
	public static Vector2 subtract(final Vector2 v1, final Vector2 v2)
	{
		return new Vector2(v1.x - v2.x, v1.y - v2.y);
	}
	
	/**
	 * Negates a vector.
	 * @param v The vector to negate.
	 * @return A {@link Vector2} that is the negative of the first vector.
	 */
	public static Vector2 negate(final Vector2 v)
	{
		return new Vector2(-v.x, -v.y);
	}

	/**
	 * Scales the vector by the scale factor provided.
	 * @param v The vector to scale
	 * @param factor The factor to scale this vector by.
	 * @return A {@link Vector2} that is 
	 */
	public static Vector2 scale(final Vector2 v, float factor)
	{
		return new Vector2(v.x * factor, v.y * factor);
	}
}
