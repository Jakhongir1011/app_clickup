package com.example.app_clickup.payload;

import com.example.app_clickup.entity.enums.AddType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {

    private UUID id;

    private String fullName;

    private String email;

    private String roleName;

    private Timestamp lastActive;

    private UUID roleId;

    private AddType addType; //
}
