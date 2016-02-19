package org.sms.component.search;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.queryparser.xml.FilterBuilder;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.sort.SortOrder;

/**
 * @author zhenxing.Liu
 */
public class SearchOptions {

  private List<String> types = new ArrayList<>();
  private SearchType searchType;
  private String timeout;
  private QueryBuilder builder;
  private FilterBuilder postFilter;
  private Integer size;
  private Integer from;
  private List<String> fields = new ArrayList<>();
  private List<SortOption> sorts = new ArrayList<>();
  
  public static final String JSON_FIELD_TYPES = "types";
  public static final String JSON_FIELD_SEARCH_TYPE = "searchType";
  public static final String JSON_FIELD_SCROLL = "scroll";
  public static final String JSON_FIELD_TIMEOUT = "timeout";
  public static final String JSON_FIELD_TERMINATE_AFTER = "terminateAfter";
  public static final String JSON_FIELD_ROUTING = "routing";
  public static final String JSON_FIELD_PREFERENCE = "preference";
  public static final String JSON_FIELD_QUERY = "query";
  public static final String JSON_FIELD_POST_FILTER = "postFilter";
  public static final String JSON_FIELD_MIN_SCORE = "minScore";
  public static final String JSON_FIELD_SIZE = "size";
  public static final String JSON_FIELD_FROM = "from";
  public static final String JSON_FIELD_EXPLAIN = "explain";
  public static final String JSON_FIELD_VERSION = "version";
  public static final String JSON_FIELD_FETCH_SOURCE = "fetchSource";
  public static final String JSON_FIELD_FIELDS = "fields";
  public static final String JSON_FIELD_TRACK_SCORES = "trackScores";
  public static final String JSON_FIELD_AGGREGATIONS = "aggregations";
  public static final String JSON_FIELD_SORTS = "sorts";
  public static final String JSON_FIELD_EXTRA_SOURCE = "extraSource";

  public SearchOptions() {
  }

  public List<String> getTypes() {
    return types;
  }

  public SearchOptions addType(String type) {
    types.add(type);
    return this;
  }

  public FilterBuilder getPostFilter() {
    return postFilter;
  }

  public SearchOptions setPostFilter(FilterBuilder postFilter) {
    this.postFilter = postFilter;
    return this;
  }

  public SearchType getSearchType() {
    return searchType;
  }

  public SearchOptions setSearchType(SearchType searchType) {
    this.searchType = searchType;
    return this;
  }

  public Integer getSize() {
    return size;
  }

  public SearchOptions setSize(Integer size) {
    this.size = size;
    return this;
  }

  public Integer getFrom() {
    return from;
  }

  public SearchOptions setFrom(Integer from) {
    this.from = from;
    return this;
  }

  public String getTimeout() {
    return timeout;
  }

  public SearchOptions setTimeout(String timeout) {
    this.timeout = timeout;
    return this;
  }

  public List<String> getFields() {
    return fields;
  }

  public SearchOptions addField(String field) {
    fields.add(field);
    return this;
  }

  public List<SortOption> getSorts() {
    return sorts;
  }

  public SearchOptions addSort(String field, SortOrder order) {
    sorts.add(new SortOption().setField(field).setOrder(order));
    return this;
  }

  /**
   * @return the builder
   */
  public QueryBuilder getBuilder() {
    return builder;
  }

  /**
   * @param builder
   *          the builder to set
   */
  public void setBuilder(QueryBuilder builder) {
    this.builder = builder;
  }
}