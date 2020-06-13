package spordniar.cloud.gateway.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import spordniar.cloud.gateway.entity.Auth;
import spordniar.cloud.gateway.entity.AuthResource;
import spordniar.cloud.gateway.entity.Role;
import spordniar.cloud.gateway.entity.RoleAuth;
import spordniar.cloud.gateway.entity.User;
import spordniar.cloud.gateway.entity.UserRole;
import spordniar.cloud.gateway.status.CommonStatus;
import spordniar.cloud.gateway.status.ResourceType;
import spordniar.cloud.gateway.status.VerifyType;

public class DataContainer {

	private DataContainer() {}

	public static List<User> users = new ArrayList<User>();
	public static List<Auth> auths = new ArrayList<Auth>();
	public static List<AuthResource> authResources = new ArrayList<AuthResource>();
	public static List<Role> roles = new ArrayList<Role>();
	public static List<RoleAuth> roleAuths = new ArrayList<RoleAuth>();
	public static List<UserRole> userRoles = new ArrayList<UserRole>();
	
	static {
		users.addAll(Arrays.asList(
				new User(1, CommonStatus.ENABLE.value(), "admin1",1 ,(byte)24, (byte)4,"123456"),
				new User(2, CommonStatus.DISABLE.value(), "admin2",1, (byte)24, (byte)4,"123456"),
				new User(3, CommonStatus.ENABLE.value(), "admin3", 2,(byte)24, (byte)4,"123456")
				));
		auths.addAll(Arrays.asList(
				new Auth(1, "SERVER_CONSUMER"),
				new Auth(2, "SERVER_PRODUCT"),
				new Auth(3, "SERVER_AGGREGATION")
				));
		roles.addAll(Arrays.asList(
				new Role(1, "PRODUCT"),
				new Role(2, "CONSUMER"),
				new Role(3, "ALL")
				));
		roleAuths.addAll(Arrays.asList(
				new RoleAuth(1, 1, 2),// PRODUCT -> SERVER_PRODUCT
				new RoleAuth(2, 2, 1),// CONSUMER -> SERVER_CONSUMER
				new RoleAuth(3, 3, 1),// ALL -> CONSUMER, PRODUCT, AGGREGATION
				new RoleAuth(4, 3, 2),
				new RoleAuth(5, 3, 3)
				));
		userRoles.addAll(Arrays.asList(
				new UserRole(1, 3, 1),// admin1 -> ALL
				new UserRole(2, 1, 2),// admin2 -> PRODUCT
				new UserRole(3, 2, 3)// admin3 -> CONSUMER
				));
		authResources.addAll(Arrays.asList(
				new AuthResource(1, 1, "/v(\\d+)/server/comsumer/*", (short)1, ResourceType.URI.value(), VerifyType.SIGN.value()),
				new AuthResource(2, 2, "/v(\\d+)/server/product/*", (short)1, ResourceType.URI.value(), VerifyType.TOKEN.value()),
				new AuthResource(3, 3, "/v(\\d+)/server/aggregation/*", (short)1, ResourceType.URI.value(), VerifyType.TOKEN.value())
				));
	}
	
}
