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

import java.util.List;
import java.util.StringJoiner;

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
        Document document = documentRepository.getByUri(joiner.toString());
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
        Document document = documentRepository.getByUri(joiner.toString());
        document.setContent(Jsoup.parse(document.getContent()).text());
        model.addAttribute("document", document);
        return "shop";
    }
}
