package kz.iitu.springlab.dto;

import jakarta.validation.constraints.*;

public record BookRequest(

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