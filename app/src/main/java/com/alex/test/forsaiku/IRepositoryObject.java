package com.alex.test.forsaiku;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.List;


@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
@JsonSubTypes({ @JsonSubTypes.Type(value = RepositoryFolderObject.class, name = "folder"), @JsonSubTypes.Type(value =
        RepositoryFileObject
                .class, name =
        "file") })

public interface IRepositoryObject {

    enum Type {
        FOLDER, FILE;



    }

    Type getType();
    String getName();
    String getId();
    int getimage();
    List<IRepositoryObject> getRepoObjects();



}