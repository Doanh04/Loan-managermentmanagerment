package com.LoanManagerment.Notification.Repository;

import com.LoanManagerment.Notification.entity.OtpRedis;
import org.springframework.data.repository.CrudRepository;

public interface OtpRepository extends CrudRepository<OtpRedis, String> {
}
