package com.bccns.umsserviceweb.common.util;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.web.multipart.MultipartFile;

public class UploadFile implements Serializable {

	private static final long serialVersionUID = 1L;

	private MultipartFile multipartFile;
	private String fileName;
	private String serverFileName;
	private String serverThumbnailFileName;
	private String fileExt;
	private long fileSize;
	private String fileType;
	private String relativePath;
	private String relativeFilePath;
	private String absoluteFilePath;
	private boolean isImage;
	private int imageWidth;
	private int imageHeight;
	private String etc1;
	private String etc2;
	private String etc3;

	public UploadFile(MultipartFile multipartFile, String serverFileName, String absoluteFilePath, String relativePath, String relativeFilePath) {
		this.multipartFile = multipartFile;
		this.serverFileName = serverFileName;
		this.fileName = multipartFile.getOriginalFilename();
		this.fileExt = StringUtils.substringAfterLast(multipartFile.getOriginalFilename(), ".");
		this.fileSize = multipartFile.getSize();
		this.fileType = multipartFile.getContentType();
		this.relativePath = relativePath;
		this.relativeFilePath = relativeFilePath;
		this.absoluteFilePath = absoluteFilePath;
	}

	public String getEtc1() {
		return etc1;
	}

	public void setEtc1(String etc1) {
		this.etc1 = etc1;
	}

	public String getEtc2() {
		return etc2;
	}

	public void setEtc2(String etc2) {
		this.etc2 = etc2;
	}

	public String getEtc3() {
		return etc3;
	}

	public void setEtc3(String etc3) {
		this.etc3 = etc3;
	}

	public MultipartFile getMultipartFile() {
		return multipartFile;
	}

	public void setMultipartFile(MultipartFile multipartFile) {
		this.multipartFile = multipartFile;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getServerFileName() {
		return serverFileName;
	}

	public void setServerFileName(String serverFileName) {
		this.serverFileName = serverFileName;
	}

	public String getServerThumbnailFileName() {
		return serverThumbnailFileName;
	}

	public void setServerThumbnailFileName(String serverThumbnailFileName) {
		this.serverThumbnailFileName = serverThumbnailFileName;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getRelativePath() {
		return relativePath;
	}

	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}

	public String getRelativeFilePath() {
		return relativeFilePath;
	}

	public void setRelativeFilePath(String relativeFilePath) {
		this.relativeFilePath = relativeFilePath;
	}

	public String getAbsoluteFilePath() {
		return absoluteFilePath;
	}

	public void setAbsoluteFilePath(String absoluteFilePath) {
		this.absoluteFilePath = absoluteFilePath;
	}

	public String getFileExt() {
		return fileExt;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}

	public boolean isImage() {
		return isImage;
	}

	public void setImage(boolean isImage) {
		this.isImage = isImage;
	}

	public int getImageWidth() {
		return imageWidth;
	}

	public void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}

	public int getImageHeight() {
		return imageHeight;
	}

	public void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
