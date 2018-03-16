package com.thanethomson.lifetracker.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "samples")
public class Sample {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The user who generated this sample.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * The metric to which this sample is relevant.
     */
    @ManyToOne
    @JoinColumn(name = "metric_id")
    private Metric metric;

    @NotNull
    private Double amount;

    /**
     * The group, if any, to which this sample belongs.
     */
    @ManyToOne
    @JoinColumn(name = "group_id")
    private SampleGroup group = null;

}
