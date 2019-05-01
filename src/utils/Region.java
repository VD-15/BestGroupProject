package utils;

/**
 * Represents an area in 2-D space
 * @author Vee
 *
 */
public final class Region implements Cloneable
{
	/**
	 * The x-component (left) of this region
	 */
	public float x;
	
	/**
	 * The y-component (top) of this region
	 */
	public float y;
	
	/**
	 * The width of this region
	 */
	public float width;
	
	/**
	 * The height of this region
	 */
	public float height;
	
	/**
	 * Creates a region object at 0,0 with a size of 0,0
	 */
	public Region()
	{
		this.x = 0f;
		this.y = 0f;
		this.width = 0f;
		this.height = 0f;
	}
	
	/**
	 * Creates a region object with the given parameters
	 * @param x The {@link Region#x x} component of this region
	 * @param y The {@link Region#y y} component of this region
	 * @param width {@link Region#width width} of the region
	 * @param height {@link Region#height height} of the region
	 */
	public Region(float x, float y, float width, float height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Creates a region object with two Vector2 objects
	 * @param location the location of the region
	 * @param size the size of the region
	 * @param centered If true, the region will treat the given Vector2 as it's center as oppose to it's top-left corner
	 */
	public Region(Vector2 location, Vector2 size, boolean centered)
	{
		if (centered)
		{
			this.x = location.x - (size.x / 2f);
			this.y = location.y - (size.y / 2f);
			this.width = size.x;
			this.height = size.y;
		}
		else
		{
			this.x = location.x;
			this.y = location.y;
			this.width = size.x;
			this.height = size.y;
		}
	}
	
	/**
	 * Gets the location (top-left corner) of this region
	 * @return A {@link Vector2 Vector2} object representing the location of this region
	 */
	public Vector2 getLocation()
	{
		return new Vector2(this.x, this.y);
	}
	
	/**
	 * Sets the location (top-left corner) of this region
	 * @param v A {@link Vector2 Vector2} object representing the new location of this region
	 */
	public void setLocation(Vector2 v)
	{
		this.x = v.x;
		this.y = v.y;
	}
	
	/**
	 * Gets the size of this region
	 * @return A {@link Vector2 Vector2} object representing the size of this region
	 */
	public Vector2 getSize()
	{
		return new Vector2(this.width, this.height);
	}
	
	/**
	 * Sets the size of this region, keeping the location in the same place
	 * @param v A {@link Vector2 Vector2} object representing the new size of this region
	 */
	public void setSize(Vector2 v)
	{
		this.width = v.x;
		this.height = v.y;
	}
	
	/**
	 * Gets the center of this region
	 * @return A {@link Vector2 Vector2} object representing the point at the center of this region
	 */
	public Vector2 getCenter()
	{
		return new Vector2(this.x + (this.width / 2), (this.y + this.height / 2));
	}
	
	/**
	 * Checks whether a location is located within this region
	 * @param v A {@link Vector2 Vector2} object representing the location to check
	 * @return true if the given vector is located within the bounds of this region, otherwise, false
	 */
	public boolean containsPoint(Vector2 v)
	{
		return (v.x > this.x && v.x < this.x + this.width && v.y > this.y && v.y < this.y + this.height);
	}

	
	/**
	 * Checks whether a location is located within this region
	 * @param v A {@link Point Point} object representing the location to check
	 * @return true if the given point is located within the bounds of this region, otherwise, false
	 */
	public boolean containsPoint(Point v)
	{
		return (v.x > this.x && v.x < this.x + this.width && v.y > this.y && v.y < this.y + this.height);
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (o.getClass() == Region.class)
		{
			Region r = (Region)o;
			return (r.x == this.x && r.y == this.y && r.width == this.width && r.height == this.height);
		}
		else
		{
			return false;
		}
	}
	
	@Override
	public Region clone()
	{
		return new Region(this.x, this.y, this.width, this.height);
	}
}
