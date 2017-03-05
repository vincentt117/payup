package src.main;

import java.util.ArrayList;
import java.util.UUID;

import org.apache.commons.fileupload.FileItem;

public class UserManager {
	
	private static ArrayList<FileItem> files = new ArrayList<FileItem>();
	private static ArrayList<ArrayList<String>> datas = new ArrayList<ArrayList<String>>();
	private static ArrayList<String> ids = new ArrayList<String>();
	
	public static void addFileItem(FileItem file, ArrayList<String> data, String id){
		
		for(int i = 0; i < ids.size();i++){
			if(ids.get(i).equals(id)){
				files.set(i, file);
				datas.set(i, data);
				return;
			}
		}
		
		files.add(file);
		datas.add(data);
		ids.add(id);
	}
	
	public static void exists(FileItem file, String id, String room){
		for(int i = 0;i < files.size();i++){
			if(compareBytes(file.get(), files.get(i).get()) && ids.get(i) == id){
				RoomManager.getRoom(room).update(datas.get(i));
			}
		}
	}
	
	private static boolean compareBytes(byte[] bs, byte[] bs2) {
		for(int i = 0; i < bs.length;i++){
			if(bs[i] != bs2[i]){
				return false;
			}
		}
		return true;
	}

}
