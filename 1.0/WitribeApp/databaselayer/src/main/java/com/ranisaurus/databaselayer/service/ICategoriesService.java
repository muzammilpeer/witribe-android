package com.ranisaurus.databaselayer.service;

import com.ranisaurus.databaselayer.exception.ServiceException;
import com.ranisaurus.databaselayer.model.DBCategories;

import java.util.List;

/**
 * Created by muzammilpeer on 9/6/15.
 */
public interface ICategoriesService {

    List<DBCategories> getAllCategories() throws ServiceException;

    void saveAllCategories(List<DBCategories> categories) throws ServiceException;

    long getCategoriesCount() throws ServiceException;

    List<DBCategories> searchQuery(String creteria) throws ServiceException;
}
