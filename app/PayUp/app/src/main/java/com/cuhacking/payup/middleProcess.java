package com.cuhacking.payup;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class middleProcess {


    public static void send(File img){
        String charset = "UTF-8";
        String requestURL = "http://172.17.192.146:8080/payupBack/OcrProcess/";

        try {
            doPost multipart = new doPost(requestURL, charset);
            multipart.addFilePart("uploadImg", img);

            List<String> response = multipart.finish();

            System.out.println("SERVER REPLIED:");

            for (String line : response) {
                System.out.println(line);
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

	public static void main(String[] args) {
        String charset = "UTF-8";
        String path = doPost.class.getResource("/receipt.jpg").toString().substring(5);
        File img = new File(path);
//        File uploadFile1 = new File("e:/Test/PIC1.JPG");
//        File uploadFile2 = new File("e:/Test/PIC2.JPG");
        String requestURL = "http://172.17.192.146:8080/payupBack/OcrProcess/";
 
        try {
            doPost multipart = new doPost(requestURL, charset);
             
//            multipart.addHeaderField("User-Agent", "CodeJava");
//            multipart.addHeaderField("Test-Header", "Header-Value");
//             
//            multipart.addFormField("description", "Cool Pictures");
//            multipart.addFormField("keywords", "Java,upload,Spring");


            multipart.addFilePart("uploadImg", img);
 
            List<String> response = multipart.finish();
             
            System.out.println("SERVER REPLIED:");
             
            for (String line : response) {
                System.out.println(line);
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}
