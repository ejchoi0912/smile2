package com.snuh.smile.domain;

import com.snuh.smile.util.AuthLogEnum;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
public class AuthLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String email;
    private String userAccessToken;
    @CreationTimestamp
    private LocalDateTime logTime;
    private String logType;

}
