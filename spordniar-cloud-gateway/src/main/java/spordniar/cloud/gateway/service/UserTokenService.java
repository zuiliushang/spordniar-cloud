package spordniar.cloud.gateway.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import spordniar.cloud.gateway.util.UserTokenDTO;

@Service
public class UserTokenService {
	
	public Optional<List<UserTokenDTO>> loadUserAvailableTokens(String parentUserId){
		
		return Optional.ofNullable(null);
	}
	
}
