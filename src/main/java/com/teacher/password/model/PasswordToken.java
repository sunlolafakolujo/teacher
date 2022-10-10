package com.teacher.password.model;

import com.teacher.appuser.model.AppUser;
import com.teacher.baseaudit.BaseAudit;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
public class PasswordToken extends BaseAudit {

    private static final Integer EXPIRATION_TIME = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private Date expirationTime;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "app_user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_APP_USER_PASSWORD_TOKEN"))
    private AppUser appUser;

    public PasswordToken(String token, AppUser appUser) {
        super();
        this.token = token;
        this.appUser = appUser;
        this.expirationTime = calculateExpirationDate(EXPIRATION_TIME);
    }

    public PasswordToken(String token) {
        super();
        this.token = token;
        this.expirationTime = calculateExpirationDate(EXPIRATION_TIME);
    }

    private Date calculateExpirationDate(Integer expirationTime){
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, expirationTime);

        return new Date(calendar.getTime().getTime());
    }
}
