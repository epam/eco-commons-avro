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
public class TestPerson extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -7031688094266659120L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"TestPerson\",\"namespace\":\"com.epam.eco.commons.avro.data\",\"fields\":[{\"name\":\"age\",\"type\":[\"null\",\"int\"]},{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"hobby\",\"type\":[\"null\",{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"TestHobby\",\"fields\":[{\"name\":\"kind\",\"type\":\"string\"},{\"name\":\"description\",\"type\":\"string\"}]}}]},{\"name\":\"job\",\"type\":{\"type\":\"record\",\"name\":\"TestJob\",\"fields\":[{\"name\":\"company\",\"type\":\"string\"},{\"name\":\"position\",\"type\":{\"type\":\"record\",\"name\":\"TestPosition\",\"fields\":[{\"name\":\"title\",\"type\":\"string\"},{\"name\":\"skill\",\"type\":{\"type\":\"map\",\"values\":{\"type\":\"record\",\"name\":\"TestSkillLevel\",\"fields\":[{\"name\":\"level\",\"type\":\"string\"},{\"name\":\"description\",\"type\":\"string\"}]}}}]}},{\"name\":\"previousJob\",\"type\":[\"null\",\"TestJob\"]}]}}],\"prop1\":\"nomatter\",\"prop2\":\"nomatter\",\"prop3\":\"nomatter\"}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  @Deprecated public java.lang.Integer age;
  @Deprecated public java.lang.CharSequence name;
  @Deprecated public java.util.List<com.epam.eco.commons.avro.data.TestHobby> hobby;
  @Deprecated public com.epam.eco.commons.avro.data.TestJob job;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public TestPerson() {}

  /**
   * All-args constructor.
   * @param age The new value for age
   * @param name The new value for name
   * @param hobby The new value for hobby
   * @param job The new value for job
   */
  public TestPerson(java.lang.Integer age, java.lang.CharSequence name, java.util.List<com.epam.eco.commons.avro.data.TestHobby> hobby, com.epam.eco.commons.avro.data.TestJob job) {
    this.age = age;
    this.name = name;
    this.hobby = hobby;
    this.job = job;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return age;
    case 1: return name;
    case 2: return hobby;
    case 3: return job;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: age = (java.lang.Integer)value$; break;
    case 1: name = (java.lang.CharSequence)value$; break;
    case 2: hobby = (java.util.List<com.epam.eco.commons.avro.data.TestHobby>)value$; break;
    case 3: job = (com.epam.eco.commons.avro.data.TestJob)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'age' field.
   * @return The value of the 'age' field.
   */
  public java.lang.Integer getAge() {
    return age;
  }

  /**
   * Sets the value of the 'age' field.
   * @param value the value to set.
   */
  public void setAge(java.lang.Integer value) {
    this.age = value;
  }

  /**
   * Gets the value of the 'name' field.
   * @return The value of the 'name' field.
   */
  public java.lang.CharSequence getName() {
    return name;
  }

  /**
   * Sets the value of the 'name' field.
   * @param value the value to set.
   */
  public void setName(java.lang.CharSequence value) {
    this.name = value;
  }

  /**
   * Gets the value of the 'hobby' field.
   * @return The value of the 'hobby' field.
   */
  public java.util.List<com.epam.eco.commons.avro.data.TestHobby> getHobby() {
    return hobby;
  }

  /**
   * Sets the value of the 'hobby' field.
   * @param value the value to set.
   */
  public void setHobby(java.util.List<com.epam.eco.commons.avro.data.TestHobby> value) {
    this.hobby = value;
  }

  /**
   * Gets the value of the 'job' field.
   * @return The value of the 'job' field.
   */
  public com.epam.eco.commons.avro.data.TestJob getJob() {
    return job;
  }

  /**
   * Sets the value of the 'job' field.
   * @param value the value to set.
   */
  public void setJob(com.epam.eco.commons.avro.data.TestJob value) {
    this.job = value;
  }

  /**
   * Creates a new TestPerson RecordBuilder.
   * @return A new TestPerson RecordBuilder
   */
  public static com.epam.eco.commons.avro.data.TestPerson.Builder newBuilder() {
    return new com.epam.eco.commons.avro.data.TestPerson.Builder();
  }

  /**
   * Creates a new TestPerson RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new TestPerson RecordBuilder
   */
  public static com.epam.eco.commons.avro.data.TestPerson.Builder newBuilder(com.epam.eco.commons.avro.data.TestPerson.Builder other) {
    return new com.epam.eco.commons.avro.data.TestPerson.Builder(other);
  }

  /**
   * Creates a new TestPerson RecordBuilder by copying an existing TestPerson instance.
   * @param other The existing instance to copy.
   * @return A new TestPerson RecordBuilder
   */
  public static com.epam.eco.commons.avro.data.TestPerson.Builder newBuilder(com.epam.eco.commons.avro.data.TestPerson other) {
    return new com.epam.eco.commons.avro.data.TestPerson.Builder(other);
  }

  /**
   * RecordBuilder for TestPerson instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<TestPerson>
    implements org.apache.avro.data.RecordBuilder<TestPerson> {

    private java.lang.Integer age;
    private java.lang.CharSequence name;
    private java.util.List<com.epam.eco.commons.avro.data.TestHobby> hobby;
    private com.epam.eco.commons.avro.data.TestJob job;
    private com.epam.eco.commons.avro.data.TestJob.Builder jobBuilder;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.epam.eco.commons.avro.data.TestPerson.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.age)) {
        this.age = data().deepCopy(fields()[0].schema(), other.age);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.name)) {
        this.name = data().deepCopy(fields()[1].schema(), other.name);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.hobby)) {
        this.hobby = data().deepCopy(fields()[2].schema(), other.hobby);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.job)) {
        this.job = data().deepCopy(fields()[3].schema(), other.job);
        fieldSetFlags()[3] = true;
      }
      if (other.hasJobBuilder()) {
        this.jobBuilder = com.epam.eco.commons.avro.data.TestJob.newBuilder(other.getJobBuilder());
      }
    }

    /**
     * Creates a Builder by copying an existing TestPerson instance
     * @param other The existing instance to copy.
     */
    private Builder(com.epam.eco.commons.avro.data.TestPerson other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.age)) {
        this.age = data().deepCopy(fields()[0].schema(), other.age);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.name)) {
        this.name = data().deepCopy(fields()[1].schema(), other.name);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.hobby)) {
        this.hobby = data().deepCopy(fields()[2].schema(), other.hobby);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.job)) {
        this.job = data().deepCopy(fields()[3].schema(), other.job);
        fieldSetFlags()[3] = true;
      }
      this.jobBuilder = null;
    }

    /**
      * Gets the value of the 'age' field.
      * @return The value.
      */
    public java.lang.Integer getAge() {
      return age;
    }

    /**
      * Sets the value of the 'age' field.
      * @param value The value of 'age'.
      * @return This builder.
      */
    public com.epam.eco.commons.avro.data.TestPerson.Builder setAge(java.lang.Integer value) {
      validate(fields()[0], value);
      this.age = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'age' field has been set.
      * @return True if the 'age' field has been set, false otherwise.
      */
    public boolean hasAge() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'age' field.
      * @return This builder.
      */
    public com.epam.eco.commons.avro.data.TestPerson.Builder clearAge() {
      age = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'name' field.
      * @return The value.
      */
    public java.lang.CharSequence getName() {
      return name;
    }

    /**
      * Sets the value of the 'name' field.
      * @param value The value of 'name'.
      * @return This builder.
      */
    public com.epam.eco.commons.avro.data.TestPerson.Builder setName(java.lang.CharSequence value) {
      validate(fields()[1], value);
      this.name = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'name' field has been set.
      * @return True if the 'name' field has been set, false otherwise.
      */
    public boolean hasName() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'name' field.
      * @return This builder.
      */
    public com.epam.eco.commons.avro.data.TestPerson.Builder clearName() {
      name = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'hobby' field.
      * @return The value.
      */
    public java.util.List<com.epam.eco.commons.avro.data.TestHobby> getHobby() {
      return hobby;
    }

    /**
      * Sets the value of the 'hobby' field.
      * @param value The value of 'hobby'.
      * @return This builder.
      */
    public com.epam.eco.commons.avro.data.TestPerson.Builder setHobby(java.util.List<com.epam.eco.commons.avro.data.TestHobby> value) {
      validate(fields()[2], value);
      this.hobby = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'hobby' field has been set.
      * @return True if the 'hobby' field has been set, false otherwise.
      */
    public boolean hasHobby() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'hobby' field.
      * @return This builder.
      */
    public com.epam.eco.commons.avro.data.TestPerson.Builder clearHobby() {
      hobby = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'job' field.
      * @return The value.
      */
    public com.epam.eco.commons.avro.data.TestJob getJob() {
      return job;
    }

    /**
      * Sets the value of the 'job' field.
      * @param value The value of 'job'.
      * @return This builder.
      */
    public com.epam.eco.commons.avro.data.TestPerson.Builder setJob(com.epam.eco.commons.avro.data.TestJob value) {
      validate(fields()[3], value);
      this.jobBuilder = null;
      this.job = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'job' field has been set.
      * @return True if the 'job' field has been set, false otherwise.
      */
    public boolean hasJob() {
      return fieldSetFlags()[3];
    }

    /**
     * Gets the Builder instance for the 'job' field and creates one if it doesn't exist yet.
     * @return This builder.
     */
    public com.epam.eco.commons.avro.data.TestJob.Builder getJobBuilder() {
      if (jobBuilder == null) {
        if (hasJob()) {
          setJobBuilder(com.epam.eco.commons.avro.data.TestJob.newBuilder(job));
        } else {
          setJobBuilder(com.epam.eco.commons.avro.data.TestJob.newBuilder());
        }
      }
      return jobBuilder;
    }

    /**
     * Sets the Builder instance for the 'job' field
     * @param value The builder instance that must be set.
     * @return This builder.
     */
    public com.epam.eco.commons.avro.data.TestPerson.Builder setJobBuilder(com.epam.eco.commons.avro.data.TestJob.Builder value) {
      clearJob();
      jobBuilder = value;
      return this;
    }

    /**
     * Checks whether the 'job' field has an active Builder instance
     * @return True if the 'job' field has an active Builder instance
     */
    public boolean hasJobBuilder() {
      return jobBuilder != null;
    }

    /**
      * Clears the value of the 'job' field.
      * @return This builder.
      */
    public com.epam.eco.commons.avro.data.TestPerson.Builder clearJob() {
      job = null;
      jobBuilder = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    @Override
    public TestPerson build() {
      try {
        TestPerson record = new TestPerson();
        record.age = fieldSetFlags()[0] ? this.age : (java.lang.Integer) defaultValue(fields()[0]);
        record.name = fieldSetFlags()[1] ? this.name : (java.lang.CharSequence) defaultValue(fields()[1]);
        record.hobby = fieldSetFlags()[2] ? this.hobby : (java.util.List<com.epam.eco.commons.avro.data.TestHobby>) defaultValue(fields()[2]);
        if (jobBuilder != null) {
          record.job = this.jobBuilder.build();
        } else {
          record.job = fieldSetFlags()[3] ? this.job : (com.epam.eco.commons.avro.data.TestJob) defaultValue(fields()[3]);
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
