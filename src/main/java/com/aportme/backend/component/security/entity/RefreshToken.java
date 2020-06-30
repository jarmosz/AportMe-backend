package com.aportme.backend.component.security.entity;

import com.aportme.backend.component.user.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class RefreshToken {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String refreshToken;

        @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
        @JoinColumn(nullable = false, name = "user_id")
        private User user;

        @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
        private DateTime expiryDate;
}