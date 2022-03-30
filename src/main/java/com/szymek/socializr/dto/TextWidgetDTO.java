package com.szymek.socializr.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public abstract class TextWidgetDTO extends BaseEntityDTO{

    @NotBlank(message = "Text cannot be blank")
    private String text;

    private Long authorId;

}
