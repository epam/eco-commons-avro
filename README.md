# Eco Commons Avro

It's a library of utilities, helpers and higher-level APIs for the [Avro](https://avro.apache.org/) serialization system.

The library can be obtained from the Maven by adding the following dependency in the pom.xml:

```
<dependency>
    <groupId>com.epam.eco</groupId>
    <artifactId>commons-avro</artifactId>
    <version>${project.version}</version>
</dependency>
```

## Features

* XPath like DSL to query and manipulate entries of an Avro documents
* Traverse/modify/generate Avro schemas
* Convert/cast Avro documents
* Get detailed results of an Avro schema compatibility checks

## Build

```
git clone git@github.com:epam/eco-commons-avro.git
cd eco-commons-avro
mvn clean package
```

## License

Licensed under the [Apache License, Version 2.0](https://www.apache.org/licenses/LICENSE-2.0)