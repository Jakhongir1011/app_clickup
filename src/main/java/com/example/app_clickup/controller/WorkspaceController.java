package com.example.app_clickup.controller;

import com.example.app_clickup.entity.User;
import com.example.app_clickup.payload.ApiResponse;
import com.example.app_clickup.payload.MemberDto;
import com.example.app_clickup.payload.WorkspaceDto;
import com.example.app_clickup.security.CurrentUser;
import com.example.app_clickup.service.WorkspaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/workspace")
public class WorkspaceController {

    @Autowired
    WorkspaceService workspaceService;

    @PostMapping
    public HttpEntity<?> addWorkspace(@Valid @RequestBody WorkspaceDto workspaceDto, @CurrentUser User user){
         ApiResponse apiResponse =  workspaceService.addWorkspace(workspaceDto, user);
         return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editWorkspace(@Valid @PathVariable Long id, @RequestBody WorkspaceDto workspaceDto){
        ApiResponse apiResponse =  workspaceService.editWorkspace(id,workspaceDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PutMapping("/changeOwner/{id}")
    public HttpEntity<?> changeOwnerWorkspace(@Valid @PathVariable Long id,
                                              @RequestParam UUID ownerId){
        ApiResponse apiResponse =  workspaceService.changeOwnerWorkspace(id,ownerId);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteWorkspace(@PathVariable Long id){
        ApiResponse apiResponse =  workspaceService.deleteWorkspace(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }



// WORKSPACE USER
    @PostMapping("/addOrEditOrRemove/{id}")
    public HttpEntity<?>  addOrEditOrRemoveWorkspace(@PathVariable Long id,
                                                     @RequestBody MemberDto memberDto){
       ApiResponse apiResponse= workspaceService.addOrEditOrRemoveWorkspaceService(id,memberDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:407).body(apiResponse);
    }

    @PutMapping("/join")
    public HttpEntity<?> joinToWorkspace(@RequestParam Long id,
                                         @CurrentUser User user) {
        ApiResponse apiResponse = workspaceService.joinToWorkspace(id, user);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/member/{id}")
    public HttpEntity<?> getMemberAnsGuest(@PathVariable Long id){
        List<MemberDto> memberDtoList = workspaceService.getMemberAnsGuest(id);
        return ResponseEntity.ok(memberDtoList);
    }

    // CurrentUser this  anatomising tismiga kiribtusrgan authentication bo'ilib turgan odamni olib beradi
    @GetMapping HttpEntity<?> getMyWorkspace(@CurrentUser User user){
            List<WorkspaceDto> workspace = workspaceService.getMyWorkspaceService(user);
            return ResponseEntity.ok(workspace);
    }
}
