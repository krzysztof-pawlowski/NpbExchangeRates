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
 * Class representing the table from NBP rates server
 */
@XmlType
@XmlRootElement(name = "tabela_kursow")
public class Table {

    private String type;
    private String tableNumber;
    private LocalDate quoteDate;
    private LocalDate publicationDate;
    private List<TablePosition> positions;

    /**
     * Type of the table.
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the table.
     * @param type type
     */
    @XmlAttribute( name = "typ", required = true )
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the number of the table.
     * @return number of the table
     */
    public String getTableNumber() {
        return tableNumber;
    }

    /**
     * Sets the number of the table.
     * @param tableNumber number of the table
     */
    @XmlElement( name = "numer_tabeli" )
    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }

    /**
     * Gets the date of quote.
     * @return date of quote
     */
    public LocalDate getQuoteDate() {
        return quoteDate;
    }

    /**
     * Sets the date of quote
     * @param quoteDate date of quote
     */
    @XmlElement( name = "data_notowania" )
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    public void setQuoteDate(LocalDate quoteDate) {
        this.quoteDate = quoteDate;
    }

    /**
     * Gets the publication date.
     * @return publication date
     */
    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    /**
     * Sets the publication date.
     * @param publicationDate publication date
     */
    @XmlElement( name = "data_publikacji" )
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    /**
     * Gets the table positions.
     * @return table positions
     */
    public List<TablePosition> getPositions() {
        return positions;
    }

    /**
     * Sets the table positions.
     * @param positions table positionss
     */
    @XmlElement( name = "pozycja" )
    public void setPositions(List<TablePosition> positions) {
        this.positions = positions;
    }
}
