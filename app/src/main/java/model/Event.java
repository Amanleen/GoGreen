package model;

/**
 * @author Tejal Shah
 *
 */
public class Event {

    private int eventId;
    private String eventTitle;
    private String eventDescription;
    private String eventLocation;
    private String eventDate;
    private String eventStartTime;
    private String eventEndTime;
    private int eventHostedById;
    private int interestAreaId;

    public int getEventId() {
        return eventId;
    }
    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
    public String getEventTitle() {
        return eventTitle;
    }
    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }
    public String getEventDescription() {
        return eventDescription;
    }
    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }
    public String getEventLocation() {
        return eventLocation;
    }
    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }
    public String getEventDate() {
        return eventDate;
    }
    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }
    public String getEventStartTime() {
        return eventStartTime;
    }
    public void setEventStartTime(String eventStartTime) {
        this.eventStartTime = eventStartTime;
    }
    public String getEventEndTime() {
        return eventEndTime;
    }
    public void setEventEndTime(String eventEndTime) {
        this.eventEndTime = eventEndTime;
    }
    public int getEventHostedById() {
        return eventHostedById;
    }
    public void setEventHostedById(int eventHostedById) {
        this.eventHostedById = eventHostedById;
    }
    public int getInterestAreaId() {
        return interestAreaId;
    }
    public void setInterestAreaId(int interestAreaId) {
        this.interestAreaId = interestAreaId;
    }
}

