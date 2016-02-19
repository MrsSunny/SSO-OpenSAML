package org.sms.component.search;

/**
 * @author zhenxing.Liu
 */
public class UpdateOptions {

  private String doc;
  
  public static final String FIELD_SCRIPT = "script";
  public static final String FIELD_SCRIPT_TYPE = "scriptType";
  public static final String FIELD_SCRIPT_LANG = "scriptLang";
  public static final String FIELD_SCRIPT_PARAMS = "scriptParams";
  public static final String FIELD_FIELDS = "fields";
  public static final String FIELD_RETRY_ON_CONFLICT = "retryOnConflict";
  public static final String FIELD_DOC = "doc";
  public static final String FIELD_UPSERT = "upsert";
  public static final String FIELD_DOC_AS_UPSERT = "docAsUpsert";
  public static final String FIELD_DETECT_NOOP = "detectNoop";
  public static final String FIELD_SCRIPTED_UPSERT = "scriptedUpsert";

  public UpdateOptions() {
  }

  public String getDoc() {
    return doc;
  }

  public UpdateOptions setDoc(String doc) {
    this.doc = doc;
    return this;
  }
}
