package com.cfe.chat.domain.views;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.OffsetDateTime;

@Data
@Entity
@Immutable
@Table(name = "last_chat_message")
public class LastChatMessage implements Serializable {

    private static final long serialVersionUID = 1234567899652175007L;

    @Id
    private Long id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private String messageBody;
    private Long userId;
    private String userName;
}
