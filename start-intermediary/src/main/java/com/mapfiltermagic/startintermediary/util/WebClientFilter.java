package com.mapfiltermagic.startintermediary.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.server.ResponseStatusException;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * Defines the WebClient filtering operations to handle logging.
 */
@Slf4j
public final class WebClientFilter {

    private WebClientFilter() {
        throw new IllegalStateException("Cannot be instantiated");
    }

    /**
     *
     * @return
     */
	public static ExchangeFilterFunction logRequest() {
		return ExchangeFilterFunction.ofRequestProcessor(request -> {
			logMethodAndUrl(request);
			logHeaders(request);
            logCookies(request);
			
			return Mono.just(request);
		});
	}
	
    /**
     *
     * @return
     */
	public static ExchangeFilterFunction logResponse() {
		return ExchangeFilterFunction.ofResponseProcessor(response -> {
			logStatus(response);
			logHeaders(response);
			logCookies(response);

			return logBody(response);
		});
	}

	private static void logStatus(ClientResponse response) {
		HttpStatus status = response.statusCode();
		log.debug("Returned staus code {} ({})", status.value(), status.getReasonPhrase());
	}
	
	private static Mono<ClientResponse> logBody(ClientResponse response) {
		if (response.statusCode() != null && (response.statusCode().is4xxClientError() || response.statusCode().is5xxServerError())) {
			return response.bodyToMono(String.class)
					.flatMap(body -> {
						log.debug("Body is {}", body);						
                        return Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                                response.rawStatusCode() + " - " + body.toString()));                    
					});
		} else {
			log.debug("Request was successful");			
			
			return Mono.just(response);
		}
	}
	
	private static void logHeaders(ClientResponse response) {
		response.headers().asHttpHeaders().forEach((name, values) -> {
			values.forEach(value -> {
				logNameAndValuePair(name, value);
			});
		});
	}
	
	private static void logHeaders(ClientRequest request) {
		request.headers().forEach((name, values) -> {
			values.forEach(value -> {
				logNameAndValuePair(name, value);
			});
		});
	}

	private static void logCookies(ClientResponse response) {
		response.cookies().forEach((name, values) -> {
			values.forEach(value -> {
				logNameAndValuePair(name, value.getValue());
			});
		});
	}
	
	private static void logCookies(ClientRequest request) {
		request.cookies().forEach((name, values) -> {
			values.forEach(value -> {
				logNameAndValuePair(name, value);
			});
		});
	}
	
	private static void logNameAndValuePair(String name, String value) {
		log.debug("{}={}", name, value);
	}

	private static void logMethodAndUrl(ClientRequest request) {
		StringBuilder sb = new StringBuilder();
		sb.append(request.method().name());
		sb.append(" to ");
		sb.append(request.url());
		
		log.debug(sb.toString());
	}

}
