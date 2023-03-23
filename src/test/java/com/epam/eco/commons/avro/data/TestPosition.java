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
public class TestPosition extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 8548217914756755307L;


  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"TestPosition\",\"namespace\":\"com.epam.eco.commons.avro.data\",\"fields\":[{\"name\":\"title\",\"type\":\"string\"},{\"name\":\"skill\",\"type\":{\"type\":\"map\",\"values\":{\"type\":\"record\",\"name\":\"TestSkillLevel\",\"fields\":[{\"name\":\"level\",\"type\":\"string\"},{\"name\":\"description\",\"type\":\"string\"}]}}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static final SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<TestPosition> ENCODER =
      new BinaryMessageEncoder<TestPosition>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<TestPosition> DECODER =
      new BinaryMessageDecoder<TestPosition>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<TestPosition> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<TestPosition> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<TestPosition> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<TestPosition>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this TestPosition to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a TestPosition from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a TestPosition instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static TestPosition fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  private java.lang.CharSequence title;
  private java.util.Map<java.lang.CharSequence,com.epam.eco.commons.avro.data.TestSkillLevel> skill;

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

  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return title;
    case 1: return skill;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: title = (java.lang.CharSequence)value$; break;
    case 1: skill = (java.util.Map<java.lang.CharSequence,com.epam.eco.commons.avro.data.TestSkillLevel>)value$; break;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
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
    if (other == null) {
      return new com.epam.eco.commons.avro.data.TestPosition.Builder();
    } else {
      return new com.epam.eco.commons.avro.data.TestPosition.Builder(other);
    }
  }

  /**
   * Creates a new TestPosition RecordBuilder by copying an existing TestPosition instance.
   * @param other The existing instance to copy.
   * @return A new TestPosition RecordBuilder
   */
  public static com.epam.eco.commons.avro.data.TestPosition.Builder newBuilder(com.epam.eco.commons.avro.data.TestPosition other) {
    if (other == null) {
      return new com.epam.eco.commons.avro.data.TestPosition.Builder();
    } else {
      return new com.epam.eco.commons.avro.data.TestPosition.Builder(other);
    }
  }

  /**
   * RecordBuilder for TestPosition instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<TestPosition>
    implements org.apache.avro.data.RecordBuilder<TestPosition> {

    private java.lang.CharSequence title;
    private java.util.Map<java.lang.CharSequence,com.epam.eco.commons.avro.data.TestSkillLevel> skill;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$, MODEL$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.epam.eco.commons.avro.data.TestPosition.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.title)) {
        this.title = data().deepCopy(fields()[0].schema(), other.title);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.skill)) {
        this.skill = data().deepCopy(fields()[1].schema(), other.skill);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
    }

    /**
     * Creates a Builder by copying an existing TestPosition instance
     * @param other The existing instance to copy.
     */
    private Builder(com.epam.eco.commons.avro.data.TestPosition other) {
      super(SCHEMA$, MODEL$);
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
    @SuppressWarnings("unchecked")
    public TestPosition build() {
      try {
        TestPosition record = new TestPosition();
        record.title = fieldSetFlags()[0] ? this.title : (java.lang.CharSequence) defaultValue(fields()[0]);
        record.skill = fieldSetFlags()[1] ? this.skill : (java.util.Map<java.lang.CharSequence,com.epam.eco.commons.avro.data.TestSkillLevel>) defaultValue(fields()[1]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<TestPosition>
    WRITER$ = (org.apache.avro.io.DatumWriter<TestPosition>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<TestPosition>
    READER$ = (org.apache.avro.io.DatumReader<TestPosition>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

  @Override protected boolean hasCustomCoders() { return true; }

  @Override public void customEncode(org.apache.avro.io.Encoder out)
    throws java.io.IOException
  {
    out.writeString(this.title);

    long size0 = this.skill.size();
    out.writeMapStart();
    out.setItemCount(size0);
    long actualSize0 = 0;
    for (java.util.Map.Entry<java.lang.CharSequence, com.epam.eco.commons.avro.data.TestSkillLevel> e0: this.skill.entrySet()) {
      actualSize0++;
      out.startItem();
      out.writeString(e0.getKey());
      com.epam.eco.commons.avro.data.TestSkillLevel v0 = e0.getValue();
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
      this.title = in.readString(this.title instanceof Utf8 ? (Utf8)this.title : null);

      long size0 = in.readMapStart();
      java.util.Map<java.lang.CharSequence,com.epam.eco.commons.avro.data.TestSkillLevel> m0 = this.skill; // Need fresh name due to limitation of macro system
      if (m0 == null) {
        m0 = new java.util.HashMap<java.lang.CharSequence,com.epam.eco.commons.avro.data.TestSkillLevel>((int)size0);
        this.skill = m0;
      } else m0.clear();
      for ( ; 0 < size0; size0 = in.mapNext()) {
        for ( ; size0 != 0; size0--) {
          java.lang.CharSequence k0 = null;
          k0 = in.readString(k0 instanceof Utf8 ? (Utf8)k0 : null);
          com.epam.eco.commons.avro.data.TestSkillLevel v0 = null;
          if (v0 == null) {
            v0 = new com.epam.eco.commons.avro.data.TestSkillLevel();
          }
          v0.customDecode(in);
          m0.put(k0, v0);
        }
      }

    } else {
      for (int i = 0; i < 2; i++) {
        switch (fieldOrder[i].pos()) {
        case 0:
          this.title = in.readString(this.title instanceof Utf8 ? (Utf8)this.title : null);
          break;

        case 1:
          long size0 = in.readMapStart();
          java.util.Map<java.lang.CharSequence,com.epam.eco.commons.avro.data.TestSkillLevel> m0 = this.skill; // Need fresh name due to limitation of macro system
          if (m0 == null) {
            m0 = new java.util.HashMap<java.lang.CharSequence,com.epam.eco.commons.avro.data.TestSkillLevel>((int)size0);
            this.skill = m0;
          } else m0.clear();
          for ( ; 0 < size0; size0 = in.mapNext()) {
            for ( ; size0 != 0; size0--) {
              java.lang.CharSequence k0 = null;
              k0 = in.readString(k0 instanceof Utf8 ? (Utf8)k0 : null);
              com.epam.eco.commons.avro.data.TestSkillLevel v0 = null;
              if (v0 == null) {
                v0 = new com.epam.eco.commons.avro.data.TestSkillLevel();
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










