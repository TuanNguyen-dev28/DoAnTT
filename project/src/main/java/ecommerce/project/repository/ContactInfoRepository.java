package ecommerce.project.repository;

import ecommerce.project.entity.ContactInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactInfoRepository extends JpaRepository<ContactInfo, Long> {
    Optional<ContactInfo> findFirstByOrderByIdAsc();
    
    @Modifying
    @Query("DELETE FROM ContactInfo WHERE id NOT IN (SELECT MIN(c.id) FROM ContactInfo c)")
    void deleteDuplicateRecords();
}

