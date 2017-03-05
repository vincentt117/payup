package src.main;

import java.util.ArrayList;
import java.util.UUID;

import org.apache.commons.fileupload.FileItem;

public class Room {
	
	private ArrayList<Person> peopleInRoom;
	
	private UUID id;
	
	public Room(UUID i){
		id = i;
		peopleInRoom = new ArrayList<Person>();
	}
	
	public UUID getId(){
		return id;
	}
	
	public void addPerson(String name){
		peopleInRoom.add(new Person(name));
	}
	
	

	public boolean checkId(String room) {
		return UUID.fromString(room).toString().equals(this.getId().toString());
	}
	
	public void update(ArrayList<String> result){
		if(result != null){
			for(int i = 0;i < peopleInRoom.size();i++){
				peopleInRoom.get(i).changeDue((Float.parseFloat(result.get(result.size() - 1)))/peopleInRoom.size());
			}
		}
	}

	public String getDues() {
		String dues = "";
		for(int i = 0; i < peopleInRoom.size();i++){
			dues = dues + "," + peopleInRoom.get(i).getString() + "," + peopleInRoom.get(i).getDue();
		}
		return dues.substring(1);
	}

	public boolean has(String name) {
		for(int i = 0;i < peopleInRoom.size();i++){
			if(peopleInRoom.get(i).getString().equals(name)){
				return true;
			}
		}
		return false;
	}
}
