package au.com.telstra.simcardactivator;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SimActivationRecordRepository extends JpaRepository<SimActivationRecord, Long> {
    Optional<SimActivationRecord> findByIccid(String iccid);}
