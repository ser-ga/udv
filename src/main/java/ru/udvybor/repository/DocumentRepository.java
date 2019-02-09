package ru.udvybor.repository;

import ru.udvybor.model.Document;

import java.util.List;

public interface DocumentRepository {

    List<Document> getMenu(int parent);

    List<Document> getAllByParentId(int parent);

    Document getByUri(String uri);

    List<Document> getTop10ByPublishedDate();

}
