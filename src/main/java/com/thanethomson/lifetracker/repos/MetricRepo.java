package com.thanethomson.lifetracker.repos;

import com.thanethomson.lifetracker.models.Metric;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
        collectionResourceRel = "metrics",
        path = "metrics"
)
public interface MetricRepo extends CrudRepository<Metric, Long> {

    Metric findFirstByName(String name);

}
