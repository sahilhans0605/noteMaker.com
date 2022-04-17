package com.sahilhans0605.notemakercom;

public class note {
    String note;
    String heading;
    String noteId;

    public note(String note, String heading, String noteId) {
        this.note = note;
        this.heading = heading;
        this.noteId = noteId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public note() {

    }
}