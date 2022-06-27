package com.example.app_clickup.entity.template;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@Data
@MappedSuperclass
public abstract class AbsUUIDEntity extends AbsMainEntity {

//    @Id  // bizni UUID mizni xatosiz generatsiya qilib berish uchum
//    @GeneratedValue(generator = "uuid2")
//    @Type(type = "org.hibernate.PostgresUUIDType")
//    @GenericGenerator(name = "uuid2",strategy = "org.hibernate.id.UUIDGenerator")

     @Id
     @GeneratedValue
     private UUID id; // USERNING TAKRORLANMAS QISMI

}
