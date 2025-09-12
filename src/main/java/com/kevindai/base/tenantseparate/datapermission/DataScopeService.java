package com.kevindai.base.tenantseparate.datapermission;

import java.util.List;
import java.util.Set;

import com.kevindai.base.tenantseparate.entity.RoleDataScopeEntity;
import com.kevindai.base.tenantseparate.entity.UserEntityEntity;
import com.kevindai.base.tenantseparate.repository.EntityClosureRepository;
import com.kevindai.base.tenantseparate.repository.RoleDataScopeRepository;
import com.kevindai.base.tenantseparate.repository.UserEntityRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RequiredArgsConstructor
@Slf4j
@Service
public class DataScopeService {

    private final RoleDataScopeRepository roleDataScopeRepository;
    private final UserEntityRepository userEntityRepository;
    private final EntityClosureRepository entityClosureRepository;

    public UserDataScope currentScope() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            String currentId = request.getHeader("x-current-id");

            log.info("current user id: {}", currentId);
            List<RoleDataScopeEntity> roleDataScopeEntities = roleDataScopeRepository.findByUserId(Long.valueOf(currentId));

            //get user entities
            List<UserEntityEntity> userEntityEntities = userEntityRepository.findByUserId(Long.valueOf(currentId));

            UserDataScope userDataScope = new UserDataScope();
            for (RoleDataScopeEntity roleDataScopeEntity : roleDataScopeEntities) {
                if (DataScope.ALL.name().equals(roleDataScopeEntity.getDataScope())) {
                    userDataScope.setAll(true);
                    return userDataScope;
                }

                if (DataScope.SELF.name().equals(roleDataScopeEntity.getDataScope())) {
                    userDataScope.getUserIds().add(Long.valueOf(currentId));
                }

                if (DataScope.ENTITY.name().equals(roleDataScopeEntity.getDataScope())) {
                    userDataScope.getUserIds().add(Long.valueOf(currentId));
                    userEntityEntities.forEach(ue -> userDataScope.getEntityIds().add(Long.valueOf(ue.getEntity().getId())));
                }

                if (DataScope.ENTITY_AND_CHILDREN.name().equals(roleDataScopeEntity.getDataScope())) {
                    List<Long> entityIds = userEntityEntities.stream().map(ue -> Long.valueOf(ue.getEntity().getId())).toList();
                    Set<Long> entityAndSubEntityIds = entityClosureRepository.findEntityAndSubByAncestorIds(entityIds);
                    userDataScope.getUserIds().add(Long.valueOf(currentId));
                    userDataScope.getEntityIds().addAll(entityAndSubEntityIds);
                }

            }

            return userDataScope;
        }
        return null;
    }
}
