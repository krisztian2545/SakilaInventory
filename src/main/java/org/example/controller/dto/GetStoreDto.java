package org.example.controller.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class GetStoreDto extends StoreDto {
    private int id;
}
