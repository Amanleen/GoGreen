package model;

public class Notification {
    private GreenEntry postNote;
    private Event eventNote;
    private String notificationMessage;
    private String notificationDate;
    private int greenEntryId;
    private int eventId;

    public GreenEntry getPostNote() {
        return postNote;
    }
    public void setPostNote(GreenEntry postNote) {
        this.postNote = postNote;
    }

    public Event getEventNote() {
        return eventNote;
    }
    public void setEventNote(Event eventNote) {
        this.eventNote = eventNote;
    }

    public int getGreenEntryId() {
        return greenEntryId;
    }

    public void setGreenEntryId(int id) {
        this.greenEntryId = id;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int id) {
        this.eventId = id;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }
    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }
    public String getNotificationDate() {
        return notificationDate;
    }
    public void setNotificationDate(String notificationDate) {
        this.notificationDate = notificationDate;
    }

}

