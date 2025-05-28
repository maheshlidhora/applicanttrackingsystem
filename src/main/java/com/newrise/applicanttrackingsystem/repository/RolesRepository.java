package com.newrise.applicanttrackingsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.newrise.applicanttrackingsystem.entities.Roles;

public interface RolesRepository extends JpaRepository<Roles, Long> 
{
	Optional<Roles> findByRoleName(String roleName);
}
