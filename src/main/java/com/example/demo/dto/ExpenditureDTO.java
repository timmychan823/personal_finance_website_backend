package com.example.demo.dto;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExpenditureDTO {
    @NotNull(message = "cost cannot be null")
    @Positive(message = "cost cannot be smaller than or equal to 0")
    private Float cost;

    @NotNull(message = "datetime cannot be null")
    @PastOrPresent(message = "datetime cannot be in the future")
    private LocalDateTime datetime;

    @NotBlank(message = "receiver cannot be blank")
    private String receiver;
}
