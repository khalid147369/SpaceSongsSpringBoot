package com.example.spctn.Repository;
import com.example.spctn.Entity.Listen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListenRepository extends JpaRepository<Listen, Long> {
	long countBySongId(Long songId);
}
