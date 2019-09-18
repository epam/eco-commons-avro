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
package com.epam.eco.commons.avro.data.derived;

import org.apache.avro.specific.SpecificData;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class TestHobbyDerived extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -5153890953593560058L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"TestHobbyDerived\",\"namespace\":\"com.epam.eco.commons.avro.data.derived\",\"fields\":[{\"name\":\"kind\",\"type\":\"string\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  @Deprecated public java.lang.CharSequence kind;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public TestHobbyDerived() {}

  /**
   * All-args constructor.
   * @param kind The new value for kind
   */
  public TestHobbyDerived(java.lang.CharSequence kind) {
    this.kind = kind;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return kind;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: kind = (java.lang.CharSequence)value$; break;
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
   * Creates a new TestHobbyDerived RecordBuilder.
   * @return A new TestHobbyDerived RecordBuilder
   */
  public static com.epam.eco.commons.avro.data.derived.TestHobbyDerived.Builder newBuilder() {
    return new com.epam.eco.commons.avro.data.derived.TestHobbyDerived.Builder();
  }

  /**
   * Creates a new TestHobbyDerived RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new TestHobbyDerived RecordBuilder
   */
  public static com.epam.eco.commons.avro.data.derived.TestHobbyDerived.Builder newBuilder(com.epam.eco.commons.avro.data.derived.TestHobbyDerived.Builder other) {
    return new com.epam.eco.commons.avro.data.derived.TestHobbyDerived.Builder(other);
  }

  /**
   * Creates a new TestHobbyDerived RecordBuilder by copying an existing TestHobbyDerived instance.
   * @param other The existing instance to copy.
   * @return A new TestHobbyDerived RecordBuilder
   */
  public static com.epam.eco.commons.avro.data.derived.TestHobbyDerived.Builder newBuilder(com.epam.eco.commons.avro.data.derived.TestHobbyDerived other) {
    return new com.epam.eco.commons.avro.data.derived.TestHobbyDerived.Builder(other);
  }

  /**
   * RecordBuilder for TestHobbyDerived instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<TestHobbyDerived>
    implements org.apache.avro.data.RecordBuilder<TestHobbyDerived> {

    private java.lang.CharSequence kind;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.epam.eco.commons.avro.data.derived.TestHobbyDerived.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.kind)) {
        this.kind = data().deepCopy(fields()[0].schema(), other.kind);
        fieldSetFlags()[0] = true;
      }
    }

    /**
     * Creates a Builder by copying an existing TestHobbyDerived instance
     * @param other The existing instance to copy.
     */
    private Builder(com.epam.eco.commons.avro.data.derived.TestHobbyDerived other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.kind)) {
        this.kind = data().deepCopy(fields()[0].schema(), other.kind);
        fieldSetFlags()[0] = true;
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
    public com.epam.eco.commons.avro.data.derived.TestHobbyDerived.Builder setKind(java.lang.CharSequence value) {
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
    public com.epam.eco.commons.avro.data.derived.TestHobbyDerived.Builder clearKind() {
      kind = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    @Override
    public TestHobbyDerived build() {
      try {
        TestHobbyDerived record = new TestHobbyDerived();
        record.kind = fieldSetFlags()[0] ? this.kind : (java.lang.CharSequence) defaultValue(fields()[0]);
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
