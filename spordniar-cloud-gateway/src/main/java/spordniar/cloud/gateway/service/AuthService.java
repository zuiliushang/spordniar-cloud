package spordniar.cloud.gateway.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import spordniar.cloud.gateway.entity.AppInfo;
import spordniar.cloud.gateway.entity.Auth;
import spordniar.cloud.gateway.entity.PartnerRole;
import spordniar.cloud.gateway.entity.ResourceDTO;
import spordniar.cloud.gateway.entity.RoleAuth;
import spordniar.cloud.gateway.status.ResourceType;
import spordniar.cloud.gateway.util.DataContainer;

@Service
public class AuthService {

	private static Logger LOGGER = LoggerFactory.getLogger(AuthService.class);
	
	public List<ResourceDTO> getAllResourceByResourceType(ResourceType type){
		return DataContainer.authResources
			.stream()
			.filter(r->type.value() == r.getResourceType())
			.map(resource->{
				ResourceDTO dto = new ResourceDTO();
				Auth auth = getAuthById(resource.getAuthId());
				dto.setAuthName(auth.getAuthName());
				dto.setResource(resource.getResource());
				dto.setVerifyType(resource.getVerifyType());
				dto.setMatchOrder(resource.getMatchOrder());
				return dto;
			}).collect(Collectors.toList());
	}
	
	public Auth getAuthById(Integer authId) {
		return DataContainer.auths
				.stream()
				.filter(auth->
					authId.equals(auth.getAuthId())
				)
				.findAny().get();
	}
	
	public Optional<ResourceDTO> getResourceAuth(String requestURI){
		Optional<ResourceDTO> rsdOp = Optional.empty();
		List<ResourceDTO> resourceDTOs = getAllResourceByResourceType(ResourceType.URI);
		Collections.sort(resourceDTOs, (o1, o2) -> o1.getMatchOrder() - o2.getMatchOrder());
		for (ResourceDTO resourceDTO : resourceDTOs) {
			String res = resourceDTO.getResource();
			Pattern pattern = Pattern.compile(res);
			Matcher matcher = pattern.matcher(requestURI);
			if (matcher.find()) {
				rsdOp = Optional.of(resourceDTO);
				LOGGER.info("uri={}, match resource {}", requestURI, res);
			}
			break;
		}
		return rsdOp;
	}
	
	public AppInfo getAppInfoByAppId(String appId) {
		Optional<AppInfo> o = DataContainer.appInfos.stream().filter(app->app.getAppId().equals(appId)).findAny();
		if (!o.isPresent()) {
			return null;
		}
		return o.get();
	}
	
	public List<PartnerRole> getPartnerRoleByPartnerId(Integer partnerId) {
		return DataContainer.partnerRoles.stream().filter(p->p.getPartnerId().equals(partnerId)).collect(Collectors.toList());
	}
	
	public List<RoleAuth> getRoleAuthByRoleId(Integer roleId) {
		return DataContainer.roleAuths.stream().filter(r->r.getRoleId().equals(roleId)).collect(Collectors.toList());
	}
	
	public List<Auth> findAuthByAppId(String appId) {
		try {
			AppInfo appInfo = getAppInfoByAppId(appId);
			if (appInfo == null) {
				return null;
			}
			Integer partnerId = appInfo.getPartnerId();
			List<PartnerRole> partnerRoles = getPartnerRoleByPartnerId(partnerId);
			List<Auth> auths =partnerRoles
				.stream()
				.map(p->{
					Integer roleId = p.getRoleId();
					List<RoleAuth> roleAuth = getRoleAuthByRoleId(roleId);
					return roleAuth;
				})
				.reduce(new ArrayList<Auth>(), 
					(t1,t2)->
					{
						t1.addAll(
							t2
							.stream()
							.map(r->getAuthById(r.getAuthId()))
							.collect(Collectors.toList())
						);
						return t1;
					}
					, (t1,t2)->t1=t2);
			return auths;
		} catch (Exception e) {
			return null;
		}
	}
	
}
