package com.newrise.applicanttrackingsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.newrise.applicanttrackingsystem.entities.Permissions;

public interface PermissionsRepository extends JpaRepository<Permissions, Long>
{
	Optional<Permissions> findByPermissionName(String permissionName);
}
