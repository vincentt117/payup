package com.cuhacking.payup;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class middleProcess {

	public static String joinRoom(String roomId, String name){
		String charset = "UTF-8";
		String requestURL = "http://172.17.192.146:8080/payupBack/joinRoom/";
		String totalResponse = "";
		try {
			doPost multipart = new doPost(requestURL, charset);
			multipart.addHeaderField("room", roomId);
			multipart.addHeaderField("name", name);
			multipart.nextStep();
			List<String> response = multipart.finish();


			for (String line : response) {
				totalResponse += line;
			}


		} catch (IOException ex) {
			System.err.println(ex);
		}

		return totalResponse;
	}

	public static ArrayList<String> getRooms(String name){
		String charset = "UTF-8";
		String requestURL = "http://172.17.192.146:8080/payupBack/getRooms/";
		ArrayList<String> uuids = new ArrayList<String>();
		try {
			doPost multipart = new doPost(requestURL, charset);
			multipart.addHeaderField("name", name);
			multipart.nextStep();
			List<String> response = multipart.finish();

			String totalResponse = "";
			for (String line : response) {
				totalResponse += line;
			}

			String[] split = totalResponse.split(",");
			for(int i = 0; i < split.length;i++){
				uuids.add(split[i]);
			}

		} catch (IOException ex) {
			System.err.println(ex);
		}

		return uuids;
	}

	public static String createRoom(String name){
		String charset = "UTF-8";
		String requestURL = "http://172.17.192.146:8080/payupBack/createRoom/";
		String totalResponse = "";
		try {
			doPost multipart = new doPost(requestURL, charset);
			multipart.addHeaderField("name", name);
			multipart.nextStep();
			List<String> response = multipart.finish();


			for (String line : response) {
				totalResponse += line;
			}

		} catch (IOException ex) {
			System.err.println(ex);
		}

		return totalResponse;
	}

	public static ArrayList<GenericTwo> update(String name,String room){
		ArrayList<GenericTwo> prices = new ArrayList<GenericTwo>();

		String charset = "UTF-8";
		String requestURL = "http://172.17.192.146:8080/payupBack/updateTable/";

		try {
			doPost multipart = new doPost(requestURL, charset);
			multipart.addHeaderField("name", name);
			multipart.addHeaderField("room", room);
			multipart.nextStep();
			List<String> response = multipart.finish();

			String totalResponse = "";
			for (String line : response) {
				totalResponse += line;
			}

			String[] split = totalResponse.split(",");
			for(int i = 0; i < split.length;i = i + 2){
				if(i + 1 < split.length){
					prices.add(new GenericTwo(split[i],Float.parseFloat(split[i+1])));
				}
			}

		} catch (IOException ex) {
			System.err.println(ex);
		}

		return prices;
	}

	public static void sendOk(File file, String name, String room){

		String charset = "UTF-8";
		String requestURL = "http://172.17.192.146:8080/payupBack/sendOk/";

		try {
			doPost multipart = new doPost(requestURL, charset);
			multipart.addHeaderField("name", name);
			multipart.addHeaderField("room", room);
			multipart.nextStep();
			multipart.addFilePart("uploadImg", file);

			List<String> response = multipart.finish();
			response.size();
		} catch (IOException ex) {
			System.err.println(ex);
		}

	}

	public static ArrayList<GenericTwo> sendFile(File file, String name){
		ArrayList<GenericTwo> products = new ArrayList<GenericTwo>();

		String charset = "UTF-8";
		String requestURL = "http://172.17.192.146:8080/payupBack/OcrProcess/";

		try {
			doPost multipart = new doPost(requestURL, charset);
			multipart.addHeaderField("name", name);
			multipart.nextStep();
			multipart.addFilePart("uploadImg", file);

			List<String> response = multipart.finish();

			String totalResponse = "";
			for (String line : response) {
				totalResponse += line;
			}

			String[] split = totalResponse.substring(1, totalResponse.length()-1).split(",");
			for(int i = 0; i < split.length;i = i + 2){
				if(i+1 < split.length){
					products.add(new GenericTwo(split[i],Float.parseFloat(split[i+1])));
				}
			}

		} catch (IOException ex) {
			System.err.println(ex);
		}

		return products;
	}


	public static void main(String[] args) {
		String charset = "UTF-8";
		String path = doPost.class.getResource("/receipt.jpg").toString().substring(5);
		File img = new File(path);
		String requestURL = "http://172.17.192.146:8080/payupBack/OcrProcess/";

		try {
			doPost multipart = new doPost(requestURL, charset);
			multipart.nextStep();
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
