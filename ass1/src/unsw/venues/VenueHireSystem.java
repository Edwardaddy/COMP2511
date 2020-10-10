/**
 * 
 */
package unsw.venues;

import java.time.LocalDate;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Venue Hire System for COMP2511.
 *
 * A basic prototype to serve as the "back-end" of a venue hire system. Input
 * and output is in JSON format.
 *
 * @author Robert Clifton-Everest
 *
 */
public class VenueHireSystem {
	public List<Request> RequestList;
	public List<Venue> venueList;

	public List<Venue> getVenueList() {
		return this.venueList;
	}
	
	public List<Request> getRequestList() {
		return this.RequestList;
	}

	public VenueHireSystem() {
		this.RequestList = new ArrayList<Request>();
		this.venueList = new ArrayList<Venue>();
	}
	public static void main(String[] args) {
		VenueHireSystem system = new VenueHireSystem();

		Scanner sc = new Scanner(System.in);

		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			if (!line.trim().equals("")) {
				JSONObject command = new JSONObject(line);
				system.processCommand(command);
			}
		}
		sc.close();
		
	}
	/**
	 * 
	 * @param json is the command
	 */

	public void processCommand(JSONObject json) {
		JSONObject obj = null;
		switch (json.getString("command")) {
		case "room":
			RoomCase(json);
			break;
		case "request":
			obj = requestCase(json);
			break;
		case "cancel":
			cancelCase(json);
			break;
		case "change":
			obj = changeCase(json);
			break;
		case "list":
			JSONArray list = listCase(json);
			if (list != null) System.out.println(list.toString(2));
			break;
		default:
			break;
		}
		if (obj != null) {
			System.out.println(obj.toString(2));
		}
	}

	/**
	 * 
	 * @param json
	 * like this: { "command": "room", "venue": "Zoo", "room": "Penguin", "size": "small" }
	 */
	public void RoomCase(JSONObject json) {
		String venue = json.getString("venue");
		String room = json.getString("room");
		String size = json.getString("size");
		if (!addRoom(venue, room, size)) System.out.println(reject().toString(2));
	}

	/**
	 * 
	 * @param json command
	 * @return success or rejected
	 */
	public JSONObject requestCase(JSONObject json) {
		LocalDate start = LocalDate.parse(json.getString("start"));
		LocalDate end = LocalDate.parse(json.getString("end"));
		String id = json.getString("id");
		int small = json.getInt("small");
		int medium = json.getInt("medium");
		int large = json.getInt("large");

		Request Request = applyRequest(id, start, end, small, medium, large);

		if (RequestAdd(Request)) return applyRequest(Request.getVenueID(), Request.getRoomIDs());
		
		return reject();
	}
	/**
	 * 
	 * @param json command
	 * @return success or rejected
	 */

	public JSONObject changeCase(JSONObject json) {

		String id = json.getString("id");
		LocalDate start = LocalDate.parse(json.getString("start"));
		LocalDate end = LocalDate.parse(json.getString("end"));
		int small = json.getInt("small");
		int medium = json.getInt("medium");
		int large = json.getInt("large");

		Request request = getRequest(id, getRequestList());
		if(request != null) {
			removeFromList(id);
			Request newRequest = applyRequest(id, start, end, small, medium, large);
			if (RequestAdd(newRequest)) return applyRequest(newRequest.getVenueID(), newRequest.getRoomIDs());

			RequestAdd(request);
		}

		return reject();

	}

	/**
	 * 
	 * @param json
	 * cancel situation
	 */
	public void cancelCase(JSONObject json) {
		String id = json.getString("id");
		removeFromList(id);
	}

	/**
	 * list situation
	 * @param json
	 * @return all the venue and request
	 */
	public JSONArray listCase(JSONObject json) {
		
		String venueID = json.getString("venue");
		
		if (!venueExists(venueID, getVenueList())) return processList(new ArrayList<String>(), new ArrayList<Request>());
		
		Venue venue = getVenueByID(venueID);
		List<Request> Requests = new ArrayList<Request>();
		for (Request request : getRequestList()) {
			if (request.getVenueID().equals(venueID)) Requests.add(request);
		}
		Requests.sort((x, y) -> { if (x.getStartDate().equals(y.getStartDate())) return 0;
			return (x.getStartDate().isBefore(y.getStartDate())) ? -1 : 1;
		});
		
		return processList(venue.getRoomListID(), Requests);
	}

	/**
	 * 
	 * @param venueID
	 * @param roomID
	 * @return venue and room
	 */
	public static JSONObject applyRequest(String venueID, List<String> roomID) {
		JSONObject obj = success();
		obj.put("venue", venueID);
		JSONArray rooms = new JSONArray();
		for (String room : roomID) rooms.put(room);
		obj.put("rooms", rooms);
		return obj;
	}
	/**
	 * 
	 * @param id venue name
	 * @param list venue list
	 * @return true or false
	 */
	public boolean venueExists(String id, List<Venue> list) {
		for (Venue venue : list) {
			if (venue.getID().equals(id)) return true;
		}
		return false;
	}

	/**
	 * 
	 * @param venueID
	 * @param roomID
	 * @param size
	 * @return true and false
	 */
	public boolean addRoom(String venueID, String roomID, String size) {
		if (venueExists(venueID, getVenueList())) {
			for (Venue v : getVenueList()) {
				if (v.getID().equals(venueID)) return v.appendList(roomID, size);
			}
			return false;
		} else {
			Venue v = new Venue(venueID, roomID, size);
			return addToVenueList(v);
		}
	}

	/**
	 * 
	 * @param roomIDList
	 * @param Requests
	 * @return 
	 */
	public static JSONArray processList(List<String> roomIDList, List<Request> Requests) {
		JSONArray result = new JSONArray();
		for(String roomID: roomIDList) {
			JSONObject new_room = new JSONObject();
			new_room.put("room", roomID);
			JSONArray new_request = new JSONArray();
			for (Request request : Requests) {
				for (String room_id : request.getRoomIDs()) {
					if (roomID.equals(room_id)) {
						JSONObject obj = new JSONObject();
						obj.put("id", request.getRequestID());
						obj.put("start", request.getStartDate().toString());
						obj.put("end", request.getEndDate().toString());
						new_request.put(obj);
					}
				}
			}
			new_room.put("reservations", new_request);
			result.put(new_room);
		}
		
		return result;
	}

	/**
	 * 
	 * @param venueID
	 * @param roomID
	 * @param start
	 * @param end
	 * @param rList
	 * @return whether room is available
	 */
	public boolean roomIsAvailable(String venueID, String roomID, LocalDate start, LocalDate end, List<Request> rList) {
		for (Request r : rList) {
			if (r.getVenueID().equals(venueID) && r.RoomIDexist(roomID) && !(r.getStartDate().isAfter(end) || r.getEndDate().isBefore(start))) return false;
		}
		return true;
	}

	/**
	 * 
	 * @param venue
	 * @param start
	 * @param end
	 * @param size
	 * @param number
	 * @return whether venue is available
	 */
	public boolean venueIsAvailable(Venue venue, LocalDate start, LocalDate end, String size, int number) {
		if (venue == null) return false;
		
		List<String> roomIDList = new ArrayList<String>();
		for(String roomID: venue.getRoomListID()) {
			if (roomIsAvailable(venue.getID(), roomID, start, end, getRequestList()) && venue.roomcheck(roomID, size)) roomIDList.add(roomID);
		}
		
		return (roomIDList.size() >= number);
	}

	/**
	 * 
	 * @param id
	 * @return venue corresponding to 
	 */
	public Venue getVenueByID(String id) {
		for (Venue v : getVenueList()) {
			if (id.equals(v.getID())) return v;
		}
		return null;
	}

	/**
	 * 
	 * @param id
	 * @param list
	 * @return request corresponding to 
	 */
	public Request getRequest(String id, List<Request> list) {
		for (Request request : list) {
			if (request.getRequestID().equals(id)) return request;
		}
		return null;
	}

	/**
	 * 
	 * @param venue
	 * @param start
	 * @param end
	 * @param small
	 * @param medium
	 * @param large
	 * @return a room list
	 */
	public List<String> findRoomID(Venue venue, LocalDate start, LocalDate end, int small, int medium, int large) {
		if (venue == null) 	return null;

		List<String> roomIDList = new ArrayList<String>();
		for (String roomID: venue.getRoomListID()) {
			if (roomIsAvailable(venue.getID(), roomID, start, end, getRequestList())) 	roomIDList.add(roomID);
		}
		
		List<String> list = new ArrayList<String>();
		for (String roomID: roomIDList) {
			if (small > 0 && venue.roomcheck(roomID, "small")) {
				list.add(roomID);
				small--;
			} else if (medium > 0 && venue.roomcheck(roomID, "medium")) {
				list.add(roomID);
				medium--;
			} else if (large > 0 && venue.roomcheck(roomID, "large")) {
				list.add(roomID);
				large--;
			}
		}
		return list;
	}

	/**
	 * 
	 * @param start
	 * @param end
	 * @param small
	 * @param medium
	 * @param large
	 * @param vList
	 * @return a venue
	 */
	public Venue findVenue(LocalDate start, LocalDate end, int small, int medium, int large, List<Venue> vList) {
		for (Venue v : vList) {
			if (venueIsAvailable(v, start, end, "small", small) && venueIsAvailable(v, start, end, "medium", medium) && venueIsAvailable(v, start, end, "large", large)) {
				return v;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param id
	 * @param start
	 * @param end
	 * @param small
	 * @param medium
	 * @param large
	 * @return request that have enough correct size room
	 */
	public Request applyRequest(String id, LocalDate start, LocalDate end, int small, int medium, int large) {
		Venue venue = findVenue(start, end, small, medium, large, getVenueList());
		if (venue == null) return null;
		List<String> roomID = findRoomID(venue, start, end, small, medium, large);
		return new Request(id, start, end, venue.getID(), roomID);
	}

	/**
	 * 
	 * @param Request
	 * @return whether request is added
	 */
	public boolean RequestAdd(Request Request) {
		if (Request != null) return this.RequestList.add(Request);
		return false;
	}
	
	/**
	 * 
	 * @param venue
	 * @return whether venue is added 
	 */
	public boolean addToVenueList(Venue venue) {
		if (venue == null) return false;
		return this.venueList.add(venue);
	}
	
	/**
	 * 
	 * @param id
	 * @return whether request is removed
	 */
	public boolean removeFromList(String id) {
		return this.RequestList.removeIf((req) -> (req.getRequestID().equals(id)));
	}

	/**
	 * 
	 * @return rejected status
	 */
	public static JSONObject reject() {
		JSONObject obj = new JSONObject();
		obj.put("status", "rejected");
		return obj;
	}

	/**
	 * 
	 * @return success status
	 */
	public static JSONObject success() {
		JSONObject obj = new JSONObject();
		obj.put("status", "success");
		return obj;
	}

}
