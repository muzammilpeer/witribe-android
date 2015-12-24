package com.ranisaurus.databaselayer.service;

import com.ranisaurus.databaselayer.exception.ServiceException;
import com.ranisaurus.databaselayer.model.DBTaglines;

import java.util.List;

/**
 * Created by muzammilpeer on 9/6/15.
 */
public class TaglinesService implements ITaglinesService {

    @Override
    public List<DBTaglines> getAllTaglines(String categoryID) throws ServiceException {
        List<DBTaglines> taglinesList = null;
        try {
            String[] values = new String[1];
            values[0] = categoryID;
            taglinesList = DBTaglines.find(DBTaglines.class, "category_id = ? ", values);
//            taglinesList =  DBTaglines.listAll(DBTaglines.class);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return taglinesList;
    }

    @Override
    public void saveAllTaglines(List<DBTaglines> taglines, String categoryID) throws ServiceException {
        try {

            if (taglines != null) {
                //remove all records from database
                String[] values = new String[1];
                values[0] = categoryID;
                DBTaglines.deleteAll(DBTaglines.class, "category_id = ? ", values);
                //save all new records
                DBTaglines.saveInTx(taglines);
            }
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public long getTaglinesCount(String categoryID) throws ServiceException {
        long count = 0;
        try {
            String[] values = new String[1];
            values[0] = categoryID;
            count = DBTaglines.count(DBTaglines.class, "category_id = ? ", values);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return count;
    }

    @Override
    public List<DBTaglines> searchQuery(String creteria) throws ServiceException {
        List<DBTaglines> categoriesList = null;
        try {
//            categoriesList = DBTaglines.findWithQuery(DBTaglines.class, "select * from db_taglines where how_to like '%" + creteria + "%' OR tagline like '%"+ creteria +"%' " , null);
            categoriesList = DBTaglines.findWithQuery(DBTaglines.class, "select * from db_taglines where tagline like '%" + creteria + "%' ", null);

        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return categoriesList;
    }
}
