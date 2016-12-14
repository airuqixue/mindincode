package mindincode.com.thread.forkjoin;

import java.io.File;
import java.io.IOException;

/**
 * Created by madic on 16-12-14.
 */
public class MainTest {
    public static void main(String []args) throws IOException {
        WordCounter wordCounter = new WordCounter();
        Folder folder = Folder.fromDirectory(new File(args[0]));
        if(args.length>3&&"fork".equals(args[2])){
            long start = System.nanoTime();
            System.out.println(wordCounter.countOccurrencesInParallel(folder,args[1]));
            long end = System.nanoTime();
            double time = (double) (end - start) / 1000000.0;
            System.out.println("elapsed time:"+time);
        }else{
            long start = System.nanoTime();
            System.out.println(wordCounter.countOccurencesOnSingleThread(folder,args[1]));
            long end = System.nanoTime();
            double time = (double) (end - start) / 1000000.0;
            System.out.println("elapsed time:"+time);
        }

    }
}
