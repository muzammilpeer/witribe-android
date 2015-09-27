package com.ranisaurus.databaselayer.service;

import com.ranisaurus.databaselayer.exception.ServiceException;
import com.ranisaurus.databaselayer.model.DBCategories;

import java.util.List;

/**
 * Created by muzammilpeer on 9/6/15.
 */
public class CategoriesService implements ICategoriesService {

    @Override
    public List<DBCategories> getAllCategories() throws ServiceException {
        List<DBCategories> categoriesList = null;
        try {
            categoriesList = DBCategories.listAll(DBCategories.class);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return categoriesList;
    }


    @Override
    public void saveAllCategories(List<DBCategories> categories) throws ServiceException {
        try {

            if (categories != null) {
                //remove all records from database
                DBCategories.deleteAll(DBCategories.class);
                //save all new records
                DBCategories.saveInTx(categories);
            }
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }


    @Override
    public long getCategoriesCount() throws ServiceException {
        long count = 0;
        try {
            count = DBCategories.count(DBCategories.class, null, null);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return count;
    }

    @Override
    public List<DBCategories> searchQuery(String creteria) throws ServiceException {
        List<DBCategories> categoriesList = null;
        try {
//            categoriesList =  Select.from(DBCategories.class).where(Condition.prop("category").like(creteria)).list();
            categoriesList = DBCategories.findWithQuery(DBCategories.class, "select * from db_categories where category like '%" + creteria + "%'", null);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return categoriesList;
    }
}
