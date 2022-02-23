package com.tora;

import com.tora.utils.day14.MultithreadedFileSplitter;
import com.tora.utils.day14.ReadingFromMultipleFilesConcurrently;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        final String fileName = "C:\\Users\\fazec\\Desktop\\tora\\week2\\trilava-2021-07-05-POST.log\\trilava-2021-07-05-POST.log";
        final String outputFileName = "C:\\Users\\fazec\\Desktop\\tora\\week2\\trilava-2021-07-05-POST.log\\split";

//        FileUtils.findLinesWithText(fileName, "subject=_RV.INFO.SYSTEM.LISTEN.START.triblues", 100);
//        FileUtils.countNoOfOccurrences(fileName, "order new");
//        FileUtils.countLines(fileName);
//        FileUtils.mostFrequentUsedSubject(fileName);
//        FileUtils.mostFrequentWords(fileName);

        MultithreadedFileSplitter.phase0(fileName, outputFileName, 4);
        List<String> inputFiles = List.of(
            outputFileName + "0",
            outputFileName + "1",
            outputFileName + "2",
            outputFileName + "3"
        );
//        ReadingFromMultipleFilesSequential.phase1(inputFiles, "order new");
//        ReadingFromMultipleFilesConcurrently.phase2(inputFiles, "order new");

    }
}
