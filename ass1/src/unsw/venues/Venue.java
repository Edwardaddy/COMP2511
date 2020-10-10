package unsw.venues;

import java.util.*;

public class Venue {
    private String id;
    private List<Room> roomList;
    
    public Venue(String id, String roomID, String size) {
        this.roomList = new ArrayList<Room>();
        this.id = id;
        appendList((roomID), size);
    }

    public String getID() {
        return id;
    }

    public List<Room> getRoomList() {
        return roomList;
    }

    public List<String> getRoomListID() {
        List<String> newList = new ArrayList<String>();
        for (Room room: getRoomList()) {
            newList.add(room.getID());
        }
        return newList;
    }

    public boolean roomcheck(String roomID, String sizev) {
        for (Room room: getRoomList()) {
            if (room.getID().equals(roomID)) {
                return room.isSize(sizev);
            }
        }
        return false;
    }
    
    public boolean appendList(String roomID, String sizev) {
        return this.roomList.add(new Room(roomID, Room.getSize(sizev)));
    }

    
}