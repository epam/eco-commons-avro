/*
 * Copyright 2019 EPAM Systems
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.epam.eco.commons.avro.data;

import org.apache.avro.specific.SpecificData;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class TestHobby extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 7520920464319872171L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"TestHobby\",\"namespace\":\"com.epam.eco.commons.avro.data\",\"fields\":[{\"name\":\"kind\",\"type\":\"string\"},{\"name\":\"description\",\"type\":\"string\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  @Deprecated public java.lang.CharSequence kind;
  @Deprecated public java.lang.CharSequence description;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public TestHobby() {}

  /**
   * All-args constructor.
   * @param kind The new value for kind
   * @param description The new value for description
   */
  public TestHobby(java.lang.CharSequence kind, java.lang.CharSequence description) {
    this.kind = kind;
    this.description = description;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return kind;
    case 1: return description;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: kind = (java.lang.CharSequence)value$; break;
    case 1: description = (java.lang.CharSequence)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'kind' field.
   * @return The value of the 'kind' field.
   */
  public java.lang.CharSequence getKind() {
    return kind;
  }

  /**
   * Sets the value of the 'kind' field.
   * @param value the value to set.
   */
  public void setKind(java.lang.CharSequence value) {
    this.kind = value;
  }

  /**
   * Gets the value of the 'description' field.
   * @return The value of the 'description' field.
   */
  public java.lang.CharSequence getDescription() {
    return description;
  }

  /**
   * Sets the value of the 'description' field.
   * @param value the value to set.
   */
  public void setDescription(java.lang.CharSequence value) {
    this.description = value;
  }

  /**
   * Creates a new TestHobby RecordBuilder.
   * @return A new TestHobby RecordBuilder
   */
  public static com.epam.eco.commons.avro.data.TestHobby.Builder newBuilder() {
    return new com.epam.eco.commons.avro.data.TestHobby.Builder();
  }

  /**
   * Creates a new TestHobby RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new TestHobby RecordBuilder
   */
  public static com.epam.eco.commons.avro.data.TestHobby.Builder newBuilder(com.epam.eco.commons.avro.data.TestHobby.Builder other) {
    return new com.epam.eco.commons.avro.data.TestHobby.Builder(other);
  }

  /**
   * Creates a new TestHobby RecordBuilder by copying an existing TestHobby instance.
   * @param other The existing instance to copy.
   * @return A new TestHobby RecordBuilder
   */
  public static com.epam.eco.commons.avro.data.TestHobby.Builder newBuilder(com.epam.eco.commons.avro.data.TestHobby other) {
    return new com.epam.eco.commons.avro.data.TestHobby.Builder(other);
  }

  /**
   * RecordBuilder for TestHobby instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<TestHobby>
    implements org.apache.avro.data.RecordBuilder<TestHobby> {

    private java.lang.CharSequence kind;
    private java.lang.CharSequence description;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.epam.eco.commons.avro.data.TestHobby.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.kind)) {
        this.kind = data().deepCopy(fields()[0].schema(), other.kind);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.description)) {
        this.description = data().deepCopy(fields()[1].schema(), other.description);
        fieldSetFlags()[1] = true;
      }
    }

    /**
     * Creates a Builder by copying an existing TestHobby instance
     * @param other The existing instance to copy.
     */
    private Builder(com.epam.eco.commons.avro.data.TestHobby other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.kind)) {
        this.kind = data().deepCopy(fields()[0].schema(), other.kind);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.description)) {
        this.description = data().deepCopy(fields()[1].schema(), other.description);
        fieldSetFlags()[1] = true;
      }
    }

    /**
      * Gets the value of the 'kind' field.
      * @return The value.
      */
    public java.lang.CharSequence getKind() {
      return kind;
    }

    /**
      * Sets the value of the 'kind' field.
      * @param value The value of 'kind'.
      * @return This builder.
      */
    public com.epam.eco.commons.avro.data.TestHobby.Builder setKind(java.lang.CharSequence value) {
      validate(fields()[0], value);
      this.kind = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'kind' field has been set.
      * @return True if the 'kind' field has been set, false otherwise.
      */
    public boolean hasKind() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'kind' field.
      * @return This builder.
      */
    public com.epam.eco.commons.avro.data.TestHobby.Builder clearKind() {
      kind = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'description' field.
      * @return The value.
      */
    public java.lang.CharSequence getDescription() {
      return description;
    }

    /**
      * Sets the value of the 'description' field.
      * @param value The value of 'description'.
      * @return This builder.
      */
    public com.epam.eco.commons.avro.data.TestHobby.Builder setDescription(java.lang.CharSequence value) {
      validate(fields()[1], value);
      this.description = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'description' field has been set.
      * @return True if the 'description' field has been set, false otherwise.
      */
    public boolean hasDescription() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'description' field.
      * @return This builder.
      */
    public com.epam.eco.commons.avro.data.TestHobby.Builder clearDescription() {
      description = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    @Override
    public TestHobby build() {
      try {
        TestHobby record = new TestHobby();
        record.kind = fieldSetFlags()[0] ? this.kind : (java.lang.CharSequence) defaultValue(fields()[0]);
        record.description = fieldSetFlags()[1] ? this.description : (java.lang.CharSequence) defaultValue(fields()[1]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  private static final org.apache.avro.io.DatumWriter
    WRITER$ = new org.apache.avro.specific.SpecificDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  private static final org.apache.avro.io.DatumReader
    READER$ = new org.apache.avro.specific.SpecificDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}
