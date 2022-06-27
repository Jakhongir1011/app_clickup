package com.example.app_clickup.entity;

import com.example.app_clickup.entity.template.AbsUUIDEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Attachment extends AbsUUIDEntity {

    private String name; // serverdagi nomi

    private String orginalName;  // kampiyutirdagi nomi

    private Long size;

    private String contentType;


}
