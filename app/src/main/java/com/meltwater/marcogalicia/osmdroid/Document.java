
package com.meltwater;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Document {

    @SerializedName("documents")
    @Expose
    private List<Document_> documents = null;

    public List<Document_> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document_> documents) {
        this.documents = documents;
    }

}
