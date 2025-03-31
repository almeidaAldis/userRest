package com.aldis.userRest.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "token")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "token")
    private String token;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
