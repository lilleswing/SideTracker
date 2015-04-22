package edu.gtech.sidetracker.web.fhir;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.util.HashSet;
import java.util.Set;

@Singleton
public class MedicationParser {

    @Inject
    public MedicationParser() {
    }

    public Set<String> parse(final String data) {
        try {
            final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            final Document doc = documentBuilder.parse(new ByteArrayInputStream(data.getBytes()));
            final NodeList nodeList = doc.getElementsByTagName("entry");
            final Set<String> medicineNames = new HashSet<>();
            for (int i = 0; i < nodeList.getLength(); i++) {
                medicineNames.addAll(getNameFromEntry(nodeList.item(i)));
            }
            return medicineNames;
        } catch (final Exception e) {
            return new HashSet<>();
        }
    }

    private Set<String> getNameFromEntry(final Node entryNode) {
        if (entryNode.getNodeType() != Node.ELEMENT_NODE) {
            return new HashSet<>();
        }
        try {
            final Set<String> medicineNames = new HashSet<>();
            final Element entryElement = (Element) entryNode;
            final NodeList medicationNodeList = entryElement.getElementsByTagName("medication");
            for (int j = 0; j < medicationNodeList.getLength(); j++) {
                final Node medicationNode = medicationNodeList.item(j);
                if (medicationNode.getNodeType() != Node.ELEMENT_NODE) {
                    continue;
                }
                final Element medicationElement = (Element) (medicationNode);
                final NodeList displayNodeList = medicationElement.getElementsByTagName("display");
                for (int k = 0; k < displayNodeList.getLength(); k++) {
                    final Node displayNode = displayNodeList.item(k);
                    if (displayNode.getNodeType() != Node.ELEMENT_NODE) {
                        continue;
                    }
                    final Element displayElement = (Element) (displayNode);
                    final String displayValue = displayElement.getAttribute("value");
                    medicineNames.add(displayValue);
                }
            }
            return medicineNames;
        } catch (Exception e) {
            return new HashSet<>();
        }
    }
}
