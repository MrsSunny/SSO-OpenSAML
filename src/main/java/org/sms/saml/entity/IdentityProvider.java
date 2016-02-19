package org.sms.saml.entity;

import java.io.Serializable;
import java.util.Date;

public class IdentityProvider implements Serializable {

  private static final long serialVersionUID = -8912945153640636885L;
  protected int id;
  private String metaData;
  private String horizonUrl;
  private Date updated;

  public IdentityProvider() {
  }

  public int getId() {
    return id;
  }

  public String getMetaData() {
    return metaData;
  }

  public void setMetaData(String metaData) {
    this.metaData = metaData;
    updated = new Date();
  }

  public String getHorizonUrl() {
    return horizonUrl;
  }

  public Date getUpdated() {
    return updated;
  }

  public void setHorizonUrl(String horizonUrl) {
    this.horizonUrl = horizonUrl;
  }
}