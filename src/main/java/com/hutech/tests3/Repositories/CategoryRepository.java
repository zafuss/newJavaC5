package com.hutech.tests3.Repositories;

import com.hutech.tests3.Entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {
}
