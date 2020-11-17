package com.example.communityweb.resolver;

import com.example.communityweb.domain.User;
import com.example.communityweb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;
import java.security.Security;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.example.communityweb.domain.enums.SocialType.KAKAO;


@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private UserRepository userRepository;

    public boolean supportsParameter(MethodParameter parameter){
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest().getSession();
        User user = (User) session.getAttribute("user");
        return getUser(user,session);
    }

    private User getUser(User user, HttpSession session){
        if(user == null){
            try {
                OAuth2AuthenticationToken authentication = (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
                Map<String, Object> map = authentication.getPrincipal().getAttributes();
                User convertUser = convertUser(authentication.getAuthorizedClientRegistrationId(),map);

                user = userRepository.findByEmail(convertUser.getEmail());
                if(user == null){
                    user = userRepository.save(convertUser);
                }
                setRoleIfNotSame(user,authentication,map);
                session.setAttribute("user",user);
            }catch (ClassCastException e){
                return user;
            }
        }
        return user;
    }

    private User convertUser(String authority, Map<String, Object> map){
        if(KAKAO.getValue().equals(authority)){
            return getKakaoUser(map);
        }
        return null;
    }
    private User getKakaoUser(Map<String,Object> map){
        HashMap<String,String> propertyMap = (HashMap<String,String>) map.get("properties");
        return User.builder().name(propertyMap.get("nickname"))
                .email(String.valueOf(map.get("kaccount_email")))
                .principal(String.valueOf(map.get("id")))
                .socialType(KAKAO)
                .createdDate(LocalDateTime.now())
                .build();
    }
    private void setRoleIfNotSame(User user, OAuth2AuthenticationToken token, Map<String, Object> map){
        if(!token.getAuthorities().contains(new SimpleGrantedAuthority(user.getSocialType().getRoleType()))){
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                    map,"N/A", AuthorityUtils.createAuthorityList(user.getSocialType().getRoleType())
            ));
        }
    }
}
