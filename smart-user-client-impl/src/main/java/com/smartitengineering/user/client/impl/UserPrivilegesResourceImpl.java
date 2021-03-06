/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartitengineering.user.client.impl;

import com.smartitengineering.user.client.api.Privilege;
import com.smartitengineering.user.client.api.PrivilegeResource;
import com.smartitengineering.user.client.api.UserPrivilegeResource;
import com.smartitengineering.user.client.api.UserPrivilegesResource;
import com.smartitengineering.util.rest.atom.AbstractFeedClientResource;
import com.smartitengineering.util.rest.atom.AtomClientUtil;
import com.smartitengineering.util.rest.client.ClientUtil;
import com.smartitengineering.util.rest.client.Resource;
import com.smartitengineering.util.rest.client.ResourceLink;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.config.ClientConfig;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.MediaType;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;


/**
 *
 * @author modhu7
 */
public class UserPrivilegesResourceImpl extends AbstractFeedClientResource<Resource<? extends Feed>> implements
    UserPrivilegesResource {

  public static final String REL_ALT = "alternate";
  public static final String REL_USER_PRIV = "privilege";

  public UserPrivilegesResourceImpl(ResourceLink privsLink, Resource referrer) {
    super(referrer, privsLink);
  }

  @Override
  protected void processClientConfig(ClientConfig clientConfig) {
  }

  @Override
  protected Resource<? extends Feed> instantiatePageableResource(ResourceLink link) {
    return new UserPrivilegesResourceImpl(link, this);
  }

  @Override
  public List<UserPrivilegeResource> getUserPrivilegeResources() {
    List<UserPrivilegeResource> privilegeResources = new ArrayList<UserPrivilegeResource>();
    for (Entry entry : getLastReadStateOfEntity().getEntries()) {
      privilegeResources.add(new UserPrivilegeResourceImpl(AtomClientUtil.convertFromAtomLinkToResourceLink(
          entry.getLink(REL_ALT)), this));
    }
    return privilegeResources;
  }

  @Override
  public UserPrivilegeResource add(Privilege privilege) {
    ClientResponse response = post(MediaType.APPLICATION_JSON, privilege, ClientResponse.Status.CREATED);
    final ResourceLink privLink = ClientUtil.createResourceLink(REL_USER_PRIV, response.getLocation(),
                                                               MediaType.APPLICATION_ATOM_XML);
    return new UserPrivilegeResourceImpl(privLink, this);
  }
}
