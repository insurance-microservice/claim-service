package test.avows.claim.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClaimDto {
    private Long claimId;
    private Long policyId;
    private String claimNumber;
    private String description;
    private String status;
    private Timestamp createdAt;
    private Timestamp approvedAt;
}
