package com.example.app_clickup.payload;

import com.example.app_clickup.entity.enums.AddType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {

    private UUID id;

    private UUID roleId;

    private AddType addType; //
}
