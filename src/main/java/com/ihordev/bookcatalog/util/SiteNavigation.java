package com.ihordev.bookcatalog.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.MessageSource;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * This class constructs navigation information for every view declared in XML
 * document that must be loaded and provided by user.
 * <h3>XML must have the following structure:</h3>
 *
 * <pre>
 * {@code
 * <view name="indexViewName" path="/...">
 *   <view name="subViewName" path="/...">
 *   	<view name="subViewName" path="/..." />
 *   </view>
 *   <view name="subViewName" path="/..." />
 * </view>
 * }
 * </pre>
 * 
 * Navigation information for concrete view is presented by ({@link java.util.List}) 
 * of ({@link com.ihordev.bookcatalog.util.NavigationPath}) objects that is mapped
 * by XML attribute 'name' with corresponding view in {@link java.util.Map} object.
 */

public class SiteNavigation
{

    private static final String NAVIGATION_PREFIX = "Navigation.";

    private Map<String, List<NavigationPath>> navigations;

    private SiteNavigation(Map<String, List<NavigationPath>> navigations)
    {
        this.navigations = navigations;
    }

    /**
     * Returns navigation paths for each view.
     * 
     * @return map the map that contains navigation paths for each view mapped
     *         by view name.
     */
    public Map<String, List<NavigationPath>> getNavigationPaths()
    {
        return navigations;
    }

    /**
     * Searches for navigation paths for a view. If path has query parameters,
     * this method tries to obtain them from view model.
     * 
     * @param viewName
     *            the view for which navigation paths are searched.
     * @param viewModelMap
     *            a map that contains data for dynamic query parameters.
     * @return list the list containing navigation paths.
     */
    public List<NavigationPath> getNavigationFor(String viewName, Map<String, Object> viewModelMap)
    {
        for (String key : navigations.keySet())
        {
            if (key.equals(viewName))
            {
                List<NavigationPath> navigationPaths = navigations.get(viewName);
                for (NavigationPath navPath : navigationPaths)
                {
                    insertParameters(navPath, viewModelMap);
                }
                return navigationPaths;
            }
        }

        return Collections.emptyList();
    }

    private static void insertParameters(NavigationPath navPath, Map<String, Object> params)
    {
        String path = navPath.getPath();

        StringBuilder sb = new StringBuilder();

        int curPos = 0;
        while (curPos < path.length())
        {
            int br_idx = path.indexOf('{', curPos);
            if (br_idx < 0)
            {
                if (curPos == 0)
                {
                    return;
                } else
                {
                    sb.append(path.substring(curPos));
                    navPath.setPath(sb.toString());
                    return;
                }
            }
            String part = path.substring(curPos, br_idx);

            curPos = br_idx;
            br_idx = path.indexOf('}', curPos);
            String placeHolder = path.substring(curPos + 1, br_idx);
            curPos = br_idx + 1;

            if (placeHolder.length() == 0)
            {
                return;
            }

            String param = getParameter(placeHolder, params);
            sb.append(part).append(param);
        }
    }

    private static String getParameter(String placeHolder, Map<String, Object> params)
    {

        String[] mappings = placeHolder.split("\\.");

        Object param = params.get(mappings[0]);

        for (int i = 1; i < mappings.length; i++)
        {
            String methodName = "get" + mappings[i].substring(0, 1).toUpperCase() + mappings[i].substring(1, mappings[i].length());
            param = getProperty(param, methodName);
        }

        return param.toString();
    }

    private static Object getProperty(Object target, String methodName)
    {
        try
        {
            Method method = target.getClass().getDeclaredMethod(methodName);
            return method.invoke(target);
        } catch (SecurityException | NoSuchMethodException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException ignored)
        {
            return null;
        }
    }

    /**
     * Builds a new instance from XML document.
     * 
     * @param doc
     *            normalized XML document that contains navigation information.
     * @param pathMessageMappings
     *            a {@link org.springframework.context.MessageSource} that
     *            contains text content for path representation in the
     *            corresponding view.
     * @return A new instance with navigation paths for each view.
     */
    public static SiteNavigation buildFrom(Document doc, MessageSource pathMessageMappings)
    {
        Map<String, List<NavigationPath>> viewNavigations = new HashMap<>();

        buildNavigations(doc.getDocumentElement(), new ArrayList<NavigationPath>(), viewNavigations, pathMessageMappings);

        return new SiteNavigation(viewNavigations);
    }

    private static void buildNavigations(Node node, List<NavigationPath> currentPaths,
            Map<String, List<NavigationPath>> viewNavigations, MessageSource pathMessageMappings)
    {
        if (node.getNodeType() == Node.ELEMENT_NODE)
        {
            NamedNodeMap nodeMap = node.getAttributes();

            String viewName = nodeMap.getNamedItem("name").getNodeValue();
            String path = nodeMap.getNamedItem("path").getNodeValue();

            if (currentPaths.isEmpty() == false)
            {
                String prev = currentPaths.get(currentPaths.size() - 1).getPath();
                if (prev.endsWith("/") == false)
                {
                    path = prev + path;
                }
            }

            currentPaths.add(new NavigationPath(path, pathMessageMappings.getMessage(NAVIGATION_PREFIX + viewName, null, null)));

            viewNavigations.put(viewName, currentPaths);

            NodeList childs = node.getChildNodes();

            for (int i = 0; i < childs.getLength(); i++)
            {
                Node child = childs.item(i);

                buildNavigations(child, new ArrayList<>(currentPaths), viewNavigations, pathMessageMappings);
            }
        }
    }

    /**
     * Builds a new instance that has no data. This method is used when XML
     * document cannot be provided.
     * 
     * @return A new instance that has no data.
     */
    public static SiteNavigation buildEmpty()
    {
        return new SiteNavigation(Collections.<String, List<NavigationPath>>emptyMap());
    }

}
