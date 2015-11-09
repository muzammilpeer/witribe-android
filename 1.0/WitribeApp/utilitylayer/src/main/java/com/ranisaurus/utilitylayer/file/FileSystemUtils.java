package com.ranisaurus.utilitylayer.file;

/**
 * Created by muzammilpeer on 11/8/15.
 */

public class FileSystemUtils {

//    public static DirectoryEnum createDirectory(String path) {
//        File directory = new File(path);
//        if (!directory.exists()) {
//            if (directory.mkdir())
//                return DirectoryEnum.SUCCUESSFULLY_DONE;
//            else
//                return DirectoryEnum.PROCESS_FAILED;
//        } else
//            return DirectoryEnum.EXISTENCE_ISSUE;
//    }
//
//    public static DirectoryEnum createFileWithContent(String path, String fileName, String[] data) {
//        File filePath = new File(path);
//        String filePathFull = path + File.separator + fileName;
//
//        try {
//            File file = new File(filePathFull);
//
//            //if file doesn't exists, then create it
//            if (!file.exists() && filePath.exists() && !data[3].isEmpty())
//                file.createNewFile();
//            else if (data[3].isEmpty()) {
//                createFileWithContent(path, fileName, data);
//            } else if (!filePath.exists())
//                return DirectoryEnum.PROCESS_FAILED;
//            else if (file.exists())
//                return DirectoryEnum.EXISTENCE_ISSUE;
//
//            FileWriter fw = new FileWriter(filePathFull);
//            for (String content : data) {
//                fw.write(content + "\n");
//            }
//            fw.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return DirectoryEnum.SUCCUESSFULLY_DONE;
//    }
//
//    public static DirectoryEnum updateFile(String path, String fileName, String[] data) {
//
//        String filePath = path + File.separator + fileName;
//        Writer output = null;
//        try {
//            File file = new File(filePath);
//            output = new BufferedWriter(new FileWriter(file, false));
//            for (String content : data) {
//                output.write(content + "\n");
//            }
//            output.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return DirectoryEnum.SUCCUESSFULLY_DONE;
//    }
//
//
//    public static boolean filesFound(String basePath) {
//        String[] paths;
//        try {
//            File file = new File(basePath);
//            paths = file.list();
//
//            if (paths != null && paths.length > 0)
//                return true;
//            else
//                return false;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return true;
//    }
//
//    public static ArrayList<StickyModel> listFiles(String path) {
//        ArrayList<StickyModel> listDataItems = new ArrayList<StickyModel>();
//        ArrayList<String> paths;
//        try {
//            File file = new File(path);
//            paths = new ArrayList<String>(Arrays.asList(file.list()));
//            for (String _path : paths) {
//                Log.v("AllFiles", _path);
//                Log.v("AllFiles", path + File.separator + _path);
//                listDataItems.add(readDataFromFile(path + File.separator + _path));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return listDataItems;
//    }
//
//    public static StickyModel readDataFromFile(String path) {
//
//        StickyModel stickyModel = new StickyModel();
//        int counter = 0;
//        String line;
//        String detail = "";
//        try {
//            File file = new File(path);
//            BufferedReader br = new BufferedReader(new FileReader(file));
//
//            while ((line = br.readLine()) != null) {
//                if (counter == 0) {
//                    stickyModel.setDateTime(line);
//                } else if (counter == 1) {
//                    stickyModel.setColor(line);
//                } else if (counter == 2) {
//                    stickyModel.setFavorite(line);
//                } else if (counter == 3) {
//                    stickyModel.setTitle(line);
//                } else {
//                    detail += "" + line+"\n";
//                }
//                counter++;
//            }
//            br.close();
//            stickyModel.setDetail(detail);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return stickyModel;
//    }

//
//    public static boolean favFilesFound(String basePath) {
//        String[] paths;
//        try {
//            File file = new File(basePath);
//            paths = file.list();
//
//            if (paths != null && paths.length > 0)
//                return true;
//            else
//                return false;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return true;
//    }
//
//    public static ArrayList<StickyModel> listFavFiles(String path) {
//        ArrayList<StickyModel> listDataItems = new ArrayList<StickyModel>();
//        ArrayList<String> paths;
//        try {
//            File file = new File(path);
//            paths = new ArrayList<String>(Arrays.asList(file.list()));
//            for (String _path : paths) {
//
//                StickyModel stickyModelReturned = findFavFiles(path + File.separator + _path);
//                if (stickyModelReturned.getFavorite().equals("1")) {
//                    listDataItems.add(stickyModelReturned);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return listDataItems;
//    }
//
//    public static StickyModel findFavFiles(String path) {
//
//        StickyModel stickyModel = new StickyModel();
//        int counter = 0;
//        String line, detail ="";
//        try {
//            File file = new File(path);
//            BufferedReader br = new BufferedReader(new FileReader(file));
//
//            while ((line = br.readLine()) != null) {
//                if (counter == 0) {
//                    stickyModel.setDateTime(line);
//                } else if (counter == 1) {
//                    stickyModel.setColor(line);
//                } else if (counter == 2) {
//                    stickyModel.setFavorite(line);
//                } else if (counter == 3) {
//                    stickyModel.setTitle(line);
//                } else {
//                    detail += "" + line+"\n";
//                }
//                counter++;
//            }
//            br.close();
//            stickyModel.setDetail(detail);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return stickyModel;
//    }
//
//    public static DirectoryEnum deleteFile(String path, String fileToDelete) {
//        String filePath = path + File.separator + fileToDelete;
//        try {
//            File file = new File(filePath);
//            if (file.exists())
//                file.delete();
//            else
//                return DirectoryEnum.EXISTENCE_ISSUE;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return DirectoryEnum.SUCCUESSFULLY_DONE;
//    }
}