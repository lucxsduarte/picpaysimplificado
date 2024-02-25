package com.picpaysimplificado.services.impl;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.dtos.NotificationDTO;
import com.picpaysimplificado.services.NotificationService;
import com.picpaysimplificado.services.exceptions.IntegrityViolation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private RestTemplate restTemplate;

    public void sendNotification(final User user, final String message) {
        final var email = user.getEmail();
        final var notificationRequest = new NotificationDTO(email, message);

         final var notificationResponse = restTemplate.postForEntity("https://run.mocky.io/v3/54dc2cf1-3add-45b5-b5a9-6bf7e7f1f4a6", notificationRequest, String.class);

         if (!notificationResponse.getStatusCode().equals(HttpStatus.OK)) {
             System.out.println("erro ao enviar notificação");
             throw new IntegrityViolation("Service de notificação fora do ar");
         }
    }
}
