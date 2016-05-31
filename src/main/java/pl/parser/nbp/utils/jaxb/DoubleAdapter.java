package pl.parser.nbp.utils.jaxb;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Double adapter for JAXB. As the server provides the double in Polish notation (comma instead of
 * the dot) there is a need to handle those fields by non-standard double adapter.
 */
public class DoubleAdapter extends XmlAdapter<String, Double> {
    @Override public Double unmarshal(String v) throws Exception {
        if (v == null) {
            return null;
        }
        return Double.parseDouble(v.replace(',', '.'));
    }

    @Override public String marshal(Double v) throws Exception {
        if (v == null) {
            return null;
        }
        return v.toString().replace('.', ',');
    }
}
