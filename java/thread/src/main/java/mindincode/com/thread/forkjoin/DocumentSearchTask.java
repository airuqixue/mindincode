package mindincode.com.thread.forkjoin;

import java.util.concurrent.RecursiveTask;
import static mindincode.com.thread.forkjoin.WordCounter.*;

/**
 * Created by madic on 16-12-14.
 */
public class DocumentSearchTask extends RecursiveTask<Long> {

    private final Document document;
    private final String searchedWord;

    DocumentSearchTask(Document document, String searchedWord) {
        super();
        this.document = document;
        this.searchedWord = searchedWord;
    }

    @Override
    protected Long compute() {
        return occurrencesCount(document,searchedWord);
    }
}
