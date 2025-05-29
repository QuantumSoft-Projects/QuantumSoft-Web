//form repository
package com.QuantomSoft.Repository;

import com.QuantomSoft.Entity.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormRepository extends JpaRepository<Form,Long> {

}

