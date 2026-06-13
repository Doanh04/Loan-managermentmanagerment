package com.LoanManagerment.Gateway.repository;

import com.LoanManagerment.Gateway.dto.request.IntrospectRequest;
import com.LoanManagerment.Gateway.dto.response.ApiResponse;
import com.LoanManagerment.Gateway.dto.response.IntrospectResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;

public interface IdentityClient {
    @PostExchange(url = "/auth/intro-spect", contentType = MediaType.APPLICATION_JSON_VALUE)
    Mono<ApiResponse<IntrospectResponse>> introspect(@RequestBody IntrospectRequest introspectRequest);
}
