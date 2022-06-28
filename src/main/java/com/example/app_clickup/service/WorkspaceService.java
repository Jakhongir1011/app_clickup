package com.example.app_clickup.service;

import com.example.app_clickup.entity.User;
import com.example.app_clickup.payload.ApiResponse;
import com.example.app_clickup.payload.MemberDto;
import com.example.app_clickup.payload.WorkspaceDto;

import java.util.List;
import java.util.UUID;

public interface WorkspaceService {


    ApiResponse addWorkspace(WorkspaceDto workspaceDto, User user);

    ApiResponse editWorkspace(Long id, WorkspaceDto workspaceDto);

    ApiResponse changeOwnerWorkspace(Long id, UUID ownerId);

    ApiResponse deleteWorkspace(Long id);

    ApiResponse addOrEditOrRemoveWorkspaceService(Long id, MemberDto memberDto);

    ApiResponse joinToWorkspace(Long id, User user);

    List<MemberDto> getMemberAnsGuest(Long id);

    List<WorkspaceDto> getMyWorkspaceService(User user);
}
