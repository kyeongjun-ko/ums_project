package com.bccns.umsserviceweb.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;


public class FileTools {
	private final Logger logger = Logger.getLogger(FileTools.class);

    private static long SERIAL = -1;
	public static String IMAGE_PATH = "upimages";
	
	
	public static int checkUploadFileExtension(String fileName) {
        
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        if(ext.equalsIgnoreCase("xls") || ext.equalsIgnoreCase("xlsx")) {
            return 1;
        } else if(ext.equalsIgnoreCase("txt")) {
            return 2;
        } else {
            return -1;
        }
    }
	
	public static int checkUploadMultiFileExtension(String fileName) {
        
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        if(ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("jpeg")|| ext.equalsIgnoreCase("gif")|| ext.equalsIgnoreCase("png")) {
            return 1;
        } else if(ext.equalsIgnoreCase("pdf") || ext.equalsIgnoreCase("xls")
        		|| ext.equalsIgnoreCase("doc")|| ext.equalsIgnoreCase("docx")
        		|| ext.equalsIgnoreCase("ppt")|| ext.equalsIgnoreCase("hwp")
        		|| ext.equalsIgnoreCase("pptx")|| ext.equalsIgnoreCase("csv")
        		|| ext.equalsIgnoreCase("txt")|| ext.equalsIgnoreCase("jpg")
        		|| ext.equalsIgnoreCase("xlsx")|| ext.equalsIgnoreCase("gif")) {
            return 2;
        } else {
            return -1;
        }
    }
	
	public boolean fileFiltering(String fullFileName){
		String filter[] = {"jsp","java","class"};
		String extention = FilenameUtils.getExtension(fullFileName);
		for (int i = 0; i < filter.length; i++) {
			if(extention.equals(filter[i])){
				return false;
			}
		}
		return true;
	}
	
	public String fileReName(String originalFullFileName, String convertFullFileName, int Squence){
		File file = new File(convertFullFileName);
		
		if (file.exists()){
			String path = FilenameUtils.getFullPath(originalFullFileName);
			String basename = FilenameUtils.getBaseName(originalFullFileName);
			String extention = FilenameUtils.getExtension(originalFullFileName); 
			convertFullFileName = path + File.separator + basename + "["+ Squence + "]." + extention;
			return fileReName(originalFullFileName, convertFullFileName, Squence + 1);
		}else{
			return convertFullFileName;
		}
	}
	
	public String fileRead(String path){
		BufferedReader reader = null;
		String rtnString = null;
        try
        {
        	File file = new File(path);
        	if(!file.exists()) throw new Exception("File not Exists!");
        	
        	rtnString = ""; 
        	reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        	
        	while(reader.ready()){
        		rtnString += reader.readLine() + "\n";
        	}
        	
            return rtnString;
        }
        catch (Exception ex){
            ex.printStackTrace();
        }finally{
            try {if(reader!=null)reader.close();}catch (Exception ex) {ex.printStackTrace();}
        }
        return null;
    }
	
	public File fileWirte(InputStream is, String path){
		BufferedOutputStream bos = null;
        try
        {
        	File file = new File(path);
        	if(file.exists()){
	        	path = this.fileReName(file.getCanonicalPath(),file.getCanonicalPath(), 0);
	        	file = new File(path);
        	}
        	
            bos = new BufferedOutputStream(new FileOutputStream(file));
            byte[] buff = new byte[1024 * 1024];
            int size = 0;

            while ((size = is.read(buff)) != -1)
            {
                bos.write(buff, 0, size);
            }
            return file;
        }
        catch (Exception ex){
            ex.printStackTrace();
        }finally{
            try {if(is!=null)is.close();}catch (Exception ex) {ex.printStackTrace();}
            try {if(bos!=null)bos.close();}catch (Exception ex) {ex.printStackTrace();}
        }
        return null;
    }

	public File fileWirte(String text, String path){
		return this.fileWrite(text, path, true);
    }
	
	public File fileWrite(String text, String path, boolean rename){
		BufferedWriter fw = null;
		BufferedReader fr = null;
        try
        {
        	File file = new File(path);
        	if(file.exists() && rename){
        		logger.debug("파일이 존재합니다.");
	        	path = this.fileReName(file.getCanonicalPath(),file.getCanonicalPath(), 0);
	        	file = new File(path);
        	}

        	fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
        	fr = new BufferedReader(new StringReader(text));
        	
        	while(fr.ready()){
        		logger.debug("파일을 씁니다.");
        		String temp = fr.readLine();
        		if(temp==null) break;
        		fw.write(temp);
        		fw.newLine();
        	}
        	
            return file;
        }
        catch (Exception ex){
            ex.printStackTrace();
        }finally{
        	try {if(fr!=null)fr.close();}catch (Exception ex) {ex.printStackTrace();}
            try {if(fw!=null)fw.close();}catch (Exception ex) {ex.printStackTrace();}
        }
        return null;
    }
	
