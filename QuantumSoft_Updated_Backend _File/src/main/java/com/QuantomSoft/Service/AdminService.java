package com.QuantomSoft.Service;


import com.QuantomSoft.Entity.Admin;
import com.QuantomSoft.Entity.Form;
import com.QuantomSoft.Entity.Job;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AdminService {

    String registerAdmin(Admin admin);
    String loginAdmin(String username, String password);

    @Transactional
    Admin updateAdmin(Long adminId, Admin adminDetails);

    void deleteAdmin(Long adminId);

    String jobpost(Admin admin);
    List<Form> getAllForms();

    Form getFormByFormId(Long formId);

    List<Job> getAllJobsUploadedByAdmin();
    Admin getAdminById(Long adminId);
}
