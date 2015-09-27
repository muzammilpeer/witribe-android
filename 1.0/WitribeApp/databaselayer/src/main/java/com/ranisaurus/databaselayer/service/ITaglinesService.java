package com.ranisaurus.databaselayer.service;

import com.ranisaurus.databaselayer.exception.ServiceException;
import com.ranisaurus.databaselayer.model.DBTaglines;

import java.util.List;

/**
 * Created by muzammilpeer on 9/6/15.
 */
public interface ITaglinesService {
    List<DBTaglines> getAllTaglines(String categoryID) throws ServiceException;

    void saveAllTaglines(List<DBTaglines> taglines, String categoryID) throws ServiceException;

    long getTaglinesCount(String categoryID) throws ServiceException;

    List<DBTaglines> searchQuery(String creteria) throws ServiceException;

}
