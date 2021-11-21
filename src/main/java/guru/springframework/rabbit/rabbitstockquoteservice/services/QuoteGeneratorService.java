package guru.springframework.rabbit.rabbitstockquoteservice.services;


import guru.springframework.rabbit.rabbitstockquoteservice.model.Quote;
import reactor.core.publisher.Flux;

import java.time.Duration;

public interface QuoteGeneratorService {

    Flux<Quote> fetchQuoteStream(Duration duration);
}
