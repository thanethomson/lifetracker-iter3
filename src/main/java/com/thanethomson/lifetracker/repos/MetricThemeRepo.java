package com.thanethomson.lifetracker.repos;

import com.thanethomson.lifetracker.models.MetricTheme;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
        collectionResourceRel = "metricThemes",
        itemResourceRel = "metricTheme",
        path = "metric-themes"
)
public interface MetricThemeRepo extends CrudRepository<MetricTheme, Long> {
}
