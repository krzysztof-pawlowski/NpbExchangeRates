package pl.parser.nbp.downloader.mapper;

import org.junit.Test;
import pl.parser.nbp.downloader.model.TablePosition;
import pl.parser.nbp.dto.CurrencyCode;
import pl.parser.nbp.dto.CurrencyRates;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Krzysztof Pawlowski on 30/05/16.
 */
public class TablePositionMapperTest {

    @Test
    public void mapShouldMapFieldsFromTablePositionToCurrencyRates() {
        // GIVEN
        double buyPrice = 0.1;
        double sellPrice = 0.2;

        TablePosition tablePosition = new TablePosition()
            .setBuyPrice(buyPrice)
            .setSellPrice(sellPrice)
            .setCurrencyCode("USD");

        // WHEN
        CurrencyRates currencyRates = TablePositionMapper.map(tablePosition);

        // THEN
        assertThat(currencyRates.getBuyPrice()).isEqualTo(buyPrice);
        assertThat(currencyRates.getSellPrice()).isEqualTo(sellPrice);
        assertThat(currencyRates.getCurrencyCode()).isEqualTo(CurrencyCode.USD);
    }

    @Test
    public void mapNullWillReturnNull() {
        // WHEN
        CurrencyRates currencyRates = TablePositionMapper.map(null);

        // THEN
        assertThat(currencyRates).isNull();
    }
}
