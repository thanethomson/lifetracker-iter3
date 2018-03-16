package com.thanethomson.lifetracker.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "metric_families")
public class MetricFamily {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "metric_themes_families",
            joinColumns = @JoinColumn(name = "family_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "theme_id", referencedColumnName = "id")
    )
    private List<MetricTheme> themes = new ArrayList<>();

}
