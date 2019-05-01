package utils;

/**
 * Stores two integer values ( x , y )
 * Used for the world coordinates and anything where integers do not provide sufficient granularity
 * @author Vee
 */
public final class Vector2 implements Cloneable
{
	/**
	 * X-component of this vector
	 */
	public float x;
	
	/**
	 * Y-component of this vector
	 */
	public float y;
	
	/**
	 * Creates a {@link Vector2 Vector2} at (0, 0)
	 */
	public Vector2()
	{
		this.x = 0f;
		this.y = 0f;
	}
	
	/**
	 * Creates a Vector2 object
	 * @param xy the joint X and Y component of the vector
	 */
	public Vector2(float xy)
	{
		this.x = xy;
		this.y = xy;
	}
	
	/**
	 * Creates a Vector2 object
	 * @param x the X-component of this vector
	 * @param y the Y-component of this vector
	 */
	public Vector2(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Gets all the components of this vector
	 * @return a two-element float array containing the X-component and the Y-component of the vector
	 */
	public float[] getArray()
	{
		return new float[] {x, y};
	}
	
	/**
	 * Adds two vectors together
	 * @param v The vector to add to this one
	 * @return The sum of the two vectors
	 */
	public Vector2 add(Vector2 v)
	{
		return new Vector2(x + v.x, y + v.y);
	}
	
	/**
	 * Multiplies a vector by the given scale factor
	 * @param s The scale factor to multiply by
	 * @return The producy of the scaling operation
	 */
	public Vector2 multiply(float s)
	{
		return new Vector2(this.x * s, this.y * s);
	}
	
	/**
	 * Rotates a vector around the origin by the given angle
	 * @param a The angle, in radians to rotate the vector by
	 * @return The product of the rotation
	 */
	public Vector2 rotate(float a)
	{
		float cosa = (float)Math.cos(a);
		float sina = (float)Math.sin(a);
		
		float newX = (x * cosa) - (y * sina);
		float newY = (y * cosa) + (x * sina);
		
		return new Vector2(newX, newY);
	}
	
	/**
	 * Rotated a vector around a given pivot by the given angle
	 * @param a The angle, in radians to rotate the vector by
	 * @param o The pivot point to rotate the vector around
	 * @return The product of the rotation
	 */
	public Vector2 rotateAround(float a, Vector2 o)
	{
		float cosa = (float)Math.cos(a);
		float sina = (float)Math.sin(a);
		float xdiff = this.x - o.x;
		float ydiff = this.y - o.y;
		
		float newX = cosa * xdiff - sina * ydiff + o.x;
		float newY = sina * xdiff + cosa * ydiff + o.y;
		
		return new Vector2(newX, newY);
	}
		
	/**
	 * Sets the x and y components of the vector
	 * Useful for updating the value without altering any references to this object
	 * @param x the new X value of this object
	 * @param y the new Y value of this object
	 */
	public void set(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Sets the value of this vector
	 * Useful for updating the value without altering any references to this object
	 * @param v the new value of the vector
	 */
	public void set(Vector2 v)
	{
		this.x = v.x;
		this.y = v.y;
	}
	
	/**
	 * Translates this vector by the given vector
	 * @param v the vector to translate by
	 */
	public void translate(Vector2 v)
	{
		this.x += v.x;
		this.y += v.y;
	}

	/**
	 * Gets the length (magnitude) of this vector
	 * @return the length of this vector
	 */
	public float length()
	{
		return (float)Math.sqrt((this.x * this.x) + (this.y * this.y));
	}
	
	/**
	 * Normalizes this vector so that it retains the same direction, but a magnitude of 1
	 */
	public void normalize()
	{
		float magnitude = length();
		
		this.x /= magnitude;
		this.y /= magnitude;
	}
	
	/**
	 * Scales this vector by the given scale factor
	 * @param s the scale factor to multiply by
	 */
	public void scale(float s)
	{
		this.x *= s;
		this.y *= s;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (o.getClass() ==  Vector2.class)
		{
			Vector2 v = (Vector2)o;
			return (v.x == this.x && v.y == this.y);
		}
		else
		{
			return false;
		}
	}
	
	@Override
	public String toString()
	{
		return "Vector2 {X:" + this.x + ", Y:" + this.y + "}";
	}
	
	@Override
	public Vector2 clone()
	{
		return new Vector2(this.x, this.y);
	}
	
	/**
	 * Constructs a Vector2 representing the up direction (0, 1)
	 */
	public static Vector2 UP()
	{
		return new Vector2(0f, 1f);
	}

	/**
	 * Constructs a Vector2 representing the down direction (0, -1)
	 */
	public static Vector2 DOWN()
	{
		return new Vector2(0f, -1f);
	}

	/**
	 * Constructs a Vector2 representing the left direction (-1, 0)
	 */
	public static Vector2 LEFT()
	{
		return new Vector2(-1f, 0f);
	}

	/**
	 * Constructs a Vector2 representing the right direction (1, 0)
	 */
	public static Vector2 RIGHT()
	{
		return new Vector2(1f, 0f);
	}
}
