package unsw.venues;

import java.time.LocalDate;
import java.util.*;

public class Request {
    private String requestID;
    private LocalDate startDate;
    private LocalDate endDate;
    private String venueID;
    private List<String> roomIDs;



    public Request(String requestID, LocalDate startDate, LocalDate endDate, String venueID, List<String> roomIDs) {
        this.requestID = requestID;
		this.startDate = startDate;
		this.endDate = endDate;
		this.venueID = venueID;
		this.roomIDs = roomIDs;
    }

    public String getRequestID() {
        return requestID;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getVenueID() {
        return venueID;
    }

    public List<String> getRoomIDs() {
        return roomIDs;
    }

    public boolean RoomIDexist(String ID) {
        return this.getRoomIDs().contains(ID);
    }
}