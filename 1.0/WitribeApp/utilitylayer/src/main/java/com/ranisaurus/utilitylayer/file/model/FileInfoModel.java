package com.ranisaurus.utilitylayer.file.model;

import com.ranisaurus.utilitylayer.base.BaseModel;

/**
 * Created by muzammilpeer on 11/9/15.
 */
public class FileInfoModel extends BaseModel {

    private String fileName;
    private String fileSize;
    private String dateCreated;
    private String dateModifed;
    private String fullPath;

    public FileInfoModel() {
    }

    public FileInfoModel(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateModifed() {
        return dateModifed;
    }

    public void setDateModifed(String dateModifed) {
        this.dateModifed = dateModifed;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }
}
