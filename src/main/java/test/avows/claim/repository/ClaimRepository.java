package test.avows.claim.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import test.avows.claim.entity.Claim;

import java.util.List;

@Repository
public interface ClaimRepository extends JpaRepository<@NonNull Claim, @NonNull Long> {
    List<Claim> findByPolicyId(Long policyId);
}
