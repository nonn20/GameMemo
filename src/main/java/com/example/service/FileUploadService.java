package com.example.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.example.form.ImageForm;

@Service
public class FileUploadService {
	
	private final AmazonS3 s3Client;
	
	public FileUploadService(AmazonS3 s3Client) {
		this.s3Client = s3Client;
	}
	
    public String fileUpload(ImageForm fileUploadForm,String s3PathName) 
			       throws IOException{
		DateTimeFormatter fm = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");

        String fileName = fileUploadForm.getCreateAt().format(fm) +".png";
        File uploadFile = new File(fileName);
    
        //try-with-resources
        try (FileOutputStream uploadFileStream = new FileOutputStream(uploadFile)){
    		byte[] bytes = fileUploadForm.getMultipartFile().getBytes();
	        uploadFileStream.write(bytes);
        	
        	//引数：S3の格納先オブジェクト名,ファイル名,ファイル
        	s3Client.putObject(s3PathName, fileName, uploadFile);
        	uploadFile.delete();
        	return fileName;
        } catch (AmazonServiceException e) {
        	e.printStackTrace();
        	throw e;
        }  catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
        
	}
    public void fileDownload() {
    	try {
            // S3のオブジェクトを取得する
            S3Object o = s3Client.getObject("gamememo-noon", "test/2022-09-22 16-45-28.png");
            S3ObjectInputStream s3is = o.getObjectContent();

            // ダウンロード先のファイルパスを指定する
            FileOutputStream fos = new FileOutputStream(new File("images/test/2022-09-22 16-45-28.png"));

            // S3のオブジェクトを1024byteずつ読み込み、ダウンロード先のファイルに書き込んでいく
            byte[] read_buf = new byte[1024];
            int read_len = 0;
            while ((read_len = s3is.read(read_buf)) > 0) {
                fos.write(read_buf, 0, read_len);
            }

            s3is.close();
            fos.close();
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
    
}
