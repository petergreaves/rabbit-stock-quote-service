package guru.springframework.rabbit.rabbitstockquoteservice.runner;

import guru.springframework.rabbit.rabbitstockquoteservice.sender.QuoteSender;
import guru.springframework.rabbit.rabbitstockquoteservice.services.QuoteGeneratorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
@Slf4j
public class QuoteRunner implements CommandLineRunner {

    private final QuoteSender quoteSender;
    private final QuoteGeneratorService quoteGeneratorService;

    @Override
    public void run(String... args) throws Exception {

        quoteGeneratorService.fetchQuoteStream(Duration.ofMillis(100))
                .take(25)
                .log("Got a quote")
                .flatMap(quoteSender::sendQuoteMessage)
                .subscribe(mono -> {
                            log.debug("Quote sent!");
                        }, throwable -> {
                            log.error("Some error", throwable);
                        }
                        , () -> {
                            log.debug("All Done!");
                        });
    }
}
