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
public class TestSkillLevel extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -2768810091499630051L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"TestSkillLevel\",\"namespace\":\"com.epam.eco.commons.avro.data\",\"fields\":[{\"name\":\"level\",\"type\":\"string\"},{\"name\":\"description\",\"type\":\"string\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  @Deprecated public java.lang.CharSequence level;
  @Deprecated public java.lang.CharSequence description;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public TestSkillLevel() {}

  /**
   * All-args constructor.
   * @param level The new value for level
   * @param description The new value for description
   */
  public TestSkillLevel(java.lang.CharSequence level, java.lang.CharSequence description) {
    this.level = level;
    this.description = description;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return level;
    case 1: return description;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: level = (java.lang.CharSequence)value$; break;
    case 1: description = (java.lang.CharSequence)value$; break;
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
   * Creates a new TestSkillLevel RecordBuilder.
   * @return A new TestSkillLevel RecordBuilder
   */
  public static com.epam.eco.commons.avro.data.TestSkillLevel.Builder newBuilder() {
    return new com.epam.eco.commons.avro.data.TestSkillLevel.Builder();
  }

  /**
   * Creates a new TestSkillLevel RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new TestSkillLevel RecordBuilder
   */
  public static com.epam.eco.commons.avro.data.TestSkillLevel.Builder newBuilder(com.epam.eco.commons.avro.data.TestSkillLevel.Builder other) {
    return new com.epam.eco.commons.avro.data.TestSkillLevel.Builder(other);
  }

  /**
   * Creates a new TestSkillLevel RecordBuilder by copying an existing TestSkillLevel instance.
   * @param other The existing instance to copy.
   * @return A new TestSkillLevel RecordBuilder
   */
  public static com.epam.eco.commons.avro.data.TestSkillLevel.Builder newBuilder(com.epam.eco.commons.avro.data.TestSkillLevel other) {
    return new com.epam.eco.commons.avro.data.TestSkillLevel.Builder(other);
  }

  /**
   * RecordBuilder for TestSkillLevel instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<TestSkillLevel>
    implements org.apache.avro.data.RecordBuilder<TestSkillLevel> {

    private java.lang.CharSequence level;
    private java.lang.CharSequence description;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.epam.eco.commons.avro.data.TestSkillLevel.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.level)) {
        this.level = data().deepCopy(fields()[0].schema(), other.level);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.description)) {
        this.description = data().deepCopy(fields()[1].schema(), other.description);
        fieldSetFlags()[1] = true;
      }
    }

    /**
     * Creates a Builder by copying an existing TestSkillLevel instance
     * @param other The existing instance to copy.
     */
    private Builder(com.epam.eco.commons.avro.data.TestSkillLevel other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.level)) {
        this.level = data().deepCopy(fields()[0].schema(), other.level);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.description)) {
        this.description = data().deepCopy(fields()[1].schema(), other.description);
        fieldSetFlags()[1] = true;
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
    public com.epam.eco.commons.avro.data.TestSkillLevel.Builder setLevel(java.lang.CharSequence value) {
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
    public com.epam.eco.commons.avro.data.TestSkillLevel.Builder clearLevel() {
      level = null;
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
    public com.epam.eco.commons.avro.data.TestSkillLevel.Builder setDescription(java.lang.CharSequence value) {
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
    public com.epam.eco.commons.avro.data.TestSkillLevel.Builder clearDescription() {
      description = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    @Override
    public TestSkillLevel build() {
      try {
        TestSkillLevel record = new TestSkillLevel();
        record.level = fieldSetFlags()[0] ? this.level : (java.lang.CharSequence) defaultValue(fields()[0]);
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
