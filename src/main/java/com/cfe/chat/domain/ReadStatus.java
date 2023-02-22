package com.cfe.chat.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "read_status")
public class ReadStatus implements Serializable {

    private static final long serialVersionUID = 1234567899652175008L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_group_id")
    private UserGroup userGroup;

    @ManyToOne
    @JoinColumn(name = "message_id")
    private Message message;

    @CreationTimestamp
    private OffsetDateTime readAt;
}
