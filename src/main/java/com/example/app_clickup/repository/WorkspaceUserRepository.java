package com.example.app_clickup.repository;

import com.example.app_clickup.entity.WorkspaceUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WorkspaceUserRepository extends JpaRepository<WorkspaceUser, UUID> {


    Optional<WorkspaceUser> findByWorkspaceIdAndUserId(Long workspace_id, UUID user_id);

    @Transactional  // xato bolish extimolini olish uchun
    @Modifying     // bu bizga kerak mas demoqchimiz natijasi yaniy void
    void deleteByWorkspaceIdAndUserId(Long workspace_id, UUID user_id);

    List<WorkspaceUser> findAllByWorkspaceId(Long workspace_id);

    List<WorkspaceUser> findAllByUserId(UUID user_id);

}
