package com.example.app_clickup.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkspaceDto {

    @NotNull(message = "name is available")
    private String name;

    @NotNull(message = "color is available")
    private String color;


    private UUID avatarId;

}
