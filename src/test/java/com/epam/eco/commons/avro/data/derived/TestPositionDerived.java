/*
 * Copyright 2020 EPAM Systems
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
public class TestPositionDerived extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -6548840352275907225L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"TestPositionDerived\",\"namespace\":\"com.epam.eco.commons.avro.data.derived\",\"fields\":[{\"name\":\"skill\",\"type\":{\"type\":\"map\",\"values\":{\"type\":\"record\",\"name\":\"TestSkillLevelDerived\",\"fields\":[{\"name\":\"level\",\"type\":\"string\"}]}}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  @Deprecated public java.util.Map<java.lang.CharSequence,com.epam.eco.commons.avro.data.derived.TestSkillLevelDerived> skill;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public TestPositionDerived() {}

  /**
   * All-args constructor.
   * @param skill The new value for skill
   */
  public TestPositionDerived(java.util.Map<java.lang.CharSequence,com.epam.eco.commons.avro.data.derived.TestSkillLevelDerived> skill) {
    this.skill = skill;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return skill;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: skill = (java.util.Map<java.lang.CharSequence,com.epam.eco.commons.avro.data.derived.TestSkillLevelDerived>)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'skill' field.
   * @return The value of the 'skill' field.
   */
  public java.util.Map<java.lang.CharSequence,com.epam.eco.commons.avro.data.derived.TestSkillLevelDerived> getSkill() {
    return skill;
  }

  /**
   * Sets the value of the 'skill' field.
   * @param value the value to set.
   */
  public void setSkill(java.util.Map<java.lang.CharSequence,com.epam.eco.commons.avro.data.derived.TestSkillLevelDerived> value) {
    this.skill = value;
  }

  /**
   * Creates a new TestPositionDerived RecordBuilder.
   * @return A new TestPositionDerived RecordBuilder
   */
  public static com.epam.eco.commons.avro.data.derived.TestPositionDerived.Builder newBuilder() {
    return new com.epam.eco.commons.avro.data.derived.TestPositionDerived.Builder();
  }

  /**
   * Creates a new TestPositionDerived RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new TestPositionDerived RecordBuilder
   */
  public static com.epam.eco.commons.avro.data.derived.TestPositionDerived.Builder newBuilder(com.epam.eco.commons.avro.data.derived.TestPositionDerived.Builder other) {
    return new com.epam.eco.commons.avro.data.derived.TestPositionDerived.Builder(other);
  }

  /**
   * Creates a new TestPositionDerived RecordBuilder by copying an existing TestPositionDerived instance.
   * @param other The existing instance to copy.
   * @return A new TestPositionDerived RecordBuilder
   */
  public static com.epam.eco.commons.avro.data.derived.TestPositionDerived.Builder newBuilder(com.epam.eco.commons.avro.data.derived.TestPositionDerived other) {
    return new com.epam.eco.commons.avro.data.derived.TestPositionDerived.Builder(other);
  }

  /**
   * RecordBuilder for TestPositionDerived instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<TestPositionDerived>
    implements org.apache.avro.data.RecordBuilder<TestPositionDerived> {

    private java.util.Map<java.lang.CharSequence,com.epam.eco.commons.avro.data.derived.TestSkillLevelDerived> skill;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.epam.eco.commons.avro.data.derived.TestPositionDerived.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.skill)) {
        this.skill = data().deepCopy(fields()[0].schema(), other.skill);
        fieldSetFlags()[0] = true;
      }
    }

    /**
     * Creates a Builder by copying an existing TestPositionDerived instance
     * @param other The existing instance to copy.
     */
    private Builder(com.epam.eco.commons.avro.data.derived.TestPositionDerived other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.skill)) {
        this.skill = data().deepCopy(fields()[0].schema(), other.skill);
        fieldSetFlags()[0] = true;
      }
    }

    /**
      * Gets the value of the 'skill' field.
      * @return The value.
      */
    public java.util.Map<java.lang.CharSequence,com.epam.eco.commons.avro.data.derived.TestSkillLevelDerived> getSkill() {
      return skill;
    }

    /**
      * Sets the value of the 'skill' field.
      * @param value The value of 'skill'.
      * @return This builder.
      */
    public com.epam.eco.commons.avro.data.derived.TestPositionDerived.Builder setSkill(java.util.Map<java.lang.CharSequence,com.epam.eco.commons.avro.data.derived.TestSkillLevelDerived> value) {
      validate(fields()[0], value);
      this.skill = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'skill' field has been set.
      * @return True if the 'skill' field has been set, false otherwise.
      */
    public boolean hasSkill() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'skill' field.
      * @return This builder.
      */
    public com.epam.eco.commons.avro.data.derived.TestPositionDerived.Builder clearSkill() {
      skill = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    @Override
    public TestPositionDerived build() {
      try {
        TestPositionDerived record = new TestPositionDerived();
        record.skill = fieldSetFlags()[0] ? this.skill : (java.util.Map<java.lang.CharSequence,com.epam.eco.commons.avro.data.derived.TestSkillLevelDerived>) defaultValue(fields()[0]);
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
