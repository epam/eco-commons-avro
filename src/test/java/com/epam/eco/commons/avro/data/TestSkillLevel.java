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
package com.epam.eco.commons.avro.data;

import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.SchemaStore;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.util.Utf8;

@org.apache.avro.specific.AvroGenerated
public class TestSkillLevel extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -1729211953889647971L;


  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"TestSkillLevel\",\"namespace\":\"com.epam.eco.commons.avro.data\",\"fields\":[{\"name\":\"level\",\"type\":\"string\"},{\"name\":\"description\",\"type\":\"string\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static final SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<TestSkillLevel> ENCODER =
      new BinaryMessageEncoder<TestSkillLevel>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<TestSkillLevel> DECODER =
      new BinaryMessageDecoder<TestSkillLevel>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<TestSkillLevel> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<TestSkillLevel> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<TestSkillLevel> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<TestSkillLevel>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this TestSkillLevel to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a TestSkillLevel from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a TestSkillLevel instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static TestSkillLevel fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  private java.lang.CharSequence level;
  private java.lang.CharSequence description;

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

  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return level;
    case 1: return description;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: level = (java.lang.CharSequence)value$; break;
    case 1: description = (java.lang.CharSequence)value$; break;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
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
    if (other == null) {
      return new com.epam.eco.commons.avro.data.TestSkillLevel.Builder();
    } else {
      return new com.epam.eco.commons.avro.data.TestSkillLevel.Builder(other);
    }
  }

  /**
   * Creates a new TestSkillLevel RecordBuilder by copying an existing TestSkillLevel instance.
   * @param other The existing instance to copy.
   * @return A new TestSkillLevel RecordBuilder
   */
  public static com.epam.eco.commons.avro.data.TestSkillLevel.Builder newBuilder(com.epam.eco.commons.avro.data.TestSkillLevel other) {
    if (other == null) {
      return new com.epam.eco.commons.avro.data.TestSkillLevel.Builder();
    } else {
      return new com.epam.eco.commons.avro.data.TestSkillLevel.Builder(other);
    }
  }

  /**
   * RecordBuilder for TestSkillLevel instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<TestSkillLevel>
    implements org.apache.avro.data.RecordBuilder<TestSkillLevel> {

    private java.lang.CharSequence level;
    private java.lang.CharSequence description;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$, MODEL$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.epam.eco.commons.avro.data.TestSkillLevel.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.level)) {
        this.level = data().deepCopy(fields()[0].schema(), other.level);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.description)) {
        this.description = data().deepCopy(fields()[1].schema(), other.description);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
    }

    /**
     * Creates a Builder by copying an existing TestSkillLevel instance
     * @param other The existing instance to copy.
     */
    private Builder(com.epam.eco.commons.avro.data.TestSkillLevel other) {
      super(SCHEMA$, MODEL$);
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
    @SuppressWarnings("unchecked")
    public TestSkillLevel build() {
      try {
        TestSkillLevel record = new TestSkillLevel();
        record.level = fieldSetFlags()[0] ? this.level : (java.lang.CharSequence) defaultValue(fields()[0]);
        record.description = fieldSetFlags()[1] ? this.description : (java.lang.CharSequence) defaultValue(fields()[1]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<TestSkillLevel>
    WRITER$ = (org.apache.avro.io.DatumWriter<TestSkillLevel>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<TestSkillLevel>
    READER$ = (org.apache.avro.io.DatumReader<TestSkillLevel>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

  @Override protected boolean hasCustomCoders() { return true; }

  @Override public void customEncode(org.apache.avro.io.Encoder out)
    throws java.io.IOException
  {
    out.writeString(this.level);

    out.writeString(this.description);

  }

  @Override public void customDecode(org.apache.avro.io.ResolvingDecoder in)
    throws java.io.IOException
  {
    org.apache.avro.Schema.Field[] fieldOrder = in.readFieldOrderIfDiff();
    if (fieldOrder == null) {
      this.level = in.readString(this.level instanceof Utf8 ? (Utf8)this.level : null);

      this.description = in.readString(this.description instanceof Utf8 ? (Utf8)this.description : null);

    } else {
      for (int i = 0; i < 2; i++) {
        switch (fieldOrder[i].pos()) {
        case 0:
          this.level = in.readString(this.level instanceof Utf8 ? (Utf8)this.level : null);
          break;

        case 1:
          this.description = in.readString(this.description instanceof Utf8 ? (Utf8)this.description : null);
          break;

        default:
          throw new java.io.IOException("Corrupt ResolvingDecoder.");
        }
      }
    }
  }
}










