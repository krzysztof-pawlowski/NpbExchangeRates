package pl.parser.nbp.utils.jaxb;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Created by Krzysztof Pawlowski on 29/05/16.
 */
public class DoubleAdapter extends XmlAdapter<String, Double> {
    @Override public Double unmarshal(String v) throws Exception {
        return Double.parseDouble(v.replace(',', '.'));
    }

    @Override public String marshal(Double v) throws Exception {
        return v.toString().replace('.', ',');
    }
}
