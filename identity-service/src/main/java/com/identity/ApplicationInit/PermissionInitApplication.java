package com.identity.ApplicationInit;

import com.identity.Constain.PermissionEnum;
import com.identity.Constain.PermissionNameEnum;
import com.identity.Repositoty.PerrmissionRepository;
import com.identity.entity.Permission;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@Slf4j
public class PermissionInitApplication {

    @Bean
    @Order(1)
    ApplicationRunner initPermisison(PerrmissionRepository perrmissionRepository){
        return args -> {
            log.info("nitializing Permissions...");

            if(!perrmissionRepository.existsById(PermissionEnum.AUDIT_LOG_VIEW)){
                perrmissionRepository.save(Permission.builder()
                                .Permission(PermissionEnum.AUDIT_LOG_VIEW)
                                .Permission_name(PermissionNameEnum.NAME_AUDIT_LOG_VIEW)
                                .Desciption("This is permission for check audit")
                        .build());
            }
            if(!perrmissionRepository.existsById(PermissionEnum.CUSTOMER_CREATE)){
                perrmissionRepository.save(Permission.builder()
                        .Permission(PermissionEnum.CUSTOMER_CREATE)
                                .Permission_name(PermissionNameEnum.NAME_CUSTOMER_CREATE)
                                .Desciption("This is permision create for customer")
                        .build());
            }
            if(!perrmissionRepository.existsById(PermissionEnum.CUSTOMER_WRITE)){
                perrmissionRepository.save(Permission.builder()
                        .Permission(PermissionEnum.CUSTOMER_WRITE)
                                .Permission_name(PermissionNameEnum.NAME_CUSTOMER_WRITE)
                                .Desciption("This is per mision write for customer")
                        .build());
            }
            if(!perrmissionRepository.existsById(PermissionEnum.CUSTOMER_READ)){
                perrmissionRepository.save(Permission.builder()
                        .Permission(PermissionEnum.CUSTOMER_READ)
                        .Permission_name(PermissionNameEnum.NAME_CUSTOMER_READ)
                        .Desciption("This is permision write for customer")
                        .build());
            }
            if(!perrmissionRepository.existsById(PermissionEnum.LOAN_CREATE)){
                perrmissionRepository.save(Permission.builder()
                                .Permission(PermissionEnum.LOAN_CREATE)
                                .Permission_name(PermissionNameEnum.NAME_LOAN_CREATE)
                                .Desciption("This is permission name loan create ")
                        .build());
            }
            if(!perrmissionRepository.existsById(PermissionEnum.LOAN_SUBMIT)){
                perrmissionRepository.save(Permission.builder()
                        .Permission(PermissionEnum.LOAN_SUBMIT)
                        .Permission_name(PermissionNameEnum.NAME_LOAN_SUBMIT)
                        .Desciption("This is permission name loan submit ")
                        .build());
            }
            if(!perrmissionRepository.existsById(PermissionEnum.CREDIT_CHECK)){
                perrmissionRepository.save(Permission.builder()
                        .Permission(PermissionEnum.CREDIT_CHECK)
                        .Permission_name(PermissionNameEnum.NAME_CREDIT_CHECK)
                        .Desciption("This is permission name creadit check ")
                        .build());
            }
            if(!perrmissionRepository.existsById(PermissionEnum.LOAN_READ_ALL)){
                perrmissionRepository.save(Permission.builder()
                        .Permission(PermissionEnum.LOAN_READ_ALL)
                        .Permission_name(PermissionNameEnum.NAME_LOAN_READ_ALL)
                        .Desciption("This is permission name loan read all ")
                        .build());
            }
            if(!perrmissionRepository.existsById(PermissionEnum.LOAN_APPROVE)){
                perrmissionRepository.save(Permission.builder()
                        .Permission(PermissionEnum.LOAN_APPROVE)
                        .Permission_name(PermissionNameEnum.NAME_LOAN_APPROVE)
                        .Desciption("This is permission name loan Approve ")
                        .build());
            }
            if(!perrmissionRepository.existsById(PermissionEnum.LOAN_REJECT)){
                perrmissionRepository.save(Permission.builder()
                        .Permission(PermissionEnum.LOAN_REJECT)
                        .Permission_name(PermissionNameEnum.NAME_LOAN_REJECT)
                        .Desciption("This is permission name loan reject ")
                        .build());
            }
            if(!perrmissionRepository.existsById(PermissionEnum.REPORT_VIEW)){
                perrmissionRepository.save(Permission.builder()
                        .Permission(PermissionEnum.REPORT_VIEW)
                        .Permission_name(PermissionNameEnum.NAME_REPORT_VIEW)
                        .Desciption("This is permission report view ")
                        .build());
            }
            if(!perrmissionRepository.existsById(PermissionEnum.DISBURSEMENT_EXECUTE)){
                perrmissionRepository.save(Permission.builder()
                        .Permission(PermissionEnum.DISBURSEMENT_EXECUTE)
                        .Permission_name(PermissionNameEnum.NAME_DISBURSEMENT_EXECUTE)
                        .Desciption("This is permission Disbursement_excute ")
                        .build());
            }
            if(!perrmissionRepository.existsById(PermissionEnum.REPAYMENT_COLLECT)){
                perrmissionRepository.save(Permission.builder()
                        .Permission(PermissionEnum.REPAYMENT_COLLECT)
                        .Permission_name(PermissionNameEnum.NAME_REPAYMENT_COLLECT)
                        .Desciption("This is permission repayment follect ")
                        .build());
            }
            if(!perrmissionRepository.existsById(PermissionEnum.FINANCIAL_REPORT)){
                perrmissionRepository.save(Permission.builder()
                        .Permission(PermissionEnum.FINANCIAL_REPORT)
                        .Permission_name(PermissionNameEnum.NAME_FINANCIAL_REPORT)
                        .Desciption("This is permission financial report ")
                        .build());
            }
            if(!perrmissionRepository.existsById(PermissionEnum.USER_CREATE)){
                perrmissionRepository.save(Permission.builder()
                        .Permission(PermissionEnum.USER_CREATE)
                        .Permission_name(PermissionNameEnum.NAME_USER_CREATE)
                        .Desciption("This is permission user create ")
                        .build());
            }
            if(!perrmissionRepository.existsById(PermissionEnum.USER_WRITE)){
                perrmissionRepository.save(Permission.builder()
                        .Permission(PermissionEnum.USER_WRITE)
                        .Permission_name(PermissionNameEnum.NAME_USER_WRITE)
                        .Desciption("This is permission user write ")
                        .build());
            }
            if(!perrmissionRepository.existsById(PermissionEnum.USER_READ)){
                perrmissionRepository.save(Permission.builder()
                        .Permission(PermissionEnum.USER_READ)
                        .Permission_name(PermissionNameEnum.NAME_USER_READ)
                        .Desciption("This is permission user read")
                        .build());
            }
            if(!perrmissionRepository.existsById(PermissionEnum.SYSTEM_CONFIG)){
                perrmissionRepository.save(Permission.builder()
                        .Permission(PermissionEnum.SYSTEM_CONFIG)
                        .Permission_name(PermissionNameEnum.NAME_SYSTEM_CONFIG)
                        .Desciption("This is permission systemconfig ")
                        .build());
            }
        };
    }
}
