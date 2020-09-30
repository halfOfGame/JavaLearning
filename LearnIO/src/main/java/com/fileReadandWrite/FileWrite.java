package com.fileReadandWrite;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author halfOfGame
 * @create 2020-03-17,17:53
 */
public class FileWrite {

    @Test
    public void Write() throws IOException {
        StringBuffer str = new StringBuffer("Hello world!!");
        FileWriter fileWriter = new FileWriter(new File("Files/file_1.txt"));
        fileWriter.write(str.toString());
        fileWriter.flush();
        fileWriter.close();
    }
}
