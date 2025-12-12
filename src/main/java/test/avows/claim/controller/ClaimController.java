package test.avows.claim.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.avows.claim.common.ApiResponse;
import test.avows.claim.dto.ClaimDto;
import test.avows.claim.service.ClaimService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/claim")
@RequiredArgsConstructor
public class ClaimController {

    private final ClaimService claimService;

    @GetMapping("/policy/{policyId}")
    public List<ClaimDto> getCustomerPolicies(@PathVariable Long policyId) {
        return claimService.getClaimsByPolicyId(policyId);
    }

    @GetMapping
    public ClaimDto getClaim(@PathVariable Long claimId) {
        return claimService.getClaim(claimId);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createClaim(@RequestBody ClaimDto param) {
        claimService.createClaim(param);
        return ResponseEntity.ok().body(
                new ApiResponse(
                        true,
                        "successfully create claim",
                        null
                )
        );
    }

    @PutMapping("approve/{claimId}")
    public ResponseEntity<ApiResponse> approveClaim(@PathVariable Long claimId) {
        claimService.approveClaim(claimId);
        return ResponseEntity.ok().body(
                new ApiResponse(
                        true,
                        "successfully update claim",
                        null
                )
        );
    }
}
