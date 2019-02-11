package ru.udvybor.web;

import org.jsoup.Jsoup;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import ru.udvybor.model.Document;
import ru.udvybor.repository.DocumentRepository;
import ru.udvybor.util.DocumentUtil;
import ru.udvybor.util.exception.NotFoundException;

import java.util.List;
import java.util.StringJoiner;

import static ru.udvybor.util.ValidationUtil.checkNotFound;

@Controller
public class RootController {

    private final DocumentRepository documentRepository;

    public RootController(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @GetMapping(value = "/")
    public String main(Model model) {
        List<Document> folders = documentRepository.getTop10ByPublishedDate();
        model.addAttribute("folders", folders);
        return "main";
    }

    @ModelAttribute("categories")
    public List<List<Document>> categories() {
        List<Document> folders = documentRepository.getMenu(2);
        return DocumentUtil.chunkify(folders, 10);
    }

    @GetMapping(value = "/online-shops/{category}")
    public String getCategory(@PathVariable("category") String category, Model model) {
        StringJoiner joiner = new StringJoiner("/");
        joiner.add("online-shops");
        joiner.add(category);
        Document document = checkNotFound(documentRepository.getByUri(joiner.toString()), category + " not found");
        model.addAttribute("document", document);
        model.addAttribute("childs", documentRepository.getAllByParentId(document.getId()));
        return "category";
    }

    @GetMapping(value = "/online-shops/{category}/{shop}")
    public String getShop(@PathVariable("category") String category,
                          @PathVariable("shop") String shop,
                          Model model) {
        StringJoiner joiner = new StringJoiner("/");
        joiner.add("online-shops");
        joiner.add(category);
        joiner.add(shop);
        Document document = checkNotFound(documentRepository.getByUri(joiner.toString()), shop + " not found");
        if (document == null) throw new NotFoundException(category + " not found");
        document.setContent(Jsoup.parse(document.getContent()).text());
        model.addAttribute("document", document);
        return "shop";
    }
}
