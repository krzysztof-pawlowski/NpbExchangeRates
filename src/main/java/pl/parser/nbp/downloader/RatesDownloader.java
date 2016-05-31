package pl.parser.nbp.downloader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.parser.nbp.downloader.mapper.TablePositionMapper;
import pl.parser.nbp.downloader.model.Table;
import pl.parser.nbp.downloader.model.TablePosition;
import pl.parser.nbp.dto.CurrencyCode;
import pl.parser.nbp.dto.CurrencyRates;
import pl.parser.nbp.http.AsyncHttpClient;
import rx.Observable;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.time.LocalDate;
import java.util.List;

/**
 * Class responsible for downloading the rates from the server.
 */
public class RatesDownloader {

    static final String PATH_PREFIX = "/kursy/xml/";
    static final String RATES_FILE_EXTENSION = ".xml";

    final static Logger logger = LoggerFactory.getLogger(RatesDownloader.class);

    private AsyncHttpClient asyncHttpClient;
    private RatesFilenamesProvider ratesFilenamesProvider;


    private static final Unmarshaller tableXmlbUnmarshaller = initUnmarshaller();

    /**
     * The constructor.
     * @param asyncHttpClient http client used to fetch the rates
     * @param ratesFilenamesProvider provider of rates filenames on the server
     */
    public RatesDownloader(AsyncHttpClient asyncHttpClient, RatesFilenamesProvider ratesFilenamesProvider) {
        this.asyncHttpClient = asyncHttpClient;
        this.ratesFilenamesProvider = ratesFilenamesProvider;
    }

    /**
     * Gets a list of currency rates.
     * @param startDate start date of rates period (inclusive)
     * @param endDate end date of rates period (inclusive)
     * @param code currency code
     * @param tableType rates table type
     * @return Observable of list of currency rates
     */
    public Observable<List<CurrencyRates>> getCurrencyRates(LocalDate startDate, LocalDate endDate, CurrencyCode code,
        TableType tableType) {

        return ratesFilenamesProvider.getRatesFilenames(startDate, endDate, tableType)
            .map(filenames -> {
                logger.debug("Fetching rates from table {} from {} to {}", tableType, startDate, endDate);
                return filenames;
            })
            .flatMapIterable(filenames -> filenames)
            .flatMap(filename -> asyncHttpClient.performGetRequest(PATH_PREFIX + filename + RATES_FILE_EXTENSION))
            .map(StringReader::new)
            .map(StreamSource::new)
            .map(this::unmarshallTable)
            .map(table -> {
                TablePosition position = table.getPositions().stream()
                    .filter(tablePosition -> code.code().equals(tablePosition.getCurrencyCode()))
                    .findFirst().get();
                return position == null ? null : TablePositionMapper.map(position)
                    .setPublicationDate(table.getPublicationDate());
            })
            .toList();

    }

    private Table unmarshallTable(StreamSource tableXml) {
        Table table;
        try {
            table = tableXmlbUnmarshaller.unmarshal(tableXml, Table.class).getValue();
        } catch (JAXBException ex) {
            throw new RuntimeException("Failed to parse xml.", ex);
        }
        return table;
    }

    private static Unmarshaller initUnmarshaller() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Table.class);
            return jaxbContext.createUnmarshaller();
        } catch (JAXBException ex) {
            throw new RuntimeException("Could not init JAXB context.", ex);
        }
    }
}
