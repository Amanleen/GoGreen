package model;

public class Notification {
    private GreenEntry postNote;
    private Event eventNote;
    private String notificationMessage;
    private String notificationDate;

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

