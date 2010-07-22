/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.smartitengineering.user.service.impl;

import com.smartitengineering.dao.common.CommonReadDao;
import com.smartitengineering.dao.common.CommonWriteDao;
import com.smartitengineering.dao.common.queryparam.MatchMode;
import com.smartitengineering.dao.common.queryparam.QueryParameter;
import com.smartitengineering.dao.common.queryparam.QueryParameterFactory;
import com.smartitengineering.dao.impl.hibernate.AbstractCommonDaoImpl;
import com.smartitengineering.domain.PersistentDTO;
import com.smartitengineering.user.domain.Organization;
import com.smartitengineering.user.domain.Privilege;
import com.smartitengineering.user.domain.Role;
import com.smartitengineering.user.domain.SecuredObject;
import com.smartitengineering.user.domain.UniqueConstrainedField;
import com.smartitengineering.user.domain.User;
import com.smartitengineering.user.filter.OrganizationFilter;
import com.smartitengineering.user.service.ExceptionMessage;
import com.smartitengineering.user.service.OrganizationService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.StaleStateException;
import org.hibernate.exception.ConstraintViolationException;

/**
 *
 * @author modhu7
 */
public class OrganizationServiceImpl extends AbstractCommonDaoImpl<Organization> implements OrganizationService{

    public OrganizationServiceImpl() {
        setEntityClass(Organization.class);
    }

    

    @Override
    public void save(Organization organization) {
        validateOrganization(organization);
        try {
            super.save(organization);
        } catch (ConstraintViolationException e) {
            String message = ExceptionMessage.CONSTRAINT_VIOLATION_EXCEPTION.name() + "-" + UniqueConstrainedField.OTHER;
            throw new RuntimeException(message, e);
        } catch (StaleStateException e) {
            String message =
                    ExceptionMessage.STALE_OBJECT_STATE_EXCEPTION.name() + "-" +
                    UniqueConstrainedField.OTHER;
            throw new RuntimeException(message, e);
        }
    }

    @Override
    public void update(Organization organization) {
        validateOrganization(organization);
        try {
            super.update(organization);
        } catch (ConstraintViolationException e) {
            String message = ExceptionMessage.CONSTRAINT_VIOLATION_EXCEPTION.name() + "-" + UniqueConstrainedField.OTHER;
            throw new RuntimeException(message, e);
        } catch (StaleStateException e) {
            String message =
                    ExceptionMessage.STALE_OBJECT_STATE_EXCEPTION.name() + "-" +
                    UniqueConstrainedField.OTHER;
            throw new RuntimeException(message, e);
        }
    }

    @Override
    public void delete(Organization organization) {
        try {
            super.delete(organization);
        } catch (RuntimeException e) {
            String message = ExceptionMessage.CONSTRAINT_VIOLATION_EXCEPTION.name() + "-" + UniqueConstrainedField.ORGANIZATION;
            throw new RuntimeException(message, e);
        }
    }

    @Override
    public Collection<Organization> search(OrganizationFilter filter) {
        QueryParameter qp;
        List<QueryParameter> queryParameters = new ArrayList<QueryParameter>();
        if (!StringUtils.isEmpty(filter.getOrganizationUniqueShortName())) {
            qp = QueryParameterFactory.getEqualPropertyParam("uniqueShortName",
                    filter.getOrganizationUniqueShortName());
            queryParameters.add(qp);
        }
        Collection<Organization> organizations = new HashSet<Organization>();
        if (queryParameters.isEmpty()) {
            try {
                organizations = super.getAll();
            } catch (Exception e) {
            }
        } else {
            organizations = super.getList(queryParameters);
        }
        return organizations;
    }

    @Override
    public Collection<Organization> getAllOrganization() {
        Collection<Organization> organizations = new HashSet<Organization>();
        try {
            organizations = super.getAll();
        } catch (Exception e) {
        }
        return organizations;
    }

    

    @Override
    public void validateOrganization(Organization organization) {
        if (organization.getId() == null) {
            Integer count = (Integer) super.getOther(
                    QueryParameterFactory.getElementCountParam("uniqueShortName"), QueryParameterFactory.getStringLikePropertyParam(
                    "uniqueShortName",
                    organization.getUniqueShortName()));
            if (count.intValue() > 0) {
                throw new RuntimeException(ExceptionMessage.CONSTRAINT_VIOLATION_EXCEPTION.
                        name() + "-" +
                        UniqueConstrainedField.ORGANIZATION_UNIQUE_SHORT_NAME.name());
            }
        } else {
            Integer count = (Integer) super.getOther(
                    QueryParameterFactory.getElementCountParam("uniqueShortName"),
                    QueryParameterFactory.getConjunctionParam(
                    QueryParameterFactory.getNotEqualPropertyParam("id",
                    organization.getId()), QueryParameterFactory.
                    getStringLikePropertyParam(
                    "uniqueShortName", organization.getUniqueShortName())));
            if (count.intValue() > 0) {
                throw new RuntimeException(ExceptionMessage.CONSTRAINT_VIOLATION_EXCEPTION.
                        name() + "-" +
                        UniqueConstrainedField.ORGANIZATION_UNIQUE_SHORT_NAME.name());
            }

        }
    }

    @Override
    public Organization getOrganizationByUniqueShortName(String uniqueShortName) {
        QueryParameter qp;
        qp = QueryParameterFactory.getEqualPropertyParam("uniqueShortName", uniqueShortName);
        Organization organization = new Organization("", "");
        try {
            organization = super.getSingle(qp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return organization;
    }

    public void populateOrganization(User user) throws Exception{
        Integer organizationID = user.getParentOrganizationID();
        if(user.getParentOrganizationID() != null){
            Organization parentOrganization = super.getById(organizationID);

            if(parentOrganization == null){
                throw new Exception("No organization found");
            }
            user.setOrganization(parentOrganization);
        }
    }
    
    public void populateOrganization(SecuredObject securedObject) throws Exception{
        Integer organizationID = securedObject.getParentOrganizationID();
        if(organizationID != null){
            Organization parentOrganization = super.getById(organizationID);

            if(parentOrganization == null){
                throw new Exception("No organization found");
            }
            securedObject.setOrganization(parentOrganization);
        }
    }
    
    public void populateOrganization(Privilege privilege) throws Exception{
        Integer organizationID = privilege.getParentOrganizationID();
        if(privilege.getParentOrganizationID() != null){
            Organization parentOrganization = super.getById(organizationID);

            if(parentOrganization == null){
                throw new Exception("No organization found");
            }
            privilege.setParentOrganization(parentOrganization);
        }
    }

    public void populateOrganization(Role role) throws Exception{
        Integer organizationID = role.getParentOrganizationID();
        if(role.getParentOrganizationID() == null){
            Organization parentOrganization = super.getById(organizationID);

            if(parentOrganization == null){
                throw new Exception("No organization found");
            }
            role.setParentOrganization(parentOrganization);
        }
    }



}