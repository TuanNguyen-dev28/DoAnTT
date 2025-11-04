package ecommerce.project.repository;

import ecommerce.project.entity.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findAllByOrderByCreatedAtDesc();
    Page<Contact> findAllByOrderByCreatedAtDesc(Pageable pageable);
    long countByIsReadFalse();
}


