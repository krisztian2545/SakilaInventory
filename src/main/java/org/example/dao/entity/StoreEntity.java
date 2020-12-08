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
@Table(name = "staff", schema = "sakila")
public class StoreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="store_id")
    private int id;

    @ManyToOne
    @JoinColumn(name="staff_id")
    private StaffEntity managerStaff;

    @ManyToOne
    @JoinColumn(name="address_id")
    private AddressEntity address;

    @Column(name="last_update")
    private Timestamp lastUpdate;
}