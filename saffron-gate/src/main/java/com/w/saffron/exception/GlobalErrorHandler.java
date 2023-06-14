package com.w.saffron.exception;

import cn.hutool.json.JSONUtil;
import com.w.saffron.common.R;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufMono;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalErrorHandler implements ErrorWebExceptionHandler {

    private static final Set<String> DISCONNECTED_CLIENT_EXCEPTIONS;

    static {
        Set<String> exceptions = new HashSet<>();
        exceptions.add("AbortedException");
        exceptions.add("ClientAbortException");
        exceptions.add("EOFException");
        exceptions.add("EofException");
        DISCONNECTED_CLIENT_EXCEPTIONS = Collections.unmodifiableSet(exceptions);
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable throwable) {
        try {
            if (exchange.getResponse().isCommitted() || isDisconnectedClientError(throwable)) {
                return Mono.error(throwable);
            }
            ServerHttpResponse response = exchange.getResponse();
            R<?> r = R.error();
            if (throwable instanceof ResponseStatusException re) {
                r.code(re.getStatusCode().value());
                r.msg(re.getReason());
            } else {
                r.code(500);
                r.msg(throwable.getMessage());
            }
            DataBuffer dataBuffer = response.bufferFactory()
                    .allocateBuffer().write(JSONUtil.toJsonStr(r).getBytes());
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return response.writeAndFlushWith(Mono.just(ByteBufMono.just(dataBuffer)));
        } catch (Exception e) {
            return Mono.error(throwable);
        }
    }

    private boolean isDisconnectedClientError(Throwable ex) {
        return DISCONNECTED_CLIENT_EXCEPTIONS.contains(ex.getClass().getSimpleName())
                || isDisconnectedClientErrorMessage(NestedExceptionUtils.getMostSpecificCause(ex).getMessage());
    }

    private boolean isDisconnectedClientErrorMessage(String message) {
        message = (message != null) ? message.toLowerCase() : "";
        return (message.contains("broken pipe") || message.contains("connection reset by peer"));
    }
}