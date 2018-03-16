package com.thanethomson.lifetracker.repos;

import com.thanethomson.lifetracker.models.Sample;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(
        collectionResourceRel = "samples",
        itemResourceRel = "sample",
        path = "samples"
)
public interface SampleRepo extends CrudRepository<Sample, Long> {

    List<Sample> findByUserId(Long id);

}
