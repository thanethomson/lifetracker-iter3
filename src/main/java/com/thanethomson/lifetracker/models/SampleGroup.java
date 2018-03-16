package com.thanethomson.lifetracker.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@Table(name = "sample_groups")
public class SampleGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    /**
     * The date, if relevant, for this sample group.
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

}
