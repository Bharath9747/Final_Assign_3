package com.accolite.app.repo1;

import com.accolite.app.entity1.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface NotificationRepo extends JpaRepository<Notification,Integer> {
}
