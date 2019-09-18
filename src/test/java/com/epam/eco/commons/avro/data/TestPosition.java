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
public class TestPosition extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -2126684643311420324L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"TestPosition\",\"namespace\":\"com.epam.eco.commons.avro.data\",\"fields\":[{\"name\":\"title\",\"type\":\"string\"},{\"name\":\"skill\",\"type\":{\"type\":\"map\",\"values\":{\"type\":\"record\",\"name\":\"TestSkillLevel\",\"fields\":[{\"name\":\"level\",\"type\":\"string\"},{\"name\":\"description\",\"type\":\"string\"}]}}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  @Deprecated public java.lang.CharSequence title;
  @Deprecated public java.util.Map<java.lang.CharSequence,com.epam.eco.commons.avro.data.TestSkillLevel> skill;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public TestPosition() {}

  /**
   * All-args constructor.
   * @param title The new value for title
   * @param skill The new value for skill
   */
  public TestPosition(java.lang.CharSequence title, java.util.Map<java.lang.CharSequence,com.epam.eco.commons.avro.data.TestSkillLevel> skill) {
    this.title = title;
    this.skill = skill;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return title;
    case 1: return skill;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: title = (java.lang.CharSequence)value$; break;
    case 1: skill = (java.util.Map<java.lang.CharSequence,com.epam.eco.commons.avro.data.TestSkillLevel>)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'title' field.
   * @return The value of the 'title' field.
   */
  public java.lang.CharSequence getTitle() {
    return title;
  }

  /**
   * Sets the value of the 'title' field.
   * @param value the value to set.
   */
  public void setTitle(java.lang.CharSequence value) {
    this.title = value;
  }

  /**
   * Gets the value of the 'skill' field.
   * @return The value of the 'skill' field.
   */
  public java.util.Map<java.lang.CharSequence,com.epam.eco.commons.avro.data.TestSkillLevel> getSkill() {
    return skill;
  }

  /**
   * Sets the value of the 'skill' field.
   * @param value the value to set.
   */
  public void setSkill(java.util.Map<java.lang.CharSequence,com.epam.eco.commons.avro.data.TestSkillLevel> value) {
    this.skill = value;
  }

  /**
   * Creates a new TestPosition RecordBuilder.
   * @return A new TestPosition RecordBuilder
   */
  public static com.epam.eco.commons.avro.data.TestPosition.Builder newBuilder() {
    return new com.epam.eco.commons.avro.data.TestPosition.Builder();
  }

  /**
   * Creates a new TestPosition RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new TestPosition RecordBuilder
   */
  public static com.epam.eco.commons.avro.data.TestPosition.Builder newBuilder(com.epam.eco.commons.avro.data.TestPosition.Builder other) {
    return new com.epam.eco.commons.avro.data.TestPosition.Builder(other);
  }

  /**
   * Creates a new TestPosition RecordBuilder by copying an existing TestPosition instance.
   * @param other The existing instance to copy.
   * @return A new TestPosition RecordBuilder
   */
  public static com.epam.eco.commons.avro.data.TestPosition.Builder newBuilder(com.epam.eco.commons.avro.data.TestPosition other) {
    return new com.epam.eco.commons.avro.data.TestPosition.Builder(other);
  }

  /**
   * RecordBuilder for TestPosition instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<TestPosition>
    implements org.apache.avro.data.RecordBuilder<TestPosition> {

    private java.lang.CharSequence title;
    private java.util.Map<java.lang.CharSequence,com.epam.eco.commons.avro.data.TestSkillLevel> skill;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.epam.eco.commons.avro.data.TestPosition.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.title)) {
        this.title = data().deepCopy(fields()[0].schema(), other.title);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.skill)) {
        this.skill = data().deepCopy(fields()[1].schema(), other.skill);
        fieldSetFlags()[1] = true;
      }
    }

    /**
     * Creates a Builder by copying an existing TestPosition instance
     * @param other The existing instance to copy.
     */
    private Builder(com.epam.eco.commons.avro.data.TestPosition other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.title)) {
        this.title = data().deepCopy(fields()[0].schema(), other.title);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.skill)) {
        this.skill = data().deepCopy(fields()[1].schema(), other.skill);
        fieldSetFlags()[1] = true;
      }
    }

    /**
      * Gets the value of the 'title' field.
      * @return The value.
      */
    public java.lang.CharSequence getTitle() {
      return title;
    }

    /**
      * Sets the value of the 'title' field.
      * @param value The value of 'title'.
      * @return This builder.
      */
    public com.epam.eco.commons.avro.data.TestPosition.Builder setTitle(java.lang.CharSequence value) {
      validate(fields()[0], value);
      this.title = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'title' field has been set.
      * @return True if the 'title' field has been set, false otherwise.
      */
    public boolean hasTitle() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'title' field.
      * @return This builder.
      */
    public com.epam.eco.commons.avro.data.TestPosition.Builder clearTitle() {
      title = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'skill' field.
      * @return The value.
      */
    public java.util.Map<java.lang.CharSequence,com.epam.eco.commons.avro.data.TestSkillLevel> getSkill() {
      return skill;
    }

    /**
      * Sets the value of the 'skill' field.
      * @param value The value of 'skill'.
      * @return This builder.
      */
    public com.epam.eco.commons.avro.data.TestPosition.Builder setSkill(java.util.Map<java.lang.CharSequence,com.epam.eco.commons.avro.data.TestSkillLevel> value) {
      validate(fields()[1], value);
      this.skill = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'skill' field has been set.
      * @return True if the 'skill' field has been set, false otherwise.
      */
    public boolean hasSkill() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'skill' field.
      * @return This builder.
      */
    public com.epam.eco.commons.avro.data.TestPosition.Builder clearSkill() {
      skill = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    @Override
    public TestPosition build() {
      try {
        TestPosition record = new TestPosition();
        record.title = fieldSetFlags()[0] ? this.title : (java.lang.CharSequence) defaultValue(fields()[0]);
        record.skill = fieldSetFlags()[1] ? this.skill : (java.util.Map<java.lang.CharSequence,com.epam.eco.commons.avro.data.TestSkillLevel>) defaultValue(fields()[1]);
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
