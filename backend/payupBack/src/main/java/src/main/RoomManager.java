package src.main;

import java.util.ArrayList;
import java.util.UUID;

import org.apache.commons.fileupload.FileItem;

public class RoomManager {
	
	private static ArrayList<Room> rooms = new ArrayList<Room>();
	

	private ArrayList<FileItem> files = new ArrayList<FileItem>();
	private ArrayList<String> ocrData = new ArrayList<String>();

	public static Room addRoom(String name){
		Room room = new Room(UUID.randomUUID().toString().substring(0, 5));
		room.addPerson(name);
		rooms.add(room);
		return room;
	}
	
	public void addFile(FileItem item, ArrayList<String> data){
		if(files.size() >= 3){
			files.remove(0);
		}
		files.add(item);
		ocrData = data;
	}
	
	public ArrayList<String> compare(FileItem item){
		for(int i = 0; i < files.size();i++){
			if(compareBytes(files.get(i).get(),item.get())){
				return ocrData;
			}
		}
		
		return null;
	}

	private boolean compareBytes(byte[] bs, byte[] bs2) {
		for(int i = 0; i < bs.length;i++){
			if(bs[i] != bs2[i]){
				return false;
			}
		}
		return true;
	}

	public static void sendOk(FileItem item) {
//		for(int i = 0; i < rooms.size();i++){
//			if(rooms.get(i).checkId(room)){
//				if(rooms.get(i).compare(item) != null){
//					rooms.get(i).update(item);
//					break;
//				}
//			}
//		}
	}

	public static String getRoomDue(String room) {
		Room room1 = getRoom(room);
		if(room1 != null){
			return room1.getDues();
		} else {
			return null;
		}
	}

	public static String getWhatRoomsIn(String name) {
		String string = "";
		for(int i = 0; i < rooms.size();i++){
			if(rooms.get(i).has(name)){
				string = string + "," + rooms.get(i).getId();
			}
		}
		
		if(string.equals("")){
			return null;
		}
		
		return string.substring(1);
	}

	public static Room getRoom(String name) {

		for(int i = 0; i < rooms.size();i++){
			if(rooms.get(i).checkId(name)){
				return rooms.get(i);
			}
		}

		return null;
	}

}
