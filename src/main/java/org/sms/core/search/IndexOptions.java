package org.sms.core.search;

import org.elasticsearch.action.index.IndexRequest;

/**
 * @author Sunny
 */
public class IndexOptions {

  private String id;
  private IndexRequest.OpType opType;
  private String timestamp;
  private Long ttl;

  public static final String FIELD_ID = "id";
  public static final String FIELD_OP_TYPE = "opType";
  public static final String FIELD_TIMESTAMP = "timestamp";
  public static final String FIELD_TTL = "ttl";

  public IndexOptions() {
  }

  public String getId() {
    return id;
  }

  public IndexOptions setId(String id) {
    this.id = id;
    return this;
  }

  public IndexRequest.OpType getOpType() {
    return opType;
  }

  public IndexOptions setOpType(IndexRequest.OpType opType) {
    this.opType = opType;
    return this;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public IndexOptions setTimestamp(String timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  public Long getTtl() {
    return ttl;
  }

  public IndexOptions setTtl(Long ttl) {
    this.ttl = ttl;
    return this;
  }
}