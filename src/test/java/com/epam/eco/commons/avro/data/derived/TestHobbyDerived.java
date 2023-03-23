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

import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.SchemaStore;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.util.Utf8;

@org.apache.avro.specific.AvroGenerated
public class TestHobbyDerived extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 5537249504063743418L;


  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"TestHobbyDerived\",\"namespace\":\"com.epam.eco.commons.avro.data.derived\",\"fields\":[{\"name\":\"kind\",\"type\":\"string\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static final SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<TestHobbyDerived> ENCODER =
      new BinaryMessageEncoder<TestHobbyDerived>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<TestHobbyDerived> DECODER =
      new BinaryMessageDecoder<TestHobbyDerived>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<TestHobbyDerived> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<TestHobbyDerived> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<TestHobbyDerived> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<TestHobbyDerived>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this TestHobbyDerived to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a TestHobbyDerived from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a TestHobbyDerived instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static TestHobbyDerived fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  private java.lang.CharSequence kind;

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

  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return kind;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: kind = (java.lang.CharSequence)value$; break;
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
    if (other == null) {
      return new com.epam.eco.commons.avro.data.derived.TestHobbyDerived.Builder();
    } else {
      return new com.epam.eco.commons.avro.data.derived.TestHobbyDerived.Builder(other);
    }
  }

  /**
   * Creates a new TestHobbyDerived RecordBuilder by copying an existing TestHobbyDerived instance.
   * @param other The existing instance to copy.
   * @return A new TestHobbyDerived RecordBuilder
   */
  public static com.epam.eco.commons.avro.data.derived.TestHobbyDerived.Builder newBuilder(com.epam.eco.commons.avro.data.derived.TestHobbyDerived other) {
    if (other == null) {
      return new com.epam.eco.commons.avro.data.derived.TestHobbyDerived.Builder();
    } else {
      return new com.epam.eco.commons.avro.data.derived.TestHobbyDerived.Builder(other);
    }
  }

  /**
   * RecordBuilder for TestHobbyDerived instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<TestHobbyDerived>
    implements org.apache.avro.data.RecordBuilder<TestHobbyDerived> {

    private java.lang.CharSequence kind;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$, MODEL$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.epam.eco.commons.avro.data.derived.TestHobbyDerived.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.kind)) {
        this.kind = data().deepCopy(fields()[0].schema(), other.kind);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
    }

    /**
     * Creates a Builder by copying an existing TestHobbyDerived instance
     * @param other The existing instance to copy.
     */
    private Builder(com.epam.eco.commons.avro.data.derived.TestHobbyDerived other) {
      super(SCHEMA$, MODEL$);
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
    @SuppressWarnings("unchecked")
    public TestHobbyDerived build() {
      try {
        TestHobbyDerived record = new TestHobbyDerived();
        record.kind = fieldSetFlags()[0] ? this.kind : (java.lang.CharSequence) defaultValue(fields()[0]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<TestHobbyDerived>
    WRITER$ = (org.apache.avro.io.DatumWriter<TestHobbyDerived>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<TestHobbyDerived>
    READER$ = (org.apache.avro.io.DatumReader<TestHobbyDerived>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

  @Override protected boolean hasCustomCoders() { return true; }

  @Override public void customEncode(org.apache.avro.io.Encoder out)
    throws java.io.IOException
  {
    out.writeString(this.kind);

  }

  @Override public void customDecode(org.apache.avro.io.ResolvingDecoder in)
    throws java.io.IOException
  {
    org.apache.avro.Schema.Field[] fieldOrder = in.readFieldOrderIfDiff();
    if (fieldOrder == null) {
      this.kind = in.readString(this.kind instanceof Utf8 ? (Utf8)this.kind : null);

    } else {
      for (int i = 0; i < 1; i++) {
        switch (fieldOrder[i].pos()) {
        case 0:
          this.kind = in.readString(this.kind instanceof Utf8 ? (Utf8)this.kind : null);
          break;

        default:
          throw new java.io.IOException("Corrupt ResolvingDecoder.");
        }
      }
    }
  }
}










