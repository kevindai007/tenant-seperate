package com.kevindai.base.tenantseparate.datapermission;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class DataScopeService {
    private Map<String, UserDataScope> userDataScopeMap = Map.of(
            "1", new UserDataScope() {{
                setAll(true);
            }},
            "2", new UserDataScope() {{
                setAll(false);
                setUserIds(List.of(2L, 3L, 4L));
                setEntityIds(List.of(1L));
            }},
            "3", new UserDataScope() {{
                setAll(false);
                setUserIds(List.of(3L, 4L));
                setEntityIds(List.of(2L));
            }},
            "4", new UserDataScope() {{
                setAll(false);
                setUserIds(List.of(4L));
                setEntityIds(List.of(3L));
            }}
    );

    public UserDataScope currentScope() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            String currentId = request.getHeader("x-current-id");
            log.info("current user id: {}", currentId);
            return userDataScopeMap.get(currentId);
        }
        return null;
    }
}
