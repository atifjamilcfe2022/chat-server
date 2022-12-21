package com.cfe.chat.domain;

import com.cfe.chat.enums.MessageStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Message")
public class Message implements Serializable {

    private static final long serialVersionUID = 1234567899652175004L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "message_body")
    private String messageBody;

    @CreationTimestamp
    private LocalDateTime createAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User sender;
}
