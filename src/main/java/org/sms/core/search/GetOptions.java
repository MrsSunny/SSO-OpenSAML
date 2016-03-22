package org.sms.core.search;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sunny
 */
public class GetOptions {

  private String preference;
  private List<String> fields = new ArrayList<>();
  private Boolean fetchSource;
  private List<String> fetchSourceIncludes = new ArrayList<>();
  private List<String> fetchSourceExcludes = new ArrayList<>();
  private Boolean transformSource;
  private Boolean realtime;
  private Boolean ignoreErrorsOnGeneratedFields;

  public static final String FIELD_PREFERENCE = "preference";
  public static final String FIELD_FIELDS = "fields";
  public static final String FIELD_FETCH_SOURCE = "fetchSource";
  public static final String FIELD_FETCH_SOURCE_INCLUDES = "fetchSourceIncludes";
  public static final String FIELD_FETCH_SOURCE_EXCLUDES = "fetchSourceExcludes";
  public static final String FIELD_TRANSFORM_SOURCE = "transformSource";
  public static final String FIELD_REALTIME = "realtime";
  public static final String FIELD_IGNORE_ERROR_ON_GENERATED_FIELDS = "ignoreErrorsOnGeneratedFields";

  public GetOptions() {
  }

  public String getPreference() {
    return preference;
  }

  public GetOptions setPreference(String preference) {
    this.preference = preference;
    return this;
  }

  public List<String> getFields() {
    return fields;
  }

  public GetOptions addField(String field) {
    this.fields.add(field);
    return this;
  }

  public Boolean isFetchSource() {
    return fetchSource;
  }

  public GetOptions setFetchSource(Boolean fetchSource) {
    this.fetchSource = fetchSource;
    return this;
  }

  public List<String> getFetchSourceIncludes() {
    return fetchSourceIncludes;
  }

  public List<String> getFetchSourceExcludes() {
    return fetchSourceExcludes;
  }

  public Boolean isTransformSource() {
    return transformSource;
  }

  public GetOptions setTransformSource(Boolean transformSource) {
    this.transformSource = transformSource;
    return this;
  }

  public Boolean isRealtime() {
    return realtime;
  }

  public GetOptions setRealtime(Boolean realtime) {
    this.realtime = realtime;
    return this;
  }

  public Boolean isIgnoreErrorsOnGeneratedFields() {
    return ignoreErrorsOnGeneratedFields;
  }

  public GetOptions setIgnoreErrorsOnGeneratedFields(Boolean ignoreErrorsOnGeneratedFields) {
    this.ignoreErrorsOnGeneratedFields = ignoreErrorsOnGeneratedFields;
    return this;
  }
}
