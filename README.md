# NpbExchangeRates

The application fetches the currency rates from Polish National Bank (NBP) and calculates two metrics:
* average buy price
* standard deviation of sell price

## How to build
To build the project execute:
```
mvn clean package
```
As the result fatjar `nbp-exchange-rates-1.0-SNAPSHOT.jar` is generated in `target/` folder.

## How to run
To run the application execute:
```
java -jar target/nbp-exchange-rates-1.0-SNAPSHOT.jar <currency_code> <start_date> <end_date>
```
example:
```
java -jar target/nbp-exchange-rates-1.0-SNAPSHOT.jar EUR 2013-01-28 2013-01-31
```
