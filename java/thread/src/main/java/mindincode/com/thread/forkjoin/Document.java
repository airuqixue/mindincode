package mindincode.com.thread.forkjoin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by madic on 16-12-14.
 */
public class Document {
    private final List<String> lines;

    Document(List<String> lines){
        this.lines = lines;
    }

    List<String> getLines() {
        return this.lines;
    }

    static Document fromFile(File file) throws IOException {
        List<String> lines = new LinkedList<String>();
        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line = reader.readLine();
            while(line != null){
                lines.add(line);
                line = reader.readLine();
            }
        }
        return new Document(lines);
    }
}
