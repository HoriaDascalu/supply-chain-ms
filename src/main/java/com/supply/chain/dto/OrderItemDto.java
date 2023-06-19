package com.supply.chain.dto;

import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Min;
import javax.validation.constraints.Max;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class OrderItemDto {
    private Long orderItemId;

    @NotEmpty(message = "name must not be empty")
    private String name;

    @NotNull(message = "quantity must not be empty")
    @Min(1)
    @Max(20)
    private Integer quantity;
}
