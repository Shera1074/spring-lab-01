package kz.iitu.springlab.dto;

import jakarta.validation.constraints.*;

public record BookRequest(

        @Null(groups = ValidationGroups.Create.class)
        @NotNull(groups = ValidationGroups.Update.class)
        Long id,

        @NotBlank(groups = {
                ValidationGroups.Create.class,
                ValidationGroups.Update.class
        })
        String title,

        @NotBlank(groups = {
                ValidationGroups.Create.class,
                ValidationGroups.Update.class
        })
        String author,

        @NotNull(groups = ValidationGroups.Create.class)
        @Min(value = 1450, groups = {
                ValidationGroups.Create.class,
                ValidationGroups.Update.class
        })
        @Max(value = 2100, groups = {
                ValidationGroups.Create.class,
                ValidationGroups.Update.class
        })
        Integer year

) {}