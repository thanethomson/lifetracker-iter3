package com.thanethomson.lifetracker.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "metrics")
public class Metric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    /**
     * The units of measurement for this metric, if any (leave null if no units).
     */
    private String units = null;

    @ManyToOne
    @JoinColumn(name = "family_id")
    private MetricFamily family;

}