	public boolean fileDelete(String path){
		
        try {
            File file = new File(path);
            //log.debug("exists : "+file.exists());
            if (file.exists()) {
                if (file.delete()) {
                    //log.debug(file.getAbsolutePath() + " : delete!");
                    return true;
                }
            }
            return false;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean fileCopy(File src, File dest, int bufferSizeKB){
    	try {
            if(!src.exists()) return false;
            if(!dest.exists()){
            	String path = this.fileReName(dest.getCanonicalPath(),dest.getCanonicalPath(), 0);
            	dest = new File(path);
            }
            
            BufferedInputStream bi = null;
            BufferedOutputStream bo = null;
            bi = new BufferedInputStream(new FileInputStream(src));
            bo = new BufferedOutputStream(new FileOutputStream(dest));
            byte[] b = new byte[bufferSizeKB * 1024];
            int len;
            while ( (len = bi.read(b, 0, b.length)) != -1) {
                bo.write(b, 0, len);
            }
            if(bi!=null) bi.close();
            if(bo!=null) bo.close();
            return true;
            
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
   }	
    
	/**
	 * 파일명 가져오기
	 * @param fileName
	 * @return
	 */
	public static String getFileName(String fileName){
		System.out.println("fileName:"+fileName);
		int posExt = fileName.lastIndexOf(".");
		String ext = "";
		if(posExt>0){
			ext = fileName.substring(posExt+1);
		}
		if(SERIAL < 0){
			SERIAL = System.currentTimeMillis();
		}
		SERIAL++;
		String tmp = Long.toHexString(SERIAL) + "." +ext;
		return tmp;
	}
	
	/**
	 *   경로 생성
	 * @param rootPath
	 * @return
	 */
	public static String getMkdirPath(String rootPath, String folder){
		
    	StringBuffer sbImagePath = new StringBuffer();
 
    	File filePath = null;
    	sbImagePath.append(folder);
    	filePath = new File(rootPath + sbImagePath.toString());
		if(!filePath.exists()) filePath.mkdir();
		sbImagePath.append("/");
		return sbImagePath.toString();
	}
	/**
	 * 이미지 경로 생성
	 * @param rootPath
	 * @return
	 */
	public static String getImagePath(String rootPath){
		
    	String sYear = DateUtils.getStringByPattern("yyyy");
    	String sMonthDay = DateUtils.getStringByPattern("MMdd");
    	StringBuffer sbImagePath = new StringBuffer();
 
    	File filePath = null;
    	sbImagePath.append(IMAGE_PATH);
    	filePath = new File(rootPath + sbImagePath.toString());
		if(!filePath.exists()) filePath.mkdir();
		
		sbImagePath.append("/"+sYear);
    	filePath = new File(rootPath + sbImagePath.toString());
		if(!filePath.exists()) filePath.mkdir();
		
		sbImagePath.append("/"+sMonthDay);
    	filePath = new File(rootPath + sbImagePath.toString());
		if(!filePath.exists()) filePath.mkdir();

		return sbImagePath.toString();
	}
	/**
	 * 이미지 경로 생성
	 * @param rootPath
	 * @return
	 */
	public static String getFilePath(String rootPath){
		
    	String sYear = DateUtils.getStringByPattern("yyyy");
    	String sMonthDay = DateUtils.getStringByPattern("MMdd");
    	StringBuffer sbImagePath = new StringBuffer();

    	File filePath = null;
		
		sbImagePath.append("/"+sYear);
    	filePath = new File(rootPath + sbImagePath.toString());
		if(!filePath.exists()) filePath.mkdir();
		
		sbImagePath.append("/"+sMonthDay);
    	filePath = new File(rootPath + sbImagePath.toString());
		if(!filePath.exists()) filePath.mkdir();

		return sbImagePath.toString();
	}
	
	/**
	 * 파일 업로드
	 * @param formFile
	 * @param svrFile
	 * @return
	 */
    public static boolean uploadFormFile(MultipartFile formFile, File svrFile) throws FileNotFoundException, IOException{
    	boolean returnValue = false;
    	
        InputStream inStream = null;
        OutputStream outStream = null;
        try {
			if (!svrFile.exists()) {  
				svrFile.createNewFile();  
			}
        	
        	inStream = formFile.getInputStream();
        	outStream = new FileOutputStream(svrFile);
            int read = 0;
            byte[] bytes = new byte[1024];
			while ((read = inStream.read(bytes)) != -1) {  
				outStream.write(bytes, 0, read);  
			}  
            returnValue = true;
        } catch (FileNotFoundException ex) {
            throw ex;
        } catch (IOException ex) {
            throw ex;
        }finally{
			try{if(inStream!=null) inStream.close();}catch(Exception ex){}
			try{if(outStream!=null) outStream.close();}catch(Exception ex){}
        }
        return returnValue;
    }
    /**
     * 파일 삭제
     * @param path
     * @param fileName
     */
    public static boolean deleteFile(File file) throws Exception{
    	boolean returnValue = false;
        try {
        	if(file.exists()){
        		if(file.delete()){
        			returnValue = true;	
        		}else{
        			throw new Exception("File delete Error");
        		}
        		
        	}else{
        		throw new FileNotFoundException(file.getPath());
        	}
        } catch (Exception ex) {
            throw ex;
        }
        return returnValue;
    }
}
