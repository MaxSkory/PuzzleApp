package dev.mskory;

import java.util.List;
import java.util.Scanner;
import dev.mskory.util.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class App {
    public static final Logger LOGGER = LoggerFactory.getLogger(App.class);
    public static final StopWatch stopWatch = new StopWatch();

    public static void main(String[] args) {
        Scanner scr = new Scanner(System.in);
        System.out.println("Enter path to the file or type \"q\" to exit.");
        String path = scr.nextLine();
        if (path.equals("q")) {
            System.exit(0);
        }
        List<String> lines = FileReader.read(path);
        Graph graph = new Graph(lines);
        stopWatch.start();
        String longestChain = graph.getLongestChain();
        LOGGER.info("Total searching time {} seconds", stopWatch.getSeconds());
        LOGGER.info("The longest chain contains {} elements", graph.getLongestChainLength());
        LOGGER.info("The very chain: {}", longestChain);
    }
}
