package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Billing;

public interface BillingRepository extends JpaRepository<Billing, Long>{

	Optional<Billing> findById(Long id);

}
