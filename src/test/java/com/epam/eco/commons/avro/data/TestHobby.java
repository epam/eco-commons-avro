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
public class TestHobby extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -6180575199285306438L;


  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"TestHobby\",\"namespace\":\"com.epam.eco.commons.avro.data\",\"fields\":[{\"name\":\"kind\",\"type\":\"string\"},{\"name\":\"description\",\"type\":\"string\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static final SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<TestHobby> ENCODER =
      new BinaryMessageEncoder<TestHobby>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<TestHobby> DECODER =
      new BinaryMessageDecoder<TestHobby>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<TestHobby> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<TestHobby> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<TestHobby> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<TestHobby>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this TestHobby to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a TestHobby from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a TestHobby instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static TestHobby fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  private java.lang.CharSequence kind;
  private java.lang.CharSequence description;

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

  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return kind;
    case 1: return description;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: kind = (java.lang.CharSequence)value$; break;
    case 1: description = (java.lang.CharSequence)value$; break;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
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
    if (other == null) {
      return new com.epam.eco.commons.avro.data.TestHobby.Builder();
    } else {
      return new com.epam.eco.commons.avro.data.TestHobby.Builder(other);
    }
  }

  /**
   * Creates a new TestHobby RecordBuilder by copying an existing TestHobby instance.
   * @param other The existing instance to copy.
   * @return A new TestHobby RecordBuilder
   */
  public static com.epam.eco.commons.avro.data.TestHobby.Builder newBuilder(com.epam.eco.commons.avro.data.TestHobby other) {
    if (other == null) {
      return new com.epam.eco.commons.avro.data.TestHobby.Builder();
    } else {
      return new com.epam.eco.commons.avro.data.TestHobby.Builder(other);
    }
  }

  /**
   * RecordBuilder for TestHobby instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<TestHobby>
    implements org.apache.avro.data.RecordBuilder<TestHobby> {

    private java.lang.CharSequence kind;
    private java.lang.CharSequence description;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$, MODEL$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.epam.eco.commons.avro.data.TestHobby.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.kind)) {
        this.kind = data().deepCopy(fields()[0].schema(), other.kind);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.description)) {
        this.description = data().deepCopy(fields()[1].schema(), other.description);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
    }

    /**
     * Creates a Builder by copying an existing TestHobby instance
     * @param other The existing instance to copy.
     */
    private Builder(com.epam.eco.commons.avro.data.TestHobby other) {
      super(SCHEMA$, MODEL$);
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
    @SuppressWarnings("unchecked")
    public TestHobby build() {
      try {
        TestHobby record = new TestHobby();
        record.kind = fieldSetFlags()[0] ? this.kind : (java.lang.CharSequence) defaultValue(fields()[0]);
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
  private static final org.apache.avro.io.DatumWriter<TestHobby>
    WRITER$ = (org.apache.avro.io.DatumWriter<TestHobby>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<TestHobby>
    READER$ = (org.apache.avro.io.DatumReader<TestHobby>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

  @Override protected boolean hasCustomCoders() { return true; }

  @Override public void customEncode(org.apache.avro.io.Encoder out)
    throws java.io.IOException
  {
    out.writeString(this.kind);

    out.writeString(this.description);

  }

  @Override public void customDecode(org.apache.avro.io.ResolvingDecoder in)
    throws java.io.IOException
  {
    org.apache.avro.Schema.Field[] fieldOrder = in.readFieldOrderIfDiff();
    if (fieldOrder == null) {
      this.kind = in.readString(this.kind instanceof Utf8 ? (Utf8)this.kind : null);

      this.description = in.readString(this.description instanceof Utf8 ? (Utf8)this.description : null);

    } else {
      for (int i = 0; i < 2; i++) {
        switch (fieldOrder[i].pos()) {
        case 0:
          this.kind = in.readString(this.kind instanceof Utf8 ? (Utf8)this.kind : null);
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










