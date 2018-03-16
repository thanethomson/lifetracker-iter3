package com.thanethomson.lifetracker.repos;

import com.thanethomson.lifetracker.models.MetricFamily;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
        collectionResourceRel = "metricFamilies",
        itemResourceRel = "metricFamily",
        path = "metric-families"
)
public interface MetricFamilyRepo extends CrudRepository<MetricFamily, Long> {

    MetricFamily findFirstByName(String name);

}
