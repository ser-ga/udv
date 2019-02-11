package ru.udvybor.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.udvybor.model.Document;
import ru.udvybor.repository.DocumentRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Path("/documents")
public class DocumentRestService {

    @Autowired
    DocumentRepository documentRepository;

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_HTML})
    @Transactional
    public Response createPodcast(Document document) {
        Document created = documentRepository.create(document);
        return Response.status(201).entity(created).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Document> getCategories(@PathParam("id") Integer id) {
        return documentRepository.getAllByParentId(id);
    }

}
