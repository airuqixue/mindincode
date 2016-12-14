package mindincode.com.thread.forkjoin;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 * Created by madic on 16-12-14.
 */
public class FolderSearchTask extends RecursiveTask<Long> {

    private final Folder folder;
    private final String searchedWord;

    FolderSearchTask(Folder folder, String searchedWord){
        super();
        this.folder = folder;
        this.searchedWord = searchedWord;
    }


    @Override
    protected Long compute() {
        long count = 0;
        List<RecursiveTask<Long>> forks = new LinkedList<>();
        for(Folder subFolder:folder.getSubFolders()){
            FolderSearchTask task = new FolderSearchTask(subFolder,searchedWord);
            forks.add(task);
            task.fork();
        }
        for(Document document:folder.getDocuments()){
            DocumentSearchTask task = new DocumentSearchTask(document,searchedWord);
            forks.add(task);
            task.fork();
        }
        for(RecursiveTask<Long> task:forks){
            count = count + task.join();
        }
        return count;
    }
}
