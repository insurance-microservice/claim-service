package test.avows.claim.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import tools.jackson.databind.JsonNode;

@Component
@RequiredArgsConstructor
public class PolicyClient {

    @Value("${services.policy-url}")
    private String policyServiceUrl;

    private final WebClient webClient;

    public JsonNode getPolicyByPolicyId(Long policyId) {
        return webClient.get()
                .uri(policyServiceUrl + "/api/v1/policy/{customerId}", policyId)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
    }
}
