package note;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractRepository<T extends Entity> {
    private List<T> entities;
    private Path path;

    public AbstractRepository(String fileName) {
        path = Paths.get(fileName);
        List<String> fileLines;

        try {
            fileLines = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException("Błąd odczytu danych");
        }

        entities = fileLines.stream()
                .map(fileLine -> createEntityFromFileLine(fileLine))
                .collect(Collectors.toList());
    }

    public List<T> getAll() {
        return entities.stream()
                .map(entity -> copy(entity))
                .collect(Collectors.toList());
    }

    public T getById(int id) {
        return entities.stream()
                .filter(entity -> entity.getId() == id)
                .map(entity -> copy(entity))  //kopiuje swojego pracownika
                .findFirst()
                .orElse(null);
    }


    public void add(T entity) {
        entity.setId(getNextId());
        entities.add(entity);
        saveEntitiesToFile();
    }

    public void delete(int id){
        entities.stream()
                .filter(ent -> ent.getId() == id)
                .findFirst()
                .ifPresent(entity -> {
                    entities.remove(entity);
                    saveEntitiesToFile();
                });
    }

    public void update(T entity) {
        entities.stream()
                .filter(ent -> ent.getId() == entity.getId())
                .forEach(entityToBeUpdated -> {
                    updateProperties(entityToBeUpdated, entity);
                    saveEntitiesToFile();
                });
    }


    private void saveEntitiesToFile() {
        List<String> fileLines = entities.stream()
                .map(entity -> createFileLineFromEntity(entity))
                .collect(Collectors.toList());
        try {
            Files.write(path, fileLines);
        } catch (IOException e) {
            throw new RuntimeException("Nieudany zapis do pliku");
        }
    }


    private int getNextId() {
        return entities.stream()
                .mapToInt(employee -> employee.getId())
                .max()
                .orElse(0) + 1;
    }

    protected abstract void updateProperties(T entityToBeUpdated, T entity);

    protected abstract String createFileLineFromEntity(T entity);

    protected abstract T copy(T entity);

    protected abstract T createEntityFromFileLine(String fileLine);



}
