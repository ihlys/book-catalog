package com.ihordev.bookcatalog.util.jsp;

import java.util.Collection;

public final class Functions
{
	
	private Functions()
	{
		throw new AssertionError("Function library class can not be instantiated");
	}
	
	public static boolean contains(Collection<?> collection, Object object)
	{
		return collection.contains(object);
	}
	
}
