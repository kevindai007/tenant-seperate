package com.kevindai.base.tenantseparate.datapermission;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserDataScope {
    private boolean isAll;
    private List<Long> userIds = new ArrayList<>();
    private List<Long> entityIds = new ArrayList<>();
}
