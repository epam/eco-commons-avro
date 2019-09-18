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
public class TestJob extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -3955724984448179721L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"TestJob\",\"namespace\":\"com.epam.eco.commons.avro.data\",\"fields\":[{\"name\":\"company\",\"type\":\"string\"},{\"name\":\"position\",\"type\":{\"type\":\"record\",\"name\":\"TestPosition\",\"fields\":[{\"name\":\"title\",\"type\":\"string\"},{\"name\":\"skill\",\"type\":{\"type\":\"map\",\"values\":{\"type\":\"record\",\"name\":\"TestSkillLevel\",\"fields\":[{\"name\":\"level\",\"type\":\"string\"},{\"name\":\"description\",\"type\":\"string\"}]}}}]}},{\"name\":\"previousJob\",\"type\":[\"null\",\"TestJob\"]}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  @Deprecated public java.lang.CharSequence company;
  @Deprecated public com.epam.eco.commons.avro.data.TestPosition position;
  @Deprecated public com.epam.eco.commons.avro.data.TestJob previousJob;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public TestJob() {}

  /**
   * All-args constructor.
   * @param company The new value for company
   * @param position The new value for position
   * @param previousJob The new value for previousJob
   */
  public TestJob(java.lang.CharSequence company, com.epam.eco.commons.avro.data.TestPosition position, com.epam.eco.commons.avro.data.TestJob previousJob) {
    this.company = company;
    this.position = position;
    this.previousJob = previousJob;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return company;
    case 1: return position;
    case 2: return previousJob;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: company = (java.lang.CharSequence)value$; break;
    case 1: position = (com.epam.eco.commons.avro.data.TestPosition)value$; break;
    case 2: previousJob = (com.epam.eco.commons.avro.data.TestJob)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'company' field.
   * @return The value of the 'company' field.
   */
  public java.lang.CharSequence getCompany() {
    return company;
  }

  /**
   * Sets the value of the 'company' field.
   * @param value the value to set.
   */
  public void setCompany(java.lang.CharSequence value) {
    this.company = value;
  }

  /**
   * Gets the value of the 'position' field.
   * @return The value of the 'position' field.
   */
  public com.epam.eco.commons.avro.data.TestPosition getPosition() {
    return position;
  }

  /**
   * Sets the value of the 'position' field.
   * @param value the value to set.
   */
  public void setPosition(com.epam.eco.commons.avro.data.TestPosition value) {
    this.position = value;
  }

  /**
   * Gets the value of the 'previousJob' field.
   * @return The value of the 'previousJob' field.
   */
  public com.epam.eco.commons.avro.data.TestJob getPreviousJob() {
    return previousJob;
  }

  /**
   * Sets the value of the 'previousJob' field.
   * @param value the value to set.
   */
  public void setPreviousJob(com.epam.eco.commons.avro.data.TestJob value) {
    this.previousJob = value;
  }

  /**
   * Creates a new TestJob RecordBuilder.
   * @return A new TestJob RecordBuilder
   */
  public static com.epam.eco.commons.avro.data.TestJob.Builder newBuilder() {
    return new com.epam.eco.commons.avro.data.TestJob.Builder();
  }

  /**
   * Creates a new TestJob RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new TestJob RecordBuilder
   */
  public static com.epam.eco.commons.avro.data.TestJob.Builder newBuilder(com.epam.eco.commons.avro.data.TestJob.Builder other) {
    return new com.epam.eco.commons.avro.data.TestJob.Builder(other);
  }

  /**
   * Creates a new TestJob RecordBuilder by copying an existing TestJob instance.
   * @param other The existing instance to copy.
   * @return A new TestJob RecordBuilder
   */
  public static com.epam.eco.commons.avro.data.TestJob.Builder newBuilder(com.epam.eco.commons.avro.data.TestJob other) {
    return new com.epam.eco.commons.avro.data.TestJob.Builder(other);
  }

  /**
   * RecordBuilder for TestJob instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<TestJob>
    implements org.apache.avro.data.RecordBuilder<TestJob> {

    private java.lang.CharSequence company;
    private com.epam.eco.commons.avro.data.TestPosition position;
    private com.epam.eco.commons.avro.data.TestPosition.Builder positionBuilder;
    private com.epam.eco.commons.avro.data.TestJob previousJob;
    private com.epam.eco.commons.avro.data.TestJob.Builder previousJobBuilder;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.epam.eco.commons.avro.data.TestJob.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.company)) {
        this.company = data().deepCopy(fields()[0].schema(), other.company);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.position)) {
        this.position = data().deepCopy(fields()[1].schema(), other.position);
        fieldSetFlags()[1] = true;
      }
      if (other.hasPositionBuilder()) {
        this.positionBuilder = com.epam.eco.commons.avro.data.TestPosition.newBuilder(other.getPositionBuilder());
      }
      if (isValidValue(fields()[2], other.previousJob)) {
        this.previousJob = data().deepCopy(fields()[2].schema(), other.previousJob);
        fieldSetFlags()[2] = true;
      }
      if (other.hasPreviousJobBuilder()) {
        this.previousJobBuilder = com.epam.eco.commons.avro.data.TestJob.newBuilder(other.getPreviousJobBuilder());
      }
    }

    /**
     * Creates a Builder by copying an existing TestJob instance
     * @param other The existing instance to copy.
     */
    private Builder(com.epam.eco.commons.avro.data.TestJob other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.company)) {
        this.company = data().deepCopy(fields()[0].schema(), other.company);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.position)) {
        this.position = data().deepCopy(fields()[1].schema(), other.position);
        fieldSetFlags()[1] = true;
      }
      this.positionBuilder = null;
      if (isValidValue(fields()[2], other.previousJob)) {
        this.previousJob = data().deepCopy(fields()[2].schema(), other.previousJob);
        fieldSetFlags()[2] = true;
      }
      this.previousJobBuilder = null;
    }

    /**
      * Gets the value of the 'company' field.
      * @return The value.
      */
    public java.lang.CharSequence getCompany() {
      return company;
    }

    /**
      * Sets the value of the 'company' field.
      * @param value The value of 'company'.
      * @return This builder.
      */
    public com.epam.eco.commons.avro.data.TestJob.Builder setCompany(java.lang.CharSequence value) {
      validate(fields()[0], value);
      this.company = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'company' field has been set.
      * @return True if the 'company' field has been set, false otherwise.
      */
    public boolean hasCompany() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'company' field.
      * @return This builder.
      */
    public com.epam.eco.commons.avro.data.TestJob.Builder clearCompany() {
      company = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'position' field.
      * @return The value.
      */
    public com.epam.eco.commons.avro.data.TestPosition getPosition() {
      return position;
    }

    /**
      * Sets the value of the 'position' field.
      * @param value The value of 'position'.
      * @return This builder.
      */
    public com.epam.eco.commons.avro.data.TestJob.Builder setPosition(com.epam.eco.commons.avro.data.TestPosition value) {
      validate(fields()[1], value);
      this.positionBuilder = null;
      this.position = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'position' field has been set.
      * @return True if the 'position' field has been set, false otherwise.
      */
    public boolean hasPosition() {
      return fieldSetFlags()[1];
    }

    /**
     * Gets the Builder instance for the 'position' field and creates one if it doesn't exist yet.
     * @return This builder.
     */
    public com.epam.eco.commons.avro.data.TestPosition.Builder getPositionBuilder() {
      if (positionBuilder == null) {
        if (hasPosition()) {
          setPositionBuilder(com.epam.eco.commons.avro.data.TestPosition.newBuilder(position));
        } else {
          setPositionBuilder(com.epam.eco.commons.avro.data.TestPosition.newBuilder());
        }
      }
      return positionBuilder;
    }

    /**
     * Sets the Builder instance for the 'position' field
     * @param value The builder instance that must be set.
     * @return This builder.
     */
    public com.epam.eco.commons.avro.data.TestJob.Builder setPositionBuilder(com.epam.eco.commons.avro.data.TestPosition.Builder value) {
      clearPosition();
      positionBuilder = value;
      return this;
    }

    /**
     * Checks whether the 'position' field has an active Builder instance
     * @return True if the 'position' field has an active Builder instance
     */
    public boolean hasPositionBuilder() {
      return positionBuilder != null;
    }

    /**
      * Clears the value of the 'position' field.
      * @return This builder.
      */
    public com.epam.eco.commons.avro.data.TestJob.Builder clearPosition() {
      position = null;
      positionBuilder = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'previousJob' field.
      * @return The value.
      */
    public com.epam.eco.commons.avro.data.TestJob getPreviousJob() {
      return previousJob;
    }

    /**
      * Sets the value of the 'previousJob' field.
      * @param value The value of 'previousJob'.
      * @return This builder.
      */
    public com.epam.eco.commons.avro.data.TestJob.Builder setPreviousJob(com.epam.eco.commons.avro.data.TestJob value) {
      validate(fields()[2], value);
      this.previousJobBuilder = null;
      this.previousJob = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'previousJob' field has been set.
      * @return True if the 'previousJob' field has been set, false otherwise.
      */
    public boolean hasPreviousJob() {
      return fieldSetFlags()[2];
    }

    /**
     * Gets the Builder instance for the 'previousJob' field and creates one if it doesn't exist yet.
     * @return This builder.
     */
    public com.epam.eco.commons.avro.data.TestJob.Builder getPreviousJobBuilder() {
      if (previousJobBuilder == null) {
        if (hasPreviousJob()) {
          setPreviousJobBuilder(com.epam.eco.commons.avro.data.TestJob.newBuilder(previousJob));
        } else {
          setPreviousJobBuilder(com.epam.eco.commons.avro.data.TestJob.newBuilder());
        }
      }
      return previousJobBuilder;
    }

    /**
     * Sets the Builder instance for the 'previousJob' field
     * @param value The builder instance that must be set.
     * @return This builder.
     */
    public com.epam.eco.commons.avro.data.TestJob.Builder setPreviousJobBuilder(com.epam.eco.commons.avro.data.TestJob.Builder value) {
      clearPreviousJob();
      previousJobBuilder = value;
      return this;
    }

    /**
     * Checks whether the 'previousJob' field has an active Builder instance
     * @return True if the 'previousJob' field has an active Builder instance
     */
    public boolean hasPreviousJobBuilder() {
      return previousJobBuilder != null;
    }

    /**
      * Clears the value of the 'previousJob' field.
      * @return This builder.
      */
    public com.epam.eco.commons.avro.data.TestJob.Builder clearPreviousJob() {
      previousJob = null;
      previousJobBuilder = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    @Override
    public TestJob build() {
      try {
        TestJob record = new TestJob();
        record.company = fieldSetFlags()[0] ? this.company : (java.lang.CharSequence) defaultValue(fields()[0]);
        if (positionBuilder != null) {
          record.position = this.positionBuilder.build();
        } else {
          record.position = fieldSetFlags()[1] ? this.position : (com.epam.eco.commons.avro.data.TestPosition) defaultValue(fields()[1]);
        }
        if (previousJobBuilder != null) {
          record.previousJob = this.previousJobBuilder.build();
        } else {
          record.previousJob = fieldSetFlags()[2] ? this.previousJob : (com.epam.eco.commons.avro.data.TestJob) defaultValue(fields()[2]);
        }
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
