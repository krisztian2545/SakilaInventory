package org.example.dao.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "store", schema = "sakila")
public class CreateStoreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="store_id")
    private int id;

    @Column(name="manager_staff_id")
    private int managerStaffId;

    @ManyToOne
    @JoinColumn(name="address_id")
    private AddressEntity address;

    @Column(name="last_update")
    private Timestamp lastUpdate;
}
