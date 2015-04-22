package edu.gtech.sidetracker.fhir;


import edu.gtech.sidetracker.web.fhir.MedicationParser;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import static junit.framework.Assert.assertEquals;

public class MedicationParserTest {

    private MedicationParser medicationParser;

    @Before
    public void init() {
        this.medicationParser = new MedicationParser();
    }

    @Test
    public void testParse() throws FileNotFoundException {
        final Set<String> answers = new HashSet<>();
        answers.add("Lisinopril");
        answers.add("Hydrochlorothiazide");
        answers.add("nifedipine");
        final ClassLoader classLoader = getClass().getClassLoader();
        final File xmlFile = new File(classLoader.getResource("sample.xml").getFile());
        final String data = new Scanner(xmlFile).useDelimiter("\\Z").next();
        final Set<String> medicineNames = medicationParser.parse(data);
        assertEquals(answers, medicineNames);
    }
}
