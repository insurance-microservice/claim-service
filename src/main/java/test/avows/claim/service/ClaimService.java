package test.avows.claim.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import test.avows.claim.client.PolicyClient;
import test.avows.claim.dto.ClaimDto;
import test.avows.claim.entity.Claim;
import test.avows.claim.exception.ApiException;
import test.avows.claim.repository.ClaimRepository;
import tools.jackson.databind.JsonNode;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClaimService {

    private final ClaimRepository claimRepository;
    private final PolicyClient policyClient;

    public List<ClaimDto> getClaimsByPolicyId(Long policyId) {
        return claimRepository.findByPolicyId(policyId).stream()
                .map(claim -> ClaimDto.builder()
                        .claimId(claim.getClaimId())
                        .policyId(claim.getPolicyId())
                        .claimNumber(claim.getClaimNumber())
                        .description(claim.getDescription())
                        .status(claim.getStatus())
                        .createdAt(claim.getCreatedAt())
                        .approvedAt(claim.getApprovedAt())
                        .build()
                ).toList();
    }

    public ClaimDto getClaim(Long claimId) {
        return claimRepository.findById(claimId)
                .map(claim -> ClaimDto.builder()
                        .claimId(claim.getClaimId())
                        .policyId(claim.getPolicyId())
                        .claimNumber(claim.getClaimNumber())
                        .description(claim.getDescription())
                        .status(claim.getStatus())
                        .createdAt(claim.getCreatedAt())
                        .approvedAt(claim.getApprovedAt())
                        .build()
                ).orElseThrow(() -> new RuntimeException("no data found for claim id " + claimId));
    }

    public void createClaim(ClaimDto param) {
        JsonNode policyData = policyClient.getPolicyByPolicyId(param.getPolicyId());
        String status = policyData.get("status").asText();
        if (!"ACTIVE".equals(status)) {
            throw new ApiException(
                    400,
                    "Inactive Policy",
                    "cannot create claim for inactive policy id " + param.getPolicyId()
            );
        }

        claimRepository.save(Claim.builder()
                .policyId(param.getPolicyId())
                .claimNumber(UUID.randomUUID().toString())
                .description(param.getDescription())
                .status("PENDING")
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .build()
        );
    }

    public void approveClaim(Long claimId) {
        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new RuntimeException("no data found for claim id " + claimId));

        if (!"PENDING".equals(claim.getStatus())) {
            throw new ApiException(
                    400,
                    "Unclaimable Policy",
                    "cannot approve claim with policy id " + claim.getPolicyId()
            );
        }

        claim.setStatus("APPROVED");
        claim.setApprovedAt(new Timestamp(System.currentTimeMillis()
        ));
        claimRepository.save(claim);
    }
}
