package spordniar.cloud.gateway.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import spordniar.cloud.gateway.entity.Auth;
import spordniar.cloud.gateway.entity.ResourceDTO;
import spordniar.cloud.gateway.status.ResourceType;
import spordniar.cloud.gateway.util.DataContainer;

@Service
public class AuthServer {

	private static Logger LOGGER = LoggerFactory.getLogger(AuthServer.class);
	
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
	
}
