package dev.binhcn.repository;

import dev.binhcn.entity.Actress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActressRepository extends JpaRepository<Actress, Long> {
}