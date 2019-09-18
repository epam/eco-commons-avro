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
public class TestSkillLevelDerived extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 5454421580124294705L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"TestSkillLevelDerived\",\"namespace\":\"com.epam.eco.commons.avro.data.derived\",\"fields\":[{\"name\":\"level\",\"type\":\"string\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  @Deprecated public java.lang.CharSequence level;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public TestSkillLevelDerived() {}

  /**
   * All-args constructor.
   * @param level The new value for level
   */
  public TestSkillLevelDerived(java.lang.CharSequence level) {
    this.level = level;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return level;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: level = (java.lang.CharSequence)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'level' field.
   * @return The value of the 'level' field.
   */
  public java.lang.CharSequence getLevel() {
    return level;
  }

  /**
   * Sets the value of the 'level' field.
   * @param value the value to set.
   */
  public void setLevel(java.lang.CharSequence value) {
    this.level = value;
  }

  /**
   * Creates a new TestSkillLevelDerived RecordBuilder.
   * @return A new TestSkillLevelDerived RecordBuilder
   */
  public static com.epam.eco.commons.avro.data.derived.TestSkillLevelDerived.Builder newBuilder() {
    return new com.epam.eco.commons.avro.data.derived.TestSkillLevelDerived.Builder();
  }

  /**
   * Creates a new TestSkillLevelDerived RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new TestSkillLevelDerived RecordBuilder
   */
  public static com.epam.eco.commons.avro.data.derived.TestSkillLevelDerived.Builder newBuilder(com.epam.eco.commons.avro.data.derived.TestSkillLevelDerived.Builder other) {
    return new com.epam.eco.commons.avro.data.derived.TestSkillLevelDerived.Builder(other);
  }

  /**
   * Creates a new TestSkillLevelDerived RecordBuilder by copying an existing TestSkillLevelDerived instance.
   * @param other The existing instance to copy.
   * @return A new TestSkillLevelDerived RecordBuilder
   */
  public static com.epam.eco.commons.avro.data.derived.TestSkillLevelDerived.Builder newBuilder(com.epam.eco.commons.avro.data.derived.TestSkillLevelDerived other) {
    return new com.epam.eco.commons.avro.data.derived.TestSkillLevelDerived.Builder(other);
  }

  /**
   * RecordBuilder for TestSkillLevelDerived instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<TestSkillLevelDerived>
    implements org.apache.avro.data.RecordBuilder<TestSkillLevelDerived> {

    private java.lang.CharSequence level;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.epam.eco.commons.avro.data.derived.TestSkillLevelDerived.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.level)) {
        this.level = data().deepCopy(fields()[0].schema(), other.level);
        fieldSetFlags()[0] = true;
      }
    }

    /**
     * Creates a Builder by copying an existing TestSkillLevelDerived instance
     * @param other The existing instance to copy.
     */
    private Builder(com.epam.eco.commons.avro.data.derived.TestSkillLevelDerived other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.level)) {
        this.level = data().deepCopy(fields()[0].schema(), other.level);
        fieldSetFlags()[0] = true;
      }
    }

    /**
      * Gets the value of the 'level' field.
      * @return The value.
      */
    public java.lang.CharSequence getLevel() {
      return level;
    }

    /**
      * Sets the value of the 'level' field.
      * @param value The value of 'level'.
      * @return This builder.
      */
    public com.epam.eco.commons.avro.data.derived.TestSkillLevelDerived.Builder setLevel(java.lang.CharSequence value) {
      validate(fields()[0], value);
      this.level = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'level' field has been set.
      * @return True if the 'level' field has been set, false otherwise.
      */
    public boolean hasLevel() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'level' field.
      * @return This builder.
      */
    public com.epam.eco.commons.avro.data.derived.TestSkillLevelDerived.Builder clearLevel() {
      level = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    @Override
    public TestSkillLevelDerived build() {
      try {
        TestSkillLevelDerived record = new TestSkillLevelDerived();
        record.level = fieldSetFlags()[0] ? this.level : (java.lang.CharSequence) defaultValue(fields()[0]);
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
