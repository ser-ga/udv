package ru.udvybor.util;

import java.util.ArrayList;
import java.util.List;

public class DocumentUtil {

    private DocumentUtil(){}

    public static <T> List<List<T>> chunkify(List<T> list, int chunkSize){
        List<List<T>> chunks = new ArrayList<>();

        for (int i = 0; i < list.size(); i += chunkSize) {
            List<T> chunk = new ArrayList<>(list.subList(i, Math.min(list.size(), i + chunkSize)));
            chunks.add(chunk);
        }
        return chunks;
    }
}
