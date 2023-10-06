package com.sebastian_daschner.coffee.control;

import com.sebastian_daschner.coffee.entity.CoffeeType;
import com.sebastian_daschner.coffee.entity.Origin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface OriginRepository extends JpaRepository<Origin, String> {

    Set<Origin> findByCoffeeTypes(CoffeeType type);

}
