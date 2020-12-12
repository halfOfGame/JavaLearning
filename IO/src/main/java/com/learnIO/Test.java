package com.learnIO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Test {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("C:\\Users\\Dm20\\IdeaProjects\\untitled\\LearnJava\\LearnIO\\content.txt");
        Path path2 = Paths.get("content.txt");
        System.out.println(Files.size(path));
    }
}
