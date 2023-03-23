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
public class TestPositionDerived extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -1301616817505139389L;


  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"TestPositionDerived\",\"namespace\":\"com.epam.eco.commons.avro.data.derived\",\"fields\":[{\"name\":\"skill\",\"type\":{\"type\":\"map\",\"values\":{\"type\":\"record\",\"name\":\"TestSkillLevelDerived\",\"fields\":[{\"name\":\"level\",\"type\":\"string\"}]}}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static final SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<TestPositionDerived> ENCODER =
      new BinaryMessageEncoder<TestPositionDerived>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<TestPositionDerived> DECODER =
      new BinaryMessageDecoder<TestPositionDerived>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<TestPositionDerived> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<TestPositionDerived> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<TestPositionDerived> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<TestPositionDerived>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this TestPositionDerived to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a TestPositionDerived from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a TestPositionDerived instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static TestPositionDerived fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  private java.util.Map<java.lang.CharSequence,com.epam.eco.commons.avro.data.derived.TestSkillLevelDerived> skill;

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

  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return skill;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: skill = (java.util.Map<java.lang.CharSequence,com.epam.eco.commons.avro.data.derived.TestSkillLevelDerived>)value$; break;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
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
    if (other == null) {
      return new com.epam.eco.commons.avro.data.derived.TestPositionDerived.Builder();
    } else {
      return new com.epam.eco.commons.avro.data.derived.TestPositionDerived.Builder(other);
    }
  }

  /**
   * Creates a new TestPositionDerived RecordBuilder by copying an existing TestPositionDerived instance.
   * @param other The existing instance to copy.
   * @return A new TestPositionDerived RecordBuilder
   */
  public static com.epam.eco.commons.avro.data.derived.TestPositionDerived.Builder newBuilder(com.epam.eco.commons.avro.data.derived.TestPositionDerived other) {
    if (other == null) {
      return new com.epam.eco.commons.avro.data.derived.TestPositionDerived.Builder();
    } else {
      return new com.epam.eco.commons.avro.data.derived.TestPositionDerived.Builder(other);
    }
  }

  /**
   * RecordBuilder for TestPositionDerived instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<TestPositionDerived>
    implements org.apache.avro.data.RecordBuilder<TestPositionDerived> {

    private java.util.Map<java.lang.CharSequence,com.epam.eco.commons.avro.data.derived.TestSkillLevelDerived> skill;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$, MODEL$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.epam.eco.commons.avro.data.derived.TestPositionDerived.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.skill)) {
        this.skill = data().deepCopy(fields()[0].schema(), other.skill);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
    }

    /**
     * Creates a Builder by copying an existing TestPositionDerived instance
     * @param other The existing instance to copy.
     */
    private Builder(com.epam.eco.commons.avro.data.derived.TestPositionDerived other) {
      super(SCHEMA$, MODEL$);
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
    @SuppressWarnings("unchecked")
    public TestPositionDerived build() {
      try {
        TestPositionDerived record = new TestPositionDerived();
        record.skill = fieldSetFlags()[0] ? this.skill : (java.util.Map<java.lang.CharSequence,com.epam.eco.commons.avro.data.derived.TestSkillLevelDerived>) defaultValue(fields()[0]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<TestPositionDerived>
    WRITER$ = (org.apache.avro.io.DatumWriter<TestPositionDerived>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<TestPositionDerived>
    READER$ = (org.apache.avro.io.DatumReader<TestPositionDerived>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

  @Override protected boolean hasCustomCoders() { return true; }

  @Override public void customEncode(org.apache.avro.io.Encoder out)
    throws java.io.IOException
  {
    long size0 = this.skill.size();
    out.writeMapStart();
    out.setItemCount(size0);
    long actualSize0 = 0;
    for (java.util.Map.Entry<java.lang.CharSequence, com.epam.eco.commons.avro.data.derived.TestSkillLevelDerived> e0: this.skill.entrySet()) {
      actualSize0++;
      out.startItem();
      out.writeString(e0.getKey());
      com.epam.eco.commons.avro.data.derived.TestSkillLevelDerived v0 = e0.getValue();
      v0.customEncode(out);
    }
    out.writeMapEnd();
    if (actualSize0 != size0)
      throw new java.util.ConcurrentModificationException("Map-size written was " + size0 + ", but element count was " + actualSize0 + ".");

  }

  @Override public void customDecode(org.apache.avro.io.ResolvingDecoder in)
    throws java.io.IOException
  {
    org.apache.avro.Schema.Field[] fieldOrder = in.readFieldOrderIfDiff();
    if (fieldOrder == null) {
      long size0 = in.readMapStart();
      java.util.Map<java.lang.CharSequence,com.epam.eco.commons.avro.data.derived.TestSkillLevelDerived> m0 = this.skill; // Need fresh name due to limitation of macro system
      if (m0 == null) {
        m0 = new java.util.HashMap<java.lang.CharSequence,com.epam.eco.commons.avro.data.derived.TestSkillLevelDerived>((int)size0);
        this.skill = m0;
      } else m0.clear();
      for ( ; 0 < size0; size0 = in.mapNext()) {
        for ( ; size0 != 0; size0--) {
          java.lang.CharSequence k0 = null;
          k0 = in.readString(k0 instanceof Utf8 ? (Utf8)k0 : null);
          com.epam.eco.commons.avro.data.derived.TestSkillLevelDerived v0 = null;
          if (v0 == null) {
            v0 = new com.epam.eco.commons.avro.data.derived.TestSkillLevelDerived();
          }
          v0.customDecode(in);
          m0.put(k0, v0);
        }
      }

    } else {
      for (int i = 0; i < 1; i++) {
        switch (fieldOrder[i].pos()) {
        case 0:
          long size0 = in.readMapStart();
          java.util.Map<java.lang.CharSequence,com.epam.eco.commons.avro.data.derived.TestSkillLevelDerived> m0 = this.skill; // Need fresh name due to limitation of macro system
          if (m0 == null) {
            m0 = new java.util.HashMap<java.lang.CharSequence,com.epam.eco.commons.avro.data.derived.TestSkillLevelDerived>((int)size0);
            this.skill = m0;
          } else m0.clear();
          for ( ; 0 < size0; size0 = in.mapNext()) {
            for ( ; size0 != 0; size0--) {
              java.lang.CharSequence k0 = null;
              k0 = in.readString(k0 instanceof Utf8 ? (Utf8)k0 : null);
              com.epam.eco.commons.avro.data.derived.TestSkillLevelDerived v0 = null;
              if (v0 == null) {
                v0 = new com.epam.eco.commons.avro.data.derived.TestSkillLevelDerived();
              }
              v0.customDecode(in);
              m0.put(k0, v0);
            }
          }
          break;

        default:
          throw new java.io.IOException("Corrupt ResolvingDecoder.");
        }
      }
    }
  }
}










