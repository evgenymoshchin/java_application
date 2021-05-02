package com.gmail.evgenymoshchin.web.config.handlers;

import com.gmail.evgenymoshchin.repository.model.RoleEnum;
import com.gmail.evgenymoshchin.service.model.UserLogin;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.gmail.evgenymoshchin.web.constants.ConfigConstant.ADMIN_URL;
import static com.gmail.evgenymoshchin.web.constants.ConfigConstant.CUSTOMER_USER_URL;
import static com.gmail.evgenymoshchin.web.constants.ConfigConstant.SALE_USER_URL;
import static com.gmail.evgenymoshchin.web.constants.ConfigConstant.SECURE_API_USER_URL;

public class AppUrlSuccessHandler implements AuthenticationSuccessHandler {

    private final Map<RoleEnum, String> roleMap = new HashMap<>() {{
        put(RoleEnum.ROLE_ADMINISTRATOR, ADMIN_URL);
        put(RoleEnum.ROLE_SALE_USER, SALE_USER_URL);
        put(RoleEnum.ROLE_CUSTOMER_USER, CUSTOMER_USER_URL);
        put(RoleEnum.ROLE_SECURE_API_USER, SECURE_API_USER_URL);
    }};

    private String getRedirectionUrl(RoleEnum name) {
        return roleMap.get(name);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException {
        UserLogin userLogin = (UserLogin) authentication.getPrincipal();
        RoleEnum role = userLogin.getUser().getRole().getName();
        String redirectionUrl = getRedirectionUrl(role);
        httpServletResponse.sendRedirect(redirectionUrl);
    }
}
