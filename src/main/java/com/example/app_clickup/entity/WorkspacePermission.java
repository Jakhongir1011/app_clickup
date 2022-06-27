package com.example.app_clickup.entity;

import com.example.app_clickup.entity.enums.WorkspacePermissionName;
import com.example.app_clickup.entity.template.AbsLongEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class WorkspacePermission extends AbsLongEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private WorkspaceRole workspaceRole;  // o'rinbosar

    @Enumerated(EnumType.STRING)
    private WorkspacePermissionName workspacePermissionName; // add member, remote member




}
