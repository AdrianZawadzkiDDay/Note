package note;

import java.time.LocalDateTime;

public class Note implements Entity{
    private Integer id;
    private String title;
    private String content;
    private LocalDateTime modificationDate;

    public Note(Integer id, String title, String content, LocalDateTime modificationDate) {
        this.id = this.id;
        this.title = title;
        this.content = content;
        this.modificationDate = this.modificationDate;
    }

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
        this.modificationDate = LocalDateTime.now();
    }

    public Note(Note note) {
        this.id = note.id;
        this.title = note.title;
        this.content = note.content;
        this.modificationDate = note.modificationDate;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getModificationDate() { return modificationDate; }

    public void setModificationDate(LocalDateTime modificationDate) {
        this.modificationDate = modificationDate;
    }


    public void updateModificationDate(){this.modificationDate = LocalDateTime.now();}

    @Override
    public String toString() {
        return  title + " " + modificationDate;
    }

    @Override
    public Integer getId() { return id; }

    @Override
    public void setId(int id) { this.id = id; }
}
