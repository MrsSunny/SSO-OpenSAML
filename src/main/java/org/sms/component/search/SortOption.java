package org.sms.component.search;

import org.elasticsearch.search.sort.SortOrder;

/**
 * @author zhenxing.Liu
 */
public class SortOption {

  private String field;
  private SortOrder order;

  public static final String JSON_FIELD_FIELD = "field";
  public static final String JSON_FIELD_ORDER = "order";

  public SortOption() {
  }

  public SortOption(SortOption other) {
    field = other.getField();
    order = other.getOrder();
  }

  public String getField() {
    return field;
  }

  public SortOption setField(String field) {
    this.field = field;
    return this;
  }

  public SortOrder getOrder() {
    return order;
  }

  public SortOption setOrder(SortOrder order) {
    this.order = order;
    return this;
  }
}