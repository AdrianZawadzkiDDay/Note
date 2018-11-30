package note;

import java.time.LocalDateTime;

public class NoteRepository extends AbstractRepository<Note> {

    public NoteRepository(String fileName) {
        super(fileName);
    }

    @Override
    protected void updateProperties(Note entityToBeUpdated, Note entity) {
        entityToBeUpdated.setTitle(entity.getTitle());
        entityToBeUpdated.setContent(entity.getContent());
        entityToBeUpdated.setModificationDate(entity.getModificationDate());
    }

    @Override
    protected String createFileLineFromEntity(Note entity) {
        return entity.getId() + "," + entity.getTitle()
                + "," + entity.getContent() + "," + entity.getModificationDate();
    }

    @Override
    protected Note copy(Note entity) {
        return new Note(entity);
    }

    @Override
    protected Note createEntityFromFileLine(String fileLine) {
        String[] fileLineParts = fileLine.split(",");
        int id = Integer.parseInt(fileLineParts[0]);
        String title = fileLineParts[1];
        String content = fileLineParts[2];
        LocalDateTime modificationDate = LocalDateTime.parse(fileLineParts[3]);
        return new Note(id, title, content, modificationDate);
    }
}
