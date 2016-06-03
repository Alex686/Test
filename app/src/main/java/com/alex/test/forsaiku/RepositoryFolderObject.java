package com.alex.test.forsaiku;

import com.alex.test.R;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class RepositoryFolderObject implements IRepositoryObject {

    private Type type;
    private String name;
    private String id;
    private String path;
    private List<IRepositoryObject> repoObjects;
    private List<AclMethod> acl;
    private int image;

    public RepositoryFolderObject(String name, String id, String path, List<AclMethod> acl, List<IRepositoryObject> repoObjects) {
        this.type = Type.FOLDER;
        this.name = name;
        this.id = id;
        this.path = path;
        //this.repoObjects = repoObjects;
        this.repoObjects = repoObjects;
        this.acl = acl;
        this.image = image;

    }
    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    @Override
    public int getimage() {
        return R.drawable.folder;
    }

    public String getPath() {
        return path;
    }

    public List<AclMethod> getAcl() {
        return acl;
    }


    @JsonProperty
    public List<IRepositoryObject> getRepoObjects() {
        return repoObjects;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setRepoObjects(List<IRepositoryObject> repoObjects) {
        this.repoObjects = repoObjects;
    }

    public void setAcl(List<AclMethod> acl) {
        this.acl = acl;
    }

    public void setImage(int image) {
        this.image =image;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
