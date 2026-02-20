package com.github.denisdementevgithub.voteproject.common.to;



import io.swagger.v3.oas.annotations.media.Schema;
import com.github.denisdementevgithub.voteproject.common.HasId;

public abstract class BaseTo implements HasId {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Unique identifier of an entity in the database")
    protected Integer id;

    public BaseTo() {
    }

    public BaseTo(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }
}
