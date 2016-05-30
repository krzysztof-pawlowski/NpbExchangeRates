package pl.parser.nbp.downloader.mapper;

import pl.parser.nbp.downloader.model.TablePosition;
import pl.parser.nbp.dto.CurrencyCode;
import pl.parser.nbp.dto.CurrencyRates;

/**
 * Mapper from @TablePosition to @CurrencyRates
 *
 * Created by Krzysztof Pawlowski on 29/05/16.
 */
public class TablePositionMapper {

    /**
     * Maps @TablePosition to @CurrencyRates
     * @param tablePosition table position to be mapped
     * @return
     */
    public static CurrencyRates map(TablePosition tablePosition) {
        if (tablePosition == null) {
            return null;
        }
        return new CurrencyRates()
            .setCurrencyCode(CurrencyCode.valueOf(tablePosition.getCurrencyCode()))
            .setBuyPrice(tablePosition.getBuyPrice())
            .setSellPrice(tablePosition.getSellPrice());
    }
}
