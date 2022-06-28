package com.example.app_clickup.service;

import com.example.app_clickup.entity.*;
import com.example.app_clickup.entity.enums.AddType;
import com.example.app_clickup.entity.enums.WorkspacePermissionName;
import com.example.app_clickup.entity.enums.WorkspaceRoleName;
import com.example.app_clickup.payload.ApiResponse;
import com.example.app_clickup.payload.MemberDto;
import com.example.app_clickup.payload.WorkspaceDto;
import com.example.app_clickup.repository.*;
import jdk.management.resource.ResourceRequestDeniedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class WorkspaceServiceImpl implements WorkspaceService{

   @Autowired
   WorkspaceRepository workspaceRepository;

   @Autowired
   AttachmentRepository attachmentRepository;

   @Autowired
   WorkspaceUserRepository workspaceUserRepository;

   @Autowired
   WorkspaceRoleRepository workspaceRoleRepository;

   @Autowired
   WorkspacePermissionRepository workspacePermissionRepository;

   @Autowired
   UserRepository userRepository;

   @Override
    public ApiResponse addWorkspace(WorkspaceDto workspaceDto, User user) {
        // 1-usul kelyapkan userni tutib olish.
       //  2-usul anatatsiya orqali security ga otamiz
//        User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

       // WORKSPACE OCHDIK
       if (workspaceRepository.existsByOwnerIdAndName(user.getId(),workspaceDto.getName()))
          return new ApiResponse("Sizda bunday nomli ishxona mavjud",false);
       Workspace workspace = new Workspace();
       workspace.setName(workspaceDto.getName());
       workspace.setColor(workspaceDto.getColor());
       workspace.setAvatar(
              workspaceDto.getAvatarId()==null?null:attachmentRepository.findById(workspaceDto.getAvatarId())
                      .orElseThrow(()-> new ResourceRequestDeniedException("attachment"))
       );
       workspace.setOwner(user);
       workspaceRepository.save(workspace);

      // WORKSPACE ROLE OCHDIK
       WorkspaceRole ownerRole
               = workspaceRoleRepository.save(new WorkspaceRole(
               workspace,
               WorkspaceRoleName.ROLE_OWNER.name(),
               null
       ));

       WorkspaceRole adminRole = workspaceRoleRepository.save(new WorkspaceRole(workspace,WorkspaceRoleName.ROLE_ADMIN.name(),null));
       WorkspaceRole memberRole = workspaceRoleRepository.save(new WorkspaceRole(workspace,WorkspaceRoleName.ROLE_MEMBER.name(),null));
       WorkspaceRole guestRole = workspaceRoleRepository.save(new WorkspaceRole(workspace,WorkspaceRoleName.ROLE_GUEST.name(),null));

       // OWNER GA PERMISSIONS BERAMIZ
       WorkspacePermissionName[] workspacePermissionNames = WorkspacePermissionName.values();
       List<WorkspacePermission> workspacePermissions = new ArrayList<>();

       for (WorkspacePermissionName workspacePermissionName : workspacePermissionNames) {
           WorkspacePermission workspacePermission = new WorkspacePermission(
                   ownerRole,
                   workspacePermissionName);
           workspacePermissions.add(workspacePermission);
           if (workspacePermissionName.getWorkspaceRoleNames().contains(WorkspaceRoleName.ROLE_ADMIN)) {
               workspacePermissions.add(new WorkspacePermission(
                       adminRole,
                       workspacePermissionName));
           }
           if (workspacePermissionName.getWorkspaceRoleNames().contains(WorkspaceRoleName.ROLE_MEMBER)) {
               workspacePermissions.add(new WorkspacePermission(
                       memberRole,
                       workspacePermissionName));
           }
           if (workspacePermissionName.getWorkspaceRoleNames().contains(WorkspaceRoleName.ROLE_GUEST)) {
               workspacePermissions.add(new WorkspacePermission(
                       guestRole,
                       workspacePermissionName));
           }
           workspacePermissionRepository.saveAll(workspacePermissions);
       }


      // WORKSPACE USER OCHDIK
       workspaceUserRepository.save(new WorkspaceUser(
               workspace,
               user,
               ownerRole,
               new Timestamp(System.currentTimeMillis()),
               new Timestamp(System.currentTimeMillis())

       ));

       return new ApiResponse("Ishxona saqlandi", true);
   }


    /**
     * name, color, avatar o'zgarishi mumkin
     */

    @Override
    public ApiResponse editWorkspace(Long id, WorkspaceDto workspaceDto) {
        return null;
    }


    @Override
    public ApiResponse changeOwnerWorkspace(Long id, UUID ownerId) {
        return null;
    }

    @Override
    public ApiResponse deleteWorkspace(Long id) {
        try{
            workspaceRepository.deleteById(id);
            return new ApiResponse("delete workspace",true);
        }catch (Exception e){
            return new ApiResponse("error",false);
        }
    }

    @Override
    public ApiResponse addOrEditOrRemoveWorkspaceService(Long id, MemberDto memberDto) {
        AddType addType = memberDto.getAddType();
        switch (addType){

            case ADD: WorkspaceUser workspaceUser = new WorkspaceUser(
                    workspaceRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("id")),
                    userRepository.findById(memberDto.getId()).orElseThrow(()->new ResourceNotFoundException("id")),
                    workspaceRoleRepository.findById(memberDto.getRoleId()).orElseThrow(()->new RuntimeException("id")),
                    new Timestamp(System.currentTimeMillis()),
                    null
            );
                    workspaceUserRepository.save(workspaceUser);
                    return new ApiResponse("added workspaceUser",true);

  //TODO EMAILGA INVITE XABAR YUBORISH   // manashu ishhonaga sizni taklif qilyapti deb emailga habar yuborishimiz kk va joinToWorkspace shunga linkka otib yuborishi kerak

                case EDIT:
                    WorkspaceUser workspaceUser1 = workspaceUserRepository.findByWorkspaceIdAndUserId(id, memberDto.getId()).orElseGet(WorkspaceUser::new);
                    workspaceUser1.setWorkspaceRole(workspaceRoleRepository.findById(memberDto.getRoleId()).orElseThrow(()->new RuntimeException("id")));
                    workspaceUserRepository.save(workspaceUser1);
                    return new ApiResponse("edit workspace ROLE",true);

            case REMOVE:
                workspaceUserRepository.deleteByWorkspaceIdAndUserId(id, memberDto.getId());
                return new ApiResponse("delete Workspace User success", true);

            default:new ApiResponse("boshqa type berdingiz ",false) ;
        }
        return new ApiResponse("boshqa soz kirittingiz",false);
    }

    @Override
    public ApiResponse joinToWorkspace(Long id, User user) {
        Optional<WorkspaceUser> optionalWorkspaceUser = workspaceUserRepository.findByWorkspaceIdAndUserId(id, user.getId());
        if (optionalWorkspaceUser.isPresent()) {
            WorkspaceUser workspaceUser = optionalWorkspaceUser.get();
            workspaceUser.setDateJoined(new Timestamp(System.currentTimeMillis()));
            workspaceUserRepository.save(workspaceUser);
            return new ApiResponse("Success", true);
        }
        return new ApiResponse("Error", false);
    }

    @Override
    public List<MemberDto> getMemberAnsGuest(Long id) {

//        1-usul
        List<WorkspaceUser> workspaceUsers = workspaceUserRepository.findAllByWorkspaceId(id);
        List<MemberDto> members = new ArrayList<>();
        for (WorkspaceUser workspaceUser : workspaceUsers) {
            members.add(mapWorkspaceUserToMemberDto(workspaceUser));
        }
        return members;

//    2-usul
//        return workspaceUsers.stream().map(this::mapWorkspaceUserToMemberDto).collect(Collectors.toList());
    }

    @Override // biz hozir manashu kirib turgan userni workspaces ni qaytarishimiz kerak
    public List<WorkspaceDto> getMyWorkspaceService(User user) {
        List<WorkspaceUser> workspaceUser = workspaceUserRepository.findAllByUserId(user.getId());
        List<WorkspaceDto> workspaceDtoList = new ArrayList<>();
        for (WorkspaceUser workspaceUser1 : workspaceUser) {
            workspaceDtoList.add(mapWorkspaceUserToWorkspaceDto(workspaceUser1));
        }
        return workspaceDtoList;
    }






    // MY METHOD


    public WorkspaceDto mapWorkspaceUserToWorkspaceDto(WorkspaceUser workspaceUser){
        WorkspaceDto workspaceDto = new WorkspaceDto();
        workspaceDto.setId(workspaceUser.getWorkspace().getId());
        workspaceDto.setName(workspaceUser.getWorkspace().getName());
        workspaceDto.setInitialLetter(workspaceUser.getWorkspace().getInitialLetter());
        workspaceDto.setAvatarId(workspaceUser.getWorkspace().getAvatar()==null?null:workspaceUser.getWorkspace().getAvatar().getId());
        workspaceDto.setColor(workspaceUser.getWorkspace().getColor());
        return workspaceDto;
    }




    public MemberDto mapWorkspaceUserToMemberDto(WorkspaceUser workspaceUser){
        MemberDto memberDto = new MemberDto();
        memberDto.setId(workspaceUser.getUser().getId()); // userni id sini oldik
        memberDto.setFullName(workspaceUser.getUser().getFullName()); // userni fullname
        memberDto.setEmail(workspaceUser.getUser().getEmail());  // user ni email
        memberDto.setRoleName(workspaceUser.getWorkspaceRole().getName()); // user role name
        memberDto.setLastActive(workspaceUser.getUser().getLastActiveTime());
        return  memberDto ;
    }




}
