package com.cfe.chat.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements Serializable {

    private static final long serialVersionUID = 1234567899652175001L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userno")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "fullname")
    private String fullName;

    @Column(name = "emailaddress")
    private String email;
}
