package ru.udvybor.repository.jdbc;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.udvybor.model.Document;
import ru.udvybor.repository.DocumentRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JdbcDocumentRepository implements DocumentRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcDocumentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Document> getMenu(int parent) {
        return jdbcTemplate.query(
                "select site_content.*, images.path as path from site_content left join images on site_content.id=images.contentid WHERE parent=? AND menutitle IS NOT NULL ORDER BY uri ASC", new DocumentMapper(), parent);
    }

    @Override
    public List<Document> getAllByParentId(int parent) {
        return jdbcTemplate.query(
                "select site_content.*, images.path as path from site_content left join images on site_content.id=images.contentid WHERE parent=? ORDER BY uri ASC", new DocumentMapper(), parent);
    }

    @Override
    public Document getByUri(String uri) {
        List<Document> users = jdbcTemplate.query("select site_content.*, images.path as path from site_content left join images on site_content.id=images.contentid WHERE uri=?", new DocumentMapper(), uri);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public List<Document> getTop10ByPublishedDate() {
        return jdbcTemplate.query("select site_content.*, images.path as path from site_content left join images on site_content.id=images.contentid WHERE images.path IS NOT NULL ORDER BY site_content.createdon DESC LIMIT 9", new DocumentMapper());
    }

    @Override
    public Document create(Document document) {
        // TODO implement this method
        return new Document();
    }

    private class DocumentMapper implements RowMapper<Document> {
        @Override
        public Document mapRow(ResultSet rs, int rowNum) throws SQLException {
            Document document = new Document();
            document.setId(rs.getInt("id"));
            document.setPageTitle(rs.getString("pagetitle"));
            document.setIntroText(rs.getString("introtext"));
            document.setParent(rs.getInt("parent"));
            document.setUri(rs.getString("uri"));
            document.setContent(rs.getString("content"));
            document.setImagePath(rs.getString("path"));
            return document;
        }
    }
}