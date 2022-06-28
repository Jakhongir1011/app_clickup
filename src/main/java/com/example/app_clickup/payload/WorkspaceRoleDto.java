package com.example.app_clickup.payload;

import com.example.app_clickup.entity.enums.AddType;
import com.example.app_clickup.entity.enums.WorkspacePermissionName;
import com.example.app_clickup.entity.enums.WorkspaceRoleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkspaceRoleDto {

    private UUID id;

    private String name;

    private WorkspaceRoleName extendsRole;

    private WorkspacePermissionName permissionName;

    private AddType addType;
}
