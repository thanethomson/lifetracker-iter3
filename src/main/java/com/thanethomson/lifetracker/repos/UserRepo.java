package com.thanethomson.lifetracker.repos;

import com.thanethomson.lifetracker.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
        collectionResourceRel = "users",
        itemResourceRel = "user",
        path = "users"
)
public interface UserRepo extends CrudRepository<User, Long> {

    User findFirstByEmail(String email);

}
