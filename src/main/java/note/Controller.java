package note;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDateTime;

public class Controller {
    @FXML
    private ListView<Note> listView;
    @FXML
    private TextField titleTextField;
    @FXML
    private TextArea contentTextArea;
    @FXML
    private Label modificationDateLabel;
    @FXML
    private Button saveButton;
    private NoteRepository noteRepository;

    public void initialize() {
        noteRepository = new NoteRepository("note.txt");
        listView.getItems().addAll(noteRepository.getAll());
        listView.getSelectionModel()
                .selectedItemProperty() //zwraca obiekt reprezentujacy własciwość wybranego obiektu na liście
                .addListener((observable, oldValue, newValue) -> onSelectedNoteChange(newValue));
    }

    private void onSelectedNoteChange(Note selectedNote) {
        if (selectedNote != null) {
            displayNote(selectedNote);
        } else {
            resetNote();
        }
    }

    private void displayNote(Note note) {
        titleTextField.setText(note.getTitle());
        contentTextArea.setText(note.getContent());
        modificationDateLabel.setVisible(true);
        updateModificationDateLabelText(note.getModificationDate());
        saveButton.setText("Save");
    }

    private void updateModificationDateLabelText(LocalDateTime modificationDate) {
        modificationDateLabel.setText("Modification date: " + modificationDate);
    }

    private void resetNote() {
        titleTextField.clear();
        contentTextArea.clear();
        modificationDateLabel.setVisible(false);
        saveButton.setText("Add");
    }

    public void addNote() {
        String title = titleTextField.getText();
        String content = contentTextArea.getText();
        Note note = new Note(title, content);
        listView.getItems().add(note);
        listView.getSelectionModel().selectLast();
        noteRepository.add(note);
    }

    public void onSaveButtonClick() {
        Note note = listView.getSelectionModel()
                .getSelectedItem();
        if (note == null) {
            addNote();
        } else {
            editNote(note);
        }
    }

    private void editNote(Note note) {
        note.setTitle(titleTextField.getText());
        note.setContent(contentTextArea.getText());
        note.updateModificationDate();
        updateModificationDateLabelText(note.getModificationDate());
        listView.refresh();
        noteRepository.update(note);
    }

    public void onRemoveButtonClick() {
        Note note = listView.getSelectionModel()
                .getSelectedItem();
        if (note != null){
            listView.getItems().remove(note);
            noteRepository.delete(note.getId());
        }
    }

    public void onCreateNewButtonClick() {
        listView.getSelectionModel()
                .clearSelection();
    }

}
