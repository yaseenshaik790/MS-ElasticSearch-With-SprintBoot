package com.sky.elasticsearch.document;

import com.sky.elasticsearch.indices.Indices;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

@Document(indexName = Indices.PERSON_INDEX)
@Setting(settingPath = "static/es-settings.json")
public class Person {

    @Id
    @Field(type = FieldType.Keyword, name = "id")
    private String id;

    @Field(type = FieldType.Text, name = "name")
    private String name;

    public Person(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Person() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
