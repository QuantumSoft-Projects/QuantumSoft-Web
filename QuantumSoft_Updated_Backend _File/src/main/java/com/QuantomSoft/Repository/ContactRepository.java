package com.QuantomSoft.Repository;

import com.QuantomSoft.Entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact,Long> {
}
