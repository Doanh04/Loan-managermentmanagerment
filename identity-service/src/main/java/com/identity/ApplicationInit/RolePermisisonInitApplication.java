package com.identity.ApplicationInit;

import com.identity.Constain.NameRoleEnum;
import com.identity.Constain.PermissionEnum;
import com.identity.Constain.RolesEnum;
import com.identity.Repositoty.PerrmissionRepository;
import com.identity.Repositoty.RolesRepository;
import com.identity.entity.Permission;
import com.identity.entity.Roles;
import com.identity.exception.AppException;
import com.identity.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.HashSet;
import java.util.Set;

@Configuration
@Slf4j
public class RolePermisisonInitApplication {
    @Bean
    @Order(2)
    ApplicationRunner rolePermissionInit(RolesRepository rolesRepository, PerrmissionRepository perrmissionRepository){
        return args -> {
            if(!rolesRepository.existsById(RolesEnum.ADMIN)){
                var permissionSystem = perrmissionRepository.findById(PermissionEnum.SYSTEM_CONFIG).orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND));
                var permissionAudit = perrmissionRepository.findById(PermissionEnum.AUDIT_LOG_VIEW).orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND));
                var permissionCreateUser = perrmissionRepository.findById(PermissionEnum.USER_CREATE).orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND));
                var permissionUserRead = perrmissionRepository.findById(PermissionEnum.USER_READ).orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND));
                var permissionUserWrite = perrmissionRepository.findById(PermissionEnum.USER_WRITE).orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND));
                var PermissionAdmin = new HashSet<>(Set.of(permissionSystem, permissionAudit, permissionCreateUser, permissionUserRead, permissionUserWrite));
                rolesRepository.save(Roles.builder()
                                .role(RolesEnum.ADMIN)
                                .Name_role(NameRoleEnum.ROLE_ADMIN)
                                .permission(PermissionAdmin)
                                .Description("this is role for admin")
                        .build());
            }

            if(!rolesRepository.existsById(RolesEnum.CUSTOMER)){
                var customercustomerCreate = perrmissionRepository.findById(PermissionEnum.CUSTOMER_CREATE).orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND));
                var customercustomerWrite = perrmissionRepository.findById(PermissionEnum.CUSTOMER_WRITE).orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND));
                var customercustomerRead = perrmissionRepository.findById(PermissionEnum.CUSTOMER_READ).orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND));
                var permissionCustomer = new HashSet<>(Set.of(customercustomerCreate, customercustomerWrite, customercustomerRead));
                rolesRepository.save(Roles.builder()
                                .role(RolesEnum.CUSTOMER)
                                .Name_role(NameRoleEnum.ROLE_CUSTOMER)
                                .permission(permissionCustomer)
                                .Description("this is role for customer")
                        .build());
            }
            if(!rolesRepository.existsById(RolesEnum.LOAN_UNDERWRITER)){
                var underwriterunderWriterCreateLoan = perrmissionRepository.findById(PermissionEnum.LOAN_CREATE).orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND));
                var underwriterunderWriterWriteLoan = perrmissionRepository.findById(PermissionEnum.LOAN_WRITE).orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND));
                var underwriterunderWriterSubmitLoan = perrmissionRepository.findById(PermissionEnum.LOAN_SUBMIT).orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND));
                var underwriterunderWriterCreditChecktLoan = perrmissionRepository.findById(PermissionEnum.CREDIT_CHECK).orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND));
                var permissionUnderWriter = new HashSet<>(Set.of(underwriterunderWriterCreateLoan, underwriterunderWriterWriteLoan, underwriterunderWriterSubmitLoan, underwriterunderWriterCreditChecktLoan));
                rolesRepository.save(Roles.builder()
                                .role(RolesEnum.LOAN_UNDERWRITER)
                                .Name_role(NameRoleEnum.ROLE_LOAN_UNDERWRITER)
                                .permission(permissionUnderWriter)
                                .Description("This is role for Loan under writer")
                        .build());
            }
            if(!rolesRepository.existsById(RolesEnum.LOAN_MANAGER)){
                var loanManagerLoanReject = perrmissionRepository.findById(PermissionEnum.LOAN_REJECT).orElseThrow(()-> new AppException(ErrorCode.PERMISSION_NOT_FOUND));
                var loanManagerReportViewReportView = perrmissionRepository.findById(PermissionEnum.REPORT_VIEW).orElseThrow(()-> new AppException(ErrorCode.PERMISSION_NOT_FOUND));
                var loanManagerReportLoanApproveLoanApprove = perrmissionRepository.findById(PermissionEnum.LOAN_APPROVE).orElseThrow(()-> new AppException(ErrorCode.PERMISSION_NOT_FOUND));
                var loanManagerReportLoanReadAll = perrmissionRepository.findById(PermissionEnum.LOAN_READ_ALL).orElseThrow(()-> new AppException(ErrorCode.PERMISSION_NOT_FOUND));
                var permissionLoanManager = new HashSet<>(Set.of(loanManagerLoanReject, loanManagerReportViewReportView, loanManagerReportLoanApproveLoanApprove, loanManagerReportLoanReadAll));
                rolesRepository.save(Roles.builder()
                                .role(RolesEnum.LOAN_MANAGER)
                                .Name_role(NameRoleEnum.ROLE_LOAN_MANAGER)
                                .permission(permissionLoanManager)
                                .Description("This is role for loan manager")
                        .build());
            }
            if(!rolesRepository.existsById(RolesEnum.ACCOUNTANT)){
                var accounTantDisburstanDisbursermentExcute = perrmissionRepository.findById(PermissionEnum.DISBURSEMENT_EXECUTE).orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND));
                var accountantRepaymentColect = perrmissionRepository.findById(PermissionEnum.REPAYMENT_COLLECT).orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND));
                var accountantFinacialColect = perrmissionRepository.findById(PermissionEnum.FINANCIAL_REPORT).orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND));
                var permissionAccountant = new HashSet<>(Set.of(accounTantDisburstanDisbursermentExcute, accountantRepaymentColect, accountantFinacialColect));

                rolesRepository.save(Roles.builder()
                                .role(RolesEnum.ACCOUNTANT)
                                .Name_role(NameRoleEnum.ROLE_ACCOUNTANT)
                                .permission(permissionAccountant)
                                .Description("This is permission for accountantaccountant                  ")
                        .build());
            }
        };
    }
}
