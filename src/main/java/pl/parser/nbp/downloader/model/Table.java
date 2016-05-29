package pl.parser.nbp.downloader.model;

import pl.parser.nbp.utils.jaxb.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by Krzysztof Pawlowski on 29/05/16.
 */
@XmlType
@XmlRootElement(name = "tabela_kursow")
public class Table {

    private String type;
    private String tableNumber;
    private LocalDate quoteDate;
    private LocalDate publicationDate;
    private List<TablePosition> positions;

    public String getType() {
        return type;
    }

    @XmlAttribute( name = "typ", required = true )
    public void setType(String type) {
        this.type = type;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    @XmlElement( name = "numer_tabeli" )
    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }

    public LocalDate getQuoteDate() {
        return quoteDate;
    }

    @XmlElement( name = "data_notowania" )
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    public void setQuoteDate(LocalDate quoteDate) {
        this.quoteDate = quoteDate;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    @XmlElement( name = "data_publikacji" )
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public List<TablePosition> getPositions() {
        return positions;
    }

    @XmlElement( name = "pozycja" )
    public void setPositions(List<TablePosition> positions) {
        this.positions = positions;
    }
}
