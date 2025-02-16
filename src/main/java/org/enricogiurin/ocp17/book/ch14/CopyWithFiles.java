package org.enricogiurin.ocp17.book.ch14;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class CopyWithFiles {

  public static void main(String[] args) throws IOException {
    new CopyWithFiles().readLazy();
  }

  void copyByPath() throws IOException {
    Path pom = Path.of("pom.xml");
    Path dest = Path.of("/tmp/pom-copied.xml");
    String contents = Files.readString(pom);
    Files.writeString(dest, contents);
  }

  void copyByLines() throws IOException {
    Path pom = Path.of("pom.xml");
    Path dest = Path.of("/tmp/" + System.currentTimeMillis() + "_pom.xml");
    List<String> listOfLines = Files.readAllLines(pom);
    Files.write(dest, listOfLines);
  }

  //to avoid OutOfMemoryError
  void readLazy() throws IOException {
    Path pom = Path.of("pom.xml");
    Path dest = Path.of("/tmp/" + System.currentTimeMillis() + "_pom.xml");
    try (Stream<String> stream = Files.lines(pom)) {
      stream
          .peek(System.out::println)
          .forEach(s -> {
            try {
              Files.writeString(dest, s);
            } catch (IOException e) {
              throw new RuntimeException(e);
            }
          });
    }
  }

  void bufferedWithFiles() throws IOException {
    Path pom = Path.of("pom.xml");
    Path dest = Path.of("/tmp/" + System.currentTimeMillis() + "_pom.xml");
    try (var reader = Files.newBufferedReader(pom);
        var writer = Files.newBufferedWriter(dest)) {
      String line;
      while ((line = reader.readLine()) != null) {
        writer.write(line);
        writer.newLine();
      }
    }

  }


}
