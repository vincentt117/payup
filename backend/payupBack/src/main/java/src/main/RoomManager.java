package src.main;

import java.util.ArrayList;
import java.util.UUID;

import org.apache.commons.fileupload.FileItem;

public class RoomManager {
	
	private static ArrayList<Room> rooms = new ArrayList<Room>();

	public static Room addRoom(String name){
		Room room = new Room(UUID.randomUUID());
		room.addPerson(name);
		rooms.add(room);
		return room;
	}

	public static void sendOk(FileItem item, String room) {
		for(int i = 0; i < rooms.size();i++){
			if(rooms.get(i).checkId(room)){
				if(rooms.get(i).compare(item) != null){
					rooms.get(i).update(item);
					break;
				}
			}
		}
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
