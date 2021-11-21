package guru.springframework.rabbit.rabbitstockquoteservice.sender;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.rabbit.rabbitstockquoteservice.config.RabbitConfig;
import guru.springframework.rabbit.rabbitstockquoteservice.model.Quote;
import guru.springframework.rabbit.rabbitstockquoteservice.services.QuoteGeneratorService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.OutboundMessage;
import reactor.rabbitmq.Sender;

@Component
@RequiredArgsConstructor
public class QuoteSender {

    private final ObjectMapper objectMapper ;
    private final Sender sender;

    @SneakyThrows
    public Mono<Void> sendQuoteMessage(Quote quote){

        byte[] jsonBytes = objectMapper.writeValueAsBytes(quote);
        return sender.send(Mono.just(new OutboundMessage("", RabbitConfig.QUEUE, jsonBytes)));
    }

}
