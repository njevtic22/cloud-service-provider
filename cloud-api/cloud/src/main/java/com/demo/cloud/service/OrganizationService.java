package com.demo.cloud.service;

import com.demo.cloud.model.Organization;

public interface OrganizationService extends EntityAdder<Organization>, EntityGetter<Long, Organization> {
    void updateLogo(Long id, String logo);
}
