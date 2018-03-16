package com.thanethomson.lifetracker.repos;

import com.thanethomson.lifetracker.models.SampleGroup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
        collectionResourceRel = "sampleGroups",
        itemResourceRel = "sampleGroup",
        path = "sample-groups"
)
public interface SampleGroupRepo extends CrudRepository<SampleGroup, Long> {
}
