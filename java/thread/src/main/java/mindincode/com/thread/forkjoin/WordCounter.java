package mindincode.com.thread.forkjoin;

import java.util.concurrent.ForkJoinPool;

/**
 * Created by madic on 16-12-14.
 */
public class WordCounter {
    static String[] wordsIn(String line){

        /**
         * //Punct is a kind of Predefined Character Class Name
         * 这段就是用非字符作为分隔符的意思
         */
        return line.trim().split("(\\s|\\p{Punct})+");
    }

   static Long occurrencesCount(Document document, String searchedWord) {
        long count = 0;
        for(String line:document.getLines()){
            for(String word:wordsIn(line)){
                if(searchedWord.equals(word)){
                    count = count + 1;
                }
            }
        }
        return count;
    }

    private final ForkJoinPool forkJoinPool = new ForkJoinPool();

    public Long countOccurrencesInParallel(Folder folder, String searchedWord){
        return forkJoinPool.invoke(new FolderSearchTask(folder, searchedWord));
    }

    public Long countOccurencesOnSingleThread(Folder folder, String searchedWord){
        long count = 0;
        for(Folder subFolder: folder.getSubFolders()){
            count = count + countOccurencesOnSingleThread(subFolder,searchedWord);
        }
        for(Document docuemnt:folder.getDocuments()){
            count = count + occurrencesCount(docuemnt,searchedWord);
        }
        return count;
    }

}
