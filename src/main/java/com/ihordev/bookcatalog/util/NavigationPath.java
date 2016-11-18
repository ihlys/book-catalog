package com.ihordev.bookcatalog.util;

/**
 * The NavigationPath represents navigation link. It holds relative URL address
 * and title.
 */

public class NavigationPath
{
    private String path;

    private String pathText;

    public NavigationPath(String path, String pathText)
    {
        this.path = path;
        this.pathText = pathText;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public String getPathText()
    {
        return pathText;
    }

    public void setPathText(String pathText)
    {
        this.pathText = pathText;
    }

}
