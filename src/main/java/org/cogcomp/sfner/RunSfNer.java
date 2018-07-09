package org.cogcomp.sfner;

import edu.illinois.cs.cogcomp.annotation.AnnotatorException;
import edu.illinois.cs.cogcomp.core.datastructures.textannotation.Constituent;
import edu.illinois.cs.cogcomp.core.datastructures.textannotation.TextAnnotation;
import edu.illinois.cs.cogcomp.core.io.LineIO;
import edu.illinois.cs.cogcomp.core.utilities.SerializationHelper;
import edu.illinois.cs.cogcomp.core.utilities.configuration.ResourceManager;
import edu.illinois.cs.cogcomp.ner.NERAnnotator;
import edu.illinois.cs.cogcomp.ner.config.NerBaseConfigurator;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * use the models trained by this packages script to tag new data
 * make sure the jar is on the classpath
 *
 * @author mssammon
 */
public class RunSfNer {

    public static void main(String[] args) {
        String taJsonPath = "/shared/corpora/corporaWeb/lorelei/evaluation-2018/il9/processed/setS/ENG_NW_020408_20160220_H0040LS14";
        String configFile = "config/sf.config";
        TextAnnotation ta = null;
        try {
            ta = SerializationHelper.deserializeFromJson(LineIO.slurp(taJsonPath));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }

        ResourceManager rm = null;
        try {
            rm = new ResourceManager(configFile);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        String viewName = rm.getString(NerBaseConfigurator.VIEW_NAME);

        NERAnnotator annotator = null;
        annotator = new NERAnnotator(new NerBaseConfigurator().getConfig(rm), viewName);

        try {
            annotator.getView(ta);
        } catch (AnnotatorException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        if(!ta.hasView(viewName)) {
            System.err.println("AAAARGH no " + viewName + " view after annotation.");
            System.exit(-1);
        }
        List<Constituent> ners = ta.getView(viewName).getConstituents();

        if(!(ners.size() > 0)) {
            System.err.println("Why u no has NER constituents u ingrate?");
            System.exit(-1);
        }

        System.out.println("NERS: ");
        for (Constituent c : ners) {
            System.out.println(c.toString() + "; type: " + c.getLabel());
        }
    }
}
