package pl.parser.nbp.downloader;

import pl.parser.nbp.downloader.mapper.TablePositionMapper;
import pl.parser.nbp.downloader.model.Table;
import pl.parser.nbp.downloader.model.TablePosition;
import pl.parser.nbp.dto.CurrencyCode;
import pl.parser.nbp.dto.CurrencyRates;
import rx.Observable;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by Krzysztof Pawlowski on 28/05/16.
 */
public class RatesDownloader {

    public static final String UTF8_BOM = "\uFEFF";

    static final String RATES_FILE_URL_PREFIX = "www.nbp.pl";

    private AsyncHttpClient asyncHttpClient;
    private RatesFilenamesProvider ratesFilenamesProvider;


    private static final Unmarshaller tableXmlbUnmarshaller = initUnmarshaller();

    public RatesDownloader() {
        this(new AsyncHttpClient(RATES_FILE_URL_PREFIX),
            new RatesFilenamesProvider());
    }

    public RatesDownloader(AsyncHttpClient asyncHttpClient, RatesFilenamesProvider ratesFilenamesProvider) {
        this.asyncHttpClient = asyncHttpClient;
        this.ratesFilenamesProvider = ratesFilenamesProvider;
    }

    public Observable<List<CurrencyRates>> getCurrencyRates(LocalDate startDate, LocalDate endDate, CurrencyCode code,
        TableType tableType) {

        return ratesFilenamesProvider.getRatesFilenames(startDate, endDate, tableType)
            .flatMapIterable(filenames -> filenames)
            .flatMap(filename -> asyncHttpClient.performGetRequest("/kursy/xml/" + filename + ".xml"))
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
        } catch (JAXBException e) {
            throw new RuntimeException("Could not init JAXB context.");
        }
    }
}
