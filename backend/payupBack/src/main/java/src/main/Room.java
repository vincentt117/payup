package src.main;

import java.util.ArrayList;
import java.util.UUID;

import org.apache.commons.fileupload.FileItem;

public class Room {
	
	private ArrayList<Person> peopleInRoom;
	private ArrayList<FileItem> files = new ArrayList<FileItem>();
	private ArrayList<String> ocrData = new ArrayList<String>();
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

	public boolean checkId(String room) {
		return UUID.fromString(room).toString().equals(this.getId().toString());
	}
	
	public void update(FileItem item){
		ArrayList<String> result = this.compare(item);
		if(result != null){
			for(int i = 0;i < peopleInRoom.size();i++){
				peopleInRoom.get(i).changeDue(Float.parseFloat(result.get(result.size() - 1)));
			}
		}
	}

	public String getDues() {
		String dues = "";
		for(int i = 0; i < peopleInRoom.size();i++){
			dues = dues + "," + peopleInRoom.get(i).getDue();
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
