package dev.mskory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GraphTest {

    @Test
    void checkDuplicates_OK() {
        List<String> list = FileReader.read("src/test/resources/test.txt");
        Graph graph = new Graph(list);

        List<String> chain = Arrays.stream(graph.getLongestChain().trim().split(" ")).toList();
        Assertions.assertTrue(chain.stream().filter(n -> Collections.frequency(chain, n) > 1).findFirst().isEmpty());
    }
}