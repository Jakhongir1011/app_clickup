package com.example.app_clickup.entity;


import com.example.app_clickup.entity.template.AbsLongEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name","owner_id"})})
@Entity
public class Workspace extends AbsLongEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String color;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User owner;

    @Column(nullable = false)
    private String initialLetter;

    @ManyToOne(fetch = FetchType.LAZY)
    private  Attachment avatar;

    @PrePersist   // ma'lumotlar omboriga qo'shishdan oldin
    @PreUpdate    // update qilishdan oldin
    public void setInitialLetterMyMethod(){
            this.initialLetter = name.substring(0,1);
    }

    public Workspace(String name, String color, User owner, Attachment avatar) {
        this.name = name;
        this.color = color;
        this.owner = owner;
        this.avatar = avatar;
    }
}
