package com.demo.cloud.service;

import com.demo.cloud.model.Organization;

public interface OrganizationService extends
        EntityAdder<Organization>,
        EntityGetter<Long, Organization>,
        EntityUpdater<Long, Organization>,
        EntityDeleter<Long>
{
    void updateLogo(Long id, String logo);

    String deleteLogo(Long id);
}
