package com.ranisaurus.databaselayer.factory;

import com.ranisaurus.databaselayer.exception.ServiceException;
import com.ranisaurus.databaselayer.service.CategoriesService;
import com.ranisaurus.databaselayer.service.ICategoriesService;
import com.ranisaurus.databaselayer.service.ITaglinesService;
import com.ranisaurus.databaselayer.service.TaglinesService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by muzammilpeer on 9/6/15.
 */
public class ServiceFactory {
    private static ServiceFactory serviceFactory = new ServiceFactory();
    private Map<String, Class> map = new HashMap<String, Class>();

    private ServiceFactory() {

        map.put(ICategoriesService.class.getName(), CategoriesService.class);
        map.put(ITaglinesService.class.getName(), TaglinesService.class);

    }

    public static ServiceFactory getInstance() {
        return serviceFactory;
    }

    public Object getService(String serviceName) throws ServiceException {

        Object service = null;
        try {
            if (map.containsKey(serviceName)) {
                return map.get(serviceName).newInstance();
            }
        } catch (InstantiationException e) {
            throw new ServiceException(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            throw new ServiceException(e.getMessage(), e);
        }

        return service;
    }
}
