package org.wso2.carbon.identity.api.idpmgt.endpoint;

import org.wso2.carbon.identity.api.idpmgt.Constants;
import org.wso2.carbon.identity.api.idpmgt.IDPMgtBridgeServiceException;
import org.wso2.carbon.identity.api.idpmgt.endpoint.Exceptions.BadRequestException;
import org.wso2.carbon.identity.api.idpmgt.endpoint.Exceptions.InternalServerErrorException;
import org.wso2.carbon.identity.api.idpmgt.endpoint.dto.ApplicationPermissionDTO;
import org.wso2.carbon.identity.api.idpmgt.endpoint.dto.ClaimConfigDTO;
import org.wso2.carbon.identity.api.idpmgt.endpoint.dto.ErrorDTO;
import org.wso2.carbon.identity.api.idpmgt.endpoint.dto.FederatedAuthenticatorConfigDTO;
import org.wso2.carbon.identity.api.idpmgt.endpoint.dto.IdPDetailDTO;
import org.wso2.carbon.identity.api.idpmgt.endpoint.dto.IdentityProviderPropertyDTO;
import org.wso2.carbon.identity.api.idpmgt.endpoint.dto.JustInTimeProvisioningConfigDTO;
import org.wso2.carbon.identity.api.idpmgt.endpoint.dto.LocalRoleDTO;
import org.wso2.carbon.identity.api.idpmgt.endpoint.dto.PermissionsAndRoleConfigDTO;
import org.wso2.carbon.identity.api.idpmgt.endpoint.dto.PropertyDTO;
import org.wso2.carbon.identity.api.idpmgt.endpoint.dto.ProvisioningConnectorConfigDTO;
import org.wso2.carbon.identity.api.idpmgt.endpoint.dto.RoleMappingDTO;
import org.wso2.carbon.identity.application.common.model.ApplicationPermission;
import org.wso2.carbon.identity.application.common.model.ClaimConfig;
import org.wso2.carbon.identity.application.common.model.FederatedAuthenticatorConfig;
import org.wso2.carbon.identity.application.common.model.IdentityProvider;
import org.apache.commons.logging.Log;
import org.wso2.carbon.identity.application.common.model.IdentityProviderProperty;
import org.wso2.carbon.identity.application.common.model.JustInTimeProvisioningConfig;
import org.wso2.carbon.identity.application.common.model.LocalRole;
import org.wso2.carbon.identity.application.common.model.PermissionsAndRoleConfig;
import org.wso2.carbon.identity.application.common.model.Property;
import org.wso2.carbon.identity.application.common.model.ProvisioningConnectorConfig;
import org.wso2.carbon.identity.application.common.model.RoleMapping;
import org.wso2.carbon.idp.mgt.IdentityProviderManagementException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EndpointUtils {

    public static IdPDetailDTO translateIDPToIDPDetail(IdentityProvider identityProvider) {

        if (identityProvider == null) {
            return null;
        }
        IdPDetailDTO identityProviderDTO = new IdPDetailDTO();

        //Basic information related to Identity Provider
        identityProviderDTO.setDisplayName(identityProvider.getIdentityProviderName());
        identityProviderDTO.setEnable(identityProvider.isEnable());
        identityProviderDTO.setHomeRealmId(identityProvider.getHomeRealmId());
        identityProviderDTO.setId(identityProvider.getId());
        identityProviderDTO.setAlias(identityProvider.getAlias());
        identityProviderDTO.setFederationHub(identityProvider.isFederationHub());
        identityProviderDTO.setCertificate(identityProvider.getCertificate());
        identityProviderDTO.setPrimary(identityProvider.isPrimary());
        identityProviderDTO.setProvisioningRole(identityProvider.getAlias());
        identityProviderDTO.setIdentityProviderName(identityProvider.getAlias());
        identityProviderDTO.setIdentityProviderDescription(identityProvider.getIdentityProviderDescription());

        //Default authenticated configurations
        FederatedAuthenticatorConfig federatedAuthenticatorConfig = identityProvider
                .getDefaultAuthenticatorConfig();
        identityProviderDTO.setDefaultAuthenticatorConfig(createDefaultAuthenticatorDTO(federatedAuthenticatorConfig));

        //Claim configurations
        ClaimConfig claimConfig = identityProvider.getClaimConfig();
        identityProviderDTO.setClaimConfig(createClaimConfigDTO(claimConfig));

        //Provisioning Connector Configuration
        ProvisioningConnectorConfig provisioningConnectorConfig = identityProvider
                .getDefaultProvisioningConnectorConfig();
        identityProviderDTO.setDefaultProvisioningConnectorConfig(createProvisioningConnectorConfigDTO
                (provisioningConnectorConfig));

        //Federated authenticator configurations
        FederatedAuthenticatorConfig[] federatedAuthenticatorConfigs = identityProvider
                .getFederatedAuthenticatorConfigs();
        List<FederatedAuthenticatorConfigDTO> federatedAuthenticatorConfigDTOs = createFederatorAuthenticatorDTOList
                (Arrays.asList(federatedAuthenticatorConfigs));
        identityProviderDTO.setFederatedAuthenticatorConfigs(federatedAuthenticatorConfigDTOs);

        //Identity Provider Properties
        IdentityProviderProperty[] identityProviderProperties = identityProvider.getIdpProperties();
        List<IdentityProviderPropertyDTO> identityProviderPropertyDTOs = createIdentityProviderDTOProperties
                (Arrays.asList(identityProviderProperties));
        identityProviderDTO.setIdpProperties(identityProviderPropertyDTOs);

        // JustInTime Provisioning Configurations
        JustInTimeProvisioningConfigDTO justInTimeProvisioningConfigDTO = createJustinTimeProvisioningConfigDTO
                (identityProvider.getJustInTimeProvisioningConfig());
        identityProviderDTO.setJustInTimeProvisioningConfig(justInTimeProvisioningConfigDTO);

        //Permissions And Role Configurations
        identityProviderDTO.setPermissionAndRoleConfig(createPermissionAndRoleConfigDTO(identityProvider
                .getPermissionAndRoleConfig()));

        //Provisioning Connector Configurations
        ProvisioningConnectorConfig[] provisioningConnectorConfigs = identityProvider
                .getProvisioningConnectorConfigs();
        List<ProvisioningConnectorConfigDTO> provisioningConnectorConfigDTOs = createProvisioningConnectorConfigDTOs
                (Arrays.asList(provisioningConnectorConfigs));
        identityProviderDTO.setProvisioningConnectorConfigs(provisioningConnectorConfigDTOs);

        return identityProviderDTO;
    }

    public static IdentityProvider translateIDPDetailToIDP(IdPDetailDTO identityProviderDTO) {

        if (identityProviderDTO == null) {
            return null;
        }
        IdentityProvider identityProvider = new IdentityProvider();

        //Basic information related to Identity Provider
        identityProvider.setIdentityProviderName(identityProviderDTO.getDisplayName());
        identityProvider.setId(identityProviderDTO.getId());
        identityProvider.setAlias(identityProviderDTO.getAlias());
        identityProvider.setCertificate(identityProviderDTO.getCertificate());
        identityProvider.setEnable(identityProviderDTO.getEnable());
        identityProvider.setFederationHub(identityProviderDTO.getFederationHub());
        identityProvider.setIdentityProviderDescription(identityProviderDTO.getIdentityProviderDescription());
        identityProvider.setPrimary(identityProviderDTO.getPrimary());
        identityProvider.setProvisioningRole(identityProviderDTO.getProvisioningRole());
        identityProvider.setDisplayName(identityProviderDTO.getDisplayName());
        identityProvider.setHomeRealmId(identityProviderDTO.getHomeRealmId());

        //Default authenticated configurations
        FederatedAuthenticatorConfigDTO federatedAuthenticatorConfigDTO = identityProviderDTO
                .getDefaultAuthenticatorConfig();
        identityProvider.setDefaultAuthenticatorConfig(createDefaultAuthenticator(federatedAuthenticatorConfigDTO));

        //Claim configurations
        ClaimConfigDTO claimConfigDTO = identityProviderDTO.getClaimConfig();
        identityProvider.setClaimConfig(createClaimConfig(claimConfigDTO));

        //Provisioning Connector Configuration
        ProvisioningConnectorConfigDTO provisioningConnectorConfigDTO = identityProviderDTO
                .getDefaultProvisioningConnectorConfig();
        identityProvider.setDefaultProvisioningConnectorConfig(createProvisioningConnectorConfig
                (provisioningConnectorConfigDTO));

        //Federated authenticator configurations
        List<FederatedAuthenticatorConfigDTO> federatedAuthenticatorConfigDTOS = identityProviderDTO
                .getFederatedAuthenticatorConfigs();
        List<FederatedAuthenticatorConfig> federatedAuthenticatorConfigs = createFederatorAuthenticatorList
                (federatedAuthenticatorConfigDTOS);
        identityProvider.setFederatedAuthenticatorConfigs(federatedAuthenticatorConfigs.toArray(new
                FederatedAuthenticatorConfig[0]));

        //Identity Provider Properties
        List<IdentityProviderPropertyDTO> identityProviderPropertyDTOS = identityProviderDTO.getIdpProperties();
        List<IdentityProviderProperty> identityProviderProperties = createIdentityProviderProperties
                (identityProviderPropertyDTOS);
        identityProvider.setIdpProperties(identityProviderProperties.toArray(new IdentityProviderProperty[0]));

        // JustInTime Provisioning Configurations
        JustInTimeProvisioningConfig justInTimeProvisioningConfig = createJustinTimeProvisioningConfig
                (identityProviderDTO.getJustInTimeProvisioningConfig());
        identityProvider.setJustInTimeProvisioningConfig(justInTimeProvisioningConfig);

        //Permissions And Role Configurations
        identityProvider.setPermissionAndRoleConfig(createPermissionAndRoleConfig(identityProviderDTO
                .getPermissionAndRoleConfig()));

        //Provisioning Connector Configurations
        List<ProvisioningConnectorConfigDTO> provisioningConnectorConfigDTOs = identityProviderDTO
                .getProvisioningConnectorConfigs();
        List<ProvisioningConnectorConfig> provisioningConnectorConfigs = createProvisioningConnectorConfigs
                (provisioningConnectorConfigDTOs);
        identityProvider.setProvisioningConnectorConfigs(provisioningConnectorConfigs.toArray(new
                ProvisioningConnectorConfig[0]));

        return identityProvider;
    }

    public static List<IdPDetailDTO> translateIDPDetailList(List<IdentityProvider> idpList) {

        List<IdPDetailDTO> idpListResponseDTOList = new ArrayList<>();
        if (idpList != null) {
            idpList.forEach(idp -> idpListResponseDTOList.add(translateIDPToIDPDetail(idp)));
        }
        return idpListResponseDTOList;
    }

    private static List<ProvisioningConnectorConfig> createProvisioningConnectorConfigs
            (List<ProvisioningConnectorConfigDTO> provisioningConnectorConfigDTOs) {

        List<ProvisioningConnectorConfig> provisioningConnectorConfigs = new ArrayList<>();
        if (provisioningConnectorConfigDTOs != null) {
            provisioningConnectorConfigDTOs.forEach(provisioningConnectorConfigDTO ->
                    provisioningConnectorConfigs.add(createProvisioningConnectorConfig(provisioningConnectorConfigDTO)));
        }
        return provisioningConnectorConfigs;
    }

    public static List<ProvisioningConnectorConfigDTO> createProvisioningConnectorConfigDTOs
            (List<ProvisioningConnectorConfig> provisioningConnectorConfigs) {

        List<ProvisioningConnectorConfigDTO> provisioningConnectorConfigDTOs = new ArrayList<>();
        if (provisioningConnectorConfigs != null) {
            provisioningConnectorConfigs.forEach(provisioningConnectorConfig ->
                    provisioningConnectorConfigDTOs.add(createProvisioningConnectorConfigDTO(provisioningConnectorConfig)));
        }
        return provisioningConnectorConfigDTOs;
    }

    public static PermissionsAndRoleConfig createPermissionAndRoleConfig(PermissionsAndRoleConfigDTO
                                                                                 permissionsAndRoleConfigDTO) {

        PermissionsAndRoleConfig permissionsAndRoleConfig = null;

        if (permissionsAndRoleConfigDTO != null) {
            permissionsAndRoleConfig = new PermissionsAndRoleConfig();
            permissionsAndRoleConfig.setIdpRoles(permissionsAndRoleConfigDTO.getIdpRoles().toArray(new String[0]));

            List<ApplicationPermissionDTO> applicationPermissionDTOS = permissionsAndRoleConfigDTO.getPermissions();
            List<ApplicationPermission> applicationPermissions = new ArrayList<>();
            for (ApplicationPermissionDTO applicationPermissionDTO : applicationPermissionDTOS) {
                ApplicationPermission applicationPermission = new ApplicationPermission();
                applicationPermission.setValue(applicationPermissionDTO.getValue());
                applicationPermissions.add(applicationPermission);
            }
            permissionsAndRoleConfig.setPermissions(applicationPermissions.toArray(new ApplicationPermission[0]));

            List<RoleMappingDTO> roleMappingDTOS = permissionsAndRoleConfigDTO.getRoleMappings();
            List<RoleMapping> roleMappings = new ArrayList<>();
            for (RoleMappingDTO roleMappingDTO : roleMappingDTOS) {
                RoleMapping roleMapping = new RoleMapping();
                roleMapping.setLocalRole(new LocalRole(roleMappingDTO.getLocalRole().getUserStoreId(), roleMappingDTO
                        .getLocalRole().getLocalRoleName()));
                roleMapping.setRemoteRole(roleMappingDTO.getRemoteRole());
                roleMappings.add(roleMapping);
            }
            permissionsAndRoleConfig.setRoleMappings(roleMappings.toArray(new RoleMapping[0]));
        }
        return permissionsAndRoleConfig;
    }

    public static PermissionsAndRoleConfigDTO createPermissionAndRoleConfigDTO(PermissionsAndRoleConfig
                                                                                       permissionsAndRoleConfig) {

        PermissionsAndRoleConfigDTO permissionsAndRoleConfigDTO = null;
        if (permissionsAndRoleConfig != null) {
            permissionsAndRoleConfigDTO = new PermissionsAndRoleConfigDTO();
            permissionsAndRoleConfigDTO.setIdpRoles(Arrays.asList(permissionsAndRoleConfig.getIdpRoles()));

            ApplicationPermission[] applicationPermissions = permissionsAndRoleConfig.getPermissions();
            List<ApplicationPermissionDTO> applicationPermissionDTOs = new ArrayList<>();
            for (ApplicationPermission applicationPermission : applicationPermissions) {
                ApplicationPermissionDTO applicationPermissionDTO = new ApplicationPermissionDTO();
                applicationPermissionDTO.setValue(applicationPermission.getValue());
                applicationPermissionDTOs.add(applicationPermissionDTO);
            }
            permissionsAndRoleConfigDTO.setPermissions(applicationPermissionDTOs);

            RoleMapping[] roleMappings = permissionsAndRoleConfig.getRoleMappings();
            List<RoleMappingDTO> roleMappingDTOs = new ArrayList<>();
            for (RoleMapping roleMapping : roleMappings) {
                RoleMappingDTO roleMappingDTO = new RoleMappingDTO();
                LocalRoleDTO localRoleDTO = new LocalRoleDTO();
                localRoleDTO.setLocalRoleName(roleMapping.getLocalRole().getLocalRoleName());
                localRoleDTO.setUserStoreId(roleMapping.getLocalRole().getUserStoreId());
                roleMappingDTO.setLocalRole(localRoleDTO);
                roleMapping.setRemoteRole(roleMappingDTO.getRemoteRole());
            }
            permissionsAndRoleConfigDTO.setRoleMappings(roleMappingDTOs);
        }
        return permissionsAndRoleConfigDTO;
    }

    public static JustInTimeProvisioningConfig createJustinTimeProvisioningConfig
            (JustInTimeProvisioningConfigDTO justInTimeProvisioningConfigDTO) {

        JustInTimeProvisioningConfig justInTimeProvisioningConfig = null;
        if (justInTimeProvisioningConfigDTO != null) {
            justInTimeProvisioningConfig = new JustInTimeProvisioningConfig();
            justInTimeProvisioningConfig.setModifyUserNameAllowed(justInTimeProvisioningConfigDTO
                    .getModifyUserNameAllowed());
            justInTimeProvisioningConfig.setPasswordProvisioningEnabled(justInTimeProvisioningConfigDTO
                    .getPasswordProvisioningEnabled());
            justInTimeProvisioningConfig.setPromptConsent(justInTimeProvisioningConfigDTO.getPromptConsent());
            justInTimeProvisioningConfig.setUserStoreClaimUri(justInTimeProvisioningConfigDTO.getUserStoreClaimUri());
        }
        return justInTimeProvisioningConfig;
    }

    public static JustInTimeProvisioningConfigDTO createJustinTimeProvisioningConfigDTO
            (JustInTimeProvisioningConfig justInTimeProvisioningConfig) {

        JustInTimeProvisioningConfigDTO justInTimeProvisioningConfigDTO = null;
        if (justInTimeProvisioningConfig != null) {
            justInTimeProvisioningConfigDTO = new JustInTimeProvisioningConfigDTO();
            justInTimeProvisioningConfigDTO.setModifyUserNameAllowed(justInTimeProvisioningConfig
                    .isModifyUserNameAllowed());
            justInTimeProvisioningConfigDTO.setPasswordProvisioningEnabled(justInTimeProvisioningConfig
                    .isPasswordProvisioningEnabled());
            justInTimeProvisioningConfigDTO.setPromptConsent(justInTimeProvisioningConfig.isPromptConsent());
            justInTimeProvisioningConfigDTO.setUserStoreClaimUri(justInTimeProvisioningConfig.getUserStoreClaimUri());
        }
        return justInTimeProvisioningConfigDTO;
    }

    private static List<IdentityProviderProperty> createIdentityProviderProperties(List<IdentityProviderPropertyDTO>
                                                                                           identityProviderPropertyDTOS) {

        List<IdentityProviderProperty> identityProviderProperties = new ArrayList<>();
        if (identityProviderPropertyDTOS != null) {
            identityProviderPropertyDTOS.forEach(identityProviderPropertyDTO -> {
                IdentityProviderProperty identityProviderProperty = new IdentityProviderProperty();
                identityProviderProperty.setDisplayName(identityProviderPropertyDTO.getDisplayName());
                identityProviderProperty.setName(identityProviderPropertyDTO.getName());
                identityProviderProperty.setValue(identityProviderPropertyDTO.getValue());
                identityProviderProperties.add(identityProviderProperty);
            });
        }
        return identityProviderProperties;
    }

    private static List<IdentityProviderPropertyDTO> createIdentityProviderDTOProperties
            (List<IdentityProviderProperty> identityProviderProperties) {

        List<IdentityProviderPropertyDTO> identityProviderDTOProperties = new ArrayList<>();
        if (identityProviderProperties != null) {
            identityProviderProperties.forEach(identityProviderProperty -> {
                IdentityProviderPropertyDTO identityProviderPropertyDTO = new IdentityProviderPropertyDTO();
                identityProviderPropertyDTO.setDisplayName(identityProviderProperty.getDisplayName());
                identityProviderPropertyDTO.setName(identityProviderProperty.getName());
                identityProviderPropertyDTO.setValue(identityProviderProperty.getValue());
                identityProviderDTOProperties.add(identityProviderPropertyDTO);
            });
        }
        return identityProviderDTOProperties;
    }

    private static List<FederatedAuthenticatorConfig> createFederatorAuthenticatorList
            (List<FederatedAuthenticatorConfigDTO> federatedAuthenticatorConfigDTOS) {

        List<FederatedAuthenticatorConfig> federatedAuthenticatorConfigs = new ArrayList<>();
        if (federatedAuthenticatorConfigDTOS != null) {
            for (FederatedAuthenticatorConfigDTO federatedAuthenticatorConfigDTO : federatedAuthenticatorConfigDTOS) {
                federatedAuthenticatorConfigs.add(createDefaultAuthenticator(federatedAuthenticatorConfigDTO));
            }
        }
        return federatedAuthenticatorConfigs;
    }

    public static List<FederatedAuthenticatorConfigDTO> createFederatorAuthenticatorDTOList
            (List<FederatedAuthenticatorConfig> federatedAuthenticatorConfigs) {

        List<FederatedAuthenticatorConfigDTO> federatedAuthenticatorConfigDTOs = new ArrayList<>();
        if (federatedAuthenticatorConfigs != null) {
            for (FederatedAuthenticatorConfig federatedAuthenticatorConfig : federatedAuthenticatorConfigs) {
                federatedAuthenticatorConfigDTOs.add(createDefaultAuthenticatorDTO(federatedAuthenticatorConfig));
            }
        }
        return federatedAuthenticatorConfigDTOs;
    }

    public static ProvisioningConnectorConfig createProvisioningConnectorConfig(ProvisioningConnectorConfigDTO
                                                                                        provisioningConnectorConfigDTO) {

        ProvisioningConnectorConfig provisioningConnectorConfig = null;
        if (provisioningConnectorConfigDTO != null) {
            provisioningConnectorConfig = new ProvisioningConnectorConfig();
            provisioningConnectorConfig.setBlocking(provisioningConnectorConfigDTO.getBlocking());
            provisioningConnectorConfig.setEnabled(provisioningConnectorConfigDTO.getEnabled());
            List<Property> propertyList = getPropertyList(provisioningConnectorConfigDTO.getProvisioningProperties());
            provisioningConnectorConfig.setProvisioningProperties(propertyList.toArray(new Property[0]));
            provisioningConnectorConfig.setRulesEnabled(provisioningConnectorConfigDTO.getRulesEnabled());
        }
        return provisioningConnectorConfig;
    }

    private static ProvisioningConnectorConfigDTO createProvisioningConnectorConfigDTO(ProvisioningConnectorConfig
                                                                                               provisioningConnectorConfig) {

        ProvisioningConnectorConfigDTO provisioningConnectorConfigDTO = null;
        if (provisioningConnectorConfig != null) {
            provisioningConnectorConfigDTO = new ProvisioningConnectorConfigDTO();
            provisioningConnectorConfigDTO.setBlocking(provisioningConnectorConfigDTO.getBlocking());
            provisioningConnectorConfigDTO.setEnabled(provisioningConnectorConfigDTO.getEnabled());
            List<PropertyDTO> propertyListDTO = getPropertyListDTO(Arrays.asList(provisioningConnectorConfig
                    .getProvisioningProperties()));
            provisioningConnectorConfigDTO.setProvisioningProperties(propertyListDTO);
            provisioningConnectorConfigDTO.setRulesEnabled(provisioningConnectorConfigDTO.getRulesEnabled());
        }
        return provisioningConnectorConfigDTO;
    }

    public static ClaimConfig createClaimConfig(ClaimConfigDTO claimConfigDTO) {

        ClaimConfig claimConfig = null;
        if (claimConfigDTO != null) {
            claimConfig = new ClaimConfig();
            claimConfig.setRoleClaimURI(claimConfigDTO.getRoleClaimURI());
            claimConfig.setAlwaysSendMappedLocalSubjectId(claimConfigDTO.getAlwaysSendMappedLocalSubjectId());
            claimConfig.setLocalClaimDialect(claimConfigDTO.getLocalClaimDialect());
            claimConfig.setRoleClaimURI(claimConfigDTO.getRoleClaimURI());
        }
        return claimConfig;
    }

    public static ClaimConfigDTO createClaimConfigDTO(ClaimConfig claimConfig) {

        ClaimConfigDTO claimConfigDTO = null;

        if (claimConfig != null) {
            claimConfigDTO = new ClaimConfigDTO();
            claimConfigDTO.setRoleClaimURI(claimConfig.getRoleClaimURI());
            claimConfigDTO.setAlwaysSendMappedLocalSubjectId(claimConfig.isAlwaysSendMappedLocalSubjectId());
            claimConfigDTO.setLocalClaimDialect(claimConfig.isLocalClaimDialect());
            claimConfigDTO.setRoleClaimURI(claimConfig.getRoleClaimURI());
        }
        return claimConfigDTO;
    }

    public static FederatedAuthenticatorConfig createDefaultAuthenticator(FederatedAuthenticatorConfigDTO
                                                                                  federatedAuthenticatorConfigDTO) {

        FederatedAuthenticatorConfig federatedAuthenticatorConfig = null;
        if (federatedAuthenticatorConfigDTO != null) {
            federatedAuthenticatorConfig = new FederatedAuthenticatorConfig();
            federatedAuthenticatorConfig.setDisplayName(federatedAuthenticatorConfigDTO.getDisplayName());
            federatedAuthenticatorConfig.setEnabled(federatedAuthenticatorConfigDTO.getEnabled());
            federatedAuthenticatorConfig.setName(federatedAuthenticatorConfigDTO.getName());

            List<Property> propertyList = getPropertyList(federatedAuthenticatorConfigDTO.getPropertyList());
            federatedAuthenticatorConfig.setProperties(propertyList.toArray(new Property[0]));
        }
        return federatedAuthenticatorConfig;
    }

    private static FederatedAuthenticatorConfigDTO createDefaultAuthenticatorDTO(FederatedAuthenticatorConfig
                                                                                         federatedAuthenticatorConfig) {

        FederatedAuthenticatorConfigDTO federatedAuthenticatorConfigDTO = null;
        if (federatedAuthenticatorConfig != null) {
            federatedAuthenticatorConfigDTO = new FederatedAuthenticatorConfigDTO();
            federatedAuthenticatorConfigDTO.setDisplayName(federatedAuthenticatorConfig.getDisplayName());
            federatedAuthenticatorConfigDTO.setEnabled(federatedAuthenticatorConfig.isEnabled());
            federatedAuthenticatorConfigDTO.setName(federatedAuthenticatorConfig.getName());

            List<PropertyDTO> propertyList = getPropertyListDTO(Arrays.asList(federatedAuthenticatorConfig.getProperties()));
            federatedAuthenticatorConfigDTO.setPropertyList(propertyList);
        }
        return federatedAuthenticatorConfigDTO;
    }

    private static List<PropertyDTO> getPropertyListDTO(List<Property> properties) {

        List<PropertyDTO> propertyList = new ArrayList<>();
        if (properties != null) {
            properties.forEach(property -> {
                PropertyDTO propertyDTO = new PropertyDTO();
                propertyDTO.setValue(property.getValue());
                propertyDTO.setAdvanced(property.isAdvanced());
                propertyDTO.setConfidential(property.isConfidential());
                propertyDTO.setDefaultValue(property.getDefaultValue());
                propertyDTO.setDisplayName(property.getDescription());
                propertyDTO.setDescription(property.getDisplayName());
                propertyDTO.setDisplayOrder(property.getDisplayOrder());
                propertyDTO.setName(property.getName());
                propertyDTO.setRequired(property.isRequired());
                propertyDTO.setType(property.getType());
                propertyDTO.setValue(property.getValue());
                propertyList.add(propertyDTO);
            });
        }

        return propertyList;
    }

    private static List<Property> getPropertyList(List<PropertyDTO> propertyDTOS) {

        List<Property> propertyList = new ArrayList<>();
        if (propertyDTOS != null) {
            propertyDTOS.forEach(propertyDTO -> {
                Property property = new Property();
                property.setValue(propertyDTO.getValue());
                property.setAdvanced(propertyDTO.getAdvanced());
                property.setConfidential(propertyDTO.getConfidential());
                property.setDefaultValue(propertyDTO.getDefaultValue());
                property.setDisplayName(propertyDTO.getDescription());
                property.setDescription(propertyDTO.getDisplayName());
                property.setDisplayOrder(propertyDTO.getDisplayOrder());
                property.setName(propertyDTO.getName());
                property.setRequired(propertyDTO.getRequired());
                property.setType(propertyDTO.getType());
                property.setValue(propertyDTO.getValue());
                propertyList.add(property);
            });
        }

        return propertyList;
    }

    public static InternalServerErrorException buildInternalServerErrorException(String errorCode,
                                                                                 Log log,
                                                                                 IdentityProviderManagementException
                                                                                         exception) {

        ErrorDTO errorDTO = getErrorDTO(Constants.STATUS_INTERNAL_SERVER_ERROR_MESSAGE_DEFAULT,
                Constants.STATUS_INTERNAL_SERVER_ERROR_MESSAGE_DEFAULT, errorCode);
        logError(log, exception);
        return new InternalServerErrorException(errorDTO);
    }

    public static BadRequestException buildBadRequestException(String description, String code,
                                                               Log log, IDPMgtBridgeServiceException e) {

        ErrorDTO errorDTO = getErrorDTO(Constants.STATUS_BAD_REQUEST_MESSAGE_DEFAULT, description, code);
        logDebug(log, e);
        return new BadRequestException(errorDTO);
    }

    private static void logDebug(Log log, Throwable throwable) {

        if (log.isDebugEnabled()) {
            log.debug(Constants.STATUS_BAD_REQUEST_MESSAGE_DEFAULT, throwable);
        }
    }

    private static void logError(Log log, Throwable throwable) {

        log.error(throwable.getMessage(), throwable);
    }

    private static ErrorDTO getErrorDTO(String message, String description, String code) {

        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setCode(code);
        errorDTO.setMessage(message);
        errorDTO.setDescription(description);
        return errorDTO;
    }
}