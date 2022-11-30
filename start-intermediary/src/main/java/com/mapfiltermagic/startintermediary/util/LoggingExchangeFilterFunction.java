package com.mapfiltermagic.startintermediary.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.http.client.reactive.ClientHttpRequestDecorator;
import org.springframework.lang.NonNull;
import org.springframework.util.StopWatch;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Math.min;
import static java.util.UUID.randomUUID;
import static net.logstash.logback.argument.StructuredArguments.v;

@Slf4j
@RequiredArgsConstructor
public class LoggingExchangeFilterFunction implements ExchangeFilterFunction {

  private static final int MAX_BYTES_LOGGED = 4_096;

  private final String externalSystem;

  @Override
  @NonNull
  public Mono<ClientResponse> filter(@NonNull ClientRequest request, @NonNull ExchangeFunction next) {
    // if (!log.isDebugEnabled()) {
    //   return next.exchange(request);
    // }

    var clientRequestId = randomUUID().toString();

    var requestLogged = new AtomicBoolean(false);
    var responseLogged = new AtomicBoolean(false);

    var capturedRequestBody = new StringBuilder();
    var capturedResponseBody = new StringBuilder();

    var stopWatch = new StopWatch();
    stopWatch.start();

    return next
      .exchange(ClientRequest.from(request).body(new BodyInserter<>() {

        @Override
        @NonNull
        public Mono<Void> insert(@NonNull ClientHttpRequest req, @NonNull Context context) {
          return request.body().insert(new ClientHttpRequestDecorator(req) {

            @Override
            @NonNull
            public Mono<Void> writeWith(@NonNull Publisher<? extends DataBuffer> body) {
              return super.writeWith(Flux.from(body).doOnNext(data -> capturedRequestBody.append(extractBytes(data)))); // number of bytes appended is maxed in real code
            }

          }, context);
        }
      }).build())
      .doOnNext(response -> {
          if (!requestLogged.getAndSet(true)) {
            log.info("| >>---> Outgoing {} request [{}]\n{} {}\n{}\n\nclientRequestBody: {}\n",
              v("externalSystem", externalSystem),
              v("clientRequestId", clientRequestId),
              v("clientRequestMethod", request.method()),
              v("clientRequestUrl", request.url()),
              v("clientRequestHeaders", request.headers()), // filtered in real code
              v("clientRequestBody", capturedRequestBody.toString()) // filtered in real code
            );
          }
        }
      )
      .doOnError(error -> {
        if (!requestLogged.getAndSet(true)) {
          log.info("| >>---> Outgoing {} request [{}]\n{} {}\n{}\n\nError: {}\n",
            v("externalSystem", externalSystem),
            v("clientRequestId", clientRequestId),
            v("clientRequestMethod", request.method()),
            v("clientRequestUrl", request.url()),
            v("clientRequestHeaders", request.headers()), // filtered in real code
            error.getMessage()
          );
        }
      })
      .map(response -> response.mutate().body(transformer -> transformer
          .doOnNext(body -> capturedResponseBody.append(extractBytes(body))) // number of bytes appended is maxed in real code
          .doOnTerminate(() -> {
            if (stopWatch.isRunning()) {
              stopWatch.stop();
            }
          })
          .doOnComplete(() -> {
            if (!responseLogged.getAndSet(true)) {
              log.info("| <---<< Response for outgoing {} request [{}] after {}ms\n{} {}\n{}\n\n{}\n",
                v("externalSystem", externalSystem),
                v("clientRequestId", clientRequestId),
                v("clientRequestExecutionTimeInMillis", stopWatch.getTotalTimeMillis()),
                v("clientResponseStatusCode", response.statusCode().value()),
                v("clientResponseStatusPhrase", response.statusCode().getReasonPhrase()),
                v("clientResponseHeaders", response.headers()), // filtered in real code
                v("clientResponseBody", capturedResponseBody.toString()) // filtered in real code
              );
            }
          })
          .doOnError(error -> {
              if (!responseLogged.getAndSet(true)) {
                log.info("| <---<< Error parsing response for outgoing {} request [{}] after {}ms\n{}",
                  v("externalSystem", externalSystem),
                  v("clientRequestId", clientRequestId),
                  v("clientRequestExecutionTimeInMillis", stopWatch.getTotalTimeMillis()),
                  v("clientErrorMessage", error.getMessage())
                );
              }
            }
          )
        ).build()
      );
  }

  private static String extractBytes(DataBuffer data) {
    int currentReadPosition = data.readPosition();
    var numberOfBytesLogged = min(data.readableByteCount(), MAX_BYTES_LOGGED);
    var bytes = new byte[numberOfBytesLogged];
    data.read(bytes, 0, numberOfBytesLogged);
    data.readPosition(currentReadPosition);
    return new String(bytes);
  }
}