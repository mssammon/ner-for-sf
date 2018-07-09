package org.cogcomp.sfner;

import edu.illinois.cs.cogcomp.core.datastructures.textannotation.TextAnnotation;
import edu.illinois.cs.cogcomp.core.io.LineIO;
import edu.illinois.cs.cogcomp.core.utilities.SerializationHelper;
import edu.illinois.cs.cogcomp.nlp.corpusreaders.CoNLLNerReader;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAARRRRRRRRRRRRRRRRRRRRRRRGGGGGGHHHHHH
 *
 * @author ConanTheLibrarian
 */
public class ConvertJsonCorpusToConll {
    private static final String NAME = ConvertJsonCorpusToConll.class.getCanonicalName();

    public static void main(String[] args) throws Exception {

        if (args.length != 2) {
            System.err.println("USAGE: " + NAME + " nerTaJsonDir nerConllDir");
            System.exit(-1);
        }
        String inDir = args[0];
        String outDir = args[1];

        DirectoryStream<Path> inDirStream = Files.newDirectoryStream(Paths.get(inDir));

        Iterator<Path> idsIter = inDirStream.iterator();
//        List<Pair<String, TextAnnotation>> tas = new ArrayList<>();
        List<TextAnnotation> tas = new ArrayList<>();
        while(idsIter.hasNext()) {
            Path next = idsIter.next();
            if (Files.isDirectory(next))
                continue;
            String taStr = LineIO.slurp(next.toFile().getAbsolutePath());
            String fileName = next.toFile().getName();
//            tas.add(new Pair(fileName, SerializationHelper.deserializeFromJson(taStr)));
            tas.add(SerializationHelper.deserializeFromJson(taStr));
        }

        CoNLLNerReader.TaToConll(tas, outDir);
    }
}
