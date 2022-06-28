package com.example.app_clickup.repository;

import com.example.app_clickup.entity.WorkspacePermission;
import com.example.app_clickup.entity.enums.WorkspacePermissionName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface WorkspacePermissionRepository extends JpaRepository<WorkspacePermission,Long> {


    Optional<WorkspacePermission> findByWorkspaceRoleIdAndWorkspacePermissionName(UUID workspaceRole_id, WorkspacePermissionName workspacePermissionName);


}
