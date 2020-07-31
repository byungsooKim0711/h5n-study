package com.humuson.imc.admin.web.domain.revision;

import com.humuson.imc.admin.security.ImcUserDetails;
import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.context.SecurityContextHolder;

public class ImcRevisionListener implements RevisionListener {

    @Override
    public void newRevision(Object revisionEntity) {
        ImcRevision revision = (ImcRevision) revisionEntity;

        ImcUserDetails details = (ImcUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        revision.setModifiedBy(details.getUsername());
    }
}
