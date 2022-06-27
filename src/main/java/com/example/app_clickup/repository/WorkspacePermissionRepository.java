package com.example.app_clickup.repository;

import com.example.app_clickup.entity.WorkspacePermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkspacePermissionRepository extends JpaRepository<WorkspacePermission,Long> {
}
