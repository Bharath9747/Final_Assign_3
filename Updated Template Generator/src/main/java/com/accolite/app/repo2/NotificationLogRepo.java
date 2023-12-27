package com.accolite.app.repo2;

import com.accolite.app.entity2.NotificationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationLogRepo extends JpaRepository<NotificationLog,Long> {
}
