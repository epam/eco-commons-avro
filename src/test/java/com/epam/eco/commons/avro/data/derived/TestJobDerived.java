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

@org.apache.avro.specific.AvroGenerated
public class TestJobDerived extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -2217110338250292422L;


  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"TestJobDerived\",\"namespace\":\"com.epam.eco.commons.avro.data.derived\",\"fields\":[{\"name\":\"position\",\"type\":{\"type\":\"record\",\"name\":\"TestPositionDerived\",\"fields\":[{\"name\":\"skill\",\"type\":{\"type\":\"map\",\"values\":{\"type\":\"record\",\"name\":\"TestSkillLevelDerived\",\"fields\":[{\"name\":\"level\",\"type\":\"string\"}]}}}]}},{\"name\":\"previousJob\",\"type\":[\"null\",\"TestJobDerived\"]}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static final SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<TestJobDerived> ENCODER =
      new BinaryMessageEncoder<TestJobDerived>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<TestJobDerived> DECODER =
      new BinaryMessageDecoder<TestJobDerived>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<TestJobDerived> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<TestJobDerived> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<TestJobDerived> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<TestJobDerived>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this TestJobDerived to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a TestJobDerived from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a TestJobDerived instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static TestJobDerived fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  private com.epam.eco.commons.avro.data.derived.TestPositionDerived position;
  private com.epam.eco.commons.avro.data.derived.TestJobDerived previousJob;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public TestJobDerived() {}

  /**
   * All-args constructor.
   * @param position The new value for position
   * @param previousJob The new value for previousJob
   */
  public TestJobDerived(com.epam.eco.commons.avro.data.derived.TestPositionDerived position, com.epam.eco.commons.avro.data.derived.TestJobDerived previousJob) {
    this.position = position;
    this.previousJob = previousJob;
  }

  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return position;
    case 1: return previousJob;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: position = (com.epam.eco.commons.avro.data.derived.TestPositionDerived)value$; break;
    case 1: previousJob = (com.epam.eco.commons.avro.data.derived.TestJobDerived)value$; break;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  /**
   * Gets the value of the 'position' field.
   * @return The value of the 'position' field.
   */
  public com.epam.eco.commons.avro.data.derived.TestPositionDerived getPosition() {
    return position;
  }


  /**
   * Sets the value of the 'position' field.
   * @param value the value to set.
   */
  public void setPosition(com.epam.eco.commons.avro.data.derived.TestPositionDerived value) {
    this.position = value;
  }

  /**
   * Gets the value of the 'previousJob' field.
   * @return The value of the 'previousJob' field.
   */
  public com.epam.eco.commons.avro.data.derived.TestJobDerived getPreviousJob() {
    return previousJob;
  }


  /**
   * Sets the value of the 'previousJob' field.
   * @param value the value to set.
   */
  public void setPreviousJob(com.epam.eco.commons.avro.data.derived.TestJobDerived value) {
    this.previousJob = value;
  }

  /**
   * Creates a new TestJobDerived RecordBuilder.
   * @return A new TestJobDerived RecordBuilder
   */
  public static com.epam.eco.commons.avro.data.derived.TestJobDerived.Builder newBuilder() {
    return new com.epam.eco.commons.avro.data.derived.TestJobDerived.Builder();
  }

  /**
   * Creates a new TestJobDerived RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new TestJobDerived RecordBuilder
   */
  public static com.epam.eco.commons.avro.data.derived.TestJobDerived.Builder newBuilder(com.epam.eco.commons.avro.data.derived.TestJobDerived.Builder other) {
    if (other == null) {
      return new com.epam.eco.commons.avro.data.derived.TestJobDerived.Builder();
    } else {
      return new com.epam.eco.commons.avro.data.derived.TestJobDerived.Builder(other);
    }
  }

  /**
   * Creates a new TestJobDerived RecordBuilder by copying an existing TestJobDerived instance.
   * @param other The existing instance to copy.
   * @return A new TestJobDerived RecordBuilder
   */
  public static com.epam.eco.commons.avro.data.derived.TestJobDerived.Builder newBuilder(com.epam.eco.commons.avro.data.derived.TestJobDerived other) {
    if (other == null) {
      return new com.epam.eco.commons.avro.data.derived.TestJobDerived.Builder();
    } else {
      return new com.epam.eco.commons.avro.data.derived.TestJobDerived.Builder(other);
    }
  }

  /**
   * RecordBuilder for TestJobDerived instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<TestJobDerived>
    implements org.apache.avro.data.RecordBuilder<TestJobDerived> {

    private com.epam.eco.commons.avro.data.derived.TestPositionDerived position;
    private com.epam.eco.commons.avro.data.derived.TestPositionDerived.Builder positionBuilder;
    private com.epam.eco.commons.avro.data.derived.TestJobDerived previousJob;
    private com.epam.eco.commons.avro.data.derived.TestJobDerived.Builder previousJobBuilder;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$, MODEL$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.epam.eco.commons.avro.data.derived.TestJobDerived.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.position)) {
        this.position = data().deepCopy(fields()[0].schema(), other.position);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (other.hasPositionBuilder()) {
        this.positionBuilder = com.epam.eco.commons.avro.data.derived.TestPositionDerived.newBuilder(other.getPositionBuilder());
      }
      if (isValidValue(fields()[1], other.previousJob)) {
        this.previousJob = data().deepCopy(fields()[1].schema(), other.previousJob);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
      if (other.hasPreviousJobBuilder()) {
        this.previousJobBuilder = com.epam.eco.commons.avro.data.derived.TestJobDerived.newBuilder(other.getPreviousJobBuilder());
      }
    }

    /**
     * Creates a Builder by copying an existing TestJobDerived instance
     * @param other The existing instance to copy.
     */
    private Builder(com.epam.eco.commons.avro.data.derived.TestJobDerived other) {
      super(SCHEMA$, MODEL$);
      if (isValidValue(fields()[0], other.position)) {
        this.position = data().deepCopy(fields()[0].schema(), other.position);
        fieldSetFlags()[0] = true;
      }
      this.positionBuilder = null;
      if (isValidValue(fields()[1], other.previousJob)) {
        this.previousJob = data().deepCopy(fields()[1].schema(), other.previousJob);
        fieldSetFlags()[1] = true;
      }
      this.previousJobBuilder = null;
    }

    /**
      * Gets the value of the 'position' field.
      * @return The value.
      */
    public com.epam.eco.commons.avro.data.derived.TestPositionDerived getPosition() {
      return position;
    }


    /**
      * Sets the value of the 'position' field.
      * @param value The value of 'position'.
      * @return This builder.
      */
    public com.epam.eco.commons.avro.data.derived.TestJobDerived.Builder setPosition(com.epam.eco.commons.avro.data.derived.TestPositionDerived value) {
      validate(fields()[0], value);
      this.positionBuilder = null;
      this.position = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'position' field has been set.
      * @return True if the 'position' field has been set, false otherwise.
      */
    public boolean hasPosition() {
      return fieldSetFlags()[0];
    }

    /**
     * Gets the Builder instance for the 'position' field and creates one if it doesn't exist yet.
     * @return This builder.
     */
    public com.epam.eco.commons.avro.data.derived.TestPositionDerived.Builder getPositionBuilder() {
      if (positionBuilder == null) {
        if (hasPosition()) {
          setPositionBuilder(com.epam.eco.commons.avro.data.derived.TestPositionDerived.newBuilder(position));
        } else {
          setPositionBuilder(com.epam.eco.commons.avro.data.derived.TestPositionDerived.newBuilder());
        }
      }
      return positionBuilder;
    }

    /**
     * Sets the Builder instance for the 'position' field
     * @param value The builder instance that must be set.
     * @return This builder.
     */

    public com.epam.eco.commons.avro.data.derived.TestJobDerived.Builder setPositionBuilder(com.epam.eco.commons.avro.data.derived.TestPositionDerived.Builder value) {
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
    public com.epam.eco.commons.avro.data.derived.TestJobDerived.Builder clearPosition() {
      position = null;
      positionBuilder = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'previousJob' field.
      * @return The value.
      */
    public com.epam.eco.commons.avro.data.derived.TestJobDerived getPreviousJob() {
      return previousJob;
    }


    /**
      * Sets the value of the 'previousJob' field.
      * @param value The value of 'previousJob'.
      * @return This builder.
      */
    public com.epam.eco.commons.avro.data.derived.TestJobDerived.Builder setPreviousJob(com.epam.eco.commons.avro.data.derived.TestJobDerived value) {
      validate(fields()[1], value);
      this.previousJobBuilder = null;
      this.previousJob = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'previousJob' field has been set.
      * @return True if the 'previousJob' field has been set, false otherwise.
      */
    public boolean hasPreviousJob() {
      return fieldSetFlags()[1];
    }

    /**
     * Gets the Builder instance for the 'previousJob' field and creates one if it doesn't exist yet.
     * @return This builder.
     */
    public com.epam.eco.commons.avro.data.derived.TestJobDerived.Builder getPreviousJobBuilder() {
      if (previousJobBuilder == null) {
        if (hasPreviousJob()) {
          setPreviousJobBuilder(com.epam.eco.commons.avro.data.derived.TestJobDerived.newBuilder(previousJob));
        } else {
          setPreviousJobBuilder(com.epam.eco.commons.avro.data.derived.TestJobDerived.newBuilder());
        }
      }
      return previousJobBuilder;
    }

    /**
     * Sets the Builder instance for the 'previousJob' field
     * @param value The builder instance that must be set.
     * @return This builder.
     */

    public com.epam.eco.commons.avro.data.derived.TestJobDerived.Builder setPreviousJobBuilder(com.epam.eco.commons.avro.data.derived.TestJobDerived.Builder value) {
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
    public com.epam.eco.commons.avro.data.derived.TestJobDerived.Builder clearPreviousJob() {
      previousJob = null;
      previousJobBuilder = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public TestJobDerived build() {
      try {
        TestJobDerived record = new TestJobDerived();
        if (positionBuilder != null) {
          try {
            record.position = this.positionBuilder.build();
          } catch (org.apache.avro.AvroMissingFieldException e) {
            e.addParentField(record.getSchema().getField("position"));
            throw e;
          }
        } else {
          record.position = fieldSetFlags()[0] ? this.position : (com.epam.eco.commons.avro.data.derived.TestPositionDerived) defaultValue(fields()[0]);
        }
        if (previousJobBuilder != null) {
          try {
            record.previousJob = this.previousJobBuilder.build();
          } catch (org.apache.avro.AvroMissingFieldException e) {
            e.addParentField(record.getSchema().getField("previousJob"));
            throw e;
          }
        } else {
          record.previousJob = fieldSetFlags()[1] ? this.previousJob : (com.epam.eco.commons.avro.data.derived.TestJobDerived) defaultValue(fields()[1]);
        }
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<TestJobDerived>
    WRITER$ = (org.apache.avro.io.DatumWriter<TestJobDerived>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<TestJobDerived>
    READER$ = (org.apache.avro.io.DatumReader<TestJobDerived>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

  @Override protected boolean hasCustomCoders() { return true; }

  @Override public void customEncode(org.apache.avro.io.Encoder out)
    throws java.io.IOException
  {
    this.position.customEncode(out);

    if (this.previousJob == null) {
      out.writeIndex(0);
      out.writeNull();
    } else {
      out.writeIndex(1);
      this.previousJob.customEncode(out);
    }

  }

  @Override public void customDecode(org.apache.avro.io.ResolvingDecoder in)
    throws java.io.IOException
  {
    org.apache.avro.Schema.Field[] fieldOrder = in.readFieldOrderIfDiff();
    if (fieldOrder == null) {
      if (this.position == null) {
        this.position = new com.epam.eco.commons.avro.data.derived.TestPositionDerived();
      }
      this.position.customDecode(in);

      if (in.readIndex() != 1) {
        in.readNull();
        this.previousJob = null;
      } else {
        if (this.previousJob == null) {
          this.previousJob = new com.epam.eco.commons.avro.data.derived.TestJobDerived();
        }
        this.previousJob.customDecode(in);
      }

    } else {
      for (int i = 0; i < 2; i++) {
        switch (fieldOrder[i].pos()) {
        case 0:
          if (this.position == null) {
            this.position = new com.epam.eco.commons.avro.data.derived.TestPositionDerived();
          }
          this.position.customDecode(in);
          break;

        case 1:
          if (in.readIndex() != 1) {
            in.readNull();
            this.previousJob = null;
          } else {
            if (this.previousJob == null) {
              this.previousJob = new com.epam.eco.commons.avro.data.derived.TestJobDerived();
            }
            this.previousJob.customDecode(in);
          }
          break;

        default:
          throw new java.io.IOException("Corrupt ResolvingDecoder.");
        }
      }
    }
  }
}










